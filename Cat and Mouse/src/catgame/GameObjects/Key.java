package catgame.GameObjects;

import java.awt.Graphics;

import catgame.logic.Position;

/**
 * 
 * @author Dan Henton
 *
 */
public class Key implements GameItem{	
	private final int id;

	public Key(int id){
		this.id = id;
	}
	
	public int getObjectID() {
		return id;
	}

	public boolean use() {
		// TODO Auto-generated method stub
		return false;
	}

	public void draw(Graphics g, Position framePosition) {
		// TODO Auto-generated method stub
		
	}

}
