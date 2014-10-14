package catgame.gameObjects;

import java.util.ArrayList;
import java.util.List;

import catgame.logic.BoardCell;
import catgame.logic.GameUtil.Direction;
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

	private final int id;
	private final int maxItems = 6;
	private int health;
	private List<GameItem> inventory = new ArrayList<GameItem>();
	private int attackPower;

	public Boss(int ID , int attackPower, int health, List<GameItem> items) {
		this.id = ID;
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

	public List<GameItem> getInventory() {
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

	public boolean isDead() {
		return health < 1;
	}


	public void reset(int attackPower, int health, int level) {
		this.health = health;
		this.attackPower = attackPower;
		
	}

	public void resetItems(List<GameItem> items) {
		this.inventory = items;
		
	}

	public void move(Direction direction) {
		// TODO Auto-generated method stub
		
	}

	public List<GameItem> removeAllFromInv() {
		List<GameItem> result = inventory;
		inventory.removeAll(inventory);
		return result;
	}
}
