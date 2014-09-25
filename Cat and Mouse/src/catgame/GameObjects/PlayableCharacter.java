package catgame.GameObjects;

import java.util.ArrayList;

import catgame.logic.BoardCell;
import catgame.logic.GameError;
import catgame.logic.Position;

/**
 *
 * @author Dan Henton
 *
 */
public class PlayableCharacter implements Character {

	private int id;
	private BoardCell currentCell;
	private int health;
	private ArrayList<GameItem> inventory = new ArrayList<GameItem>();
	private int maxItems = 10;
	private int attackPower;
	private int level;
	private int xp;

	public PlayableCharacter(int ID, BoardCell cell, int level,
			int attackPower, int health, ArrayList<GameItem> items) {
		this.id = ID;
		this.currentCell = cell;
		this.level = level;
		this.attackPower = attackPower;
		this.health = health;
		this.inventory.addAll(items);
	}

	public BoardCell getCurrentCell() {
		return currentCell;
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

	public ArrayList<GameItem> getInventory() {
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
		// check the cell in the direction
		if (direction.equals("NORTH")) {
			
		} else if (direction.equals("SOUTH")) {

		} else if (direction.equals("EAST")) {

		} else if (direction.equals("WEST")) {

		} else {
			throw new GameError("Unknown movement :" + direction);
		}
	}

	public int getLevel() {
		return level;
	}

	private void levelUp() {
		this.level++;
	}

	public void setLevel(int lvl) {
		this.level = lvl;
	}

	public int getXp() {
		return this.xp;
	}

	/**
	 * Adds xp to the player if the xp pool exceeds 100 the player is leveled up
	 * and the over flow xp is then equals the xp pool.z
	 *
	 * @param xpToAdd
	 * @throws GameError
	 */
	public void addXp(int xpToAdd) throws GameError {
		if (xpToAdd < 1 || xpToAdd > 100) {
			throw new GameError("Add xp < 1 || xp > 100  xp:" + xpToAdd);
		}
		this.xp += xpToAdd;
		if (this.xp > 100) {
			this.xp = xp - 100;
			levelUp();
		}
	}

	public boolean isDead() {
		return health < 0;
	}

	public void useItem(GameItem item) {
		// TODO
	}
}