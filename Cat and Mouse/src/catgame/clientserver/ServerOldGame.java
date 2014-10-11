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
import catgame.logic.BoardData;


/**
 * handles the setup of the server and singleplayer
 * handles sockets and also manages whether the game is over or not
 * @author Francine
 *
 */
public class ServerOldGame extends Thread {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;
	private int broadcastClock;
	private int gameClock;
	private int port = 32768; // default
	private static boolean readyToStart = false;
	private NetworkHandler handler;
	private static int maxPlayers;
	private String fileName;
	private List<Integer> playerIDs;
	private BoardData boardData;

	private String url = null;	

	public ServerOldGame(){
		broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		gameClock = DEFAULT_CLK_PERIOD;
	}

	/**
	 * sets up the server given a number of players
	 * @param numPlayers
	 */
	public void setServer(String fileName){
		if(fileName==null){
			return; // cannot load from null file
		}
		this.fileName = fileName;
		if(url != null) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		}
		// Run in Server mode
		start();		
	}

	/**
	 * accepts the clients "slave" and makes a master connection for them 
	 */
	public void run() {

		setUpGame();
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

				Master mconn = new Master(s,broadcastClock,handler);
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
					setMasterIDs(connections);
					allowMastersStart(connections);
					multiUserGame(handler,connections);
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return; // done
				}
			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		} 
	}

	private void setMasterIDs(List<Master> connections) {
		if(playerIDs!=null){
			int i=0;
			for(Master m: connections){
				m.setUID(playerIDs.get(i));
				i++;
			}
		}

	}

	private void setUpGame() {
		// TODO loadXML = new LoadOldGame(fileName)
		// TODO boardData = loadXML.getBoardData();
		// TODO GameUtil game = boardData.getGame();
		// TODO handler.setGameUtil(game);
		// TODO playerIDs = loadXML.getPlayerIDs();
		// TODO maxPlayers = playerIDs.size();
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
	private static void multiUserGame(NetworkHandler game,
			List<Master> connections) throws IOException {						


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
