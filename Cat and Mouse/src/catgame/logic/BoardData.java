package catgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dan
 * 
 *         stores all of the boards data
 */
public class BoardData {

	private List<Room> allRooms = new ArrayList<Room>();

	public BoardData() {
		
	}

	/**
	 * TODO
	 * @return
	 */
	public List<Room> loadLevel() {
		ArrayList toLoad = new ArrayList<Room>();
		return toLoad;
	}
	
	public List<Room> getAllRooms(){
		return allRooms;
	}

}
