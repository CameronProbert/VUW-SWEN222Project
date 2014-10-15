package catgame.gamestarting;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import catgame.clientserver.Update;
import catgame.logic.GameUtil;

/**
 * Game state holder specifically used by games where networking is present, it handles multiplayer gaming
 * @author Francine
 *
 */
public class NetworkHandler extends GameRunner{



	public enum Type{
		CLIENT,
		SERVER
	}

	private Update lastUpdate = new Update(0,0,0);
	private Type stateType;
	private List<Integer> playerIds = new ArrayList<Integer>();
	private int clientPlayerID; // this will only have a value if the client is running the program, used so the UI knows whos who

	public NetworkHandler(Type type){
		this.stateType = type;
	}

	/***
	 * add a a player
	 * @return the uid of the player added
	 */
	public int registerPlayer() {
		int playerID = 100000 + (noPlayers+10)*100 + (noPlayers+10); // making the unique id for each player
		playerIds.add(playerID);
		noPlayers++;
		return playerID;
	}

	/**
	 * remove player given their user id
	 * @param uid
	 */
	public void disconnectPlayer(int uid) {
		// this.playerIds.remove(uid);
	}


	/**
	 * 
	 * @param update
	 * @param changeLastUpdate - this is for distinguishing whether the update should be saved or not
	 * (is usually not saved when used by the slave to update from the masters call)
	 */
	public void update(Update update, boolean changeLastUpdate) {
		if(changeLastUpdate)		this.lastUpdate = update;
		update.decode(boardData.getGameUtil()); 
	}

	/**
	 * get last update - this is for the masters to send updates to the slaves
	 * @return
	 */
	public Update getLatestUpdate() {
		return this.lastUpdate;
	}

	/**
	 * may be obsolete
	 * 
	 * @param uid
	 */
	public void addClientPlayer(int uid) {
		this.clientPlayerID = uid;
	}


	/**
	 * may be obsolete
	 * @return
	 */
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
