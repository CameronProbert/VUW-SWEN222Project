package catgame.clientserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Master extends Thread {
	

	private final GameMain game;
	private final int broadcastClock;
	private final int uid;
	private final Socket socket;
	private int lastUpdateSent = 0; // this keeps track of the last update sent

	public Master(Socket socket, int uid, int broadcastClock, GameMain game) {
		this.game = game;	
		this.broadcastClock = broadcastClock;
		this.socket = socket;
		this.uid = uid;
	}
	
	public void run() {		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			// First, give the client its uid
			output.writeInt(uid);
			
			
			boolean exit=false;
			while(!exit) {
				try {
					
					int updateFromSlave = input.readInt(); // this will read the last update from the slave
					if(updateFromSlave != 0){
						game.update(updateFromSlave, true);
					}
					
					if(input.available() != 0) { // if a key was pressed and a message was being sent from the slave
						
						updateFromSlave = input.readInt();
						if(updateFromSlave!=0){
							game.update(updateFromSlave, true);
						}
					} 
					
					// Now, broadcast the latest update of the board to client
					
					int updateToSlave = game.getLatestUpdate();
					if(updateToSlave == this.lastUpdateSent){
						output.writeInt(0); // writes a 'no update'
					}
					else{
						output.writeInt(updateToSlave); // will record last update in game
						this.lastUpdateSent = updateToSlave;
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

}
