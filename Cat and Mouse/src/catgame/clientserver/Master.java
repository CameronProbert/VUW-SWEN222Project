package catgame.clientserver;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import catgame.gameObjects.Chest;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;
import catgame.gamestarting.NetworkHandler;
import catgame.logic.ObjectStorer;

/**
 * This is the thread that runs at the server end,
 * it is constantly checking for updates and every clock tick writes the
 * latest update out to the client it is connected to over the socket
 * 
 * Also sends out a mass update periodically (less frequent than other updates)
 * 
 * @author Francine
 *
 */
public final class Master extends Thread {


	private final int MASSUPDATE = 35;
	private final int MINORUPDATE = 30;


	private final NetworkHandler game;
	private final int broadcastClock;
	private int uid;
	private final Socket socket;
	private int timer = 0;
	private boolean canStart = false;
	private File file;

	private final static int TIMESUP = 0; // when timer reaches TIMESUP massive update to system 

	public Master(Socket socket, int broadcastClock, NetworkHandler game) {
		this.game = game;	
		this.broadcastClock = 1000;
		this.socket = socket;
	}

	public void run() {		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			System.out.println("about to write start");
			writeStart(input, output);

			boolean exit=false;
			while(!exit) {
				try {

					if(timer==TIMESUP){
						timer=0;
						output.writeInt(MASSUPDATE);
						broadcastGameState(output);
					}
					else{/*

						output.writeInt(MINORUPDATE);


						// this will read the last update from the slave
						if(input.available()!=0){
							Update update = new Update(input);
							if(update.getInst() != 0){
								System.out.printf("\n\nMy clients uid is : %d and I had a non zero update\n", uid);
								System.out.println("latest update to the game was, just after reupdating! : " + update.toString() );
								game.update(update, true);
								this.lastUpdateReceived = update;
							}else{
								System.out.printf("\n\nMy clients uid is : %d and I had a zero update\n\n", uid);
							}
						}


						// Now, broadcast the latest update of the board to client

						Update updateToSlave = game.getLatestUpdate();

						if(updateToSlave.equals(lastUpdateReceived)){
							updateToSlave = Update.noUpdate;
							updateToSlave.send(output);
							System.out.printf("\n\nMy clients uid is : %d and I had a nothing to update\n", uid);
							System.out.printf("the games latest update is : %f\n", updateToSlave.toString());
							System.out.printf("the masters last update received was : %f\n\n", lastUpdateReceived.toString());
						}
						else{
							updateToSlave.send(output); 
							System.out.printf("\n\nMy clients uid is : %d and I have SOMETHING to update\n", uid);
							System.out.printf("the games latest update is : %f\n\n", updateToSlave.toString());
						}

						timer++;
*/
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

	/**
	 * Handles the mass update, updates every object in the game (other than rooms)
	 * 
	 * @param output
	 */
	private void broadcastGameState(DataOutputStream output) {
		//for each player send update
		//for each non playable character send update
		//for each chest
		//for each item
		SendMassUpdate broadcast = new SendMassUpdate(output);
		try {
			ObjectStorer storer = game.getBoardData().getObjStorer();

			int noChars = storer.getNumChars();
			output.writeInt(noChars);
			for(int i : storer.getCharIDs()){
				PlayableCharacter c = storer.findCharacter(i);
				broadcast.sendCharacter(i, c, game.getBoardData());
			}

			int noNCPs = storer.getNumNCPs();
			output.writeInt(noNCPs);
			for(int i: storer.getNCPIDs()){
				NonPlayableCharacter nc = storer.findNCP(i);
				broadcast.sendCharacter(i, nc, game.getBoardData());
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

	/**
	 * first messages sent, telling the slave the more important information
	 * @param output
	 */
	public void writeStart(DataInputStream input, DataOutputStream output){
		try {
			boolean hasStarted = false;
			while(!hasStarted){
				if(canStart){
					output.writeInt(uid);
					
					System.out.println("got uid : " + uid);
					
					if(file==null){
						output.writeInt(0);
						System.out.println("File was null");
						return;
					}
					
					output.writeInt((int)file.length());

					// send file
					
					byte [] mybytearray  = new byte [(int)file.length()];
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis);
					bis.read(mybytearray,0,mybytearray.length);
					System.out.println("Sending " + file + "(" + mybytearray.length + " bytes)");
					output.write(mybytearray,0,mybytearray.length);
					output.flush();
					System.out.println("Done.");



					hasStarted = true;
				}
				else{
					System.out.printf(""); // if I don't have this it go break
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStart(boolean b) {
		this.canStart = b;
		System.out.println("\nI have been allowed to start, my id is : " + uid + "\n");
	}

	public void setUID(int uid){
		this.uid = uid;
	}

	public void setFile(File file){
		this.file = file;
	}

}
