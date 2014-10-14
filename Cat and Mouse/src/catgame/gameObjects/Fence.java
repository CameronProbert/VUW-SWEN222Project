package catgame.gameObjects;

import catgame.logic.GameUtil.Direction;

public class Fence implements NonMovavble{

	private final int id;
	
	public Fence(int id){
		this.id = id;
	}
	public int getObjectID() {
		return id;
	}

}
