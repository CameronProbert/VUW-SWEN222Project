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

	public void loadCharacters() throws XMLException {
		for (int i = 1; i < playerIDList.size(); i++) {
			Room room = loaderMain.getRoomIDMap().get(0);
			List<GameItem> newInv = new ArrayList<GameItem>();
			newInv.add(new Food(808080, 20));
			PlayableCharacter character = new PlayableCharacter(playerIDList.get(i+1),
					Direction.NORTH, 20, 100, newInv);
			loaderMain.getBoardData().getObjStorer()
					.addplayableChs(playerIDList.get(i+1), character);
			loaderMain.addPlayerToMap(character);
			BoardCell cell = room.getBoardGrid()[room.getBoardGrid().length][i];
			cell.setObjectOnCell(character);
			room.addToInventory(character);
			room.addToPlayerLocationMap(playerIDList.get(i+1), cell);
		}
	}

	public List<Integer> getPlayerIDList() {
		return playerIDList;
	}

	public BoardData getBoardData() {
		return boardData;
	}
}
