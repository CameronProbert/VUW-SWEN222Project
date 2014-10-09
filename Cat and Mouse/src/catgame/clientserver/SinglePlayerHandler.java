package catgame.clientserver;

import java.util.ArrayList;

import catgame.gameObjects.Food;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.Key;
import catgame.gameObjects.PlayableCharacter;
import catgame.gui.ClientFrame;
import catgame.logic.GameUtil;

/**
 * simple handler for singleplayer games, has a main method for easier testing
 * @author Francine
 *
 */
public class SinglePlayerHandler extends GameRunner {



	/**
	 * set up a game for one person on single player
	 * @param playerID
	 */
	public SinglePlayerHandler (){
		game = new GameUtil();
		ClientFrame frame = new ClientFrame(this, false, null);
		
	}
	
	public static void main(String[] args){
		new SinglePlayerHandler();
	}
}
