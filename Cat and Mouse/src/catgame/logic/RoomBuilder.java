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
 * Creates rooms by reading in .csv files adds objects and items for them game. 
 * this is a helper class to get the rooms make for saving to xml
 *
 */
public class RoomBuilder {
	private String fileName;
	private BoardCell[][] boardFile;

	public RoomBuilder(String fileName) {
		boardFile = loadBoard(fileName);
	}

	/**
	 * Reads a .cvs file and creates a room for the game.
	 * @param fileName
	 * @return
	 */
	public BoardCell[][] loadBoard(String fileName) {
		BoardCell[][] board = new BoardCell[0][0];
		// make a new board from file
		BufferedReader buffer;

		int y = 0;
		try {
			buffer = new BufferedReader(new FileReader(fileName));
			//Set the formatting for the array
			String formatingLine = buffer.readLine();
			String[] formatArray = formatingLine.split(",");
			int maxY = Integer.parseInt(formatArray[0]);
			int maxX= Integer.parseInt(formatArray[1]);			
			board = new BoardCell[maxY][maxX];
			
			loop: while (true) {
				String line = buffer.readLine();
				if (line == null) {
					break loop;
				}
				String[] values = line.split(",");
				
				//TODO Make it a switch
				for (int x = 0; x < values.length; x++) {
					if (values[x].equals("0")) {
						board[y][x] = addEmptyCell(x, y);
					} else if (values[x].equals("1")) {
						board[y][x] = addGrass(x, y);
					} else if (values[x].equals("2")) {
						board[y][x] = addBush(x, y);
					} else if (values[x].equals("3")) {
						board[y][x] = addTree(x, y);
					} else if (values[x].equals("4")) {
						board[y][x] = addRock(x, y);
					} else {
						System.out.println(values[x]);
					}
				}
				y++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return board;
	}

	public BoardCell addGrass(int x, int y) {
		return new BoardCell(new Position(x, y), null, "Grass");
	}

	public BoardCell addTree(int x, int y) {
		return new BoardCell(new Position(x, y), new Tree(3), "Grass");
	}

	public BoardCell addBush(int x, int y) {
		return new BoardCell(new Position(x, y), new Bush(2), "Grass");
	}

	public BoardCell addRock(int x, int y) {
		return new BoardCell(new Position(x, y), new Rock(4), "Grass");
	}

	public BoardCell addEmptyCell(int x, int y) {
		return new BoardCell(new Position(x, y), null, null);
	}

	public BoardCell[][] getBoardFile() {
		return this.boardFile;
	}

	public static void main(String[] args) {
		 RoomBuilder buildBoard = new RoomBuilder("SwenProjectRoomTestOne.csv");
		 Room testRoom = new Room(1, buildBoard.getBoardFile());
		 testRoom.printBoard();
		
		
		
	}
}
