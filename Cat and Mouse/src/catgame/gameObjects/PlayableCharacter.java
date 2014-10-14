package catgame.gameObjects;

import java.util.ArrayList;
import java.util.List;

import catgame.logic.GameError;
import catgame.logic.GameUtil.Direction;
import catgame.logic.Room;

/**
 *
 * @author Dan Henton
 * 
 *         A Dumb playable Character which only holds information about it's
 *         self: id, currentRoom, facing direction, health and inventory
 *
 */
public class PlayableCharacter implements Character {

	private final int START_ATTACK_POWER = 10; // arbitrary number, can change
	private final int START_HEALTH_LEVEL = 10; // arbitrary number, can change
	private final int id;
	private final int maxItems = 6;
	private Direction facingDirection;
	private int health;
	private List<GameItem> inventory = new ArrayList<GameItem>();
	private int attackPower;

	/**
	 * Constuct a new Playable Character object designed such that it can be
	 * made dynamically for the .xml loading and saving
	 * 
	 * @param ID
	 * @param currentRoom
	 * @param direction
	 * @param attackPower
	 * @param health
	 * @param items
	 */
	public PlayableCharacter(int ID, Direction direction, int attackPower, int health, List<GameItem> items) {
		this.id = ID;
		this.facingDirection = direction;
		this.attackPower = attackPower;
		this.health = health;
		if (items != null) {
			this.inventory.addAll(items);
		}
	}

	public int getObjectID() {
		return id;
	}

	public int getHealth() {
		return health;
	}

	public void changeHealth(int change) {
		health += change;
	}

	public List<GameItem> getInventory() {
		return inventory;
	}

	/**
	 * Add a List of items to the inventory
	 * 
	 * @param items
	 * @return 1 if successful -1 if unsuccessful
	 */
	public int addAllToInventory(List<GameItem> items) {
		if (items == null || items.size() == 0) {
			return -1;
		} else if (inventory.size() + items.size() >= maxItems) {
			return -1;
		}
		System.out.println("ADDING ALL ITEMS");
		inventory.addAll(items);
		return 1;
	}

	/**
	 * TODO the item could be checked via the items id
	 */
	public GameItem removeFromInventory(GameItem item) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getObjectID() == item.getObjectID()) {
				return inventory.remove(i);
			}
		}
		return null;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void changeAttackpower(int change) {
		this.attackPower += change;
	}

	public boolean isDead() {
		return health < 0;
	}

	// TODO Check if this is even needed i don't think it is used
	/**
	 * Use an item in the players inventory removes the GameItem if it has been
	 * used
	 * 
	 * @param itemId
	 */
	public void useItem(int itemId) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getObjectID() == itemId) {
				removeFromInventory(inventory.get(i)).use();
			}
		}
	}

	/**
	 * 
	 * @return true if the player has a key in its current inventory
	 */
	public boolean hasKey() {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof Key) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Use a key in the players inventory. Its best to check hasKey() before
	 * useKey() as it can return null if there is not key
	 * 
	 * @return
	 */
	public Key useKey() {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof Key) {
				return (Key) inventory.remove(i);
			}
		}
		return null;
	}

	public Direction getFacingDirection() {
		return facingDirection;
	}

	public void changeDirection(Direction direction) {
		this.facingDirection = direction;
	}

	public void reset(int attackPower, int health, int level) {
		// TODO Auto-generated method stub

	}

	public void resetItems(List<GameItem> items) {
		// TODO Auto-generated method stub

	}

	public boolean addToInventory(GameItem item) {
		if (item == null) {
			return false;
		}
		return inventory.add(item);
	}

	/**
	 * Eat food in the inv removing it in the process
	 * 
	 * @param objectID
	 */
	public int eat(int objectID) {
		Food toEat = null;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getObjectID() == objectID) {
				toEat = (Food) inventory.remove(i);
			}
		}
		if (toEat != null) {
			changeHealth(toEat.getHeal());
			return 1;
		}
		return -1;
	}
	
	public boolean canAddItem(){
		return (inventory.size() <= 5);
	}
}
