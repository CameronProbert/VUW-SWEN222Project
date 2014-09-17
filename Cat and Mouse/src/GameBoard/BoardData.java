package GameBoard;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 
 * @author Dan
 * 
 *         stores all of the boards data
 */
public class BoardData {

	private Room[] levelOne;

	public BoardData() {

	}

	/**
	 * TODO
	 * 
	 * @param levelSize
	 *            the amount of rooms in the level
	 * @return
	 */
	public Room[] loadLevel(int levelSize) {
		return new Room[levelSize];
	}

}
