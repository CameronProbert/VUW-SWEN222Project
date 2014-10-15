package catgame.gameObjects;

import java.util.ArrayList;
import java.util.List;

import catgame.logic.BoardCell;
import catgame.logic.Position;

/**
 * 
 * @author Dan Henton
 *
 *         GameObject Chest Stores GameItems
 */
public class Chest implements NonMovavble {

	private final int id;
	private List<GameItem> inventory = new ArrayList<GameItem>();

	public Chest(int ID, List<GameItem> inv) {
		this.id = ID;
		this.inventory = inv;
	}

	public List<GameItem> getLoot() {
		return inventory;
	}

	public int getObjectID() {
		return id;
	}

	public List<GameItem> openChest() {
		return inventory;
	}

	public void updateLoot(List<GameItem> items) {
		this.inventory = items;
	}

	public void removeInv() {
		this.inventory = new ArrayList<GameItem>();
	}

}
