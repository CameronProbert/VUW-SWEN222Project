package catgame.GameObjects;

import java.awt.List;
import java.util.ArrayList;

import catgame.logic.GameError;

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
	public ArrayList<GameItem> getInventory();

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
	 *
	 * @return (int) level of the charactor
	 */
	public int getLevel();

	/**
	 * set The level of a charactor
	 *
	 * @param lvl
	 *
	 */
	public void setLevel(int lvl);

	/**
	 * @return true if the character is dead
	 */
	public boolean isDead();
}
