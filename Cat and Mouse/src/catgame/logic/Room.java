package catgame.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import catgame.gameObjects.*;
import catgame.logic.GameUtil.Direction;

/**
 * 
 * @author Dan Henton
 * 
 *         A Room inside the Game this holds a BoardCell[][] in for the game.
 *         all of the actions that require a player to see what is going on
 *         around its boardCell is done here. The room is designed this way so
 *         that it has low coupling
 *
 */
public class Room {

	private final int roomID;
	private BoardCell[][] roomGrid;
	private List<GameObject> roomInventory = new ArrayList<GameObject>();
	private HashMap<Integer, BoardCell> playerLocationMap = new HashMap<Integer, BoardCell>();
	private HashMap<Integer, BoardCell> doorsLocation = new HashMap<Integer, BoardCell>();

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
				// TODO remove cheap fix
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
	 * Move a player around a room, Check to see if the move is valid(the move
	 * is on the board and there is an empty space
	 * 
	 * //TODO fix it such that the player only moves if it is facing in the
	 * direction otherwise change the direction that the player is facing
	 * 
	 * @param playerID
	 * @param direction
	 */
	public int movePlayer(int playerID, Direction playerDirection, Direction boardDirection) {
		// TODO add the direction facing stuff here

		if (!(getCharactorCell(playerID).getObjectOnCell() instanceof PlayableCharacter) && getCharactorCell(playerID).getObjectOnCell().getObjectID() != playerID) {
			throw new GameError("PlayerLocationMap isn't pointing to a player");
		}
		if (((PlayableCharacter) getCharactorCell(playerID).getObjectOnCell()).isDead()) {
			throw new GameError("Player " + playerID + " is dead!");
		}
		if (((PlayableCharacter) getCharactorCell(playerID).getObjectOnCell()).getFacingDirection().getValue() != playerDirection.getValue()) {
			((PlayableCharacter) getCharactorCell(playerID).getObjectOnCell()).changeDirection(getNewDirection(playerDirection));
			return -1;
		}

		Position newPos = findPosition(playerID, boardDirection , playerDirection);
		// Check if the move is on the board
		if (newPos.getX() < 0 || newPos.getY() < 0 || newPos.getX() > roomGrid.length || newPos.getY() > roomGrid[0].length) {
			System.out.println("New Move Position to x:" + newPos.getX() + " y:" + newPos.getY() + " is not valid");
			return -1;
		}
		// Check that the next Position is empty then move the player
		if (roomGrid[newPos.getY()][newPos.getX()].getGroundType() != null && roomGrid[newPos.getY()][newPos.getX()].getObjectOnCell() == null) {
			BoardCell oldCell = playerLocationMap.get(playerID);
			roomGrid[newPos.getY()][newPos.getX()].setObjectOnCell(oldCell.removeObjectOnCell());
			playerLocationMap.put(playerID, roomGrid[newPos.getY()][newPos.getX()]);
			return 1;
		}
		// the move wasn't successful;
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
	public int playerAttack(int playerID, Direction boardDirection) {
		if (playerLocationMap.get(playerID).getObjectOnCell() instanceof PlayableCharacter) {
			BoardCell playersCell = playerLocationMap.get(playerID);
			PlayableCharacter player = (PlayableCharacter) playersCell.getObjectOnCell();
			Position attackPosition = findPosition(playerID, boardDirection, player.getFacingDirection());
			//Check to see if a npc is there then we can attack it
			if (roomGrid[attackPosition.getY()][attackPosition.getX()].getObjectOnCell() != null
					&& roomGrid[attackPosition.getY()][attackPosition.getX()].getObjectOnCell() instanceof NonPlayableCharacter) {
				NonPlayableCharacter npc = (NonPlayableCharacter) roomGrid[attackPosition.getY()][attackPosition.getX()].getObjectOnCell();
				npc.changeHealth(-player.getAttackPower());
				player.changeHealth(-npc.getAttackPower());
				return 1;
			}
		}

		return -1;
	}

	/**
	 * 
	 * @return a position on the board from where the player is and
	 *         Corresponding to the movementDirection
	 */
	private Position findPosition(int playerID, Direction boardOrientation, Direction playerDirection) {
		int direction = directionTranslator(boardOrientation, playerDirection);
		Position playerPos = playerLocationMap.get(playerID).getPosition();
		switch (direction) {
		case 0:
			return new Position(playerPos.getX(), playerPos.getY() - 1);
		case 1:
			return new Position(playerPos.getX() + 1, playerPos.getY());
		case 2:
			return new Position(playerPos.getX(), playerPos.getY() + 1);
		case 3:
			return new Position(playerPos.getX() - 1, playerPos.getY());
		}
		// should be dead Code just in case its not
		throw new GameError("Find Position Couldn't find a new Position for :" + direction);
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
	public int directionTranslator(Direction boardOrientation, Direction playerDirection) {
		return (boardOrientation.getValue() + playerDirection.getValue()) % 4;
	}

	public BoardCell getCharactorCell(int playerID) {
		return playerLocationMap.get(playerID);
	}

	public void loadBoardCellToRoom(BoardCell[][] newRoom) {
		this.roomGrid = newRoom;
	}

	public void addToInventory(GameObject object) {
		this.roomInventory.add(object);
	}

	public void removeFromInventory(GameObject object) {

	}

	public void addToPlayerLocationMap(int playersID, BoardCell cell) {
		playerLocationMap.put(playersID, cell);
	}

	public void RemoveFromPlayerLocationMap(int playersID) {
		playerLocationMap.remove(playersID);
	}

	/**
	 * Finds and returns the correct facing direction rather than a moving
	 * direction
	 * 
	 * @param movementDirection
	 * @return
	 */
	public Direction getNewDirection(Direction movementDirection) {
		switch (movementDirection.getValue()) {
		case 0:
			return Direction.NORTH;
		case 1:
			return Direction.EAST;
		case 2:
			return Direction.SOUTH;
		}
		return Direction.WEST;
	}

	public HashMap<Integer, BoardCell> getDoorsLocation() {
		return doorsLocation;
	}

	public void addToDoorsLocation(int doorId, BoardCell cell) {
		doorsLocation.put(doorId, cell);
	}
	
	public BoardCell findDoor(int doorID){
		return doorsLocation.get(doorID);
	}
}