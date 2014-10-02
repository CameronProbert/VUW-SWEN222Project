package catgame.gameObjects;

import catgame.logic.BoardCell;
import catgame.logic.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public class Tree implements NonMovavble {
	
	private final int id;

	public Tree(int ID) {
		this.id = ID;
	}

	public int getObjectID() {
		return id;
	}

	public String toString(){
		return "Tree";
	}
}
