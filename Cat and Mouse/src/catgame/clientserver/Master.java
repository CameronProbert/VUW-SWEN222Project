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
			// First, write the period to the stream				
			output.writeInt(uid);
			boolean exit=false;
			while(!exit) {
				try {
					
					if(input.available() != 0) {
						
						// read direction event from client.
						int dir = input.readInt();
						switch(dir) {
							case 1:
								game.player(uid).moveUp();
								break;
							case 2:
								game.player(uid).moveDown();
								break;
							case 3:
								game.player(uid).moveRight();
								break;
							case 4:
								game.player(uid).moveLeft();
								break;
						}
					} 
					
					// Now, broadcast the state of the board to client
					byte[] state = game.toByteArray(); 
					output.writeInt(state.length);
					output.write(state);
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
