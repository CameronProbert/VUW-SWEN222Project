package catgame.GameObjects;

import java.awt.List;
import java.util.ArrayList;

import catgame.logic.BoardCell;
import catgame.logic.Position;
import catgame.logic.Room;

/**
 * 
 * @author Dan Henton
 * 
 *         Minion is a low health NPC which the player can attack and loot
 */
public class Minion implements NonPlayableCharacter {

	private int id;
	private Room currentRoom;
	private int health;
	private ArrayList<GameItem> inventory = new ArrayList<GameItem>();
	private final int maxItems = 3;
	private int attackPower;
 
	public Minion(int ID,Room currentRoom, int attackPower, int health, ArrayList<GameItem> items) {
		this.id = ID;
		this.currentRoom = currentRoom;
		this.attackPower = attackPower;
		this.health = health;
		this.inventory.addAll(items);
	}

	public int getHealth() {
		return health;
	}

	public void changeHealth(int change) {
		this.health += change;
	}

	public ArrayList<GameItem> getInventory() {
		return inventory;
	}

	public boolean addToInventory(GameItem item) {
		return inventory.add(item);
	}

	public GameItem removeFromInventory(GameItem item) {
		GameItem result = null;
		if (inventory.contains(item)) {
			for (GameItem i : inventory) {
				if (i.equals(item)) {
					result = i;
					break;
				}
			}
		}
		return result;
	}
	
	public int getObjectID() {
		return id;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void changeAttackpower(int change) {
		this.attackPower += change;
	}

	public void move(String Direction) {
		// TODO Auto-generated method stub
	}
	
	public boolean isDead() {
		return health < 1;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

}
