package catgame.GameObjects;

import catgame.logic.BoardCell;
import catgame.logic.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public interface GameObject extends MasterObject{

	/**
	 *
	 * @return Position of the object
	 */
	public BoardCell getCurrentCell();
	
	
}
