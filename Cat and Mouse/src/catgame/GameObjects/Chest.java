package catgame.GameObjects;

import java.util.List;

import catgame.logic.BoardCell;
import catgame.logic.Position;
/**
 * 
 * @author Dan Henton
 *
 */
public class Chest implements NonMovavble {
	
	private List<GameItem> inventory;
	private BoardCell cell;
	private int id;

	public Chest(int ID, List<GameItem> inv){
		this.id = ID;
		this.inventory = inv;
	}
	public List<GameItem> getLoot(){
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
