package catgame.gameObjects;

import catgame.logic.BoardCell;
import catgame.logic.Room;

public class Door implements NonMovable {
	private final int id;
	private final BoardCell cell;
	private final DoorsEntrance entranceTo;
	private boolean isLocked = false;
	private int keyID;

	/**
	 * The GameObject Door which can be locked and opened by keys. When created
	 * if keyID != 0 then keys ID will be the only thing that will unlock the
	 * door
	 * 
	 * @param id
	 * @param cell
	 * @param entrance
	 * @param keyId
	 *            (If keyID == 0 then there is no key for the door)
	 */
	public Door(int id, BoardCell cell, DoorsEntrance entrance, int keyId) {
		this.id = id;
		this.cell = cell;
		this.entranceTo = entrance;
		if (keyId != 0) {
			this.isLocked = true;
			this.keyID = keyId;
		}
	}

	public int getObjectID() {
		return id;
	}

	/**
	 * Enters the door
	 * 
	 * @return The Other side of the door
	 */
	public DoorsEntrance enterDoor() {
		if (isLocked) {
			System.out.println("TODO POP UP FOR UNLOCK DOOR");
			return null;
		}
		return this.entranceTo;
	}

	/**
	 * Unlock the door using a keyId if the key ID matches the keyID for the
	 * door then it will be unlocked
	 * 
	 * @param int : keyId
	 * @return boolean : current state of the door
	 */
	public boolean unlockDoor(int keyId) {
		if (this.keyID == keyId) {
			this.isLocked = false;
		}
		return this.isLocked;
	}

	/**
	 * 
	 * @author Dan Henton
	 * 
	 *         Helper Class for using a door. This Class stores The room in
	 *         which a door will enter and the other side of the door.
	 */
	protected class DoorsEntrance {
		private Room room;
		private Door entrance;

		public DoorsEntrance(Room room, Door entrance) {
			this.room = room;
			this.entrance = entrance;
		}
	}

}
