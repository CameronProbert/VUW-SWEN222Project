package catgame.clientserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import catgame.gameObjects.Chest;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;
import catgame.gui.ClientFrame;

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
	private ClientFrame frame;

	public SlaveReceiver(Slave slave, GameRunner net, ClientFrame frame){
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
				uid = (int)input.readDouble();		
				System.out.println("read uid from server");
				
				net.addClientPlayer(uid);
				
				double noPlayers = input.readDouble();
				System.out.println("reading noPlayers from server");
				List<Integer> playerIds = new ArrayList<Integer>();

				for(; noPlayers>0; noPlayers--){
					playerIds.add((int)input.readDouble());
					System.out.println("reading player ids from server");
				}
				/////////////////////////////////////////////////////////////////
				// LOAD the game
				////////////////////////////////////////////////////////////////
				frame.startXMLFiles(playerIds);
				frame.repaint();
				net.setPlayerIds(playerIds); //  may be obsolete 
				readyToStart=true; // now the players can start trying to do things

				while(locked){
					workOutUpdate(input);
					frame.repaint();
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
				//recieveMassUpdate(input);
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
			ReceiveMessage receiver = new ReceiveMessage(input, net.getGameUtill());

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
			if(updateFromMaster!=0){
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

}
