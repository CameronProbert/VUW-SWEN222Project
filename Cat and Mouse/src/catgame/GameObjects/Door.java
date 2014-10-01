package catgame.GameObjects;

import catgame.logic.BoardCell;
import catgame.logic.Room;

public class Door implements NonMovavble {
	private final int id;
	private final BoardCell cell;
	private final DoorsEntrance entranceTo;

	public Door(int id, BoardCell cell, DoorsEntrance entrance) {
		this.id = id;
		this.cell = cell;
		this.entranceTo = entrance;
	}

	public int getObjectID() {
		return id;
	}

	/**
	 * Enters the door
	 * @return The Other side of the door
	 */
	public DoorsEntrance enterDoor() {
		return this.entranceTo;
	}

	/**
	 * 
	 * @author Dan Helper Class for using a door. This Class stores The room in
	 *         which a door will enter and the other side of the door.
	 */
	public class DoorsEntrance {
		private Room room;
		private Door entrance;

		public DoorsEntrance(Room room, Door entrance) {
			this.room = room;
			this.entrance = entrance;
		}
	}
}
