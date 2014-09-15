package GameObjects;

import GameBoard.Position;
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
	public Position getPosition();
	
	
}
