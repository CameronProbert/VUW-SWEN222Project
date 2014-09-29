package catgame.logic;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import catgame.GameObjects.GameItem;
import catgame.GameObjects.MasterObject;

public class Room {
	private BoardCell[][] boardGrid;
	private ArrayList<MasterObject> roomInventory = new ArrayList<MasterObject>();
	private HashMap<Character, BoardCell> locationMap = new HashMap<Character, BoardCell>();

	/**
	 * TODO Room parameters are subject to change, once the .xml file readers
	 * are sorted we will have to decide how we want to load rooms
	 * 
	 * @param groundFile
	 * @param objectLayerFile
	 */
	public Room(String groundFile) {
		boardGrid = loadFile(groundFile);
	}

	/**
	 * Reads and constructs byte[][]'s for the board
	 * 
	 * @param size
	 * @param file
	 * @param objectLayerFile
	 * @return byte[][]
	 */
	public BoardCell[][] loadFile(String groundFile) {

		return new BoardCell[0][0];
	}

	public BoardCell[][] getBoardGrid() {
		return boardGrid;
	}

	public ArrayList<MasterObject> getRoomInventory() {
		return roomInventory;
	}

	/**
	 * TODO need to sort out the orienation part of how this will work
	 * 
	 * @param playerID
	 * @param direction
	 */
	public void movePlayer(int playerID, String direction) {
		Position playerCurrentPos = locationMap.get(playerID).getPosition();
		// new GameError("Move Player Direction not reconised" + direction);

	}

	/**
	 * TODO
	 * 
	 * @param playerID
	 * @param direction
	 */
	public void playerAction(int playerID, String direction) {

	}

	/**
	 * TODO
	 * 
	 * @param playerID
	 * @param direction
	 * @param attackPower 
	 */
	public void playerAttack(int playerID, String direction, int attackPower) {

	}
	
	/**
	 * Algorithm that determines the position for the next move
	 * @return
	 */
	private Position findPosition(String direction, Position playerPosition){
		//Get the grapics oriantation then detemine which direction on the array the move will be
		return new Position(0, 0);
	}
}
