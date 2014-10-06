package catgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dan
 * 
 * This class holds all of the rooms in the game.
 * TODO Fill out the boardData
 */
public class BoardData {

	private static List<Room> allRooms = new ArrayList<Room>();

	public BoardData() {
		
	}

	/**
	 * TODO Loading level
	 * @return
	 */
	public List<Room> loadLevel() {
		ArrayList toLoad = new ArrayList<Room>();
		return toLoad;
	}
	
	/**
	 * 
	 * @return all of the rooms
	 */
	public static List<Room> getAllRooms(){
		return allRooms;
	}

}
