package catgame.logic;

import java.util.HashMap;

import catgame.GameObjects.MasterObject;

public class GameMain {

	HashMap gameMap = new HashMap();

	public GameMain() {

	}

	private HashMap<Integer, MasterObject> loadMap() {
		HashMap<Integer, MasterObject> toLoad = new HashMap<Integer, MasterObject>();
		// TODO BODY
		return toLoad;
	}

	public boolean moveNorth(int playerID) {
		return false; // TODO gameMap.get(playerID).move("NORTH");
	}

	public boolean moveSouth(int playerID) {
		return false; // TODO gameMap.get(playerID).move("SOUTH");
	}

	public boolean moveEast(int playerID) {
		return false; // TODO gameMap.get(playerID).move("EAST");
	}

	public boolean moveWest(int playerID) {
		return false; // TODO gameMap.get(playerID).move("WEST");
	}

	public int attack(int playerID) {
		// SOMETHING??
		return -1; // should return the attackerID
	}

	public int attack(int playerID, int attackedID) {
		// SOMETHING??
		return attackedID; // should return the attackerID (though it is
							// obsolete for this method, so it is acceptable
							// to return -1)
	}

	public boolean addObjectToInventory(int playerID, int ObjectID) {
		return false; // TODO gameMap.get(playerID).addToInventory(GameITem);
	}

	public boolean removeItem(int playerID, int ObjectID) {
		return false; // TODO
						// gameMap.get(playerID).removeFromInventory(GameITem);
	}

	public boolean useItem(int playerID, int objectID) {
		return false; // TODO gameMap.get(playerID).useItem(GameITem)
	}

	public boolean moveToNextRoom(int playerID, int roomID) {
		return false;
	}
}