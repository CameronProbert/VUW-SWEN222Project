package catgame.clientserver;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import catgame.datastorage.LoadOldGame;
import catgame.datastorage.LoadingGameMain;
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


	public SlaveReceiver(Slave slave, GameRunner net, FrameClient frame){
		this.slave = slave;
		this.net = (NetworkHandler)net;
		this.frame = frame;
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
				uid = input.readInt();	
				System.out.println("read in my uid : " + uid);
				/////////////////////////////////////////////////////////////////
				// LOAD the game
				////////////////////////////////////////////////////////////////
				String FILE_TO_RECEIVED = "files/Load_From.xml";
				URL fileURL = LoadingGameMain.class.getResource(FILE_TO_RECEIVED);
				File file = null;
				try {
					file = new File(fileURL.toURI());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				if(file==null){
					throw new FileNotSentError();
				}

				int FILE_SIZE = input.readInt();
				if(FILE_SIZE!=0){
					// receive file
					System.out.println("received file size : " + FILE_SIZE);
					byte[] data = new byte[FILE_SIZE];
					input.readFully(data);	
	
					 //convert array of bytes into file
				    FileOutputStream fileOuputStream = 
			                  new FileOutputStream(file); 
				    fileOuputStream.write(data);
				    fileOuputStream.close();
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
				if(frame == null){
					System.out.println("Frame is null");
				}
				frame.startMyClient(uid);

				while(locked){
					recieveMassUpdate(input);
					frame.repaint();
					try {
						Thread.sleep(50);
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
	 * deals with receiving a mass update from the server
	 * @param input
	 */
	private void recieveMassUpdate(DataInputStream input) {
		try {
			ReceiveMassUpdate receiver = new ReceiveMassUpdate(input, net.getBoardData());
			int noChars = input.readInt();

			for(int i=0; i<noChars; i++){
				receiver.readPlayer();
			}
			

			int noNCPs = input.readInt();

			for(int i=0; i<noNCPs; i++){
				receiver.readNonPlayChar();
			}
			

			int noChests = input.readInt();

			for(int i=0; i<noChests; i++){
				receiver.readChest();
			}
			
			
			int noDoors = input.readInt();
			
			for(int i=0; i<noDoors; i++){
				receiver.readDoor();
			}


		} catch (IOException e) {
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
		if(frame == null){
			System.out.println("In AddFrame. Frame is null");
		}
		this.frame=frame;
	}

}
