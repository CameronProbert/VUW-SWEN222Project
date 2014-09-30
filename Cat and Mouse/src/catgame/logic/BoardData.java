package catgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 
 * @author Dan
 * 
 *         stores all of the boards data
 */
public class BoardData {

	private ArrayList<Room> allRooms;

	public BoardData() {
		
	}

	/**
	 * TODO
	 * @return
	 */
	public ArrayList<Room> loadLevel() {
		ArrayList toLoad = new ArrayList<Room>();
		return toLoad;
	}
	
	public ArrayList<Room> getAllRooms(){
		return allRooms;
	}

}
