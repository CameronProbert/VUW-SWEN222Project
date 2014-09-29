package catgame.GameObjects;

import java.awt.Graphics;

import catgame.logic.Position;

/**
 * 
 * @author Dan Henton
 *
 */
public interface GameItem extends MasterObject {
	
	/**
	 * Use the Item
	 * @return
	 */
	public boolean use();
	
	/**
	 * Draws a game item for use in the inventory
	 * @param framePosition
	 */
	public void draw(Graphics g, Position framePosition);
	
}
