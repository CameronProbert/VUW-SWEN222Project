package catgame.gameObjects;

import java.awt.Dimension;

import catgame.logic.BoardCell;
import catgame.logic.GameUtil.Direction;
import catgame.logic.Room;

/**
 * 
 * @author Dan
 *
 *         Door Object in the Game this object holds another Door that is the other side
 *         when a player exits the door it is moved to the boardCell inwards in the gameBoard
 *         a door is locked iff when the other side of a door is loaded the key != 0, otherwise the door is always unlocked (islocked = false)
 *        
 */
public class Door implements NonMovavble {
	private final int ID;
	private Door entranceToDoor;
	private boolean isLocked = false;
	private int keyID;
	Direction wallEdge;
	private Room room;

	/**
	 * The GameObject Door which can be locked and opened by keys. When created
	 * if keyID != 0 then keys ID will be the only thing that will unlock the
	 * door
	 * 
	 * @param id
	 * @param door
	 *            ID to the other side of the door
	 * @param keyId
	 *            (If keyID == 0 then there is no key for the door)
	 */
	public Door(int id, Direction direction, Room room) {
		this.ID = id;
		this.wallEdge = direction;
		this.room = room;
	}

	public int getObjectID() {
		return ID;
	}
	
	/**
	 * Add the other side of the door this needs to be called once an entire level has been build as it links rooms together
	 * if the key != 0 then the door is locked. 
	 * @param entranceTo
	 * @param keyId
	 */
	public void addOtherSide(Door entranceTo, int keyId) {
		this.entranceToDoor = entranceTo;
		// this.wallEdge = wallEdge;
		if (keyId != 0) {
			System.out.println("LOCKING DOOR :" + ID);
			this.isLocked = true;
			this.keyID = keyId;
		}
	}

	/**
	 * Enters the door
	 * 
	 * @return The Other side of the door
	 */
	public Door enterDoor() {
		return this.entranceToDoor;
	}

	/**
	 * Unlock the door using a keyId if the key ID matches the keyID for the
	 * door then it will be unlocked
	 * 
	 * @param int : keyId
	 * @return boolean : current state of the door
	 */
	public boolean unlockDoor(Key key) {
		if (key != null) {
			this.isLocked = false;
		}
		return this.isLocked;
	}

	public boolean getIsLocked() {
		return isLocked;
	}

	public Direction getDoorsWallEdge() {
		return this.wallEdge;
	}
	
	/**
	 * added the player to the room infront of the door pointing towards the centre
	 * @param player
	 */
	public void exitDoor(PlayableCharacter player) {
		BoardCell doorsCell = room.getDoorsLocation().get(ID);
		BoardCell newPlayersCell = null;

		switch (wallEdge.getValue()) {
		case 0:
			newPlayersCell = room.getBoardGrid()[doorsCell.getPosition().getY() + 1][doorsCell.getPosition().getX()];
			break;
		case 1:
			newPlayersCell = room.getBoardGrid()[doorsCell.getPosition().getY()][doorsCell.getPosition().getX() - 1];
			break;
		case 2:
			newPlayersCell = room.getBoardGrid()[doorsCell.getPosition().getY() - 1][doorsCell.getPosition().getX()];
			break;
		case 3:
			newPlayersCell = room.getBoardGrid()[doorsCell.getPosition().getY()][doorsCell.getPosition().getX() + 1];
			break;

		}
		newPlayersCell.setObjectOnCell(player);
		room.addToPlayerLocationMap(player.getObjectID(), newPlayersCell);
		room.getRoomInventory().add(player);

	}
	
	/**
	 * check to see that the other side of a door is clear before a player can move through it
	 * @return
	 */
	public boolean checkExitDoor() {
		BoardCell doorsCell = room.getDoorsLocation().get(ID);
		switch (wallEdge.getValue()) {
		case 0:
			if (room.getBoardGrid()[doorsCell.getPosition().getY() + 1][doorsCell.getPosition().getX()].getObjectOnCell() == null) {
				return true;
			} else {
				return false;
			}
		case 1:
			if (room.getBoardGrid()[doorsCell.getPosition().getY()][doorsCell.getPosition().getX() - 1].getObjectOnCell() == null) {
				return true;
			} else {
				return false;
			}
		case 2:
			if (room.getBoardGrid()[doorsCell.getPosition().getY() - 1][doorsCell.getPosition().getX()].getObjectOnCell() == null) {
				return true;
			} else {
				return false;
			}
		case 3:
			if (room.getBoardGrid()[doorsCell.getPosition().getY()][doorsCell.getPosition().getX() + 1].getObjectOnCell() == null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public Room getRoom() {
		return room;
	}

	public void setIsLocked(boolean b) {
		this.isLocked = b;
	}

	public void setKeyId(int id) {
		this.keyID = id;
	}
}
