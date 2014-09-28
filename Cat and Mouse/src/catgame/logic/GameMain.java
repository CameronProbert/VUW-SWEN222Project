package catgame.logic;

import java.util.HashMap;

import catgame.GameObjects.MasterObject;
import catgame.GameObjects.Chest;

public class GameMain {

	HashMap gameMap = new HashMap();

	public GameMain() {

	}

	private HashMap<Integer, MasterObject> loadMap() {
		HashMap<Integer, MasterObject> toLoad = new HashMap<Integer, MasterObject>();
		// TODO BODY
		return toLoad;
	}

	/**
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveNorth(int playerID) {
		return -1; // TODO gameMap.get(playerID).move("NORTH");
	}

	/**
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveSouth(int playerID) {
		return -1; // TODO gameMap.get(playerID).move("SOUTH");
	}

	/**
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveEast(int playerID) {
		return -1; // TODO gameMap.get(playerID).move("EAST");
	}

	/**
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveWest(int playerID) {
		return -1; // TODO gameMap.get(playerID).move("WEST");
	}

	/**
	 * 
	 * @param playerID
	 * @return whether could attack or not, -1 for not an attack 
	 * and if it could attack return the id of what was attacked
	 */
	public int attack(int playerID) {
		// TODO find the target and attack it, then return the ID of the target
		return -1; // should return the attackerID
	}

	/**
	 * 
	 * @param playerID
	 * @return whether could attack or not, -1 for not an attack 
	 * and if it could attack return the id of what was attacked
	 */
	public int attack(int playerID, int attackedID) {
		//TODO attack the target
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

	/**
	 * Return the chest object that the player is standing infront of, return null if none
	 * @param clientsUID
	 * @return
	 */
	public Chest getChest(int clientsUID) {
		// TODO Auto-generated method stub
		return null;
	}
}