package catgame.datastorage;

import catgame.gameObjects.*;
import catgame.logic.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

/**
 * Main loading class for reading the xml file and loading up a game. We are
 * using JDOM v2.0.5 from http://www.jdom.org/ The loading is similar style to
 * saving. We make a boarddata first then make all the rooms. All the rooms load
 * their inventory and boardgrid. If we are loading a new game, we load from a
 * standard starting game xml file (which doesn't load the playable character
 * until the end). But if we are loading an old game from a save, we simply load
 * it without complicated conditions.
 * 
 * @author MIla
 *
 */
public class LoadingGameMain {
	private Map<Integer, MasterObject> objectIDMap = new HashMap<Integer, MasterObject>();
	private Map<Integer, Room> roomIDMap = new HashMap<Integer, Room>();
	private Map<Integer, PlayableCharacter> playerIDMap = new HashMap<Integer, PlayableCharacter>();
	private Map<Integer, Door> doorIDMap = new HashMap<Integer, Door>();
	private boolean isLoadOldGame;
	private LoadMasterObjects masterObjectLoader;
	private LoadingHelper helper;
	private BoardData boardData = new BoardData();
	private File xmlFile;

	public LoadingGameMain(boolean isLoadOldGame, File xmlFile)
			throws JDOMException, XMLException {
		this.isLoadOldGame = isLoadOldGame;
		// make obj loader
		this.masterObjectLoader = new LoadMasterObjects(this, boardData);
		// make loader helper
		this.helper = new LoadingHelper(this, isLoadOldGame);

		this.xmlFile = xmlFile;
		boardData = startLoading();
		if (boardData != null) {
			System.out.println("Done");
		}
		boardData.attachDoorsForLevelOne();
	}

