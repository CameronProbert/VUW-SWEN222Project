package catgame.logic;

import java.util.ArrayList;
import java.util.List;

import catgame.gameObjects.Bush;
import catgame.gameObjects.Food;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.Minion;
import catgame.gameObjects.PlayableCharacter;
import catgame.gameObjects.Rock;
import catgame.gameObjects.Tree;
import catgame.logic.GameUtil.Direction;

/**
 * 
 * @author Dan
 *
 *         Holds all of the preset object building methods
 */
public class ObjectBuilder {

	private final String groundTypeGrass = "Grass";

	// Unique Id numbers for objects

	private int playerNum = 10;
	private int minoinNum = 10;
	private int bossNum = 10;
	private int chestNum = 10;
	private int doorNum = 10;
	private int treeNum = 10;
	private int bushNum = 10;
	private int rockNum = 10;

	public ObjectBuilder() {

	}

	/*
	 * Construct an empty BoardCell used for having holes and different room
	 * shapes
	 */
	public BoardCell addEmptyCell(int x, int y, int ObjType, Room room) {
		return new BoardCell(new Position(x, y), null, "");
	}

	/**
	 * Construct a basic BoardCell on that doesn't have any objects on it
	 * 
	 * @param x
	 * @param y
	 * @param ObjType
	 * @param objStore
	 * @return new grass BoardCell
	 */
	public BoardCell addGrass(int x, int y, int ObjType, Room room) {
		return new BoardCell(new Position(x, y), null, groundTypeGrass);
	}

	/**
	 * Construct a grass BoardCell thats object is a bush
	 * 
	 * @param x
	 * @param y
	 * @param ObjType
	 * @param objStore
	 * @return new bush BoardCell
	 */
	public BoardCell addBush(int x, int y, int ObjType, Room room) {
		int newBushId = genorateObjectId(ObjType, genorateRandomObjectType(1), bushNum++);
		Bush newBush = new Bush(newBushId);
		room.addToInventory(newBush);
		return new BoardCell(new Position(x, y), newBush, groundTypeGrass);
	}

	/**
	 * Construct a grass BoardCell thats object is a tree
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @param objStore
	 * @param objStore
	 * @return new tree BoardCell
	 */
	public BoardCell addTree(int x, int y, int ObjType, Room room) {
		int newTreeId = genorateObjectId(ObjType, genorateRandomObjectType(2), treeNum++);
		Tree newTree = new Tree(newTreeId);
		room.addToInventory(newTree);
		return new BoardCell(new Position(x, y), newTree, groundTypeGrass);
	}

	/**
	 * Construct a grass BoardCell thats object is a rock
	 * 
	 * @param x
	 * @param y
	 * @param ObjType
	 * @param objStore
	 * @return new bush BoardCell
	 */
	public BoardCell addRock(int x, int y, int ObjType, Room room) {
		int newRockId = genorateObjectId(ObjType, genorateRandomObjectType(1), rockNum++);
		Rock newRock = new Rock(newRockId);
		room.addToInventory(newRock);
		return new BoardCell(new Position(x, y), newRock, groundTypeGrass);
	}

	/**
	 * Construct a grass BoardCell thats object is a player
	 * 
	 * @param x
	 * @param ObjType
	 * @param objStore
	 * @return
	 */
	public BoardCell addPlayer(int x, int y, int ObjType, Room room, ObjectStorer objStore) {
		int newPlayerId = genorateObjectId(ObjType, playerNum, playerNum++);
		// TODO OwnerId
		// Create starting items
		List<GameItem> newInv = new ArrayList<GameItem>();
		// 80 is food
		newInv.add(new Food(808080, 20));
		// Create new Player
		PlayableCharacter newPlayableCharacter = new PlayableCharacter(newPlayerId, newPlayerId, room, Direction.NORTH, 20, 100, newInv);
		objStore.addplayableChs(newPlayerId, newPlayableCharacter);
		room.addToInventory(newPlayableCharacter);
		return new BoardCell(new Position(x, y), newPlayableCharacter, groundTypeGrass);
	}

	public BoardCell addMinionOne(int x, int y, int ObjType, Room room, ObjectStorer objStore) {
		int newMinionId = genorateObjectId(ObjType, genorateRandomObjectType(1), minoinNum++);
		List<GameItem> newInv = new ArrayList<GameItem>();
		newInv.add(new Food(808080, 10));
		Minion newMin = new Minion(newMinionId, room, 8, 60, newInv);
		objStore.addNPC(newMinionId, newMin);
		room.addToInventory(newMin);
		return new BoardCell(new Position(x, y), newMin, groundTypeGrass);

	}

	/**
	 * Concatenates the IDs together and returns them as a integer
	 * 
	 * @param objectID
	 * @param objectType
	 * @param objectNum
	 * @return
	 */
	public int genorateObjectId(int objectID, int objectType, int objectNum) throws GameError {
		String result = "";
		result += objectID;
		result += objectType;
		result += objectNum;
		// System.out.println(result);
		if (result.length() != 6) {
			throw new GameError("InvalidObject ID:" + result);
		}
		return Integer.parseInt(result);
	}

	/**
	 * 
	 * @param numOfType
	 *            needs to be < 9
	 * @return
	 */
	public int genorateRandomObjectType(int numOfType) {
		if (numOfType == 1 || numOfType > 9) {
			return 10;
		}
		return (int) ((Math.random() * numOfType) + 10);
	}
}
