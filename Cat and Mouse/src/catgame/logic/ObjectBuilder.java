package catgame.logic;

import java.util.ArrayList;
import java.util.List;

import catgame.gameObjects.*;
import catgame.logic.GameUtil.Direction;

/**
 * 
 * @author Dan
 *
 *         Holds all of the preset object building methods
 */
public class ObjectBuilder {

	private final String GROUNDTYPEGRASS = "Grass";

	// Unique Id numbers for objects

	private int playerNum = 10;
	private int minoinNum = 10;
	private int bossNum = 10;
	private int chestNum = 10;
	private int doorNum = 10;
	private int treeNum = 10;
	private int bushNum = 10;
	private int rockNum = 10;
	private int hedgeNum = 10;

	public ObjectBuilder() {

	}

	/*
	 * Construct an empty BoardCell used for having holes and different room
	 * shapes
	 */
	public BoardCell addEmptyCell(int x, int y, Room room) {
		return new BoardCell(new Position(x, y), null, "");
	}

	/**
	 * Construct a basic BoardCell on that doesn't have any objects on it
	 * 
	 * @param x
	 * @param y
	 * @param objStore
	 * @return new grass BoardCell
	 */
	public BoardCell addGrass(int x, int y, Room room) {
		return new BoardCell(new Position(x, y), null, GROUNDTYPEGRASS);
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
	public BoardCell addBush(int x, int y, Room room) {
		int newBushId = genorateObjectId(GameUtil.BUSH, genorateRandomObjectType(1), bushNum++);
		Bush newBush = new Bush(newBushId);
		room.addToInventory(newBush);
		return new BoardCell(new Position(x, y), newBush, GROUNDTYPEGRASS);
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
	public BoardCell addTree(int x, int y, Room room) {
		int newTreeId = genorateObjectId(GameUtil.TREE, genorateRandomObjectType(2), treeNum++);
		Tree newTree = new Tree(newTreeId);
		room.addToInventory(newTree);
		return new BoardCell(new Position(x, y), newTree, GROUNDTYPEGRASS);
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
	public BoardCell addRock(int x, int y, Room room) {
		int newRockId = genorateObjectId(GameUtil.ROCK, genorateRandomObjectType(1), rockNum++);
		Rock newRock = new Rock(newRockId);
		room.addToInventory(newRock);
		return new BoardCell(new Position(x, y), newRock, GROUNDTYPEGRASS);
	}

	/**
	 * Construct a grass BoardCell thats object is a player
	 * 
	 * @param x
	 * @param ObjType
	 * @param objStore
	 * @return
	 */
	public BoardCell addPlayer(int x, int y, Room room, ObjectStorer objStore) {
		int newPlayerId = genorateObjectId(GameUtil.PLAYABLECHARACTER, playerNum, playerNum++);
		// Create starting items
		List<GameItem> newInv = new ArrayList<GameItem>();
		// 80 is food
		newInv.add(new Food(808080, 20));
		// Create new Player
		PlayableCharacter newPlayableCharacter = new PlayableCharacter(newPlayerId, room, Direction.NORTH, 20, 100, newInv);
		objStore.addplayableChs(newPlayerId, newPlayableCharacter);
		room.addToInventory(newPlayableCharacter);
		return new BoardCell(new Position(x, y), newPlayableCharacter, GROUNDTYPEGRASS);
	}

	public BoardCell addMinionOne(int x, int y, Room room, ObjectStorer objStore) {
		int newMinionId = genorateObjectId(GameUtil.MINIONONE, genorateRandomObjectType(1), minoinNum++);
		List<GameItem> newInv = new ArrayList<GameItem>();
		newInv.add(new Food(GameUtil.FOOD, 10));
		Minion newMin = new Minion(newMinionId, room, 8, 60, newInv);
		objStore.addNPC(newMinionId, newMin);
		room.addToInventory(newMin);
		return new BoardCell(new Position(x, y), newMin, GROUNDTYPEGRASS);

	}

	public BoardCell addBossOne(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newBossID = genorateObjectId(GameUtil.BOSSONE, 10, bossNum++);
		List<GameItem> bossInv = new ArrayList<GameItem>();
		bossInv.add(new Food(GameUtil.FOOD, 40));
		bossInv.add(new Key(GameUtil.KEY));
		Boss newBoss = new Boss(newBossID, 10, 180, bossInv);
		objStore.addNPC(newBossID, newBoss);
		loadingRoom.addToInventory(newBoss);
		return new BoardCell(new Position(x, y), newBoss, GROUNDTYPEGRASS);
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

	public BoardCell addChestOne(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newChestId = genorateObjectId(GameUtil.CHESTONE, genorateRandomObjectType(1), chestNum++);
		List<GameItem> chestinv = new ArrayList<GameItem>();
		chestinv.add(new Food(80, 20));
		Chest newChest = new Chest(newChestId, chestinv);
		objStore.addChest(newChestId, newChest);
		loadingRoom.addToInventory(newChest);
		return new BoardCell(new Position(x, y), newChest, GROUNDTYPEGRASS);
	}

	public BoardCell addChestTwo(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newChestId = genorateObjectId(GameUtil.CHESTONE, genorateRandomObjectType(1), chestNum++);
		List<GameItem> chestinv = new ArrayList<GameItem>();
		chestinv.add(new Food(GameUtil.FOOD, 20));
		chestinv.add(new Key(GameUtil.KEY));
		Chest newChest = new Chest(newChestId, chestinv);
		objStore.addChest(newChestId, newChest);
		loadingRoom.addToInventory(newChest);
		return new BoardCell(new Position(x, y), newChest, GROUNDTYPEGRASS);
	}

	public BoardCell addDoorN(int x, int y, Room loadingRoom) {
		int newDoorId = genorateObjectId(GameUtil.DOORN, genorateRandomObjectType(1), doorNum++);
		Door newDoor = new Door(newDoorId, Direction.NORTH, loadingRoom);
		loadingRoom.addToInventory(newDoor);
		return new BoardCell(new Position(x, y), newDoor, GROUNDTYPEGRASS);
	}

	public BoardCell addDoorE(int x, int y, Room loadingRoom) {
		int newDoorId = genorateObjectId(GameUtil.DOORE, genorateRandomObjectType(1), doorNum++);
		Door newDoor = new Door(newDoorId, Direction.EAST, loadingRoom);
		loadingRoom.addToInventory(newDoor);
		return new BoardCell(new Position(x, y), newDoor, GROUNDTYPEGRASS);
	}

	public BoardCell addDoorS(int x, int y, Room loadingRoom) {
		int newDoorId = genorateObjectId(GameUtil.DOORS, genorateRandomObjectType(1), doorNum++);
		Door newDoor = new Door(newDoorId, Direction.SOUTH , loadingRoom);
		loadingRoom.addToInventory(newDoor);
		return new BoardCell(new Position(x, y), newDoor, GROUNDTYPEGRASS);
	}

	public BoardCell addDoorW(int x, int y, Room loadingRoom) {
		int newDoorId = genorateObjectId(GameUtil.DOORW, genorateRandomObjectType(1), doorNum++);
		Door newDoor = new Door(newDoorId, Direction.WEST, loadingRoom);
		loadingRoom.addToInventory(newDoor);
		return new BoardCell(new Position(x, y), newDoor, GROUNDTYPEGRASS);
	}

	public BoardCell addHedgeN(int x, int y, Room loadingRoom) {
		int newHedgeId = genorateObjectId(GameUtil.HEDGEN, genorateRandomObjectType(1), hedgeNum++);
		Hedge newHedge = new Hedge(newHedgeId, Direction.NORTH);
		loadingRoom.addToInventory(newHedge);
		return new BoardCell(new Position(x, y), newHedge, GROUNDTYPEGRASS);
	}
	public BoardCell addHedgeE(int x, int y, Room loadingRoom) {
		int newHedgeId = genorateObjectId(GameUtil.HEDGEN, genorateRandomObjectType(1), hedgeNum++);
		Hedge newHedge = new Hedge(newHedgeId, Direction.EAST);
		loadingRoom.addToInventory(newHedge);
		return new BoardCell(new Position(x, y), newHedge, GROUNDTYPEGRASS);
	}
	public BoardCell addHedgeS(int x, int y, Room loadingRoom) {
		int newHedgeId = genorateObjectId(GameUtil.HEDGEN, genorateRandomObjectType(1), hedgeNum++);
		Hedge newHedge = new Hedge(newHedgeId, Direction.SOUTH);
		loadingRoom.addToInventory(newHedge);
		return new BoardCell(new Position(x, y), newHedge, GROUNDTYPEGRASS);
	}
	public BoardCell addHedgeW(int x, int y, Room loadingRoom) {
		int newHedgeId = genorateObjectId(GameUtil.HEDGEN, genorateRandomObjectType(1), hedgeNum++);
		Hedge newHedge = new Hedge(newHedgeId, Direction.WEST);
		loadingRoom.addToInventory(newHedge);
		return new BoardCell(new Position(x, y), newHedge, GROUNDTYPEGRASS);
	}

	
	
}