	/**
	 * Begin loading from xml file.
	 * 
	 * @return
	 * @throws XMLException
	 * @throws JDOMException
	 */
	public BoardData startLoading() throws XMLException, JDOMException {
		SAXBuilder builder = new SAXBuilder(); // make SAXBuilder
		// if xmlFile != null, we are loading old game
		if (xmlFile == null) {
			// xmlFile is null meaning we are loading a standard new game
			URL fileURL = LoadingGameMain.class
					.getResource("files/TestingMultiple.xml");
			try {
				xmlFile = new File(fileURL.toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		try {
			// make document and read root
			Document document = (Document) builder.build(xmlFile);
			Element root = document.getRootElement();
			// validate root
			if (root == null || root.getChildren().isEmpty()) {
				throw new XMLException("XML file is empty at root");
			}
			return loadBoardData(root);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Take the boardData element and parses the information to make all rooms.
	 * Creates the BoardData when loading of rooms is finished.
	 * 
	 * @param root
	 * @return
	 * @throws XMLException
	 */
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

	/**
	 * Takes a room element and parses the information for the room. Firstly
	 * makes a room inventory.
	 * 
	 * @param roomElement
	 * @return
	 * @throws XMLException
	 */
	public Room loadRooms(Element roomElement) throws XMLException {
		if (roomElement == null) {
			throw new XMLException("roomElement is null");
		}
		// get room id
		int id = Integer.parseInt(roomElement.getAttribute("id").getValue());
		Room room = addRoomToMap(id);
		// create room inventory
		createRoomInventory(roomElement.getChildren().get(0), room);
		linkAllDoors(roomElement.getChildren().get(1));
		// create boardgrid
		room.loadBoardCellToRoom(loadRoomGrid(roomElement.getChildren().get(2)));
		// at this stage, the doors are all made, so we now link them
		helper.populateDoorLocationMap(room);
		if (isLoadOldGame) {
			// Once the players are created, update their location on map
			helper.populatePlayerLocationMap(room);
		}

		return room;
	}

	// 192.168.20.107
	/**
	 * 
	 * @param element
	 * @throws XMLException
	 */
	private void linkAllDoors(Element element) throws XMLException {
		// <DoorLinks>(431010,null) (431011,null) (421013,null)
		// (411012,null)</DoorLinks>
		// String doorLinksWholeSting = element.getText();
		// System.out.println("Link all doors, whole string: " +
		// doorLinksWholeSting);
		// String[] doorLinksWholeArray = doorLinksWholeSting.split(" ");
		// // now format is (431010,null)
		// for(String link : doorLinksWholeArray){
		// link = link.substring(1, link.length()-1);
		// String[] singleDoorLink = link.split(",");
		// int firstDoorID = Integer.parseInt(singleDoorLink[0]);
		// if(!doorIDMap.containsKey(firstDoorID)){
		// throw new XMLException("First Door" + firstDoorID +
		// " is missing from the DoorIDMap");
		// }
		// Door firstDoor = doorIDMap.get(firstDoorID);
		// if(!singleDoorLink[1].equals("null")){
		// int secondDoorID = Integer.parseInt(singleDoorLink[1]);
		// if(!doorIDMap.containsKey(secondDoorID)){
		// throw new XMLException("Second Door" + secondDoorID +
		// " is missing from the DoorIDMap");
		// }
		// Door secondDoor = doorIDMap.get(secondDoorID);
		//
		// }
		// else{
		// firstDoor.addOtherSide(null, 0);
		// }
		//
		// }

	}

	/**
	 * Create a list of gameObjects for the room inventory.
	 * 
	 * @param childrenElement
	 * @param room
	 * @throws XMLException
	 */
	private void createRoomInventory(Element childrenElement, Room room)
			throws XMLException {
		List<Element> gameObjList = childrenElement.getChildren();
		// read and create all the non-playable game objects
		for (Element objElement : gameObjList) {
			if (objElement.getName().equals("non-playerableInventory")) {
				for (Element nonPlayerableObj : objElement.getChildren()) {
					MasterObject inventoryObj = masterObjectLoader
							.verifyElement(nonPlayerableObj);
					addObjectToMap(inventoryObj);
					// Add object to room inventory
					if (inventoryObj instanceof GameObject) {
						room.addToInventory((GameObject) inventoryObj);
					}

				}
			}
			// isLoadOldGame is a check if we are loading an old game or new
			// game. if we are loading an old game then we load and create
			// players from xml
			// if not then we create standard players ready for a new game after
			// reading xml
			else if (isLoadOldGame) {
				if (objElement.getName().equals("playerableInventory")) {
					for (Element playerableObj : objElement.getChildren()) {
						PlayableCharacter inventoryObj = (PlayableCharacter) masterObjectLoader
								.verifyElement(playerableObj);
						if (inventoryObj instanceof PlayableCharacter) {
							addPlayerToMap((PlayableCharacter) inventoryObj);
						}

						addObjectToMap(inventoryObj);
						room.addToInventory((GameObject) inventoryObj);
					}
				}
			}

		}
	}

	/**
	 * Makes the room's board grid.
	 * 
	 * @param childrenElement
	 * @return BoardCell[][] roomGrid
	 * @throws XMLException
	 */
	public BoardCell[][] loadRoomGrid(Element childrenElement)
			throws XMLException {

		// get the dimensions of the boardCell array
		int firstArrayLength = Integer.parseInt(childrenElement.getChild(
				"FirstArray.length").getText());
		int secondArrayLength = Integer.parseInt(childrenElement.getChild(
				"SecondArray.length").getText());
		BoardCell[][] boardCell = new BoardCell[firstArrayLength][secondArrayLength];
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
			// System.out.println("Row: " + row + " wholeElementText = "
			// + wholeElementText);
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
				// System.out.println("Calling helper method on string: "
				// + wholeElementArray[x]);
				boardCell[y][x] = helper.loadBoardCell(wholeElementArray[x]);
			}
		}
		return boardCell;
	}

	/**
	 * Adds a room and it's ID to map
	 * 
	 * @param ID
	 * @return
	 */
	public Room addRoomToMap(int ID) {
		if (!roomIDMap.containsKey(ID)) {
			roomIDMap.put(ID, new Room(ID));
		}
		return roomIDMap.get(ID);
	}

	/**
	 * Adds a gameObject and it's ID to map
	 * 
	 * @param inventoryObj
	 * @throws XMLException
	 */
	public void addObjectToMap(MasterObject inventoryObj) throws XMLException {
		if (inventoryObj == null) {
			throw new XMLException("inventoryObj is null");
		}
		objectIDMap.put(inventoryObj.getObjectID(), inventoryObj);
	}

	/**
	 * Adds a player and it's ID to map
	 * 
	 * @param obj
	 */
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

	public Map<Integer, Door> getDoorIDMap() {
		return doorIDMap;
	}

	public BoardData getBoardData() throws XMLException {
		if (boardData == null) {
			throw new XMLException("BoardData is null");
		}
		return boardData;
	}

	public LoadingHelper getHelper() {
		return helper;
	}

	public LoadMasterObjects getLoadMasterObj() {
		return masterObjectLoader;
	}

	public static void main(String[] args) throws JDOMException, XMLException {
		// List<Integer> temp = new ArrayList<Integer>();
		// temp.add(23);
		// temp.add(2345);
		// new LoadingGameMain();

	}

}
