package catgame.clientserver;

import java.io.IOException;
import java.util.ArrayList;

import catgame.gameObjects.Food;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.Key;
import catgame.gameObjects.PlayableCharacter;
import catgame.gui.FrameClient;
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
	public SinglePlayerHandler (String fileName){
		if(!fileName.equals("no file")){
			// TODO loadXML = new LoadOldGame(fileName)
			// TODO boardData = loadXML.getBoardData();
			// TODO GameUtil util = boardData.getGame();
			// TODO this.setGameUtil(util);
		}
		else{
			// TODO loadXML = new LoadNewGame(playerIds);
			// TODO boardData = loadXML.getBoardData();
			// TODO GameUtil util = boardData.getGame();
			// TODO this.setGameUtil(util);
		}
		
		FrameClient frame = new FrameClient(this, false, null);
		try {
			singleUserGame(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	private static void singleUserGame(GameRunner game) throws IOException {

		while(game.isNotOver()) {
			// keep going until the frame becomes invisible
			game.setState(GameRunner.GameState.READY);
			pause(3000);
			game.setState(GameRunner.GameState.PLAYING);
			// now, wait for the game to finish
			while(game.state() == GameRunner.GameState.PLAYING) {
				Thread.yield();
			}
			// If we get here, then we're in game over mode
			pause(3000);
			// Reset board state
			//game.fromByteArray(state);
		}
	}

	public static void main(String[] args){
		new SinglePlayerHandler(null);
	}

	private static void pause(int delay) {
		try {
			Thread.sleep(delay);
		} catch(InterruptedException e){			
		}
	}
}
