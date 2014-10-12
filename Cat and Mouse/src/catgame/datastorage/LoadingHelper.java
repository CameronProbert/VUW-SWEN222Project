package catgame.datastorage;

import catgame.gameObjects.*;
import catgame.logic.*;
import catgame.logic.GameUtil.Direction;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LoadingHelper {
	LoadingGameMain main;

	public LoadingHelper(LoadingGameMain main) {
		this.main = main;
	}

	public BoardCell loadBoardCell(String boardString) throws XMLException {
		// get (x,y,ID,groundType)
		String newBoardString = boardString.substring(1,
				boardString.length() - 1);
		String[] boardInfoArray = newBoardString.split(",");
		int x = Integer.parseInt(boardInfoArray[0]);
		int y = Integer.parseInt(boardInfoArray[1]);
		Position position = new Position(x, y);
		GameObject ObjectID = null;
		if (!boardInfoArray[2].equals("null")) {
			int ID = Integer.parseInt(boardInfoArray[2]);
			if (main.getObjectIDMap().containsKey(ID)) {
				ObjectID = main.getObjectIDMap().get(ID);
			} else {
				throw new XMLException(
						"Cannot find GameObject that is supposed to be already made in the room. Cannot make BoardCell without this!");
			}
		}
		String groundType = null;
		if (!boardInfoArray[3].equals("null")) {
			groundType = boardInfoArray[3];
		}

		return new BoardCell(position, ObjectID, groundType);
	}

	/**
	 * Main just for quick testing for parsing strings. This will be removed in
	 * the future.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// LoadingHelper helper = new LoadingHelper();
		// String temp = "12,13,55342,235";
		// String temp2 = "12, 13, 55342, 235";
		// String[] array = helper.loadBoardCell(temp);
		// System.out.println("First array");
		// for (int i = 0; i < array.length; i++) {
		// System.out.println(array[i]);
		// }
		// System.out.println("Second array");
		// String[] array2 = helper.loadBoardCell(temp2);
		// for (int i = 0; i < array.length; i++) {
		// System.out.println(array2[i]);
		// }

	}
}
