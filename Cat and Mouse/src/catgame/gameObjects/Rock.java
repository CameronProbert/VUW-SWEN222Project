package catgame.gameObjects;

import catgame.logic.BoardCell;
import catgame.logic.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public class Rock implements NonMovable {

	private final int id;

	public Rock(int ID) {
		this.id = ID;
	}

	public int getObjectID() {
		return id;
	}
	
	public String toString(){
		return "Rock";
	}
}
