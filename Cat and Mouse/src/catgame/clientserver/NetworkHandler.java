package catgame.clientserver;

import java.util.ArrayList;
import java.util.List;

import catgame.logic.GameMain;

public class NetworkHandler extends GameRunner{

	

	public enum Type{
		CLIENT,
		SERVER
	}

	private Update lastUpdate = null;
	private Type stateType;
	private List<Integer> playerIds = new ArrayList<Integer>();
	private int clientPlayerID; // this will only have a value if the client is running the program, used so the UI knows whos who
	
	public NetworkHandler(Type type){
		this.stateType = type;
		this.game = new GameMain();
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
	 * remove player given their user id
	 * @param uid
	 */
	public void disconnectPlayer(int uid) {
		this.playerIds.remove(uid);
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
	
}
