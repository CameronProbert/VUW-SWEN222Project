package catgame.GameObjects;

import java.util.ArrayList;
import java.util.List;

import catgame.logic.BoardCell;
import catgame.logic.GameError;
import catgame.logic.Position;
import catgame.logic.Room;

/**
 *
 * @author Dan Henton
 *
 */
public class PlayableCharacter implements Character {

	private final int id;
	private final int maxItems = 6;
	private Room currentRoom;
	private String facingDirection;
	private int health;
	private List<GameItem> inventory = new ArrayList<GameItem>();
	
	private int attackPower;
	private int xp;

	public PlayableCharacter(int ID, Room currentRoom, String direction, int attackPower, int health, List<GameItem> items) {
		this.id = ID;
		this.currentRoom = currentRoom;
		this.facingDirection = direction;
		this.attackPower = attackPower;
		this.health = health;
		this.inventory.addAll(items);
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

	public boolean addToInventory(GameItem item) {
		if (inventory.size() == maxItems) {
			return false;
		}
		return inventory.add(item);
	}

	/**
	 * TODO the item could be checked via the items id
	 */
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

	public int getAttackPower() {
		return attackPower;
	}

	public void changeAttackpower(int change) {
		this.attackPower += change;
	}

	public void move(String direction) throws GameError {
		if (isDead()) {
			throw new GameError("Player is dead");
		}
		if (direction.equals(facingDirection)) {
			forward(direction);
		} else {
			turn(direction);
		}
	}

	private void forward(String direction) {
		this.currentRoom.movePlayer(id, direction);
	}

	private void turn(String direction) {
		this.facingDirection = direction;
	}

	public int getXp() {
		return this.xp;
	}


	public boolean isDead() {
		return health < 0;
	}

	public void useItem(GameItem item) {
		
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public String getFacingDirection() {
		return facingDirection;
	}
	
	public void attack(){
		this.currentRoom.playerAttack(id, facingDirection , this.attackPower);
	}
}
