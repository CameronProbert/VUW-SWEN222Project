package catgame.clientserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import pacman.control.ClockThread;



public class Main {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;
	private int broadcastClock;
	private int gameClock;
	private int port = 32768; // default
	private boolean server = false;
	private int nclients = 0;
	private GameMain game;
	
	private String url = null;	
	
	public Main(){
		broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		gameClock = DEFAULT_CLK_PERIOD;
	}

	public void setServer(){
		server = true;
		runMain();
	}

	public void setClient(String url){
		this.url = url;
		runMain();
	}

	public void runMain() {
		if(url != null && server) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		}

		try {
			if(server) {
				// Run in Server mode
				game = new GameMain();
				runServer(port,gameClock,broadcastClock, game);			
			} else if(url != null) {
				// Run in client mode
				runClient(url,port);
			} else {			
				// single user game
				game = new GameMain();
				singleUserGame(gameClock, game);							
			}
		} catch(IOException ioe) {			
			System.out.println("I/O error: " + ioe.getMessage());
			ioe.printStackTrace();
			System.exit(1);
		}

		System.exit(0);
	}

	private static void runClient(String addr, int port) throws IOException {		
		Socket s = new Socket(addr,port);
		System.out.println("CLIENT CONNECTED TO " + addr + ":" + port);			
		new Slave(s).run();		
	}

	private static void runServer(int port, int gameClock, int broadcastClock, GameMain game) {

		ClockThread clk = new ClockThread(gameClock,game,null);	
		
		// Listen for connections
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("ERVER AWAITING CLIENTS");
		try {
			List<Master> connections = new ArrayList<Master>();
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);			
			while (true) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());				
				int uid = game.registerPlayer();
				Master mconn = new Master(s,uid,broadcastClock,game);
				connections.add(mconn);
				mconn.start();
				////////////////////////////////////////////////////////////////
				/////////////// HOW WILL WE KNOW WHEN TO START THE GAME????????????
				//////////////
				////////////////////////////////////////////////////////////////
				if(nclients == 0) {
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
	private static void multiUserGame(ClockThread clk, GameMain game,
			List<Master> connections) throws IOException {
		// save initial state of board, so we can reset it.
		byte[] state = game.toByteArray();						

		clk.start();
		
		// loop forever
		while(atleastOneConnection(connections)) {
			game.setState(Board.READY);
			pause(3000);
			game.setState(Board.PLAYING);
			// now, wait for the game to finish
			while(game.state() == Board.PLAYING) {
				Thread.yield();
			}
			// If we get here, then we're in game over mode
			pause(3000);
			// Reset board state
			game.setState(Board.WAITING);
			game.fromByteArray(state);			
		}
	}

	/**
	 * Check whether or not there is at least one connection alive.
	 * 
	 * @param connections
	 * @return
	 */
	private static boolean atleastOneConnection(Master... connections) {
		for (Master m : connections) {
			if (m.isAlive()) {
				return true;
			}			
		}
		return false;
	}

	private static void singleUserGame(int gameClock, GameMain game) throws IOException {
		int playerID = game.registerPlayer();
		game.setupSinglePlayer(playerID);		
		// save initial state of board, so we can reset it.
		ClockThread clk = new ClockThread(gameClock,game,display);
		byte[] state = game.toByteArray();	
		
		clk.start();

		while(game.isNotOver()) {
			// keep going until the frame becomes invisible
			game.setState(Board.READY);
			pause(3000);
			game.setState(Board.PLAYING);
			// now, wait for the game to finish
			while(game.state() == Board.PLAYING) {
				Thread.yield();
			}
			// If we get here, then we're in game over mode
			pause(3000);
			// Reset board state
			game.fromByteArray(state);
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
