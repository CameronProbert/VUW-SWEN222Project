package catgame.datastorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

import catgame.gameObjects.*;
import catgame.logic.*;

public class SavingMain {
	private SavingMasterObjects masterObj;
	private SavingHelper helper;
	private Document document;
	private Element root;
	private BoardData boardData;
	private File xmlFile;

	public SavingMain(BoardData boardData) throws IOException {
		this.masterObj = new SavingMasterObjects(this);
		this.helper = new SavingHelper(this, masterObj);

		this.boardData = boardData;
		this.document = new Document();
		this.root = new Element("CatGame");
		document.setRootElement(root);
		write();
	}

	/**
	 * Creates an xml file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void write() throws IOException {
		document.getRootElement().addContent(writeBoardData());
		// Outputting xml file
		XMLOutputter xmlOutputter = new XMLOutputter(
				org.jdom2.output.Format.getPrettyFormat());
		xmlFile = new File("test.xml");
		xmlOutputter.output(document, new FileOutputStream(xmlFile));
	}

	/**
	 * Saves the details of BoardData class, in particular creates elements for
	 * all the rooms
	 * 
	 * @return Element
	 */
	public Element writeBoardData() {
		Element boardDataElement = new Element("BoardData");
		List<Room> boardDataRooms = boardData.getAllRooms();
		boardDataElement.setAttribute(new Attribute("allRooms", ""
				+ boardDataRooms.size()));

		// Add elements about all the rooms in boardData to boardDataElement
		for (int id = 0; id < boardDataRooms.size(); id++) {
			try {
				boardDataElement.addContent(writeRoom(boardDataRooms.get(id),
						id));
			} catch (XMLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return boardDataElement;
	}

	/**
	 * Saves the room's id, roomGrid, and details on objects in the room's
	 * inventory
	 * 
	 * @param room
	 *            Room
	 * @param id
	 *            int
	 * @return Element
	 * @throws XMLException
	 */
	private Element writeRoom(Room room, int id) throws XMLException {
		// Create element and include room ID
		Element roomElement = new Element("Room_" + id);
		roomElement.setAttribute(new Attribute("id", "" + id));
		if (room == null) {
			throw new XMLException("Room_" + id + " is null");
		}
		// ----------------------------------------------------------------

		// ------------ Inventory ------------
		// Split the Room Inventory into lists of non-playerable objects
		// and playerable
		List<GameObject> gameObjectsList = new ArrayList<GameObject>();
		List<GameObject> playersInRoomList = new ArrayList<GameObject>();

		for (GameObject gameobj : room.getRoomInventory()) {
			// Save all the objects that are ONLY playable character
			if (gameobj instanceof PlayableCharacter) {
				playersInRoomList.add(gameobj);
			} else {
				// Save all the objects that are NOT playable character
				gameObjectsList.add(gameobj);
			}
		}
		if (gameObjectsList.isEmpty()) {
			throw new XMLException("gameObjectsList is empty");
		} else if (playersInRoomList.isEmpty()) {
			throw new XMLException("playersInRoomList is empty");
		}
		Element roomInventoryElement = new Element("Inventory");
		Element nonplayerableObjectsElement = new Element(
				"non-playerableInventory");
		Element playerableObjectsElement = new Element("playerableInventory");
		// save non-playerable
		for (MasterObject obj : gameObjectsList) {
			nonplayerableObjectsElement.addContent(helper
					.writeMasterObject(obj));
		}
		// save playerable
		for (MasterObject obj : playersInRoomList) {
			playerableObjectsElement.addContent(helper.writeMasterObject(obj));
		}
		roomInventoryElement.addContent(nonplayerableObjectsElement);
		roomInventoryElement.addContent(playerableObjectsElement);

		roomElement.addContent(roomInventoryElement);// add to roomElement
		// ----------------------------------------------------------------

		// ------------ RoomGrid ------------
		BoardCell[][] roomGrid = room.getBoardGrid();
		Element boardGrid = new Element("boardGrid");
		// boardGrid.setAttribute(new Attribute("" + roomGrid.length, ""
		// + roomGrid[0].length));

		// TODO check about attributes!!! especially if roomGrid[0].length
		// works!!
		boardGrid.addContent(new Element("FirstArray.length").setText(""
				+ roomGrid.length));
		boardGrid.addContent(new Element("SecondArray.length").setText(""
				+ roomGrid[0].length));
		System.out.println("FirstArray.length: " + roomGrid.length);
		System.out.println("SecondArray.length: " + roomGrid[0].length);
		for (int y = 0; y < roomGrid.length; y++) {
			String rowName = "Row" + y;
			Element rowBoardCellElement = new Element(rowName);
			String cellValuesOfRow = "";
			for (int x = 0; x < roomGrid[y].length; x++) {
				String boardCellValues = "(";
				boardCellValues += helper.makeBoardCell(roomGrid[y][x]) + ") ";
				// if (x == roomGrid[y].length - 1) {
				// boardCellValues += ",";
				// }
				cellValuesOfRow += boardCellValues;
			}
			rowBoardCellElement.setText(cellValuesOfRow);
			boardGrid.addContent(rowBoardCellElement);
		}
		roomElement.addContent(boardGrid); // add to roomElement
		// ----------------------------------------------------------------

		return roomElement;
	}

	public SavingHelper getHelper() {
		return helper;
	}

	public File getXMLFile() {
		return xmlFile;
	}

	/**
	 * Main for testing writing out xml files without evoking the whole game.
	 * Will be removed in the future.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		RoomBuilder roomBuilder = new RoomBuilder();
		BoardData board = new BoardData();

		board.addRoom(roomBuilder.loadRoom(new ObjectStorer()));
		if (board.getAllRooms().isEmpty()) {
			System.out.println("room list is empty");
		} else if (board.getAllRooms().get(0).getRoomInventory().isEmpty()) {
			System.out.println("room's inventory is empty");
		} else {
			new SavingMain(board);
		}

	}
}
