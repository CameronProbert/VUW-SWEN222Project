package catgame.GameObjects;

import java.util.ArrayList;

import catgame.logic.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public class Chest implements NonMovavble {
	
	private ArrayList<GameItem> inventory;
	private Position currentPosition;
	private int id;

	public Chest(){
		
	}
	public ArrayList<GameItem> getLoot(){
		return inventory;
	}

	public Position getPosition() {
		// TODO Auto-generated method stub
		return currentPosition;
	}

	public int getObjectID() {
		return id;
	}

}
