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
	private Room currentRoom;
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
	public PlayableCharacter(int ID, Room currentRoom, Direction direction, int attackPower, int health, List<GameItem> items) {
		this.id = ID;
		this.currentRoom = currentRoom;
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

	public int addAllToInventory(List<GameItem> items) {
		if(items == null || items.size() == 0){
			return -1;
		}else if (inventory.size() + items.size() >= maxItems) {
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

	public void useItem(int itemId) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getObjectID() == itemId) {
				removeFromInventory(inventory.get(i)).use();
			}
		}
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public Direction getFacingDirection() {
		return facingDirection;
	}

	public void changeDirection(Direction direction) {
		this.facingDirection = direction;
	}

	public void attack() {
		// this.currentRoom.playerAttack(id, facingDirection ,
		// this.attackPower);
	}

	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void reset(int attackPower, int health, int level) {
		// TODO Auto-generated method stub

	}

	public void resetItems(List<GameItem> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addToInventory(GameItem item) {
		// TODO Auto-generated method stub
		return false;
	}
}
