package catgame.logic;

import java.util.ArrayList;
import java.util.HashMap;

import catgame.GameObjects.Character;
import catgame.GameObjects.Food;
import catgame.GameObjects.GameItem;
import catgame.GameObjects.GameObject;
import catgame.GameObjects.Key;
import catgame.GameObjects.MasterObject;
import catgame.GameObjects.Chest;
import catgame.GameObjects.PlayableCharacter;

public class GameUtill {

	HashMap gameMap = new HashMap();

	public GameUtill() {

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

	public PlayableCharacter findCharacter(int objectID) {
		// TODO Auto-generated method stub
		////////////////////////////////////////////////////
		// For testing
		ArrayList<GameItem> items = new ArrayList<GameItem>();
		items.add(new Food(2, 30));
		items.add(new Key(3));
		items.add(new Food(2, 30));

		PlayableCharacter ch = new PlayableCharacter(1, null, " ", 3,
				5, items);	
		return ch;
		/////////////////////////////////////////////////////
	}

	public GameItem findItem(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPlayer(int playerID) {
		// TODO Auto-generated method stub
	}

	public GameObject findGameObject(int ownerID) {
		// TODO Auto-generated method stub
		return null;
	}
}