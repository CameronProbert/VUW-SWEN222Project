package catgame.gameObjects;

import java.awt.Graphics;

import catgame.logic.Position;

/**
 * 
 * @author Dan Henton
 *
 */
public class Food implements GameItem{
	private final int id;
	private final int heal;
	private GameObject owner;

	public Food(int id , int heal){
		this.id = id;
		this.heal = heal;
		
	}
	public int getObjectID() {
		return id;
	}
	public int getHeal() {
		return heal;
	}

	public boolean use() {
		//TODO
		return false;
	}
	
	
	
	public void draw(Graphics g ,Position framePosition) {
		
		
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
