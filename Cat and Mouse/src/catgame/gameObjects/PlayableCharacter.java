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
 */
public class PlayableCharacter implements Character {

	private final int id;
	private final int maxItems = 6;
	private Room currentRoom;
	private Direction facingDirection;
	private int health;
	private List<GameItem> inventory = new ArrayList<GameItem>();
	
	private int attackPower;
	private int xp;

	public PlayableCharacter(int ID, Room currentRoom, Direction direction, int attackPower, int health, List<GameItem> items) {
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

	public void move(Direction direction) throws GameError {
		if (isDead()) {
			throw new GameError("Player is dead");
		}
		if (direction.equals(facingDirection)) {
			forward(direction);
		} else {
			turn(direction);
		}
	}

	private void forward(Direction direction) {
		//this.currentRoom.movePlayer(id, direction);
	}

	private void turn(Direction direction) {
		this.facingDirection = direction;
	}

	public boolean isDead() {
		return health < 0;
	}

	public void useItem(GameItem item) {
		
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public Direction getFacingDirection() {
		return facingDirection;
	}
	
	public void attack(){
		//this.currentRoom.playerAttack(id, facingDirection , this.attackPower);
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
}
