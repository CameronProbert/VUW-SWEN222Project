package catgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;

import catgame.gameObjects.Bush;
import catgame.gameObjects.Rock;
import catgame.gameObjects.Tree;
import catgame.logic.GameUtil.Direction;

public class RoomBuilder {
	private String fileName;
	private BoardCell[][] boardFile;

	public RoomBuilder() {
		boardFile = loadBoard();
	}

	public BoardCell[][] loadBoard() {
		BoardCell[][] board = new BoardCell[6][6];
		// make a new board from file
		BufferedReader buffer;

		int y = 0;
		try {
			buffer = new BufferedReader(new FileReader("SwenProjectRoomTestOne.csv"));
			loop: while (true) {
				String line = buffer.readLine();
				if (line == null) {
					break loop;
				}
				String[] values = line.split(",");
				for (int x = 0; x < values.length; x++) {
					if (values[x].equals("0")) {
						board[x][y] = addEmptyCell(x, y);
					} else if (values[x].equals("1")) {
						board[x][y] = addGrass(x, y);
					} else if (values[x].equals("2")) {
						board[x][y] = addBush(x, y);
					} else if (values[x].equals("3")) {
						board[x][y] = addTree(x, y);
					} else if (values[x].equals("4")) {
						board[x][y] = addRock(x, y);
					} else {
						System.out.println(values[x]);
					}
				}
				y++;
				if(y == board.length){
					break loop;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		String boardTest = "";

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
		// RoomBuilder buildBoard = new RoomBuilder();
		// Room testRoom = new Room(1, buildBoard.getBoardFile());
		// testRoom.printBoard();
		
		
		
	}

}
