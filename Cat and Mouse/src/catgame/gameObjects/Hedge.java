package catgame.gameObjects;

import catgame.logic.GameUtil.Direction;

public class Hedge implements NonMovavble {

	private final int id;
	private Direction direction;
	
	public Hedge(int id){
		this.id = id;
		this.direction = direction;
	}

	public Direction getDirection(){
		return direction;
	}
	
	public int getObjectID() {
		return id;
	}

}
