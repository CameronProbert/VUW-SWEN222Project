package catgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import catgame.gameObjects.Bush;
import catgame.gameObjects.Food;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.PlayableCharacter;
import catgame.gameObjects.Rock;
import catgame.gameObjects.Tree;
import catgame.logic.GameUtil.Direction;

/**
 * 
 * @author Dan Henton
 * 
 *         Creates rooms by reading in .csv files adds objects and items for
 *         them game, Using a parser with the Object ID's we construct the
 *         boardCells for each room File
 * 
 *         this is a helper class to get the rooms make for saving to xml
 *
 */
public class RoomBuilder {
	private String fileName;

	private final String groundTypeGrass = "Grass";

	// Object ID's
	private final int empty = 0;
	private final int grass = 1;

	// characters id's 10 - 19
	private final int playableCharacters = 10;

	private final int bossOne = 11;
	private final int bossTwo = 12;
	private final int bossThree = 13;

	private final int minionOne = 14;
	private final int minionTwo = 15;
	private final int minionThree = 16;
	private final int minionFour = 17;

	// Room accessories 20-39
	private final int bush = 20;
	private final int tree = 21;
	private final int rock = 22;
	private final int hedge = 23;
	private final int fence = 24;
	private final int chestOne = 25;
	private final int chestTwo = 26;
	private final int chestTree = 27;
	private final int chestFour = 28;

	// Game Items
	private final int food = 80;

	// TODO do the doors
	private final int door = 40;

	// Unique Id numbers for objects
	private int roomNum = 0;
	private int playerNum = 10;
	private int minoinNum = 10;
	private int bossNum = 10;
	private int chestNum = 10;
	private int doorNum = 10;
	private int treeNum = 10;
	private int bushNum = 10;
	private int rockNum = 10;

	public RoomBuilder() {

	}

	/**
	 * Reads a .cvs file and creates a room for the game by using a parser, The
	 * correct syntax is required for a room to be built
	 * 
	 * @param fileName
	 * @return
	 */
	public Room loadRoom() {
		// Make the new Room
		Room loadingRoom = new Room(roomNum++);
		System.out.println(loadingRoom.getRoomID());
		// this will need to be added in at the end
		BoardCell[][] board = new BoardCell[0][0];

		BufferedReader buffer;
		int y = 0;
		// Load the file
		try {
			buffer = new BufferedReader(new FileReader("SwenProjectRoomTestOne.csv"));
			// Set the arrays size [y][x]
			String formatingLine = buffer.readLine();
			String[] formatArray = formatingLine.split(",");
			int maxY = Integer.parseInt(formatArray[0]);
			int maxX = Integer.parseInt(formatArray[1]);
			board = new BoardCell[maxY][maxX];

			loop: while (true) {
				String line = buffer.readLine();
				if (line == null) {
					break loop;
				}
				String[] values = line.split(",");

				for (int x = 0; x < values.length; x++) {
					// Parser that decides what is going in the BoardCell[][]
					// using a switch for efficiency
					switch (values[x]) {
					case empty + "":
						board[y][x] = addEmptyCell(x, y, loadingRoom);
						break;
					case grass + "":
						board[y][x] = addGrass(x, y, loadingRoom);
						break;
					case bush + "":
						board[y][x] = addBush(x, y, loadingRoom);
						break;
					case tree + "":
						board[y][x] = addTree(x, y, loadingRoom);
						break;
					case rock + "":
						board[y][x] = addRock(x, y, loadingRoom);
						break;
					case playableCharacters + "":
						board[y][x] = addPlayer(x, y, loadingRoom);
						loadingRoom.addToPlayerLocationMap(board[y][x].getObjectOnCell().getObjectID(), board[x][y]);
						break;
					}
				}
				y++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// now we load board to the room
		loadingRoom.loadBoardCellToRoom(board);
		return loadingRoom;
	}

	/*
	 * Construct an empty BoardCell used for having holes and different room
	 * shapes
	 */
	private BoardCell addEmptyCell(int x, int y, Room room) {
		return new BoardCell(new Position(x, y), null, "");
	}

	/**
	 * Construct a basic BoardCell on that doesn't have any objects on it
	 * 
	 * @param x
	 * @param y
	 * @return new grass BoardCell
	 */
	private BoardCell addGrass(int x, int y, Room room) {
		return new BoardCell(new Position(x, y), null, groundTypeGrass);
	}

	/**
	 * Construct a grass BoardCell thats object is a bush
	 * 
	 * @param x
	 * @param y
	 * @return new bush BoardCell
	 */
	private BoardCell addBush(int x, int y, Room room) {
		int newBushId = genorateObjectId(bush, genorateRandomObjectType(1), bushNum++);
		Bush newBush = new Bush(newBushId);
		room.addToInventory(newBush);
		return new BoardCell(new Position(x, y), newBush, groundTypeGrass);
	}

	/**
	 * Construct a grass BoardCell thats object is a tree
	 * 
	 * @param x
	 * @param y
	 * @return new tree BoardCell
	 */
	private BoardCell addTree(int x, int y, Room room) {
		int newTreeId = genorateObjectId(tree, genorateRandomObjectType(2), treeNum++);
		Tree newTree = new Tree(newTreeId);
		room.addToInventory(newTree);
		return new BoardCell(new Position(x, y), newTree, groundTypeGrass);
	}

	/**
	 * Construct a grass BoardCell thats object is a rock
	 * 
	 * @param x
	 * @param y
	 * @return new bush BoardCell
	 */
	private BoardCell addRock(int x, int y, Room room) {
		int newRockId = genorateObjectId(rock, genorateRandomObjectType(1), rockNum++);
		Rock newRock = new Rock(newRockId);
		room.addToInventory(newRock);
		return new BoardCell(new Position(x, y), newRock, groundTypeGrass);
	}

	/**
	 * Construct a grass BoardCell thats object is a player
	 * 
	 * @param x
	 * @return
	 */
	private BoardCell addPlayer(int x, int y, Room room) {
		int newPlayerId = genorateObjectId(playableCharacters, playerNum, playerNum);
		// TODO OwnerId
		// Create starting items
		Food startingFood = new Food(food, 20);
		List<GameItem> newInv = new ArrayList<GameItem>();
		newInv.add(startingFood);
		PlayableCharacter newPlayableCharacter = new PlayableCharacter(newPlayerId, newPlayerId, room, Direction.NORTH, 10, 100, newInv);
		room.addToInventory(newPlayableCharacter);
		return new BoardCell(new Position(x, y), newPlayableCharacter, groundTypeGrass);
	}

	/**
	 * use for quick testing in the roomBuilder
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RoomBuilder buildBoard = new RoomBuilder();
		buildBoard.loadRoom();
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
