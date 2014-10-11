package catgame.dataStorage;

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

public class LoadNewGame {
	private List<Integer> playerIDList = new ArrayList<Integer>();
	private LoadingGameMain loaderMain;
	private BoardData boardData;

	public BoardData LoadNewGame(List<Integer> playerIDList) throws JDOMException,
			XMLException {
		this.playerIDList = playerIDList;
		loaderMain = new LoadingGameMain(playerIDList);
		loaderMain.getBoardData();
		return boardData;
	}

	public List<Integer> getPlayerIDList() {
		return playerIDList;
	}
}
