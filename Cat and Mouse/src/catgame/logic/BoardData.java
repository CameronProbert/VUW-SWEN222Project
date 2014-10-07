package catgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dan
 * 
 *         This class holds all of the rooms in the game. TODO Fill out the
 *         boardData
 */
public class BoardData {

	private static List<Room> allRooms = new ArrayList<Room>();

	public BoardData() {

	}

	/**
	 * TODO Loading level
	 * 
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
	public static List<Room> getAllRooms() {
		return allRooms;
	}

	/**
	 * Populates the allRooms list with a new list of rooms that was created
	 * when game is loaded from a saved game
	 * 
	 * @param newRooms List<Room>
	 */
	public void populateRooms(List<Room> newRooms) {
		allRooms = newRooms;
	}

}
