package catgame.logic;

import catgame.gameObjects.GameObject;

public class BoardCell {
	private Position cellPosition;
	private GameObject objectOnCell;
	private String groundType;

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
			return " ";
		}else if (objectOnCell == null) {
			return this.groundType;
		}else{
			return this.objectOnCell.toString();
		}
	}
}