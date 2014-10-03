package catgame.clientserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public final class Master extends Thread {


	private final int MASSUPDATE = 35;
	private final int MINORUPDATE = 30;


	private final NetworkHandler game;
	private final int broadcastClock;
	private final int uid;
	private final Socket socket;
	private Update lastUpdateSent = null; // this keeps track of the last update sent
	private int number = 0;
	private boolean testing = false; // true when testing
	private int timer = 0;

	private final static int TIMESUP = 10; // when timer reaches TIMESUP massive update to system 

	public Master(Socket socket, int uid, int broadcastClock, NetworkHandler game) {
		this.game = game;	
		this.broadcastClock = broadcastClock + 2000;
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
						System.out.printf("My clients uid is : %d\n", uid);

						// this will read the last update from the slave
						if(input.available()!=0){
							int updateFromSlave = input.readInt(); 
							if(updateFromSlave != 0){
								game.update(new Update(updateFromSlave), true);
								System.out.printf("recieved update from client : %d\n", updateFromSlave);
							}
						}

						// Now, broadcast the latest update of the board to client

						Update updateToSlave = game.getLatestUpdate();
						if(this.lastUpdateSent==null || updateToSlave.equals(this.lastUpdateSent)){
							output.writeInt(0); // writes a 'no update'
							System.out.printf("writing update to client from server : %d\n", 0);
						}
						else{
							output.writeInt(updateToSlave.getCode()); // will record last update in game
							this.lastUpdateSent = updateToSlave;
							System.out.printf("writing update to client from server : %d\n", updateToSlave);
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
