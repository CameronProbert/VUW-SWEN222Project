package catgame.clientserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



import catgame.gui.ClientFrame;

public final class Slave {


	private final Socket socket;
	private DataOutputStream output;

	/**
	 * Construct a slave connection from a socket. A slave connection does no
	 * local computation, other than to display the current state of the board;
	 * instead, board logic is controlled entirely by the server, and the slave
	 * display is only refreshed when data is received from the master
	 * connection.
	 * 
	 * @param socket
	 * @param dumbTerminal
	 */
	public Slave(Socket socket) {				
		this.socket = socket;	
		System.out.println("made slave");
	}

	public void sendUpdate(Update update){
		try {
			output = new DataOutputStream(socket.getOutputStream());
			output.writeInt(update.getCode());
			System.out.printf("writing update to client : %d\n", update.getCode());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Socket getSocket(){
		return this.socket;
	}




}
