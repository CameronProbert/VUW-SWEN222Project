package catgame.gameObjects;

import java.util.List;

/**
 * 
 * @author Dan Henton
 *
 */
public interface NonPlayableCharacter extends Character{

	public List<GameItem> removeAllFromInv();
}
