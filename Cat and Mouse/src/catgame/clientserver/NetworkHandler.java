package catgame.clientserver;

import java.util.ArrayList;
import java.util.List;

import catgame.logic.GameMain;

public class NetworkHandler {

	// TODO change to enum
	public static final int WAITING = 0;
	public static final int READY = 1;
	public static final int PLAYING = 2;
	public static final int GAMEOVER = 3;
	public static final int GAMEWON = 4;

	public enum Type{
		CLIENT,
		SERVER,
		SINGLEPLAYER
	}

	private int gameState;
	private Update lastUpdate = null;
	private Type stateType;
	private int noPlayers = 0;
	private List<Integer> playerIds = new ArrayList<Integer>();
	private int clientPlayerID; // this will only have a value if the client is running the program, used so the UI knows whos who
	private GameMain game;
	
	public NetworkHandler(Type type){
		this.stateType = type;
		game = new GameMain();
	}

	/***
	 * add a a player
	 * @return the uid of the player added
	 */
	public int registerPlayer() {
		noPlayers++;
		playerIds.add(noPlayers);
		return noPlayers;
	}

	/**
	 * set up a game for one person on single player
	 * @param playerID
	 */
	public void setupSinglePlayer(int playerID) {
		// TODO make a single player panel and pass it the gamemain
		
	}

	/**
	 * 
	 * @return whether game is over or not
	 */
	public boolean isNotOver() {
		// TODO always return true unless frame has been deleted, needs to check for this somehow
		return true;
	}

	/**
	 * remove player given their user id
	 * @param uid
	 */
	public void disconnectPlayer(int uid) {
		this.playerIds.remove(uid);
	}


	/**
	 * updates game --- calls tick on each player
	 * the tick for each player will need to check the health and experience etc and act accordingly 
	 * MAY NOT BE NEEDED
	 */
	public void clockTick() {
		// TODO Auto-generated method stub

	}

	/**
	 * sets the state of the game Playing, over etc)
	 * @param state
	 */
	public void setState(int state) {
		gameState = state;
	}

	/**
	 * receives the state of the game
	 * @return
	 */

	public int state(){
		return gameState;
	}

	/**
	 * 
	 * @param update
	 * @param changeLastUpdate - this is for distinguishing whether the update should be saved or not
	 * (is usually not saved when used by the slave to update from the masters call)
	 */
	public void update(Update update, boolean changeLastUpdate) {
		if(changeLastUpdate)		this.lastUpdate = update;
		update.decode(game); 
	}

	/**
	 * get last update - this is for the masters to send updates to the slaves
	 * @return
	 */
	public Update getLatestUpdate() {
		return this.lastUpdate;
	}

	public void addClientPlayer(int uid) {
		this.clientPlayerID = uid;
	}

	/**
	 * returns number of players
	 * @return
	 */
	public int noPlayers() {
		return noPlayers;
	}

	public List<Integer> getPlayerIds() {
		return playerIds;
	}

	/**
	 * this method is used only by the slave
	 * to add the other players to the game for drawing purposes
	 * 
	 * will pass this list to the game logic main which will then make the characters associated
	 *  
	 * @param playerIds
	 */
	public void setPlayerIds(List<Integer> playerIds) {
		this.playerIds = playerIds;
		// TODO pass to game main logic so it can make the characters associated
	}
	
	
	public GameMain getGameMain(){
		return game;
	}
	
}
