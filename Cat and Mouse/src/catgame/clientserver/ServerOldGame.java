package catgame.clientserver;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import catgame.datastorage.LoadOldGame;
import catgame.datastorage.XMLException;
import catgame.logic.BoardData;


/**
 * handles the setup of the server and singleplayer
 * handles sockets and also manages whether the game is over or not
 * @author Francine
 *
 */
public class ServerOldGame extends StartGame {

	private int broadcastClock;
	private int port = 32768; // default
	private NetworkHandler handler;
	private static int maxPlayers;
	private String fileName;
	private List<Integer> playerIDs;
	private BoardData boardData;

	private String url = null;	

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

	protected void setMasterIDs(List<Master> connections) {
		if(playerIDs!=null){
			int i=0;
			for(Master m: connections){
				m.setUID(playerIDs.get(i));
				i++;
			}
		}

	}

	private void setUpGame() {
		try {
			LoadOldGame loadXML = new LoadOldGame(new File(fileName));
			boardData = loadXML.getBoardData();
			handler.setBoardData(boardData);
			playerIDs = boardData.getObjStorer().getPlayerIDs();
			maxPlayers = playerIDs.size();
		} catch (JDOMException | XMLException e) {
			e.printStackTrace();
		}
	}


}
