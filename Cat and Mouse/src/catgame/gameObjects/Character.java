package catgame.gameObjects;

import java.util.List;

import catgame.logic.GameError;
import catgame.logic.Room;
import catgame.logic.GameUtil.Direction;

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
	 * @return true if the character is dead
	 */
	public boolean isDead();

	/**
	 * completely reset the player according to server instructions
	 * @param attackPower
	 * @param health
	 * @param level
	 */
	public void reset(int attackPower, int health, int level);

	/**
	 * reset the items the player is holding
	 * @param items
	 */
	public void resetItems(List<GameItem> items);
}
