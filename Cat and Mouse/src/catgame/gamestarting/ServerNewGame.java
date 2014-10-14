package catgame.gamestarting;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import catgame.clientserver.Master;
import catgame.datastorage.LoadNewGame;
import catgame.datastorage.SavingMain;
import catgame.datastorage.XMLException;
import catgame.logic.BoardData;

public class ServerNewGame extends StartServer {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;
	private int broadcastClock;
	private int gameClock;
	private int port = 32768; // default
	private static boolean readyToStart = false;
	private NetworkHandler handler;
	private static int maxPlayers;
	private List<Integer> playerIDs = new ArrayList<Integer>();
	private BoardData boardData;

	private String url = null;	

	public ServerNewGame(){
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
		start();		
	}

	/**
	 * accepts the clients "slave" and makes a master connection for them 
	 */
	public void run() {

		// Listen for connections
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("SERVER AWAITING CLIENTS");
		try {
			handler = new NetworkHandler(NetworkHandler.Type.SERVER);
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
					setUpGame(connections);
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

	private void setUpGame(List<Master> connections) {
		try {
			LoadNewGame loadXML = new LoadNewGame(playerIDs);
			boardData = loadXML.getBoardData();
			handler.setBoardData(boardData);
			File saveInitial = new File("Save_To");
			SavingMain save = new SavingMain(boardData, saveInitial);
			File file = save.getXMLFile();
			for(Master m: connections){
				m.setFile(file);
			}
		} catch (JDOMException | XMLException | IOException e) {
			e.printStackTrace();
		}
	}

	protected void setMasterIDs(List<Master> connections) {
		for(Master m: connections){
			int id = handler.registerPlayer();
			m.setUID(id);
			playerIDs.add(id);
		}
	}


}
