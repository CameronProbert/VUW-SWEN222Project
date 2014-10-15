package catgame.gameObjects;

import catgame.logic.GameUtil.Direction;

/**
 * 
 * @author Dan
 *
 *GameObject Fence
 */
public class Fence implements NonMovavble{

	private final int id;
	
	public Fence(int id){
		this.id = id;
	}
	public int getObjectID() {
		return id;
	}

}
