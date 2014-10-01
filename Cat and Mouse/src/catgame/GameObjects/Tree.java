package catgame.GameObjects;

import catgame.logic.BoardCell;
import catgame.logic.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public class Tree implements NonMovavble {
	
	private final BoardCell cell;
	private final int id;

	public Tree(int ID, BoardCell cell) {
		this.id = ID;
		this.cell = cell;
	}

	public BoardCell getCurrentCell() {
		return cell;
	}

	public int getObjectID() {
		return id;
	}

}
