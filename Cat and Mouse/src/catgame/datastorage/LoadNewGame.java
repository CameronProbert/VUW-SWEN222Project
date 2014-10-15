package catgame.datastorage;

import java.util.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import catgame.*;
import catgame.clientserver.*;
import catgame.gameObjects.*;
import catgame.gui.*;
import catgame.gui.renderpanel.*;
import catgame.gui.textfiles.*;
import catgame.logic.*;
import catgame.logic.GameUtil.Direction;

/**
 * Loads a new game. When a new game is run, it reads the standard starting xml
 * file but does not read any of the players that might be in the xml file. The
 * reason for this is all the players need to have unique IDs that are given to
 * this class in a list from the client/server. So the xml file is read, and the
 * game is built except for the players. Once that is done, we iterate through
 * the list to make PlayableCharacter with the IDs passed in. These
 * PlayableCharacters are added to the room and map.
 * 
 * 
 * @author MIla
 *
 */
public class LoadNewGame {
	private List<Integer> playerIDList = new ArrayList<Integer>();
	private LoadingGameMain loaderMain;
	private BoardData boardData;

	public LoadNewGame(List<Integer> playerIDList) throws JDOMException,
			XMLException {
		this.playerIDList = playerIDList;
		// isLoadOldGame is set to false because we are
		// loading a new game here!
		loaderMain = new LoadingGameMain(false, null);
		boardData = loaderMain.getBoardData();
		loadCharacters();
	}

	/**
	 * Creates PlayableCharacters with unique IDs (passed through from
	 * client/server) and added them to the game.
	 * 
	 * @throws XMLException
	 */
	public void loadCharacters() throws XMLException {
		Room room = loaderMain.getRoomIDMap().get(0);
		for (int i = 0; i < playerIDList.size(); i++) {
			List<GameItem> newInv = new ArrayList<GameItem>();
			newInv.add(new Food(808080, 20));
			PlayableCharacter character = new PlayableCharacter(
					playerIDList.get(i), Direction.NORTH, 20, 100, newInv);
			System.out
					.println("added the playable ch to obj storer inside loadCh\n\n");
			loaderMain.getBoardData().getObjStorer()
					.addplayableChs(playerIDList.get(i), character);
			loaderMain.addPlayerToMap(character);
			loaderMain.addObjectToMap(character);
			BoardCell cell = room.getBoardGrid()[room.getBoardGrid().length - 1][i + 1];
			cell.setObjectOnCell(character);
			room.addToInventory(character);
			room.addToPlayerLocationMap(playerIDList.get(i), cell);
			boardData.getObjStorer().addplayableChs(character.getObjectID(),
					character);
		}
		loaderMain.getHelper().populateDoorLocationMap(room);
	}

	public List<Integer> getPlayerIDList() {
		return playerIDList;
	}

	public BoardData getBoardData() {
		return boardData;
	}

	public static void main(String[] args) throws JDOMException, XMLException {
		List<Integer> temp = new ArrayList<Integer>();
		temp.add(23);
		temp.add(2345);
		new LoadNewGame(temp);
	}
}
