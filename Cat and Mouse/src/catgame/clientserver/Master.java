package catgame.clientserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import catgame.gameObjects.Chest;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;
import catgame.logic.ObjectStorer;

public final class Master extends Thread {


	private final int MASSUPDATE = 35;
	private final int MINORUPDATE = 30;


	private final NetworkHandler game;
	private final int broadcastClock;
	private final int uid;
	private final Socket socket;
	private Update lastUpdateSent = new Update(0); // this keeps track of the last update sent
	private Update lastUpdateReceived = new Update(0);
	private int number = 0;
	private boolean testing = false; // true when testing
	private int timer = 0;

	private final static int TIMESUP = 10; // when timer reaches TIMESUP massive update to system 

	public Master(Socket socket, int uid, int broadcastClock, NetworkHandler game) {
		this.game = game;	
		this.broadcastClock = broadcastClock + 1000;
		this.socket = socket;
		this.uid = uid;
	}

	public void run() {		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			writeStart(output);

			boolean exit=false;
			while(!exit) {
				try {

					if(timer==TIMESUP){
						timer=0;
						output.writeInt(MASSUPDATE);
						broadcastGameState(output);
					}
					else{

						output.writeInt(MINORUPDATE);


						// this will read the last update from the slave
						if(input.available()!=0){
							int updateFromSlave = input.readInt(); 
							if(updateFromSlave != 0){
								game.update(new Update(updateFromSlave), true);
								this.lastUpdateReceived = new Update(updateFromSlave);
								System.out.printf("\n\nMy clients uid is : %d and I had a non zero update\n", uid);
								System.out.println("latest update to the game was, just after reupdating! : " + updateFromSlave + "\n\n");
							}else{
								System.out.printf("\n\nMy clients uid is : %d and I had a zero update\n\n", uid);
							}
						}


						// Now, broadcast the latest update of the board to client

						Update updateToSlave = game.getLatestUpdate();

						if(updateToSlave.equals(lastUpdateReceived)){
							output.writeInt(0); // writes a 'no update'
							System.out.printf("\n\nMy clients uid is : %d and I had a nothing to update\n", uid);
							System.out.printf("the games latest update is : %d\n", updateToSlave.getCode());
							System.out.printf("the masters last update received was : %d\n\n", lastUpdateReceived.getCode());
						}
						else{
							output.writeInt(updateToSlave.getCode()); 
							System.out.printf("\n\nMy clients uid is : %d and I have SOMETHING to update\n", uid);
							System.out.printf("the games latest update is : %d\n\n", updateToSlave.getCode());
						}

						timer++;

					}
					output.flush();
					Thread.sleep(broadcastClock);
				} catch(InterruptedException e) {					
				}
			}

			socket.close(); // release socket ... v.important!
		} catch(IOException e) {
			System.err.println("PLAYER " + uid + " DISCONNECTED");
			game.disconnectPlayer(uid);
		}		
	}

	private void broadcastGameState(DataOutputStream output) {
		//for each player send update
		//for each non playable character send update
		//for each chest
		//for each item
		BroadcastMessage broadcast = new BroadcastMessage(output);
		try {
			ObjectStorer storer = game.getGameUtill().getStorer();
			
			int noChars = storer.getNumChars();
			output.writeInt(noChars);
			for(int i : storer.getCharIDs()){
				PlayableCharacter c = storer.findCharacter(i);
				broadcast.sendCharacter(i, c);
			}
			
			int noNCPs = storer.getNumNCPs();
			output.writeInt(noNCPs);
			for(int i: storer.getNCPIDs()){
				NonPlayableCharacter nc = storer.findNCP(i);
				broadcast.sendCharacter(i, nc);
			}
			
			int noChests = storer.getNumChests();
			output.writeInt(noChests);
			for(int i: storer.getChestIDs()){
				Chest chest = storer.findChest(i);
				broadcast.sendChest(i, chest);
			}
			
			int noItems = storer.getNumItems();
			output.writeInt(noItems);
			for(int i: storer.getItemIDs()){
				GameItem item = storer.findItem(i);
				broadcast.sendItem(i, item);
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	public int getNumber(){
		return number;
	}

	public void writeStart(DataOutputStream output){
		// First, give the client its uid
		try {
			output.writeInt(uid);
			System.out.println("wrote uid to client");

			//then give it all the players IDs
			int noPlayers = game.noPlayers();
			output.writeInt(noPlayers);
			System.out.println("wrote no players to client");

			for(int id : game.getPlayerIds()){
				output.writeInt(id);
				System.out.println("writing player ids to client");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
