package catgame.dataStorage;

import catgame.gameObjects.*;
import catgame.logic.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LoadingGameMain {
	private Map<Integer, GameObject> objectIDMap = new HashMap<Integer, GameObject>();
	private Map<Integer, Room> roomIDMap = new HashMap<Integer, Room>();
	private List<Integer> playerIDList = new ArrayList<Integer>();
	private LoadMasterObjects masterObjectLoader;
	private LoadingHelper helper;
	private BoardData boardData;

	public LoadingGameMain(List<Integer> playerIDList) throws JDOMException,
			XMLException {
		// make obj loader
		this.masterObjectLoader = new LoadMasterObjects(this);
		// make loader helper
		this.helper = new LoadingHelper(this);
		this.playerIDList = playerIDList; // save ID list
		boardData = startLoading();
	}

	public BoardData startLoading() throws XMLException, JDOMException {
		SAXBuilder builder = new SAXBuilder(); // make SAXBuilder
		File xmlFile = new File("test.xml");
		try {
			// make document and read root
			Document document = (Document) builder.build(xmlFile);
			Element root = document.getRootElement();
			// validate
			if (root == null || root.getChildren().isEmpty()) {
				throw new XMLException("XML file is empty at root");
			}
			return loadBoardData(root);

		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	private BoardData loadBoardData(Element root) throws XMLException {
		Element boardData = root.getChildren().get(0);
		if (boardData == null || !boardData.getName().equals("BoardData")) {
			throw new XMLException("XML file is empty at boardData");
		}
		// create rooms
		List<Room> allRooms = new ArrayList<Room>();
		for (Element roomElement : boardData.getChildren()) {
			allRooms.add(loadRooms(roomElement));
		}
		// create BoardData class
		BoardData board = new BoardData();
		board.populateRooms(allRooms);
		return board;
	}

	private Room loadRooms(Element roomElement) throws XMLException {
		if (roomElement == null) {
			throw new XMLException("roomElement is null");
		}
		int id = Integer.parseInt(roomElement.getAttribute("id").getValue());
		Room room = addRoomToMap(id);

		for (Element childrenElement : roomElement.getChildren()) {
			if (childrenElement.getName().equals("Inventory")) {
				createRoomInventory(childrenElement, room);
			} else if (childrenElement.getName().equals("boardGrid")) {
				room.loadBoardCellToRoom(loadRoomGrid(childrenElement));
			}
		}

		return room;
	}

	private GameObject createRoomInventory(Element childrenElement, Room room)
			throws XMLException {
		List<Element> gameObjList = childrenElement.getChildren();
		for (Element objElement : gameObjList) {
			if (objElement.getName().equals("non-playerable inventory")) {
				for (Element nonPlayerableObj : objElement.getChildren()) {
					room.addToInventory((GameObject) masterObjectLoader
							.verifyElement(nonPlayerableObj));
				}
			}
			// TODO Check casting!!!!
			else if (objElement.getName().equals("playerable inventory")) {
				for (Element playerableObj : objElement.getChildren()) {
					room.addToInventory((GameObject) masterObjectLoader
							.verifyElement(playerableObj));
				}
			}

		}
		return null;
	}

	private BoardCell[][] loadRoomGrid(Element childrenElement)
			throws XMLException {
		// get the dimensions of the boardCell array
		int firstArrayLength = Integer.parseInt(childrenElement.getChild(
				"FirstArray.length").getText());
		int secondArrayLength = Integer.parseInt(childrenElement.getChild(
				"SecondArray.length").getText());
		// make new boardCell array using dimensions
		BoardCell[][] boardCell = new BoardCell[firstArrayLength][secondArrayLength];
		// ------------------------------------------------------------------------
		// Start dealing with boardCell info stored in elements
		// Get the element's text containing all the info and split
		// the string into an array, "wholeElementArray". Each String value
		// in this array is in format (x,y,ID,groundType) representing info
		// to make each BoardCell. Once each BoardCell is created,
		// it is saved in the new boardCell[][] which is then returned
		for (int y = 0; y < boardCell.length; y++) {
			String row = "Row" + y;
			String wholeElementText = childrenElement.getChild(row).getText();
			String[] wholeElementArray = wholeElementText.split(",");
			if (wholeElementArray.length != boardCell[y].length) {
				throw new XMLException(
						"String[] element info is not same size as boardCell[]. wholeElementArray.length: "
								+ wholeElementArray.length
								+ " , boardCell[y].length: "
								+ boardCell[y].length);
			}
			for (int x = 0; x < boardCell[y].length; x++) {
				// use helper method to parse string of (x,y,ID,groundType)
				// and make new BoardCells
				boardCell[y][x] = helper.loadBoardCell(wholeElementArray[y]);
			}
		}
		return boardCell;
	}

	public Room addRoomToMap(int ID) {
		if (!roomIDMap.containsKey(ID)) {
			roomIDMap.put(ID, new Room(ID));
		}
		return roomIDMap.get(ID);
	}

	public void addObjectToMap(GameObject obj) {
		if (!objectIDMap.containsKey(obj.getObjectID())) {
			objectIDMap.put(obj.getObjectID(), obj);
		}
	}

	public Map<Integer, Room> getRoomIDMap() {
		return roomIDMap;
	}

	public Map<Integer, GameObject> getObjectIDMap() {
		return objectIDMap;
	}

	public BoardData getBoardData() throws XMLException {
		if (boardData == null) {
			throw new XMLException("BoardData is null");
		}
		return boardData;
	}

	public static void main(String[] args) throws JDOMException, XMLException {
		List<Integer> temp = new ArrayList<Integer>();
		temp.add(23);
		temp.add(2345);
		new LoadingGameMain(temp);
	}

}