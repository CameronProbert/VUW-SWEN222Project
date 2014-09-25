package catgame.logic;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import catgame.GameObjects.GameItem;
import catgame.GameObjects.MasterObject;

public class Room {
	private BoardCell[][] boardGrid;
	private ArrayList<MasterObject> roomInventory= new ArrayList<MasterObject>();
	
	/**
	 * TODO
	 * Room parameters are subject to change, once the .xml file readers are sorted we will have to decide how we want to load rooms 
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
		
		return new BoardCell[0][0] ;
	}

	public BoardCell[][] getBoardGrid() {
		return boardGrid;
	}
	
	public ArrayList<MasterObject> getRoomInventory(){
		return roomInventory;
	}
}
