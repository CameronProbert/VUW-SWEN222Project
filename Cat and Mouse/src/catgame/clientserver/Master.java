package catgame.clientserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public final class Master extends Thread {


	private final NetworkHandler game;
	private final int broadcastClock;
	private final int uid;
	private final Socket socket;
	private Update lastUpdateSent = null; // this keeps track of the last update sent
	private int number = 0;
	private boolean testing = true; // true when testing
	private int timer = 0;

	private final static int TIMESUP = 10; // when timer reaches TIMESUP massive update to system 

	public Master(Socket socket, int uid, int broadcastClock, NetworkHandler game) {
		this.game = game;	
		this.broadcastClock = broadcastClock;
		this.socket = socket;
		this.uid = uid;
	}

	public void run() {		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			if(!testing){
				// First, give the client its uid
				output.writeInt(uid);
				//then give it all the players IDs
				int noPlayers = game.noPlayers();
				output.writeInt(noPlayers);

				for(int id : game.getPlayerIds()){
					output.writeInt(id);
				}



				boolean exit=false;
				while(!exit) {
					try {

						// this will read the last update from the slave

						int updateFromSlave = input.readInt(); 
						if(updateFromSlave != 0){
							game.update(new Update(updateFromSlave), true);
						}

						// Now, broadcast the latest update of the board to client

						Update updateToSlave = game.getLatestUpdate();
						if(this.lastUpdateSent==null || updateToSlave.equals(this.lastUpdateSent)){
							output.writeInt(0); // writes a 'no update'
						}
						else{
							output.writeInt(updateToSlave.getCode()); // will record last update in game
							this.lastUpdateSent = updateToSlave;
						}

						if(timer==TIMESUP){
							timer=0;
							broadcastGameState(output);
						}

						timer++;
						output.flush();
						Thread.sleep(broadcastClock);
					} catch(InterruptedException e) {					
					}
				}
			}
			else{
				while(true){
					output.writeInt(30);
					// First, give the client its uid
					output.writeInt(uid);
					//then give it all the players IDs
					int noPlayers = 5;
					output.writeInt(noPlayers);

					for(int i=0; i<noPlayers; i++){
						output.writeInt(i);
					}
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

}
