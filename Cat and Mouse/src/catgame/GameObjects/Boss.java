package catgame.GameObjects;

import java.util.ArrayList;

import catgame.logic.BoardCell;
import catgame.logic.Position;

/**
 * 
 * @author Dan Henton
 * 
 *         Boss is a high health NPC which the player can attack and loot. A
 *         Boss Has better gear but is harder to kill.
 */
public class Boss implements NonPlayableCharacter {

	private int id;
	private BoardCell currentCell;
	private int health;
	private ArrayList<GameItem> inventory = new ArrayList<GameItem>();
	private int maxItems = 6;
	private int attackPower;
	private int level;

	public Boss(int ID, int level, int attackPower, int health, ArrayList<GameItem> items) {
		this.id = ID;
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

	public BoardCell getCurrentCell() {
		return currentCell;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int lvl) {
		this.level = lvl;
	}

	public boolean isDead() {
		return health < 1;
	}
}
