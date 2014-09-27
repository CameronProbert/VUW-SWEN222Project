package catgame.clientserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class NetworkSetUp {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;
	private int broadcastClock;
	private int gameClock;
	private int port = 32768; // default
	private static boolean readyToStart = false;
	private NetworkHandler game;
	
	private String url = null;	
	
	public NetworkSetUp(){
		broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		gameClock = DEFAULT_CLK_PERIOD;
	}

	public void setServer(){
		if(url != null) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		}
		// Run in Server mode
		game = new NetworkHandler(NetworkHandler.Type.SERVER);
		runServer(port,gameClock,broadcastClock, game);		
		// TODO start the server pane with a button that says ready!
		System.exit(0);
	}
	
	public void setSinglePlayer(){
		
		try {
			singleUserGame(gameClock);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setClient(String url){
		this.url = url;
		if(url != null) {
			// Run in client mode
			try {
				runClient(url,port);
			} catch (IOException e) {
				System.out.println("I/O error: " + e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
			
			System.exit(0);
		} 
	}

	private static void runClient(String addr, int port) throws IOException {		
		Socket s = new Socket(addr,port);
		System.out.println("CLIENT CONNECTED TO " + addr + ":" + port);			
		new Slave(s).run();		
	}

	private static void runServer(int port, int gameClock, int broadcastClock, NetworkHandler game) {

		ClockThread clk = new ClockThread(gameClock,game);	
		
		// Listen for connections
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("ERVER AWAITING CLIENTS");
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
				////////////////////////////////////////////////////////////////
				/////////////// HOW WILL WE KNOW WHEN TO START THE GAME????????????
				//////////////
				////////////////////////////////////////////////////////////////
				if(readyToStart) {
					if(nclients==0){
						System.out.println("No clients game over");
						return;
					}
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					multiUserGame(clk, game,connections);
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return; // done
				}
			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
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
		
		// loop forever
		while(atleastOneConnection(connections)) {
			game.setState(NetworkHandler.READY);
			pause(3000);
			game.setState(NetworkHandler.PLAYING);
			// now, wait for the game to finish
			while(game.state() == NetworkHandler.PLAYING) {
				Thread.yield();
			}
			// If we get here, then we're in game over mode
			pause(3000);
			// Reset board state
			game.setState(NetworkHandler.WAITING);
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

	private static void singleUserGame(int gameClock) throws IOException {
		SinglePlayerHandler game = new SinglePlayerHandler(1); // pass a uid, 1 does fine as only one player		
		// save initial state of board, so we can reset it.
		ClockThread clk = new ClockThread(gameClock,game);
		//byte[] state = game.toByteArray();	
		
		clk.start();

		while(game.isNotOver()) {
			// keep going until the frame becomes invisible
			game.setState(NetworkHandler.READY);
			pause(3000);
			game.setState(NetworkHandler.PLAYING);
			// now, wait for the game to finish
			while(game.state() == NetworkHandler.PLAYING) {
				Thread.yield();
			}
			// If we get here, then we're in game over mode
			pause(3000);
			// Reset board state
			//game.fromByteArray(state);
		}
	}
	
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
