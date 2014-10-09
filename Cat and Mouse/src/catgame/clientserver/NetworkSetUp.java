package catgame.clientserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import catgame.dataStorage.LoadingGameMain;
import catgame.dataStorage.XMLException;
import catgame.gui.ClientFrame;


/**
 * handles the setup of the server and singleplayer
 * handles sockets and also manages whether the game is over or not
 * @author Francine
 *
 */
public class NetworkSetUp extends Thread {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;
	private int broadcastClock;
	private int gameClock;
	private int port = 32768; // default
	private static boolean readyToStart = false;
	private NetworkHandler game;
	private static int maxPlayers;
	
	private String url = null;	
	
	public NetworkSetUp(){
		broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		gameClock = DEFAULT_CLK_PERIOD;
	}

	/**
	 * sets up the server given a number of players
	 * @param numPlayers
	 */
	public void setServer(int numPlayers){
		maxPlayers = numPlayers;
		if(url != null) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		}
		// Run in Server mode
		game = new NetworkHandler(NetworkHandler.Type.SERVER);
		start();		
	}
	
	/**
	 * sets the single player, may be obsolete may only need to make a singleplayer handler
	 * 
	 */
	public void setSinglePlayer(){
		
		try {
			singleUserGame(gameClock);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * accepts the clients "slave" and makes a master connection for them 
	 */
	public void run() {

		ClockThread clk = new ClockThread(gameClock,game);	
		
		// Listen for connections
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("SERVER AWAITING CLIENTS");
		try {
			List<Master> connections = new ArrayList<Master>();
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);
			int nclients = 0;
			while (true) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());				
				int uid = game.registerPlayer();
				Master mconn = new Master(s,uid,broadcastClock,game);
				connections.add(mconn);
				nclients++;
				mconn.start();
				System.out.println("\n joined a master to slave\n");
				if(nclients == maxPlayers) {
					if(nclients==0){
						System.out.println("No clients game over");
						return;
					}
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					allowMastersStart(connections);
					multiUserGame(clk, game,connections);
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return; // done
				}
			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		} 
	}

	private void allowMastersStart(List<Master> connections) {
		for(Master m : connections){
			m.setStart(true);
		}
	}

	/**
	 * The following method controls a multi-user game. When a given game is
	 * over, it will simply restart the game with whatever players are
	 * remaining. However, if all players have disconnected then it will stop.
	 * 
	 * @param clk
	 * @param game
	 * @param connections
	 * @throws IOException
	 */
	private static void multiUserGame(ClockThread clk, NetworkHandler game,
			List<Master> connections) throws IOException {
		// save initial state of board, so we can reset it.
		//byte[] state = game.toByteArray();						

		clk.start();
		/*try {
			LoadingGameMain loadMain = new LoadingGameMain(game.getPlayerIds());
		} catch (JDOMException | XMLException e) {
			e.printStackTrace();
		}*/
		
		// loop forever
		while(atleastOneConnection(connections)) {
			game.setState(GameRunner.GameState.READY);
			pause(3000);
			game.setState(GameRunner.GameState.PLAYING);
			// now, wait for the game to finish
			while(game.state() == GameRunner.GameState.PLAYING) {
				Thread.yield();
			}
			// If we get here, then we're in game over mode
			pause(3000);
			// Reset board state
			game.setState(GameRunner.GameState.WAITING);
			//game.fromByteArray(state);			
		}
	}

	/**
	 * Check whether or not there is at least one connection alive.
	 * 
	 * @param connections
	 * @return
	 */
	private static boolean atleastOneConnection(List<Master> connections) {
		for (Master m : connections) {
			if (m.isAlive()) {
				return true;
			}			
		}
		return false;
	}

	/**
	 * similar to multiplayer except only one user, checks whether game is over or not
	 * @param gameClock
	 * @throws IOException
	 */
	private static void singleUserGame(int gameClock) throws IOException {
		SinglePlayerHandler game = new SinglePlayerHandler(); 		
		// save initial state of board, so we can reset it.
		ClockThread clk = new ClockThread(gameClock,game);
		//byte[] state = game.toByteArray();	
		
		clk.start();

		while(game.isNotOver()) {
			// keep going until the frame becomes invisible
			game.setState(GameRunner.GameState.READY);
			pause(3000);
			game.setState(GameRunner.GameState.PLAYING);
			// now, wait for the game to finish
			while(game.state() == GameRunner.GameState.PLAYING) {
				Thread.yield();
			}
			// If we get here, then we're in game over mode
			pause(3000);
			// Reset board state
			//game.fromByteArray(state);
		}
	}
	
	/**
	 * activate by a button press may be obsolete
	 */
	public void readyToStart(){
		this.readyToStart = true;
	}
	
	private static void pause(int delay) {
		try {
			Thread.sleep(delay);
		} catch(InterruptedException e){			
		}
	}


	// The following two bits of code are a bit sneaky, but they help make the
	// problems more visible.
	static {
		System.setProperty("sun.awt.exception.handler", "servermain.Main");
	}

	public void handle(Throwable ex) {
		try {
			ex.printStackTrace();
			System.exit(1); } 
		catch(Throwable t) {}
	}

}
