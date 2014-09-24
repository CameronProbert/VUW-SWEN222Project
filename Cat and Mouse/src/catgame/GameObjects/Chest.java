package catgame.GameObjects;

import java.util.ArrayList;

import catgame.logic.BoardCell;
import catgame.logic.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public class Chest implements NonMovavble {
	
	private ArrayList<GameItem> inventory;
	private BoardCell cell;
	private int id;

	public Chest(){
		
	}
	public ArrayList<GameItem> getLoot(){
		return inventory;
	}

	public BoardCell getCurrentCell() {
		// TODO Auto-generated method stub
		return cell;
	}

	public int getObjectID() {
		return id;
	}

}
