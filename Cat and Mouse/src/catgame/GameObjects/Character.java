package catgame.GameObjects;

import java.util.List;

import catgame.logic.GameError;
import catgame.logic.Room;

/**
 *
 * @author Dan Henton
 *
 */
public interface Character extends Movable {

	/**
	 *
	 * @return Characters current health
	 */
	public int getHealth();

	/**
	 * Changes the characters health (+Heal -Damage)
	 *
	 * @param health
	 *            to change
	 */
	public void changeHealth(int change);

	/**
	 *
	 * @return List of the characters inventory
	 */
	public List<GameItem> getInventory();

	/**
	 * Add to a characters inventory
	 */
	public boolean addToInventory(GameItem item);

	/**
	 * remove to a characters inventory
	 */
	public GameItem removeFromInventory(GameItem item);

	/**
	 * @return (int) attack power of a character
	 */
	public int getAttackPower();

	/**
	 *
	 * @param change
	 *            (int) attack power
	 */
	public void changeAttackpower(int change);

	/**
	 * designed so that move can work straight from keybindings or an algorirthm
	 * to move a charactors checks if a move is valid then moves character
	 *
	 * 
	 */
	public void move(String direction);

	/**
	 * @return true if the character is dead
	 */
	public boolean isDead();
	
	/**
	 * s
	 * @return current Room
	 */
	public Room getCurrentRoom();

	public int getLevel();

	public void reset(int attackPower, int health, int level);

	public void resetItems(List<GameItem> items);
}
