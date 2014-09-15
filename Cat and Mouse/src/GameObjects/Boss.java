package GameObjects;

import java.util.ArrayList;

import GameBoard.Position;

/**
 * 
 * @author Dan Henton
 * 
 *         Boss is a high health NPC which the player can attack and loot. A
 *         Boss Has better gear but is harder to kill.
 */
public class Boss implements NonPlayableCharacter {

	private int id;
	private Position currentPosition;
	private int health;
	private ArrayList<GameItem> inventory = new ArrayList<GameItem>();
	private int maxItems = 6;
	private int attackPower;
	private int level;

	public Boss(int level, int attackPower, int health, ArrayList<GameItem> items) {
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

	public Position getPosition() {
		return this.currentPosition;
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

	public void move(int x, int y) {
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
