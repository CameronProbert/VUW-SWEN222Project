package catgame.gameObjects;

import catgame.logic.GameUtil.Direction;

/**
 * 
 * @author Dan
 *
 *Hedge GameObject 
 */
public class Hedge implements NonMovavble {

	private final int id;
	
	public Hedge(int id){
		this.id = id; 
	}

	
	public int getObjectID() {
		return id;
	}

}
