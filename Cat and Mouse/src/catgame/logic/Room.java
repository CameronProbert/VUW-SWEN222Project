package catgame.logic;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import catgame.GameObjects.GameItem;
import catgame.GameObjects.MasterObject;

public class Room {
	private byte[][] boardGrid;
	
	private ArrayList<MasterObject> roomInventory= new ArrayList<MasterObject>();
	
	/**
	 * TODO
	 * Room parameters are subject to change, once the .xml file readers are sorted we will have to decide how we want to load rooms 
	 * @param groundFile
	 * @param objectLayerFile
	 */
	public Room(String groundFile, String objectLayerFile) {
		boardGrid = loadFile(groundFile);
	}

	/**
	 * Reads and constructs byte[][]'s for the board
	 * 
	 * @param size
	 * @param file
	 * @return byte[][]
	 */
	public byte[][] loadFile(String file) {

		byte[][] loadingBoard = null;

		int y = 0;
		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new FileReader(file));
			String sizeLine = buffer.readLine();
			String[] sizeValues = sizeLine.split(",");

			int width = Integer.parseInt(sizeValues[0]);
			int height = Integer.parseInt(sizeValues[1]);

			// System.out.println("width :"+width+" height :"+height);

			loadingBoard = new byte[width][height];

			loop: while (true) {
				String line = buffer.readLine();
				if (line == null) {
					break loop;
				}
				String[] values = line.split(",");
				for (int x = 0; x < values.length; x++) {
					// System.out.println("Value :"+values[x]+" Byte :"+Byte.valueOf(values[x]));
					loadingBoard[x][y] = Byte.valueOf(values[x]);
				}
				y++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return loadingBoard;
	}

	public byte[][] getBoardGrid() {
		return boardGrid;
	}
	
	public ArrayList<MasterObject> getRoomInventory(){
		return roomInventory;
	}
}
