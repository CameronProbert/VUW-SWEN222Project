package catgame.GameObjects;

import catgame.logic.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public class Tree implements NonMovavble {
	
	private Position position;
	private int id;

	public Tree(Position p, int id) {
		this.position = p;
		this.id = id;
	}

	public Position getPosition() {
		return position;
	}

	public int getObjectID() {
		return id;
	}

}
