package catgame.gameObjects;

import java.awt.Graphics;

import catgame.logic.Position;

/**
 * 
 * @author Dan Henton
 *
 *Interface for the GameItems (Keys & Food)
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
	public void draw(Graphics g);
	
	public boolean isUsable();

	public GameObject getOwner();
	
	public void setOwner(GameObject own);
	
}
