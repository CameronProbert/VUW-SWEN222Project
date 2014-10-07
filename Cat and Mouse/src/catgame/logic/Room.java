package catgame.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import catgame.gameObjects.GameObject;
import catgame.gameObjects.MasterObject;
import catgame.gameObjects.PlayableCharacter;
import catgame.gui.renderpanel.RenderPanel;
import catgame.logic.GameUtil.Direction;

/**
 * 
 * @author Dan Henton
 * 
 * A Room inside the Game this holds a BoardCell[][] in for the game.
 * all of the actions that require a player to see what is going on around its boardCell is done here.
 * The room is designed this way so that it has low coupling 
 *
 */
public class Room {

	private final int roomID;
	private BoardCell[][] roomGrid;
	private List<GameObject> roomInventory = new ArrayList<GameObject>();
	private HashMap<Integer, BoardCell> playerLocationMap = new HashMap<Integer, BoardCell>();

	/**
	 * TODO Room parameters are subject to change, once the .xml file readers
	 * are sorted we will have to decide how we want to load rooms
	 * 
	 * @param groundFile
	 * @param objectLayerFile
	 */
	public Room(int roomID) {
		this.roomID = roomID;
	}

	public void printBoard() {
		for (int x = 0; x < roomGrid.length; x++) {
			String line = "";
			for (int y = 0; y < roomGrid[0].length; y++) {
				//TODO remove cheap fix
				if (roomGrid[x][y] != null) {
					line += roomGrid[x][y].toString() + "\t";
				}
			}
			System.out.println(line);
			line = "";
		}

	}

	public BoardCell[][] getBoardGrid() {
		return roomGrid;
	}

	public List<GameObject> getRoomInventory() {
		return roomInventory;
	}

	/**
	 *  Move a player around a room,
	 *  Check to see if the move is valid(the move is on the board and there is an empty space
	 *  
	 *  //TODO fix it such that the player only moves if it is facing in the direction otherwise change the direction that the player is facing
	 *  
	 * @param playerID
	 * @param direction
	 */
	public int movePlayer(int playerID, Direction playerDirection) {
		//TODO add the direction facing stuff here
		
		Position newPos = findPosition(playerID, RenderPanel.viewDirection, playerDirection);
		//Check if the move is on the board
		if (newPos.getX() < 0 || newPos.getY() < 0 || newPos.getX() > roomGrid.length || newPos.getY() > roomGrid[0].length) {
			System.out.println("New Move Position to x:" + newPos.getX() + " y:" + newPos.getY() + " is not valid");
			return -1;
		}
		//Check that the next Position is empty then move the player
		if (roomGrid[newPos.getX()][newPos.getY()].getGroundType() != null && roomGrid[newPos.getX()][newPos.getY()].getObjectOnCell() == null) {
			BoardCell oldCell = playerLocationMap.get(playerID);
			roomGrid[newPos.getX()][newPos.getY()].setObjectOnCell(oldCell.removeObjectOnCell());
			playerLocationMap.put(playerID, roomGrid[newPos.getY()][newPos.getY()]);
			return 1;
		}
		//the move wasn't successful;
		System.out.println("Grid @ x:" + newPos.getX() + " y:" + newPos.getY() + " is not empty");
		return -1;
	}

	/**
	 * TODO check that that move is working then once it is this should be easy
	 * 
	 * @param playerID
	 * @param direction
	 */
	public int playerAction(int playerID, int playerDirection) {

		return -1;
	}

	/**
	 * TODO check that that move is working then once it is this should be easy
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
	 *         Corresponding to the movementDirection
	 */
	private Position findPosition(int playerID, Direction boardOrientation, Direction playerDirection) {
		int direction = translateForGrid(boardOrientation, playerDirection);
		Position playerPos = playerLocationMap.get(playerID).getPosition();

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
	public int translateForGrid(Direction boardOrientation, Direction playerDirection) {
		return (boardOrientation.getValue() + playerDirection.getValue()) % 4;
	}

	public PlayableCharacter getCharactor(int playerID) {
		return (PlayableCharacter) playerLocationMap.get(playerID).getObjectOnCell();
	}
	
	public void loadBoardCellToRoom(BoardCell[][] newRoom){
		this.roomGrid = newRoom;
	}
	
	public void addToInventory(GameObject object){
		this.roomInventory.add(object);
	}
	
	public void removeFromInventory(GameObject object){
		
	}
	
	public void addToPlayerLocationMap(int playersID , BoardCell cell){
		playerLocationMap.put(playersID, cell);
	}
	
	public void RemoveFromPlayerLocationMap(int playersID){
		playerLocationMap.remove(playersID);
	}
}