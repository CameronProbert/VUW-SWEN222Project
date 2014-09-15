package GameObjects;

import GameBoard.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public class Rock implements NonMovavble {

	private Position position;
	private int id;

	public Rock(Position p, int id) {
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
