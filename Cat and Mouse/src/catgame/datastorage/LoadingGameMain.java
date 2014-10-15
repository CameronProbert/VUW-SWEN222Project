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
		boardData.TESTattachDoors();
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
		linkAllDoors(roomElement.getChildren().get(1));
		room.loadBoardCellToRoom(loadRoomGrid(roomElement.getChildren().get(2)));
		helper.populateDoorLocationMap(room); 
		// TODO fix door links on dans side
		if (isLoadOldGame) {
			helper.populatePlayerLocationMap(room);
		}

		return room;
	}

	// 192.168.20.107
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
						// System.out.println("Adding a PlayableCharacter");
						addPlayerToMap((PlayableCharacter) inventoryObj);
					}

					addObjectToMap(inventoryObj);
					room.addToInventory((GameObject) inventoryObj);
				}
			}
		}
	}

	public BoardCell[][] loadRoomGrid(Element childrenElement)
			throws XMLException {

		// get the dimensions of the boardCell array
		int firstArrayLength = Integer.parseInt(childrenElement.getChild(
				"FirstArray.length").getText());
		int secondArrayLength = Integer.parseInt(childrenElement.getChild(
				"SecondArray.length").getText());
		// System.out.println("IN LOAD ROOM GRID. FirstArray.length = "
		// + firstArrayLength + " SecondArray.length = "
		// + secondArrayLength);
		// make new boardCell array using dimensions
		BoardCell[][] boardCell = new BoardCell[firstArrayLength][secondArrayLength];
		// ----------------------------Test----------------------------------------
		// for (Integer id : objectIDMap.keySet()) {
		// System.out.println("ID: " + id + " Object: "
		// + objectIDMap.get(id).toString());
		// }
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

		// for (int y = 0; y < firstArrayLength; y++) {
		// for (int x = 0; x < secondArrayLength; x++) {
		// System.out.print("(y = " + y + " x = " + x + " " +
		// boardCell[y][x].getGroundType() + ") ");
		// }
		// System.out.println();
		// }
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

	public Map<Integer, Door> getDoorIDMap() {
		return doorIDMap;
	}

	public BoardData getBoardData() throws XMLException {
		if (boardData == null) {
			throw new XMLException("BoardData is null");
		}
		return boardData;
	}

	
	public LoadingHelper getHelper(){
		return helper;
	}
	
	public LoadMasterObjects getLoadMasterObj(){
		return masterObjectLoader;
	}
	public static void main(String[] args) throws JDOMException, XMLException {
		// List<Integer> temp = new ArrayList<Integer>();
		// temp.add(23);
		// temp.add(2345);
		// new LoadingGameMain();

	}

}