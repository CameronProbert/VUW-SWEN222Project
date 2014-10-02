package catgame.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import catgame.gameObjects.Character;
import catgame.gameObjects.Chest;
import catgame.gameObjects.Food;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.GameObject;
import catgame.gameObjects.Key;
import catgame.gameObjects.MasterObject;
import catgame.gameObjects.PlayableCharacter;
/**
 * 
 * @author Dan Henton
 *
 */
public class GameUtil {

	public GameUtil() {

	}

	/**
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveUp(int playerID) {
		for (Room room : BoardData.allRooms) {
			// find the character then try and move it
			if (room.getCharactor(playerID) != null && room.getCharactor(playerID).getObjectID() == playerID) {
				return room.movePlayer(playerID, Direction.UP);
			}
		}

		return -1;
	}

	/**
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveRight(int playerID) {
		return -1; // TODO gameMap.get(playerID).move("RIGHT");
	}

	/**
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveDown(int playerID) {
		return -1; // TODO gameMap.get(playerID).move("DOWN");
	}

	/**
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveLeft(int playerID) {
		return -1; // TODO gameMap.get(playerID).move("LEFT");
	}

	/**
	 * 
	 * @param playerID
	 * @return whether could attack or not, -1 for not an attack and if it could
	 *         attack return the id of what was attacked
	 */
	public int attack(int playerID) {
		// TODO find the target and attack it, then return the ID of the target
		return -1; // should return the attackerID
	}

	/**
	 * 
	 * @param playerID
	 * @return whether could attack or not, -1 for not an attack and if it could
	 *         attack return the id of what was attacked
	 */
	public int attack(int playerID, int attackedID) {
		// TODO attack the target
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
	 * Return the chest object that the player is standing infront of, return
	 * null if none
	 * 
	 * @param clientsUID
	 * @return
	 */
	public Chest getChest(int clientsUID) {
		// TODO Auto-generated method stub
		return null;
	}

	public PlayableCharacter findCharacter(int objectID) {
		// TODO Auto-generated method stub
		// //////////////////////////////////////////////////
		// For testing
		ArrayList<GameItem> items = new ArrayList<GameItem>();
		items.add(new Food(2, 30));
		items.add(new Key(3));
		items.add(new Food(2, 30));

		PlayableCharacter ch = new PlayableCharacter(1, null, Direction.NORTH, 3, 5, items);
		return ch;
		// ///////////////////////////////////////////////////
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

	/**
	 * 
	 * @author Dan Henton
	 *
	 *
	 *         Direction of the map or a players orientation and movement
	 * 
	 *         * NORTH = UP = 0, EAST = RIGHT = 1, SOUTH = DOWN = 2, WEST = LEFT
	 *         = 3,
	 * 
	 */
	public static enum Direction {
		NORTH(0), UP(0), EAST(1), RIGHT(1), SOUTH(2), DOWN(2), WEST(3), LEFT(3);
		private int value;

		private Direction(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	};

}