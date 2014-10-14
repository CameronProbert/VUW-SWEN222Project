package catgame.clientserver;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import catgame.datastorage.LoadOldGame;
import catgame.datastorage.XMLException;
import catgame.gameObjects.Chest;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;
import catgame.gamestarting.GameRunner;
import catgame.gamestarting.NetworkHandler;
import catgame.gui.FrameClient;

/**
 * receives updates from the server (from its master connection)
 * and updates the local game state
 * @author Francine
 *
 */
public class SlaveReceiver {

	private boolean locked = true;
	private Slave slave;
	private int uid;
	private NetworkHandler net;
	private final int MASSUPDATE = 35;
	private final int MINORUPDATE = 30;
	private boolean readyToStart = false;
	private FrameClient frame;


	public SlaveReceiver(Slave slave, GameRunner net){
		this.slave = slave;
		this.net = (NetworkHandler)net;
	}

	public void run(){
		new Thread(r).start();
	}

	/**
	 * Threader for this class, setting it up like this allows it to be run in the background
	 * so as to not interfere with the drawing of the game
	 */
	Runnable r = new Runnable(){
		public void run() {
			Socket s = slave.getSocket();
			DataInputStream input;
			try {
				input = new DataInputStream(s.getInputStream());

				// First job, is to read the period so we can create the clock				
				uid = (int)input.readDouble();	
				System.out.println("read in my uid : " + uid);
				/////////////////////////////////////////////////////////////////
				// LOAD the game
				////////////////////////////////////////////////////////////////
				String FILE_TO_RECEIVED = "Load_From.xml";
				File file = new File(FILE_TO_RECEIVED);

				int FILE_SIZE = input.readInt() + 100;
				if(FILE_SIZE!=0){
					// receive file
					System.out.println("received file size : " + FILE_SIZE);
					byte [] mybytearray  = new byte [FILE_SIZE];
					FileOutputStream fos = new FileOutputStream(file);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					int bytesRead = input.read(mybytearray, 0, mybytearray.length);
				    bos.write(mybytearray, 0, bytesRead);
				    bos.close();
				    System.out.println("finished reading");
					try {
						LoadOldGame loadXML = new LoadOldGame(file);
						net.setBoardData(loadXML.getBoardData());
					} catch (JDOMException | XMLException e) {
						e.printStackTrace();
					}
				}
				else{
					throw new FileNotSentError();
				}

				readyToStart=true; // now the players can start trying to do things
				frame.startMyClient();

				while(locked){
					workOutUpdate(input);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	/**
	 * works out if the incoming message is a normal update or a mass update
	 * @param input
	 */
	private void workOutUpdate(DataInputStream input){

		try {

			double todo = input.readDouble();
			if(todo==MINORUPDATE){
				System.out.println("received 30");
				recieveUpdate(input);
			}
			else if (todo==MASSUPDATE){
				System.out.println("received 35");
				recieveMassUpdate(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * deals with receiving a mass update from the server
	 * @param input
	 */
	private void recieveMassUpdate(DataInputStream input) {
		try {
			ReceiveMassUpdate receiver = new ReceiveMassUpdate(input, net.getBoardData().getGameUtil());

			double noChars = input.readDouble();

			for(int i=0; i<noChars; i++){
				receiver.readPlayer();
			}

			double noNCPs = input.readDouble();

			for(int i=0; i<noNCPs; i++){
				receiver.readPlayer();
			}

			double noChests = input.readDouble();

			for(int i=0; i<noChests; i++){
				receiver.readChest();
			}

			double noItems = input.readDouble();

			for(int i=0; i<noItems; i++){
				receiver.readItem();
			}


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * handles receiving a minor update from the server
	 * @param input
	 */
	private void recieveUpdate(DataInputStream input) {
		System.out.println("still running");
		try {
			double updateFromMaster = input.readDouble();
			if(updateFromMaster == Update.UN_PAUSE_STATE){
				frame.setState("running");
			}
			else if(updateFromMaster == Update.PAUSE_STATE){
				frame.setState("paused");
			}
			else if(updateFromMaster!=0){
				net.update(new Update(updateFromMaster), false);// will not record last update
				System.out.println("update recieved to actually use");
			}
			System.out.printf("received update from the server : %f\n", updateFromMaster);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	



	}

	/**
	 * can stop the infinite loop
	 */
	public void unlock(){
		this.locked=false;
	}

	public int getUID() {
		return uid;
	}

	public boolean isReady(){
		return readyToStart;
	}

	public void addFrame(FrameClient frame) {
		this.frame=frame;
	}

}
