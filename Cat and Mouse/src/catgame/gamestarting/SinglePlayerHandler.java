package catgame.gamestarting;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jdom2.JDOMException;

import catgame.datastorage.LoadNewGame;
import catgame.datastorage.LoadOldGame;
import catgame.datastorage.LoadingGameMain;
import catgame.datastorage.XMLException;
import catgame.gameObjects.Food;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.Key;
import catgame.gameObjects.PlayableCharacter;
import catgame.gui.FrameClient;
import catgame.logic.BoardData;
import catgame.logic.GameUtil;
import catgame.logic.RoomBuilder;

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
			try {
				File xmlFile = new File(fileName);
				LoadOldGame loadXML = new LoadOldGame(xmlFile);
				boardData = loadXML.getBoardData();
			} catch (JDOMException | XMLException e) {
				e.printStackTrace();
			}
		}
		else{
			List<Integer> ids = new ArrayList<Integer>();
			ids.add(101010);
			try {
				LoadNewGame loadXML = new LoadNewGame(ids);
				boardData = loadXML.getBoardData();
			} catch (JDOMException | XMLException e) {
				e.printStackTrace();
			}
		}

		
		 //boardData = new BoardData();
		 //boardData.loadLevelOne();
		
		FrameClient frame = new FrameClient(this, false, null, 101010);
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
