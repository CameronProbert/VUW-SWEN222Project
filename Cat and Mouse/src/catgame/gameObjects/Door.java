package catgame.gameObjects;

import java.awt.Dimension;

import catgame.logic.BoardCell;
import catgame.logic.GameUtil.Direction;
import catgame.logic.Room;

public class Door implements NonMovavble {
	private final int ID;
	private Door entranceToDoor;
	private boolean isLocked = true;
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

	public void addOtherSide(Door entranceTo, int keyId) {
		this.entranceToDoor = entranceTo;
		// this.wallEdge = wallEdge;
		if (keyId != 0) {
			System.out.println("LOCKING DOOR :"+ID);
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
		if (this.keyID == key.getObjectID()) {
			this.isLocked = false;
		}
		return this.isLocked;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public Direction getDoorsWallEdge() {
		return this.wallEdge;
	}

	public void exitDoor(PlayableCharacter player) {
		System.out.println("EXIT DOOR");
		BoardCell doorsCell = room.getDoorsLocation().get(ID);
		BoardCell newPlayersCell = null;
		
		switch (wallEdge.getValue()) {
		case 0:
			newPlayersCell = room.getBoardGrid()[doorsCell.getPosition().getY()-1][doorsCell.getPosition().getX()];
			break;
		case 1:
			newPlayersCell = room.getBoardGrid()[doorsCell.getPosition().getY()][doorsCell.getPosition().getX()-1];
			break;
		case 2:
			newPlayersCell = room.getBoardGrid()[doorsCell.getPosition().getY()+1][doorsCell.getPosition().getX()];
			break;
		case 3:
			newPlayersCell = room.getBoardGrid()[doorsCell.getPosition().getY()][doorsCell.getPosition().getX()+1];
			break;

		}
		newPlayersCell.setObjectOnCell(player);
		room.addToPlayerLocationMap(player.getObjectID(), newPlayersCell);
		room.getRoomInventory().add(player);
		
	}

	public Room getRoom() {
		return room;
	}
}
