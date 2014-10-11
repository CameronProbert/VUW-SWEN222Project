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

//characters id's 10 - 19

/**
 * 
 * @author Dan Henton
 *
 *         This Class is used to talk to the game logic via the client. The
 *         networking traffic comes in and calls methods inside this class then
 *         this decides how to go about these specific tasks within the games
 *         logic
 */
public class GameUtil {

	// Object ID's
	public static final int EMPTY = 0;
	public static final int GRASS = 1;

	// characters id's 10 - 19
	public static final int PLAYABLECHARACTER = 10;

	//NPC id's 11-19
	public static final int BOSSONE = 11;
	public static final int BOSSTWO = 12;
	public static final int BOSSTHREE = 13;
	public static final int MINIONONE = 14;
	public static final int MINIONTWO = 15;
	public static final int MINIONTHREE = 16;
	public static final int MINIONFOUR = 17;

	// Room accessories 20-39
	public static final int BUSH = 20;
	public static final int TREE = 21;
	public static final int ROCK = 22;
	public static final int HEDGE = 23;
	public static final int FENCE = 24;
	public static final int CHESTONE = 25;
	public static final int CHESTTWO = 26;
	public static final int CHESTTHREE = 27;
	public static final int CHESTFOUR = 28;

	private ObjectStorer storer = new ObjectStorer();

	public static Direction VIEWDIRECTION = Direction.NORTH;

	public GameUtil() {

	}

	/**
	 * This method tells the logic the direction of a playable characters
	 * movement, Call to move the player in an upward(NORTH) direction (going
	 * north on the screen) via the "w" key
	 * 
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveUp(int playerID) {
		return move(playerID, Direction.UP);
	}

	/**
	 * This method tells the logic the direction of a playable characters
	 * movement, Call to move the player in an right(EAST) direction via the "d"
	 * key
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveRight(int playerID) {
		return move(playerID, Direction.RIGHT);
	}

	/**
	 * This method tells the logic the direction of a playable characters
	 * movement, Call to move the player in an down(SOUTH) direction via the "s"
	 * key
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveDown(int playerID) {
		return move(playerID, Direction.DOWN);
	}

	/**
	 * 
	 * This method tells the logic the direction of a playable characters
	 * movement, Call to move the player in an left(WEST) direction via the "a"
	 * key
	 * 
	 * @param playerID
	 * @return whether a successful move or not (-1 = not success, 1 = success)
	 */
	public int moveLeft(int playerID) {
		return move(playerID, Direction.LEFT);
	}

	private int move(int playerID, Direction direction) {
		// find the character then try and move it
		if (findPlayersRoom(playerID) != null) {
			return findPlayersRoom(playerID).movePlayer(playerID, direction);
		}
		return -1;
	}

	private Room findPlayersRoom(int playerID) {
		for (Room room : BoardData.getAllRooms()) {
			if (room.getCharactorCell(playerID) != null && room.getCharactorCell(playerID).getObjectOnCell().getObjectID() == playerID) {
				// move the player in the room
				return room;
			}
		}
		return null;
	}

	// TODO CHECK WHICH ONE IS NEEDED
	// //////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Call to the logic to attack in the facing direction of a character
	 * 
	 * @param playerID
	 * @return whether could attack or not, -1 for not an attack and if it could
	 *         attack return the id of what was attacked
	 */
	public int attack(int playerID) {
		if (findPlayersRoom(playerID) != null) {
			return findPlayersRoom(playerID).playerAttack(playerID);
		}
		return -1;
	}

	/**
	 * Call to the logic to attack in the facing direction of a character
	 * 
	 * @param playerID
	 * @return whether could attack or not, -1 for not an attack and if it could
	 *         attack return the id of what was attacked
	 */
	public int attack(int playerID, int attackedID) {
		// TODO if either ID not found throw IDNotFoundError
		// TODO attack the target
		return attackedID; // should return the attackerID (though it is
							// obsolete for this method, so it is acceptable
							// to return -1)
	}

	// //////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Add an Gameobject to a players inventory using an objects Id.
	 * 
	 * @param playerID
	 * @param ObjectID
	 * @return
	 */
	public boolean addObjectToInventory(int playerID, int ObjectID) {
		// TODO if either ID not found throw IDNotFoundError
		return false; // TODO gameMap.get(playerID).addToInventory(GameITem);
	}

	/**
	 * Remove an GameObject from an players inventory using the objects id
	 * 
	 * @param playerID
	 * @param ObjectID
	 * @return
	 */
	public boolean removeItem(int playerID, int ObjectID) {
		// TODO if either ID not found throw IDNotFoundError
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
	public int useItem(int playerID, int objectID) {
		// TODO if either ID not found throw IDNotFoundError
		return -1; // TODO gameMap.get(playerID).useItem(GameITem)
	}

	/**
	 * Call to the logic to move the player to the other side of a door
	 * 
	 * @param playerID
	 * @param roomID
	 * @return
	 */
	public boolean moveToNextRoom(int playerID, int roomID) {
		// TODO if either ID not found throw IDNotFoundError
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
		// TODO if ID not found throw IDNotFoundError
		// TODO Auto-generated method stub
		return null;
	}

	// TODO CHECK IF THESE ARE NEEDED

	/**
	 * 
	 * @author Dan Henton
	 *
	 *
	 *         Direction of the map or a players orientation and movement, Every
	 *         enum maps to a corrosponding int for calculation
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

	public ObjectStorer getStorer() {
		return this.storer;
	}

}