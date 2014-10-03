package catgame.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import catgame.gameObjects.MasterObject;
import catgame.gameObjects.PlayableCharacter;
import catgame.logic.GameUtil.Direction;

public class Room {

	private final int roomID;
	private BoardCell[][] roomGrid;
	private List<MasterObject> roomInventory = new ArrayList<MasterObject>();
	private HashMap<Integer, BoardCell> locationMap = new HashMap<Integer, BoardCell>();

	/**
	 * TODO Room parameters are subject to change, once the .xml file readers
	 * are sorted we will have to decide how we want to load rooms
	 * 
	 * @param groundFile
	 * @param objectLayerFile
	 */
	public Room(int roomID, BoardCell[][] room) {
		this.roomID = roomID;
		this.roomGrid = room;
	}

	public void printBoard() {
		for (int x = 0; x < roomGrid.length; x++) {
			String line = "";
			for (int y = 0; y < roomGrid[0].length; y++) {
				line += roomGrid[x][y].toString() + "\t";
			}
			System.out.println(line);
			line = "";
		}

	}

	public BoardCell[][] getBoardGrid() {
		return roomGrid;
	}

	public List<MasterObject> getRoomInventory() {
		return roomInventory;
	}

	/**
	 * 
	 * @param playerID
	 * @param direction
	 */
	public int movePlayer(int playerID, Direction playerDirection) {
		Position newPos = findPosition(playerID, Direction.NORTH, playerDirection);
		if (newPos.getX() < 0 || newPos.getY() < 0 || newPos.getX() > roomGrid.length || newPos.getY() > roomGrid[0].length) {
			System.out.println("New Move Position to x:" + newPos.getX() + " y:" + newPos.getY() + " is not valid");
			return -1;
		}
		if (roomGrid[newPos.getX()][newPos.getY()].getGroundType() != null && roomGrid[newPos.getX()][newPos.getY()].getObjectOnCell() == null) {
			BoardCell oldCell = locationMap.get(playerID);
			roomGrid[newPos.getX()][newPos.getY()].setObjectOnCell(oldCell.removeObjectOnCell());
			return 1;
		}
		System.out.println("Grid @ x:" + newPos.getX() + " y:" + newPos.getY() + " is not empty");
		return -1;
	}

	/**
	 * TODO
	 * 
	 * @param playerID
	 * @param direction
	 */
	public int playerAction(int playerID, int playerDirection) {
		
		return -1;
	}

	/**
	 * TODO
	 * 
	 * @param playerID
	 * @param direction
	 * @param attackPower
	 */
	public int playerAttack(int playerID, int playerDirection, int attackPower) {
		
		return -1;
	}

	/**
	 * 
	 * @return a position on the board from where the player is and
	 *         corrisponding to the movementDirection
	 */
	private Position findPosition(int playerID, Direction boardOrientation, Direction playerDirection) {
		int direction = translateForGid(boardOrientation, playerDirection);
		Position playerPos = locationMap.get(playerID).getPosition();

		switch (direction) {
		case 0:
			new Position(playerPos.getX(), playerPos.getY() - 1);
		case 1:
			new Position(playerPos.getX() + 1, playerPos.getY());
		case 2:
			new Position(playerPos.getX(), playerPos.getY() + 1);
		case 3:
			new Position(playerPos.getX() - 1, playerPos.getY());
		}

		return new Position(0, 0);
	}

	public int getRoomID() {
		return this.roomID;
	}

	/**
	 * finds the direction in which a players action is on the grid no matter
	 * the maps orientation
	 * 
	 * NORTH = UP = 0, EAST = RIGHT = 1, SOUTH = DOWN = 2, WEST = LEFT = 3,
	 * 
	 * @param boardOrientation
	 * @param playerDirection
	 * 
	 */
	public int translateForGid(Direction boardOrientation, Direction playerDirection) {
		return (boardOrientation.getValue() + playerDirection.getValue()) % 4;
	}

	public PlayableCharacter getCharactor(int playerID) {
		return (PlayableCharacter) locationMap.get(playerID).getObjectOnCell();
	}
}