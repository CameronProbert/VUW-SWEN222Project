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
			int ID = Integer.parseInt(boardInfoArray[2]);
			if (main.getObjectIDMap().containsKey(ID)) {
				ObjectID = (GameObject) main.getObjectIDMap().get(ID);
				System.out.println("Found object ID: " + ID);
			} else {
				 throw new XMLException(
				 "Cannot find GameObject_" + ID + " that is supposed to be already made in the room. Cannot make BoardCell without this!");
				//System.out.println("***********   Cannot find GameObject ID = " + ID + "   ***********");
			}
		} 
		String groundType = null;
		if (!boardInfoArray[3].equals("null")) {
			groundType = boardInfoArray[3];
		}
		System.out.println("GroundType is: " + groundType);
		return new BoardCell(position, ObjectID, groundType);
	}
	
	public Integer checkPlayerID(String loadingString){
		// input: "101010"
		String temp = loadingString.substring(0, 2);
		int id = Integer.parseInt(temp);
		return id;
	}

	// public void addPlayerToRoom(Room room, )

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
				if(roomGrid[y][x].getObjectOnCell() instanceof Door){
					System.out.println("Adding Door_" + roomGrid[y][x].getObjectOnCell().getObjectID());
					room.addToDoorsLocation(roomGrid[y][x].getObjectOnCell().getObjectID(), roomGrid[y][x]);
				}
			}
			System.out.println();
		}
		
		
	}
}
