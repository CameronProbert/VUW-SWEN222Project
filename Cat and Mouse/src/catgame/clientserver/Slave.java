package catgame.clientserver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import catgame.GameObjects.PlayableCharacter;
import catgame.gui.ClientFrame;

public class Slave extends Thread {


	private final Socket socket;
	private NetworkHandler game;	
	private DataOutputStream output;
	private DataInputStream input;
	private int uid;
	private int totalSent;
	private List<Integer> numbers = new ArrayList<Integer>() ;
	private boolean testing = true; //turned true when I am testing

	private Update lastSentUpdate;

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
	}

	public void run() {
		try {		

			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
			if(!testing){
				
				// First job, is to read the period so we can create the clock				
				uid = input.readInt();		

				// now make new game for the client
				game = new NetworkHandler(NetworkHandler.Type.CLIENT);
				game.addClientPlayer(uid);

				// now read the other players IDs
				int noPlayers = input.readInt();
				List<Integer> playerIds = new ArrayList<Integer>();

				for(; noPlayers>0; noPlayers--){
					playerIds.add(input.readInt());
				}

				game.setPlayerIds(playerIds);
				
				PlayableCharacter ch = game.getGameUtill().findCharacter(uid);
				
				ClientFrame frame = new ClientFrame(game, uid, true, ch);
				// TODO set up client panel and pass it the networkHandler and the networkhandlers gamemmain
				// TODO make sure a player CANNOT DO ANYTHING unless the 'game' is set to PLAYING

				boolean exit=false;
				long totalRec = 0;

				while(!exit) {

					// read event
					//////////////////////////
					int updateFromMaster = input.readInt();
					if(updateFromMaster!=0){
						game.update(new Update(updateFromMaster), false);// will not record last update
					}

					//write event
					//////////////////////////
					Update updateToMaster = game.getLatestUpdate();
					if (this.lastSentUpdate!=null && !updateToMaster.equals(this.lastSentUpdate)){
						output.writeInt(updateToMaster.getCode());
						lastSentUpdate = updateToMaster;
					}
					else{
						output.writeInt(0);
					}

					// TODO may need a repaint method here

				}
			}
			else{
				// First job, is to read the period so we can create the clock				
				uid = input.readInt();		

				// now read the other players IDs
				int noPlayers = input.readInt();
				List<Integer> playerIds = new ArrayList<Integer>();

				for(; noPlayers>0; noPlayers--){
					playerIds.add(input.readInt());
				}
				
				numbers = playerIds;
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

	public List<Integer> getNumbers (){
		return numbers;
	}
	
	public int getUID(){
		return uid;
	}




}
