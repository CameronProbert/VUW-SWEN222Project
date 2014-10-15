package catgame.clientserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;




/**
 * Sends update across socket to master everytime he user successfully makes a change to their game state
 * @author Francine
 *
 */
public final class Slave {


	private final Socket socket;
	private DataOutputStream output;

	/**
	 * Construct a slave connection from a socket. 
	 * 
	 * @param socket
	 * @param dumbTerminal
	 */
	public Slave(Socket socket) {				
		this.socket = socket;	
		System.out.println("made slave");
	}

	/**
	 * dumb method, takes the update and passes it on
	 * @param update
	 */
	public void sendUpdate(Update update){
		try {
			output = new DataOutputStream(socket.getOutputStream());
			update.send(output);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Socket getSocket(){
		return this.socket;
	}




}
