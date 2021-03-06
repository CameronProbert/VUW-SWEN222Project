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
	 * Rooms are a smart controller for any action that a player does inside the room.
	 * A Room holds the games boardCell which is a grind of cells holding gameobjects
	 * to construct the game.
	 * 
	 * @param groundFile
	 * @param objectLayerFile
	 */
	public Room(int roomID) {
		this.roomID = roomID;
	}

	public BoardCell[][] getBoardGrid() {
		return roomGrid;
	}

	public List<GameObject> getRoomInventory() {
		return roomInventory;
	}

	/**
	 * Move a player around a room, Check to see if the move is valid(the move
	 * is on the board and there is an empty space. The player only moves if it is facing in the
	 * direction otherwise change the direction that the player is facing
	 * 
	 * @param playerID
	 * @param direction
	 * @return unsuccesful = -1, succesful = 1 facing direction changed = 2 , entered a door = 3
	 */
	public int movePlayer(int playerID, Direction playerDirection, Direction boardDirection) {
		if (!(getCharactorCell(playerID).getObjectOnCell() instanceof PlayableCharacter) && getCharactorCell(playerID).getObjectOnCell().getObjectID() != playerID) {
			throw new GameError("PlayerLocationMap isn't pointing to a player");
		}
		if (((PlayableCharacter) getCharactorCell(playerID).getObjectOnCell()).isDead()) {
			System.out.println("PLAYER IS DEAD!");
		}

		if (directionTranslator(boardDirection,playerDirection) != ((PlayableCharacter) getCharactorCell(playerID).getObjectOnCell()).getFacingDirection().getValue() ) {
			((PlayableCharacter) getCharactorCell(playerID).getObjectOnCell()).changeDirection(getNewDirection(boardDirection, playerDirection));
//			System.err.println("Board Dir :" + boardDirection + " player Dir :" + playerDirection + " current dir: "
//					+ ((PlayableCharacter) getCharactorCell(playerID).getObjectOnCell()).getFacingDirection());
			return 2;
		}

		Position newPos = findPosition(playerID, boardDirection, playerDirection);
		// Check if the move is on the board
		if (newPos.getX() < 0 || newPos.getY() < 0 || newPos.getX() >= roomGrid.length || newPos.getY() >= roomGrid[0].length) {
			System.out.println("New Move Position to x:" + newPos.getX() + " y:" + newPos.getY() + " is not valid");
			return -1;
		}
		//TODO
		//If the object infront of the player is a door try and use it
		if (roomGrid[newPos.getY()][newPos.getX()].getGroundType() != null && roomGrid[newPos.getY()][newPos.getX()].getGroundType().equals("Grass")) {
			if (roomGrid[newPos.getY()][newPos.getX()].getObjectOnCell() instanceof Door) {
				System.out.println("DOOR AHEADS ID :" + roomGrid[newPos.getY()][newPos.getX()].getObjectOnCell().getObjectID());
				return useDoor(playerID, (Door) roomGrid[newPos.getY()][newPos.getX()].getObjectOnCell());
			}
			// Check that the next Position is empty then move the player
			if (roomGrid[newPos.getY()][newPos.getX()].getObjectOnCell() == null) {
				BoardCell oldCell = playerLocationMap.get(playerID);
				roomGrid[newPos.getY()][newPos.getX()].setObjectOnCell(oldCell.removeObjectOnCell());
				playerLocationMap.put(playerID, roomGrid[newPos.getY()][newPos.getX()]);
				return 1;
			}
		}
		// the move wasn't successful;
		System.out.println("Grid @ x:" + newPos.getX() + " y:" + newPos.getY() + " is not empty");
		return -1;
	}

	/**
	 * Removes the player from the current Room and moves the player to the
	 * exitingSides Room and puts the player in front of the exiting sides door
	 * 
	 * @param playerID
	 * @param door
	 */
	private int useDoor(int playerID, Door door) {
		if (door.getIsLocked()) {
			System.out.println("Unlocking Door");
			if (((PlayableCharacter) playerLocationMap.get(playerID).getObjectOnCell()).hasKey()) {
				System.out.println("Unlocked Door");
				door.unlockDoor(((PlayableCharacter) playerLocationMap.get(playerID).getObjectOnCell()).useKey());
			} else {
				System.out.println("Door is locked and requires a key");
				return -1;
			}
		}
		if (door.enterDoor().checkExitDoor()) {
			PlayableCharacter player = (PlayableCharacter) playerLocationMap.get(playerID).getObjectOnCell();
			BoardCell delete = playerLocationMap.get(playerID);
			playerLocationMap.remove(playerID);
			roomInventory.remove(player);
			delete.removeObjectOnCell();
			door.enterDoor().exitDoor(player);
			return 3;
		} else {
			System.out.println("SOMETHING IS INFRONT OF THE DOOR");
		}
		return -1;
	}

	/**
	 * Used for attacking npc if they are in the direction you are facing
	 * 
	 * @param playerID
	 * @param direction
	 */
	public int playerAction(int playerID, Direction boardDirection) {
		if (playerLocationMap.get(playerID).getObjectOnCell() instanceof PlayableCharacter) {
			BoardCell playersCell = playerLocationMap.get(playerID);
			PlayableCharacter player = (PlayableCharacter) playersCell.getObjectOnCell();
			Position actionPosition = findPosition(playerID, boardDirection, player.getFacingDirection());
			//check if the position is on the board
			if(actionPosition.getY() < 0 || actionPosition.getX() < 0 || actionPosition.getY() > roomGrid.length || actionPosition.getX() > roomGrid[0].length){
				return -1;
			}
			// Check to see if a npc is there then we can attack it 
			if (roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell() != null 
					&& roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell() instanceof NonPlayableCharacter) {
				// if the npc is not dead attack it
				if (!((NonPlayableCharacter) roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell()).isDead()) {
					NonPlayableCharacter npc = (NonPlayableCharacter) roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell();
					npc.changeHealth(-player.getAttackPower());
					player.changeHealth(-npc.getAttackPower());
					return 1;
				}
			}
		}
		return -1;
	}

	/**
	 * 
	 * Check to see if the there is a chest of a dead npc infront of the char if
	 * there is we can then loot the items from it
	 * 
	 * @param playerID
	 * @param boardDirection
	 * @return
	 */
	public int PlayerLoot(int playerID, Direction boardDirection) {
		if (playerLocationMap.get(playerID).getObjectOnCell() instanceof PlayableCharacter) {
			BoardCell playersCell = playerLocationMap.get(playerID);
			PlayableCharacter player = (PlayableCharacter) playersCell.getObjectOnCell();
			Position actionPosition = findPosition(playerID, boardDirection, player.getFacingDirection());

			// Check to see if a npc is there then we can attack it
			if (roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell() != null
					&& roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell() instanceof NonPlayableCharacter) {
				// if the npc is dead loot the body
				if (((NonPlayableCharacter) roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell()).isDead()) {
					List<GameItem> npcInv = ((NonPlayableCharacter) roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell()).getInventory();
					switch (player.addAllToInventory(npcInv)) {
					case 1:
						((NonPlayableCharacter) roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell()).removeAllFromInv();
						return 1;
					case -1:
						return -1;
					}
				}// lootChest
			} else if (roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell() != null && roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell() instanceof Chest) {
				List<GameItem> chestInv = ((Chest) roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell()).getLoot();
				switch (player.addAllToInventory(chestInv)) {
				case 1:
					System.out.println("LOOTING CHEST");
					((Chest) roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell()).removeInv();
					return 1;
				case -1:
					return -1;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Return the GameObject ahead of a character if there is no object it will
	 * return null
	 * 
	 * @param playerID
	 * @param boardDirection
	 * @return
	 */
	public GameObject getObjectAheadOfCharactor(int playerID, Direction boardDirection) {
		GameObject returnObj = null;
		if (playerLocationMap.get(playerID).getObjectOnCell() instanceof PlayableCharacter) {
			BoardCell playersCell = playerLocationMap.get(playerID);
			PlayableCharacter player = (PlayableCharacter) playersCell.getObjectOnCell();
			Position actionPosition = findPosition(playerID, boardDirection, player.getFacingDirection());

			if (actionPosition.getX() < 0 || actionPosition.getY() < 0 || actionPosition.getX() >= roomGrid.length || actionPosition.getY() >= roomGrid[0].length) {
				return null;
			}
			returnObj = roomGrid[actionPosition.getY()][actionPosition.getX()].getObjectOnCell();
		}
		return returnObj;
	}

	/**
	 * Force a gameState update called by the networking
	 * 
	 * @param playerID
	 * @param pos
	 * @param dir
	 * @return
	 */
	public int forcePlayerMove(int playerID, Position pos, Direction dir) {
		BoardCell oldCell = playerLocationMap.get(playerID);
		roomGrid[pos.getY()][pos.getX()].setObjectOnCell(oldCell.removeObjectOnCell());
		playerLocationMap.put(playerID, roomGrid[pos.getY()][pos.getX()]);
		((PlayableCharacter) playerLocationMap.get(playerID).getObjectOnCell()).changeDirection(dir);
		return 1;
	}


	/**
	 * 
	 * @return a position on the board from where the player is and
	 *         Corresponding to the movementDirection
	 */
	private Position findPosition(int playerID, Direction boardOrientation, Direction playerDirection) {
		Position playerPos = playerLocationMap.get(playerID).getPosition();
		switch (boardOrientation.getValue()) {
		case 0:
			switch (playerDirection.getValue()) {
			case 0:
				return new Position(playerPos.getX(), playerPos.getY() - 1);
			case 1:
				return new Position(playerPos.getX() + 1, playerPos.getY());
			case 2:
				return new Position(playerPos.getX(), playerPos.getY() + 1);
			case 3:
				return new Position(playerPos.getX() - 1, playerPos.getY());
			}
		case 1:
			switch (playerDirection.getValue()) {
			case 0:
				return new Position(playerPos.getX() + 1, playerPos.getY());
			case 1:
				return new Position(playerPos.getX(), playerPos.getY() + 1);
			case 2:
				return new Position(playerPos.getX() - 1, playerPos.getY());
			case 3:
				return new Position(playerPos.getX(), playerPos.getY() - 1);
			}
		case 2:
			switch (playerDirection.getValue()) {
			case 0:
				return new Position(playerPos.getX(), playerPos.getY() + 1);
			case 1:
				return new Position(playerPos.getX() - 1, playerPos.getY());
			case 2:
				return new Position(playerPos.getX(), playerPos.getY() - 1);
			case 3:
				return new Position(playerPos.getX() + 1, playerPos.getY());
			}
		case 3:
			switch (playerDirection.getValue()) {
			case 0:
				return new Position(playerPos.getX() - 1, playerPos.getY());
			case 1:
				return new Position(playerPos.getX(), playerPos.getY() - 1);
			case 2:
				return new Position(playerPos.getX() + 1, playerPos.getY());
			case 3:
				return new Position(playerPos.getX(), playerPos.getY() + 1);
			}
		}
		throw new GameError("Find Position Couldn't find a new Position for :");
	}
	
	/**
	 * Finds and returns the correct facing direction rather than a moving
	 * direction
	 * 
	 * @param movementDirection
	 * @return
	 */
	public static Direction getNewDirection(Direction boardDirection, Direction movementDirection) {
		switch (boardDirection.getValue()) {
		case 0:
			switch (movementDirection.getValue()) {
			case 0:
				return Direction.NORTH;
			case 1:
				return Direction.EAST;
			case 2:
				return Direction.SOUTH;
			case 3:
				return Direction.WEST;
			}

		case 1:
			switch (movementDirection.getValue()) {
			case 0:
				return Direction.EAST;
			case 1:
				return Direction.SOUTH;
			case 2:
				return Direction.WEST;
			case 3:
				return Direction.NORTH;
			}
		case 2:
			switch (movementDirection.getValue()) {
			case 0:
				return Direction.SOUTH;
			case 1:
				return Direction.WEST;
			case 2:
				return Direction.NORTH;
			case 3:
				return Direction.EAST;
			}
		case 3:
			switch (movementDirection.getValue()) {
			case 0:
				return Direction.WEST;
			case 1:
				return Direction.NORTH;
			case 2:
				return Direction.EAST;
			case 3:
				return Direction.SOUTH;
			}
		}
		System.err.println("CANNOT FIND A FACING DIR SET IT TO NORTH");
		return Direction.NORTH;
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

	
	public HashMap<Integer, BoardCell> getDoorsLocation() {
		return doorsLocation;
	}

	public void addToDoorsLocation(int doorId, BoardCell cell) {
		doorsLocation.put(doorId, cell);
	}

	public BoardCell findDoor(int doorID) {
		return doorsLocation.get(doorID);
	}

	public PlayableCharacter getPlayerInRoom(int playerID) {
		return (PlayableCharacter) playerLocationMap.get(playerID).getObjectOnCell();
	}

	public void forceUseDoor(int playerId, Direction boardDirection) {
		Position newPos = findPosition(playerId, boardDirection, ((PlayableCharacter) getCharactorCell(playerId).getObjectOnCell()).getFacingDirection());
		if (roomGrid[newPos.getY()][newPos.getX()].getObjectOnCell() instanceof Door) {
			//System.out.println("DOOR AHEADS ID :" + roomGrid[newPos.getY()][newPos.getX()].getObjectOnCell().getObjectID());
			 useDoor(playerId, (Door) roomGrid[newPos.getY()][newPos.getX()].getObjectOnCell());
		}
	}	
}