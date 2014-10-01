package catgame.clientserver;

import catgame.gui.ClientFrame;
import catgame.logic.GameUtill;
import catgame.GameObjects.PlayableCharacter;

public class SinglePlayerHandler extends GameRunner {



	/**
	 * set up a game for one person on single player
	 * @param playerID
	 */
	public SinglePlayerHandler (int playerID){
		GameUtill game = new GameUtill();
		game.addPlayer(playerID);
		PlayableCharacter ch = game.findCharacter(playerID);
		ClientFrame frame = new ClientFrame(this, playerID, false, ch);
		
	}
	
	public static void main(String[] args){
		new SinglePlayerHandler(1);
	}
}
