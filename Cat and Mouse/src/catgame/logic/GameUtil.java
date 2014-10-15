package catgame.logic;

import catgame.gameObjects.Boss;
import catgame.gameObjects.Character;
import catgame.gameObjects.GameObject;
import catgame.gameObjects.Minion;
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

	// NPC id's 11-19
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

	public static final int FENCE = 24;
	public static final int CHESTONE = 25;
	public static final int CHESTTWO = 26;
	public static final int CHESTTHREE = 27;
	public static final int CHESTFOUR = 28;

	public static final int HEDGEL = 30;
	public static final int HEDGER = 31;

	public static final int FENCEL = 34;
	public static final int FENCER = 35;

	public static final int DOORN = 40;
	public static final int DOORE = 41;
	public static final int DOORS = 42;
	public static final int DOORW = 43;

	public static final int KEY = 60;
	public static final int FOOD = 80;

	private ObjectStorer storer;

	private Direction viewDirection = Direction.NORTH;

	private final BoardData boardData;

	public GameUtil(BoardData boardData, ObjectStorer objStore) {
		this.boardData = boardData;
		this.storer = objStore;
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
			return findPlayersRoom(playerID).movePlayer(playerID, direction, this.viewDirection);
		}
		return -1;
	}

	public Room findPlayersRoom(int playerID) {
		if(boardData.getAllRooms().size()==0){
			System.out.println("no rooms");
		}
		for (Room room : boardData.getAllRooms()) {
			if (room.getCharactorCell(playerID) != null && room.getCharactorCell(playerID).getObjectOnCell().getObjectID() == playerID) {
				// move the player in the room
				return room;
			}
		}
		return null;
	}

	/**
	 * Call to the logic to attack in the facing direction of a character
	 * 
	 * @param playerID
	 * @return whether could attack or not, -1 for not an attack and if it could
	 *         attack return the id of what was attacked
	 */
	public int action(int playerID) {
		if (findPlayersRoom(playerID) != null) {
			return findPlayersRoom(playerID).playerAction(playerID, this.viewDirection);
		}
		return -1;
	}

	/**
	 * Call to the logic loot an object in the facing direction of a character
	 * 
	 * @param playerID
	 * @return
	 */
	public int playerloot(int playerID) {
		if (findPlayersRoom(playerID) != null) {
			return findPlayersRoom(playerID).PlayerLoot(playerID, this.viewDirection);
		}
		return -1;
	}

	/**
	 * Add an Gameobject to a players inventory using an objects Id.
	 * 
	 * @param playerID
	 * @param ObjectID
	 * @return
	 */
	public boolean addObjectToInventory(int playerID, int objectID) {
		if (findPlayersRoom(playerID).getPlayerInRoom(playerID).canAddItem()) {
			return findPlayersRoom(playerID).getPlayerInRoom(playerID).addToInventory(storer.findItemInGame(objectID));
		}
		return false;
	}

	/**
	 * Use an item in a players Inventory
	 * 
	 * @param playerID
	 * @param objectID
	 * @return
	 */
	public int useItem(int playerID, int objectID) {
		return findPlayersRoom(playerID).getPlayerInRoom(playerID).eat(objectID);
	}

	/**
	 * Return the chest object that the player is standing in front of, return
	 * null if none
	 * 
	 * @param clientsUID
	 * @return
	 */
	public GameObject getObjectAhead(int playerID) {
		// TODO Find the chest in front of yourself
		if (findPlayersRoom(playerID) != null) {
			return findPlayersRoom(playerID).getObjectAheadOfCharactor(playerID, this.viewDirection);
		}
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
	public enum Direction {
		NORTH(0), UP(0), EAST(1), RIGHT(1), SOUTH(2), DOWN(2), WEST(3), LEFT(3);
		private int value;

		private Direction(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

		/**
		 * Returns the Direction to the left of the given Direction
		 */
		public static Direction leftDir(Direction dir) {
			Direction newDir = NORTH;
			switch (dir) {
			case NORTH:
				return EAST;
			case UP:
				return RIGHT;
			case EAST:
				return SOUTH;
			case RIGHT:
				return DOWN;
			case SOUTH:
				return WEST;
			case DOWN:
				return LEFT;
			case WEST:
				return NORTH;
			case LEFT:
				return UP;
			}
			return newDir;
		}

		/**
		 * Returns the Direction to the right of the given Direction
		 */
		public static Direction rightDir(Direction dir) {
			Direction newDir = NORTH;
			switch (dir) {
			case NORTH:
				return WEST;
			case UP:
				return LEFT;
			case EAST:
				return NORTH;
			case RIGHT:
				return UP;
			case SOUTH:
				return EAST;
			case DOWN:
				return RIGHT;
			case WEST:
				return SOUTH;
			case LEFT:
				return DOWN;
			}
			return newDir;
		}
	}

	public void TESTsetViewDirection(Direction d) {
		this.viewDirection = d;
	}

	public ObjectStorer getStorer() {
		return this.storer;
	}

	public Direction getViewDirection() {
		return viewDirection;
	}

	public void lookLeft() {
		viewDirection = Direction.leftDir(viewDirection);
	}

	public void lookRight() {
		viewDirection = Direction.rightDir(viewDirection);
	}

	public void attackUpdate(int playerID, int minionID) {
		PlayableCharacter pC = this.storer.findCharacter(playerID);
		Character ch = this.storer.findNCP(minionID);
		if(ch instanceof Boss){
			pC.changeHealth(10);
			ch.changeHealth(20);
		}
		else if(ch instanceof Minion){
			pC.changeHealth(8);
			pC.changeHealth(20);
		}
	}

	public void moveToNextRoom(int playerID) {
		findPlayersRoom(playerID).forceUseDoor(playerID, this.viewDirection);
	}
}
