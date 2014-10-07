package catgame.clientserver;

import java.util.ArrayList;

import catgame.gameObjects.Food;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.Key;
import catgame.gameObjects.PlayableCharacter;
import catgame.gui.ClientFrame;
import catgame.logic.GameUtil;

public class SinglePlayerHandler extends GameRunner {



	/**
	 * set up a game for one person on single player
	 * @param playerID
	 */
	public SinglePlayerHandler (int playerID){
		game = new GameUtil();
		game.getStorer().addPlayer(playerID);
		PlayableCharacter ch = game.getStorer().findCharacter(playerID);
		ClientFrame frame = new ClientFrame(this, playerID, false, ch, null);
		
	}
	
	public static void main(String[] args){
		new SinglePlayerHandler(1);
	}
}
