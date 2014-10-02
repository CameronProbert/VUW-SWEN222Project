package catgame.clientserver;

import java.util.ArrayList;

import catgame.gui.ClientFrame;
import catgame.logic.GameUtil;
import catgame.GameObjects.Food;
import catgame.GameObjects.GameItem;
import catgame.GameObjects.Key;
import catgame.GameObjects.PlayableCharacter;

public class SinglePlayerHandler extends GameRunner {



	/**
	 * set up a game for one person on single player
	 * @param playerID
	 */
	public SinglePlayerHandler (int playerID){
		GameUtil game = new GameUtil();
		game.addPlayer(playerID);
		PlayableCharacter ch = game.findCharacter(playerID);
		ClientFrame frame = new ClientFrame(this, playerID, false, ch);
		
	}
	
	public static void main(String[] args){
		new SinglePlayerHandler(1);
	}
}
