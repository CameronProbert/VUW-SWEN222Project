package catgame.logic;

import catgame.gameObjects.GameObject;
/**
 * 
 * @author Dan Henton
 *
 */
public class BoardCell {
	private Position cellPosition;
	private GameObject objectOnCell;
	private String groundType;
	
	/**
	 * Create a new BoardCell for a Room, the boardCell can no ground type which corresponds to an empty cell,
	 * If a boardCell has an object on it it is then not a basic boardCell this object is drawn in the renderPanel
	 * @param position
	 * @param object
	 * @param groundType
	 */
	public BoardCell(Position position, GameObject object, String groundType) {
		this.cellPosition = position;
		this.objectOnCell = object;
		this.groundType = groundType;
	}
	
	public Position getPosition() {
		return this.cellPosition;
	}
	
	public String getGroundType(){
		return groundType;
	}
	
	public GameObject getObjectOnCell(){
		return objectOnCell;
	}

	public String toString() {
		if (groundType == null) {
			return "Empty";
		}else if (objectOnCell == null) {
			return this.groundType;
		}else{
			return this.objectOnCell.toString();
		}
	}
	
	public void setObjectOnCell(GameObject newObject){
		this.objectOnCell = newObject;
	}
	
	public GameObject removeObjectOnCell(){
		GameObject tempObj = this.objectOnCell;
		this.objectOnCell = null;
		return tempObj;
		
	}
}