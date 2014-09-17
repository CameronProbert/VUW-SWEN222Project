package catgame.clientserver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Slave extends Thread implements KeyListener{


	private final Socket socket;
	private GameMain game;	
	private DataOutputStream output;
	private DataInputStream input;
	private int uid;
	private int totalSent;
	private int number;
	
	private int lastSentUpdate;

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
		//TODO this will need a key listener 
	}

	public void run() {
		try {			
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());

			// First job, is to read the period so we can create the clock				
			uid = input.readInt();		
			
			// now make new game for the client
			//TODO set up a new game for the server given the uid
			game = new GameMain();
			game.setGameType(GameMain.Type.CLIENT);
			game.addClientPlayer();

			boolean exit=false;
			long totalRec = 0;

			while(!exit) {
				
				// read event
				//////////////////////////
				int updateFromMaster = input.readInt();
				if(updateFromMaster!=0){
					game.update(updateFromMaster, false);// will not record last update
				}
				
				//write event
				//////////////////////////
				int updateToMaster = game.getLatestUpdate();
				if (updateToMaster!= this.lastSentUpdate){
					output.writeInt(updateToMaster);
					lastSentUpdate = updateToMaster;
				}
				else{
					output.writeInt(0);
				}
				
				// TODO may need a repaint method here

			}
			socket.close(); // release socket ... v.important!
		} catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	/**
	 * The following method calculates the rate of data received in bytes/s, albeit
	 * in a rather coarse manner.
	 * 
	 * @param amount
	 * @return
	 */
	private int rate(int amount) {
		rateTotal += amount;
		long time = System.currentTimeMillis();
		long period = time - rateStart;		
		if(period > 1000) {
			// more than a second since last calculation
			currentRate = (rateTotal * 1000) / (int) period;
			rateStart = time;
			rateTotal = 0;
		}

		return currentRate;		
	}
	private int rateTotal = 0;   // total accumulated this second
	private int currentRate = 0; // rate of reception last second
	private long rateStart = System.currentTimeMillis();  // start of this accumulation perioud 

	// The following intercept keyboard events from the user.

	public void keyPressed(KeyEvent e) {		
		try {
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {													
				output.writeInt(3);
				totalSent += 4;
			} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {				
				output.writeInt(4);
				totalSent += 4;
			} else if(code == KeyEvent.VK_UP) {				
				output.writeInt(1);
				totalSent += 4;
			} else if(code == KeyEvent.VK_DOWN) {						
				output.writeInt(2);
				totalSent += 4;
			}
			//TODO if the code is 'e, r, q' etc it needs to listen for it
			output.flush();
		} catch(IOException ioe) {
			// something went wrong trying to communicate the key press to the
			// server.  So, we just ignore it.
		}
	}

	public int getNumber(){
		return number;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
