package catgame.GameObjects;

import catgame.logic.BoardCell;
import catgame.logic.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public class Rock implements NonMovavble {

	private BoardCell cell;
	private int id;

	public Rock(BoardCell cell , int id) {
		this.cell = cell;
		this.id = id;
	}

	public BoardCell getCurrentCell() {
		return cell;
	}

	public int getObjectID() {
		return id;
	}
}
