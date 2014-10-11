package catgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;
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

	private int roomNum = 0;

	// Game Items
	private final int food = 80; // Don't chnage

	// TODO do the doors
	private final int door = 40;

	private ObjectBuilder objBuilder;

	public RoomBuilder() {
		objBuilder = new ObjectBuilder();
	}

	/**
	 * Reads a .cvs file and creates a room for the game by using a parser, The
	 * correct syntax is required for a room to be built
	 * 
	 * @param fileName
	 * @return
	 */
	public Room loadRoom(ObjectStorer objStore) {
		// Make the new Room
		Room loadingRoom = new Room(roomNum++);
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
					case GameUtil.EMPTY + "":
						board[y][x] = objBuilder.addEmptyCell(x, y, loadingRoom);
						break;
					case GameUtil.GRASS + "":
						board[y][x] = objBuilder.addGrass(x, y, loadingRoom);
						break;
					case GameUtil.BUSH + "":
						board[y][x] = objBuilder.addBush(x, y, loadingRoom);
						break;
					case GameUtil.TREE + "":
						board[y][x] = objBuilder.addTree(x, y, loadingRoom);
						break;
					case GameUtil.ROCK + "":
						board[y][x] = objBuilder.addRock(x, y, loadingRoom);
						break;
					case GameUtil.PLAYABLECHARACTER + "":
						board[y][x] = objBuilder.addPlayer(x, y, loadingRoom, objStore);
						loadingRoom.addToPlayerLocationMap(board[y][x].getObjectOnCell().getObjectID(), board[y][x]);
						break;
					case GameUtil.MINIONONE + "":
						board[y][x] = objBuilder.addMinionOne(x, y, loadingRoom, objStore);
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

}
