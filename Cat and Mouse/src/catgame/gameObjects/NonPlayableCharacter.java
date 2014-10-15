package catgame.gameObjects;

import java.util.List;

/**
 * 
 * @author Dan Henton
 *
 * Npc are nonplayable characters (Boss and Minions
 */
public interface NonPlayableCharacter extends Character{

	public List<GameItem> removeAllFromInv();
}
