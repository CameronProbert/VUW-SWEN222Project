package catgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;

import catgame.gameObjects.Bush;
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
	
	//Object ID's
	private final int empty = 0;
	private final int grass = 1;
	
	//characters id's 10 - 19
	private final int playableCharactors = 10;
	private final int bossOne = 11;
	private final int bossTwo = 12;
	private final int bossThree = 13;
	private final int minion = 1;
	
	//Room accessories 20-39
	private final int bushOne = 20;
	private final int bushTwo = 21;
	private final int bushThree = 22;
	private final int treeOne = 23;
	private final int treeTwo = 24;
	private final int treeThree = 25;
	private final int rockOne = 26;
	private final int rockTwo = 27;
	private final int rockThree = 28;
	private final int hedgeOne = 29;
	private final int hedgeTwo = 30;
	private final int hedgeThree = 31;
	
	
	private final int door = 40;

	// Unique Id numbers for objects
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
	 * Reads a .cvs file and creates a room for the game by using a parser,
	 * The correct syntax is required for a room to be built
	 * 
	 * @param fileName
	 * @return
	 */
	public BoardCell[][] loadBoard() {
		BoardCell[][] board = new BoardCell[0][0];
		BufferedReader buffer;
		int y = 0;
		//Load the file
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
					//Parser that decides what is going in the BoardCell[][] using a switch for efficiency 
					switch (values[x]) {
					case empty + "":
						board[y][x] = addEmptyCell(x, y);
						break;
					case grass + "":
						board[y][x] = addGrass(x, y);
						break;
					case bush + "":
						board[y][x] = addBush(x, y);
						break;
					case tree + "":
						board[y][x] = addTree(x, y);
						break;
					case rock + "":
						board[y][x] = addRock(x, y);
						break;
					}
				}
				y++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return board;
	}
	/*
	 * Construct an empty BoardCell used for having holes and different room shapes
	 */
	public BoardCell addEmptyCell(int x, int y) {
		return new BoardCell(new Position(x, y), null, "");
	}

	/**
	 * Construct a basic BoardCell on that doesn't have any objects on it
	 * @param x
	 * @param y
	 * @return new grass BoardCell
	 */
	public BoardCell addGrass(int x, int y) {
		return new BoardCell(new Position(x, y), null, "Grass");
	}
	/**
	 * Construct a grass BoardCell thats object is a bush
	 * @param x
	 * @param y
	 * @return new bush BoardCell
	 */
	public BoardCell addBush(int x, int y) {
		int newBushId = genorateObjectId(bush, 10, bushNum);
		bushNum++;
		return new BoardCell(new Position(x, y), new Bush(newBushId), "Grass");
	}
	
	/**
	 * Construct a grass BoardCell thats object is a tree
	 * @param x
	 * @param y
	 * @return new tree BoardCell
	 */
	public BoardCell addTree(int x, int y) {
		int newTreeId = genorateObjectId(tree, 10, treeNum);
		treeNum++;
		return new BoardCell(new Position(x, y), new Tree(3), "Grass");
	}
	
	/**
	 * Construct a grass BoardCell thats object is a rock
	 * @param x
	 * @param y
	 * @return new bush BoardCell
	 */
	public BoardCell addRock(int x, int y) {
		int newRockId = genorateObjectId(rock, 10, rockNum);
		rockNum++;
		return new BoardCell(new Position(x, y), new Rock(4), "Grass");
	}
	
	
	/**
	 * use for quick testing in the roomBuilder
	 * @param args
	 */
	public static void main(String[] args) {
		RoomBuilder buildBoard = new RoomBuilder();
		Room testRoom = new Room(1, buildBoard.loadBoard());
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
		System.out.println(result);
		if(result.length() != 6){
			throw new GameError("InvalidObject ID:"+result);
		}
		return Integer.parseInt(result);
	}
}
