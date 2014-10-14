package catgame.gameObjects;

import catgame.logic.GameUtil.Direction;

public class Fence implements NonMovavble{

	private final int id;
	private Direction direction;
	
	public Fence(int id, Direction direction){
		this.id = id;
		this.direction = direction;
	}
	public int getObjectID() {
		return id;
	}
	
	public Direction getDirection(){
		return direction;
	}

}
