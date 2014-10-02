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
	private GameObject owner;

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
	
	@Override
	public GameObject getOwner() {
		return owner;
	}
	@Override
	public void setOwner(GameObject own) {
		this.owner = own;
	}

}
