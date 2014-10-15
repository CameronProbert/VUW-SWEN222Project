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
	private int food = 10;
	private int key = 10;
	private int fence = 10;

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
		newInv.add(new Food(genorateObjectId(GameUtil.FOOD, 10, food++), 20));
		// Create new Player
		PlayableCharacter newPlayableCharacter = new PlayableCharacter(newPlayerId, Direction.NORTH, 20, 100, newInv);
		objStore.addItems(newInv.get(0).getObjectID(), newInv.get(0));
		objStore.addplayableChs(newPlayerId, newPlayableCharacter);
		room.addToInventory(newPlayableCharacter);
		return new BoardCell(new Position(x, y), newPlayableCharacter, GROUNDTYPEGRASS);
	}

	/**
	 * Creates a minion with food in its inv, the minion has an attackPower of 8
	 * and a health pool of 60
	 * 
	 * @param x
	 * @param y
	 * @param room
	 * @param objStore
	 * @return the boardCell with a minion on it
	 */
	public BoardCell addMinionOne(int x, int y, Room room, ObjectStorer objStore) {
		int newMinionId = genorateObjectId(GameUtil.MINIONONE, genorateRandomObjectType(1), minoinNum++);
		List<GameItem> newInv = new ArrayList<GameItem>();
		newInv.add(new Food(genorateObjectId(GameUtil.FOOD, 10, food++), 20));
		Minion newMin = new Minion(newMinionId, room, 8, 60, newInv);
		objStore.addItems(newInv.get(0).getObjectID(), newInv.get(0));
		objStore.addNPC(newMinionId, newMin);
		room.addToInventory(newMin);
		return new BoardCell(new Position(x, y), newMin, GROUNDTYPEGRASS);

	}

	/**
	 * Create a Boss with food and a key in its inventory the boss has an
	 * attackpower of 10 and a health pool of 180
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @param objStore
	 * @return BoardCell with a boss on it
	 */
	public BoardCell addBossOne(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newBossID = genorateObjectId(GameUtil.BOSSONE, 10, bossNum++);
		List<GameItem> bossInv = new ArrayList<GameItem>();
		bossInv.add(new Food(genorateObjectId(GameUtil.FOOD, 10, food++), 40));
		bossInv.add(new Key(genorateObjectId(GameUtil.KEY, 10, key++)));
		Boss newBoss = new Boss(newBossID, 10, 180, bossInv);
		objStore.addItems(bossInv.get(0).getObjectID(), bossInv.get(0));
		objStore.addItems(bossInv.get(1).getObjectID(), bossInv.get(1));
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

	/**
	 * Create a chest with food in it
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @param objStore
	 * @return BoardCell with a chest on it
	 */
	public BoardCell addChestOne(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newChestId = genorateObjectId(GameUtil.CHESTONE, genorateRandomObjectType(1), chestNum++);
		List<GameItem> chestinv = new ArrayList<GameItem>();
		chestinv.add(new Food(genorateObjectId(GameUtil.FOOD, 10, food++), 20));
		Chest newChest = new Chest(newChestId, chestinv);
		objStore.addItems(chestinv.get(0).getObjectID(), chestinv.get(0));
		objStore.addChest(newChestId, newChest);
		loadingRoom.addToInventory(newChest);
		return new BoardCell(new Position(x, y), newChest, GROUNDTYPEGRASS);
	}

	/**
	 * Create a chest with food and a key inside it
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @param objStore
	 * @return BoardCell with a chest on it
	 */
	public BoardCell addChestTwo(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newChestId = genorateObjectId(GameUtil.CHESTONE, genorateRandomObjectType(1), chestNum++);
		List<GameItem> chestinv = new ArrayList<GameItem>();
		chestinv.add(new Food(genorateObjectId(GameUtil.FOOD, 10, food++), 20));
		chestinv.add(new Key(genorateObjectId(GameUtil.KEY, 10, key++)));
		Chest newChest = new Chest(newChestId, chestinv);
		objStore.addItems(chestinv.get(0).getObjectID(), chestinv.get(0));
		objStore.addItems(chestinv.get(1).getObjectID(), chestinv.get(1));
		objStore.addChest(newChestId, newChest);
		loadingRoom.addToInventory(newChest);
		return new BoardCell(new Position(x, y), newChest, GROUNDTYPEGRASS);
	}

	/**
	 * Create a Northen Door
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @param objStore 
	 * @return boardCell with a northern Door
	 */
	public BoardCell addDoorN(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newDoorId = genorateObjectId(GameUtil.DOORN, genorateRandomObjectType(1), doorNum++);
		Door newDoor = new Door(newDoorId, Direction.NORTH, loadingRoom);
		loadingRoom.addToInventory(newDoor);
		objStore.addDoor(newDoorId, newDoor);
		return new BoardCell(new Position(x, y), newDoor, GROUNDTYPEGRASS);
	}

	/**
	 * Create a Eastern Door
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @param objStore 
	 * @return boardCell with a Eastern Door
	 */
	public BoardCell addDoorE(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newDoorId = genorateObjectId(GameUtil.DOORE, genorateRandomObjectType(1), doorNum++);
		Door newDoor = new Door(newDoorId, Direction.EAST, loadingRoom);
		loadingRoom.addToInventory(newDoor);
		objStore.addDoor(newDoorId, newDoor);
		return new BoardCell(new Position(x, y), newDoor, GROUNDTYPEGRASS);
	}

	/**
	 * Create a Southern Door
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @param objStore 
	 * @return boardCell with a Southern Door
	 */
	public BoardCell addDoorS(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newDoorId = genorateObjectId(GameUtil.DOORS, genorateRandomObjectType(1), doorNum++);
		Door newDoor = new Door(newDoorId, Direction.SOUTH, loadingRoom);
		loadingRoom.addToInventory(newDoor);
		objStore.addDoor(newDoorId, newDoor);
		return new BoardCell(new Position(x, y), newDoor, GROUNDTYPEGRASS);
	}

	/**
	 * Create a Western Door
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @param objStore 
	 * @return boardCell with a Western Door
	 */
	public BoardCell addDoorW(int x, int y, Room loadingRoom, ObjectStorer objStore) {
		int newDoorId = genorateObjectId(GameUtil.DOORW, genorateRandomObjectType(1), doorNum++);
		Door newDoor = new Door(newDoorId, Direction.WEST, loadingRoom);
		loadingRoom.addToInventory(newDoor);
		objStore.addDoor(newDoorId, newDoor);
		return new BoardCell(new Position(x, y), newDoor, GROUNDTYPEGRASS);
	}

	/**
	 * Create a Northern Hedge
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @return boardCell with a Northern Hedge
	 */
	public BoardCell addHedgeL(int x, int y, Room loadingRoom) {
		int newHedgeId = genorateObjectId(GameUtil.HEDGEL, genorateRandomObjectType(1), hedgeNum++);
		Hedge newHedge = new Hedge(newHedgeId);
		loadingRoom.addToInventory(newHedge);
		return new BoardCell(new Position(x, y), newHedge, GROUNDTYPEGRASS);
	}

	/**
	 * Create a Eastern Hedge
	 * 
	 * @param x
	 * @param y
	 * @param loadingRoom
	 * @return boardCell with a Eastern Hedge
	 */
	public BoardCell addHedgeR(int x, int y, Room loadingRoom) {
		int newHedgeId = genorateObjectId(GameUtil.HEDGEL, genorateRandomObjectType(1), hedgeNum++);
		Hedge newHedge = new Hedge(newHedgeId);
		loadingRoom.addToInventory(newHedge);
		return new BoardCell(new Position(x, y), newHedge, GROUNDTYPEGRASS);
	}

	public BoardCell addFenceL(int x, int y, Room loadingRoom) {
		int newFenceId = genorateObjectId(GameUtil.FENCEL, 10, fence++);
		Fence newFence = new Fence(newFenceId);
		loadingRoom.addToInventory(newFence);
		return new BoardCell(new Position(x, y), newFence, GROUNDTYPEGRASS);
	}

	public BoardCell addFenceR(int x, int y, Room loadingRoom) {
		int newFenceId = genorateObjectId(GameUtil.FENCEL, 10, fence++);
		Fence newFence = new Fence(newFenceId);
		loadingRoom.addToInventory(newFence);
		return new BoardCell(new Position(x, y), newFence, GROUNDTYPEGRASS);
	}

}
