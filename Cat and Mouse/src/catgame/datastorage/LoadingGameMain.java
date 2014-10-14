package catgame.datastorage;

import catgame.gameObjects.*;
import catgame.logic.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LoadingGameMain {
	private Map<Integer, MasterObject> objectIDMap = new HashMap<Integer, MasterObject>();
	private Map<Integer, Room> roomIDMap = new HashMap<Integer, Room>();
	private Map<Integer, PlayableCharacter> playerIDMap = new HashMap<Integer, PlayableCharacter>();
	private boolean isLoadOldGame;
	private LoadMasterObjects masterObjectLoader;
	private LoadingHelper helper;
	private BoardData boardData = new BoardData();
	private File xmlFile;


	public LoadingGameMain(boolean isLoadOldGame, File xmlFile)
			throws JDOMException, XMLException {

		// make obj loader
		this.masterObjectLoader = new LoadMasterObjects(this);
		// make loader helper
		this.helper = new LoadingHelper(this);
		this.isLoadOldGame = isLoadOldGame;
		this.xmlFile = xmlFile;
		boardData = startLoading();
		if (boardData != null) {
			System.out.println("Done");
		}
	}

	public BoardData startLoading() throws XMLException, JDOMException {
		SAXBuilder builder = new SAXBuilder(); // make SAXBuilder
		// if xmlFile != null, we are loading old game
		if (xmlFile == null) {
			// xmlFile is null meaning we are loading a standard new game
			xmlFile = new File("RoomBuilder_01.xml");
		}
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
		Element boardDataElement = root.getChildren().get(0);
		if (boardDataElement == null
				|| !boardDataElement.getName().equals("BoardData")) {
			throw new XMLException("XML file is empty at boardData");
		}
		// create rooms
		List<Room> allRooms = new ArrayList<Room>();
		for (Element roomElement : boardDataElement.getChildren()) {
			allRooms.add(loadRooms(roomElement));
		}
		// update BoardData class
		boardData.populateRooms(allRooms);
		return boardData;
	}

	private Room loadRooms(Element roomElement) throws XMLException {
		if (roomElement == null) {
			throw new XMLException("roomElement is null");
		}
		int id = Integer.parseInt(roomElement.getAttribute("id").getValue());
		Room room = addRoomToMap(id);
		createRoomInventory(roomElement.getChildren().get(0), room);
		room.loadBoardCellToRoom(loadRoomGrid(roomElement.getChildren().get(1)));
		return room;
	}

	private void createRoomInventory(Element childrenElement, Room room)
			throws XMLException {
		List<Element> gameObjList = childrenElement.getChildren();
		for (Element objElement : gameObjList) {
			if (objElement.getName().equals("non-playerableInventory")) {
				for (Element nonPlayerableObj : objElement.getChildren()) {
					MasterObject inventoryObj = masterObjectLoader
							.verifyElement(nonPlayerableObj);
					addObjectToMap(inventoryObj);
					if (inventoryObj instanceof GameObject) {
						room.addToInventory((GameObject) inventoryObj);
					}

				}
			}
			// TODO Check casting!!!!
			// isLoadOldGame is a check if we are loading an old game or new
			// game. if we are then we load and create players from xml
			// if not then we create standard players ready for a new game
			else if (isLoadOldGame
					&& objElement.getName().equals("playerableInventory")) {
				for (Element playerableObj : objElement.getChildren()) {
					PlayableCharacter inventoryObj = (PlayableCharacter) masterObjectLoader
							.verifyElement(playerableObj);
					if (inventoryObj instanceof PlayableCharacter) {
						System.out.println("Adding a PlayableCharacter");
						addPlayerToMap((PlayableCharacter) inventoryObj);
					}

					addObjectToMap(inventoryObj);
					room.addToInventory((GameObject) inventoryObj);

				}
			}
		}
	}

	private BoardCell[][] loadRoomGrid(Element childrenElement)
			throws XMLException {

		// get the dimensions of the boardCell array
		int firstArrayLength = Integer.parseInt(childrenElement.getChild(
				"FirstArray.length").getText());
		int secondArrayLength = Integer.parseInt(childrenElement.getChild(
				"SecondArray.length").getText());
		System.out.println("IN LOAD ROOM GRID. FirstArray.length = "
				+ firstArrayLength + " SecondArray.length = "
				+ secondArrayLength);
		// make new boardCell array using dimensions
		BoardCell[][] boardCell = new BoardCell[firstArrayLength][secondArrayLength];
		// ----------------------------Test----------------------------------------
		for (Integer id : objectIDMap.keySet()) {
			System.out.println("ID: " + id + " Object: "
					+ objectIDMap.get(id).toString());
		}
		// ------------------------------------------------------------------------
		// Start dealing with boardCell info stored in elements
		// Get the element's text containing all the info and split
		// the string into an array, "wholeElementArray". Each String value
		// in this array is in format (x,y,ID,groundType) representing info
		// to make each BoardCell. Once each BoardCell is created,
		// it is saved in the new boardCell[][] which is then returned
		for (int y = 0; y < firstArrayLength; y++) {
			String row = "Row" + y;
			String wholeElementText = childrenElement.getChild(row).getText();
			System.out.println("Row: " + row + " wholeElementText = "
					+ wholeElementText);
			String[] wholeElementArray = wholeElementText.split(" ");
			if (wholeElementArray.length != boardCell[y].length) {
				throw new XMLException(
						"String[] element info is not same size as boardCell[]. wholeElementArray.length: "
								+ wholeElementArray.length
								+ " , boardCell[y].length: "
								+ boardCell[y].length);
			}
			for (int x = 0; x < secondArrayLength; x++) {
				// use helper method to parse string of (x,y,ID,groundType)
				// and make new BoardCells
				System.out.println("Calling helper method on string: "
						+ wholeElementArray[x]);
				boardCell[y][x] = helper.loadBoardCell(wholeElementArray[x]);
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

	public void addObjectToMap(MasterObject inventoryObj) throws XMLException {
		if (inventoryObj == null) {
			throw new XMLException("inventoryObj is null");
		}
		objectIDMap.put(inventoryObj.getObjectID(), inventoryObj);
	}

	public void addPlayerToMap(PlayableCharacter obj) {
		if (!playerIDMap.containsKey(obj.getObjectID())) {
			playerIDMap.put(obj.getObjectID(), obj);
		} else if (playerIDMap.containsKey(obj.getObjectID())) {
			playerIDMap.put(obj.getObjectID(), obj);
		}
	}

	public Map<Integer, Room> getRoomIDMap() {
		return roomIDMap;
	}

	public Map<Integer, MasterObject> getObjectIDMap() {
		return objectIDMap;
	}

	public BoardData getBoardData() throws XMLException {
		if (boardData == null) {
			throw new XMLException("BoardData is null");
		}
		return boardData;
	}

	public static void main(String[] args) throws JDOMException, XMLException {
		// List<Integer> temp = new ArrayList<Integer>();
		// temp.add(23);
		// temp.add(2345);
		// new LoadingGameMain();

	}

}