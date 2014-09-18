package catgame.clientserver;

import java.util.ArrayList;
import java.util.List;

public class NetworkHandler {

	public static final int WAITING = 0;
	public static final int READY = 1;
	public static final int PLAYING = 2;
	public static final int GAMEOVER = 3;
	public static final int GAMEWON = 4;

	public enum Type{
		CLIENT,
		SERVER
	}

	private int gameState;
	private int lastUpdate = 0;
	private Type stateType;
	private int noPlayers;
	private List<Integer> playerIds = new ArrayList<Integer>();

	/***
	 * add a a player
	 * @return the uid of the player added
	 */
	public int registerPlayer() {
		noPlayers++;
		// TODO create new ID for new player, add to playerIds and return it
		return 0;
	}

	/**
	 * set up a game for one person on single player
	 * @param playerID
	 */
	public void setupSinglePlayer(int playerID) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @return whether game is over or not
	 */
	public boolean isNotOver() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * remove player given their user id
	 * @param uid
	 */
	public void disconnectPlayer(int uid) {
		// TODO Auto-generated method stub

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
	public void update(int update, boolean changeLastUpdate) {
		if(changeLastUpdate)		this.lastUpdate = update;
		//TODO this will need to decode the update and actually update the game
	}

	/**
	 * get last update - this is for the masters to send updates to the slaves
	 * @return
	 */
	public int getLatestUpdate() {
		return this.lastUpdate;
	}

	public void addClientPlayer(int uid) {
		// TODO Auto-generated method stub

	}

	public void setGameType(Type type){
		this.stateType = type;
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

	public void setPlayerIds(List<Integer> playerIds) {
		this.playerIds = playerIds;
	}

}
