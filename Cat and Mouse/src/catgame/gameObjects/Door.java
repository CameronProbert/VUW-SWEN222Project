package catgame.gameObjects;

import java.awt.Dimension;

import catgame.logic.BoardCell;
import catgame.logic.GameUtil.Direction;
import catgame.logic.Room;

public class Door implements NonMovavble {
	private final int ID;
	private int entranceToDoor = 0;
	private boolean isLocked = false;
	private int keyID;  
	Direction wallEdge;

	/**
	 * The GameObject Door which can be locked and opened by keys. When created
	 * if keyID != 0 then keys ID will be the only thing that will unlock the 
	 * door
	 * 
	 * @param id
	 * @param door ID to the other side of the door
	 * @param keyId
	 *            (If keyID == 0 then there is no key for the door)
	 */
	public Door(int id, Direction direction) {
		this.ID = id;	
		this.wallEdge = direction;
	}

	public int getObjectID() {
		return ID;
	}
	
	public void addOtherSide(int entrance, int keyId){
		this.entranceToDoor = entrance;
		//this.wallEdge = wallEdge;
		if (keyId != 0) {
			this.isLocked = true;
			this.keyID = keyId;
		}
	}

	/**
	 * Enters the door
	 * 
	 * @return The Other side of the door
	 */
	public int enterDoor() {
		if (isLocked) {
			System.out.println("TODO POP UP FOR UNLOCK DOOR");
			return -1;
		}
		return this.entranceToDoor;
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
	
	public int getOtherSide(){
		if(entranceToDoor == 0){
			return 0;
		}
		else {
			return entranceToDoor;
		}
	}
	
	public Direction getDoorsWallEdge(){
		return this.wallEdge;
	}
}
