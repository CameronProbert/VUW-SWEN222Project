package catgame.datastorage;

import catgame.gameObjects.*;
import catgame.logic.*;
import catgame.logic.GameUtil.Direction;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Helper class for loading xml files. Holds methods which are used by several
 * loading classes.
 * 
 * @author MIla
 *
 */
public class LoadingHelper {
	LoadingGameMain main;
	boolean isOldGame;

	public LoadingHelper(LoadingGameMain main, boolean isOldGame) {
		this.main = main;
		this.isOldGame = isOldGame;
	}

	/**
	 * Creates a boardcell from an element string parsing. Looks up in the
	 * Object ID map (previously loaded game objects) to find the object that is
	 * supposed to be on that cell. NOTE: if we are looking for an object ID
	 * starting with "10" it is a PlayableCharacter which means we have to see
	 * if we are loading an old game or a new game. If we are loading an old
	 * game, then we place the player on that cell. But if we are loading a new
	 * game, we place null on that cell for now. Later when the xml file is
	 * finished reading for a new game, we make new PlayableCharacter and put
	 * them on the cell.
	 * 
	 * @param boardString
	 * @return
	 * @throws XMLException
	 */
	public BoardCell loadBoardCell(String boardString) throws XMLException {
		// boardString format: (x,y,ID,groundType)
		// remove the brackets
		String newBoardString = boardString.substring(1,
				boardString.length() - 1);
		// parse the string into comma separated values
		String[] boardInfoArray = newBoardString.split(",");
		int x = Integer.parseInt(boardInfoArray[0]);
		int y = Integer.parseInt(boardInfoArray[1]);
		Position position = new Position(x, y);
		GameObject ObjectID = null;
		if (!boardInfoArray[2].equals("null")) {
			if (checkPlayerID(boardInfoArray[2]) == 10) {
				if (isOldGame) {
					int ID = Integer.parseInt(boardInfoArray[2]);
					if (main.getObjectIDMap().containsKey(ID)) {
						ObjectID = (GameObject) main.getObjectIDMap().get(ID);
					} else {
						throw new XMLException(
								"Cannot find GameObject_"
										+ ID
										+ " that is supposed to be already made in the room. Cannot make BoardCell without this!");
					}
				}
			} else {
				int ID = Integer.parseInt(boardInfoArray[2]);
				if (main.getObjectIDMap().containsKey(ID)) {
					ObjectID = (GameObject) main.getObjectIDMap().get(ID);
				} else {
					throw new XMLException(
							"Cannot find GameObject_"
									+ ID
									+ " that is supposed to be already made in the room. Cannot make BoardCell without this!");
				}
			}

		}
		String groundType = null;
		if (!boardInfoArray[3].equals("null")) {
			groundType = boardInfoArray[3];
		}
		return new BoardCell(position, ObjectID, groundType);
	}

	/**
	 * Helper method to return the first two digits of a game object ID
	 * 
	 * @param loadingString
	 * @return
	 */
	public Integer checkPlayerID(String loadingString) {
		// input: "101010"
		String temp = loadingString.substring(0, 2);
		int id = Integer.parseInt(temp);
		return id;
	}

	/**
	 * Main just for quick testing for parsing strings. This will be removed in
	 * the future.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String loadingString = "101010";
		String temp = loadingString.substring(0, 2);
		int id = Integer.parseInt(temp);
		System.out.println(id);
	}

	public void populateDoorLocationMap(Room room) {
		BoardCell[][] roomGrid = room.getBoardGrid();
		for (int y = 0; y < roomGrid.length; y++) {
			for (int x = 0; x < roomGrid[y].length; x++) {
				if (roomGrid[y][x].getObjectOnCell() instanceof Door) {
					System.out.println("Adding Door_"
							+ roomGrid[y][x].getObjectOnCell().getObjectID());
					room.addToDoorsLocation(roomGrid[y][x].getObjectOnCell()
							.getObjectID(), roomGrid[y][x]);
				}
			}
			System.out.println();
		}

	}

	public void populatePlayerLocationMap(Room room) {
		BoardCell[][] roomGrid = room.getBoardGrid();
		for (int y = 0; y < roomGrid.length; y++) {
			for (int x = 0; x < roomGrid[y].length; x++) {
				if (roomGrid[y][x].getObjectOnCell() instanceof PlayableCharacter) {
					System.out.println("Adding Player_"
							+ roomGrid[y][x].getObjectOnCell().getObjectID());
					room.addToPlayerLocationMap(roomGrid[y][x]
							.getObjectOnCell().getObjectID(), roomGrid[y][x]);
				}
			}
		}
	}
}
