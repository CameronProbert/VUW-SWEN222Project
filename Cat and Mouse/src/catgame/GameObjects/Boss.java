package catgame.GameObjects;

import java.util.ArrayList;

import catgame.logic.BoardCell;
import catgame.logic.Position;
import catgame.logic.Room;

/**
 * 
 * @author Dan Henton
 * 
 *         Boss is a high health NPC which the player can attack and loot. A
 *         Boss Has better gear but is harder to kill.
 */
public class Boss implements NonPlayableCharacter {

	private int id;
	private Room currentRoom;
	private int health;
	private ArrayList<GameItem> inventory = new ArrayList<GameItem>();
	private int maxItems = 6;
	private int attackPower;
	private int level;

	public Boss(int ID, Room currentRoom, int level, int attackPower, int health, ArrayList<GameItem> items) {
		this.id = ID;
		this.currentRoom = currentRoom;
		this.level = level;
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

	@Override
	public Room getCurrentRoom() {
		return currentRoom;
	}
}
