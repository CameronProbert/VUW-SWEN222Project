package catgame.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import catgame.gameObjects.Character;
import catgame.gameObjects.Chest;
import catgame.gameObjects.Food;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.GameObject;
import catgame.gameObjects.Key;
import catgame.gameObjects.MasterObject;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;

/**
 * 
 * @author Dan Henton
 *
 *This Class is used to talk to the game logic via the client. The networking traffic comes in and calls methods inside this class
 *then this decides how to go about these specific tasks within the games logic
 */
public class GameUtil {
	
	private ObjectStorer storer = new ObjectStorer();

	public GameUtil() {

	}

	/**
	 * This method tells the logic the direction of a playable characters movement, 
	 * Call to move the player in an upward(NORTH) direction (going north on the screen) via the "w" key
	 * 
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveUp(int playerID) {
		// find the character then try and move it
		for (Room room : BoardData.getAllRooms()) {
			if (room.getCharactor(playerID) != null
					&& room.getCharactor(playerID).getObjectID() == playerID) {
				//move the player in the room
				return room.movePlayer(playerID, Direction.UP);
			}
		}

		return -1;
	}

	/**
	 * This method tells the logic the direction of a playable characters movement, 
	 * Call to move the player in an right(EAST) direction via the "d" key
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveRight(int playerID) {
		return 1; //TODO once we have the game going we can check move up and most of the logic is the same
	}

	/**
	 * This method tells the logic the direction of a playable characters movement, 
	 * Call to move the player in an down(SOUTH) direction via the "s" key
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveDown(int playerID) {
		return 1;//TODO once we have the game going we can check move up and most of the logic is the same
	}

	/**
	 * 
	 * This method tells the logic the direction of a playable characters movement, 
	 * Call to move the player in an left(WEST) direction via the "a" key
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveLeft(int playerID) {
		return 1; //TODO once we have the game going we can check move up and most of the logic is the same
	}
								//TODO CHECK WHICH ONE IS NEEDED
	////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Call to the logic to attack in the facing direction of a character
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
	 * Call to the logic to attack in the facing direction of a character
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
	
////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Add an Gameobject to a players inventory using an objects Id.
	 * @param playerID
	 * @param ObjectID
	 * @return
	 */
	public boolean addObjectToInventory(int playerID, int ObjectID) {
		return false; // TODO gameMap.get(playerID).addToInventory(GameITem);
	}
	/**
	 * Remove an GameObject from an players inventory using the objects id
	 * @param playerID
	 * @param ObjectID
	 * @return
	 */
	public boolean removeItem(int playerID, int ObjectID) {
		return false; // TODO
						// gameMap.get(playerID).removeFromInventory(GameITem);
	}
	
	/**
	 * Use an item in a players Inventory
	 * 
	 * @param playerID
	 * @param objectID
	 * @return
	 */
	public boolean useItem(int playerID, int objectID) {
		return false; // TODO gameMap.get(playerID).useItem(GameITem)
	}
	
	/**
	 * Call to the logic to move the player to the other side of a door
	 * @param playerID
	 * @param roomID
	 * @return
	 */
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
							//TODO CHECK IF THESE ARE NEEDED

	/**
	 * 
	 * @author Dan Henton
	 *
	 *
	 *         Direction of the map or a players orientation and movement, 
	 *         Every enum maps to a corrosponding int for calculation
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
	}

	public ObjectStorer getStorer(){
		return this.storer;
	}
	
	
}