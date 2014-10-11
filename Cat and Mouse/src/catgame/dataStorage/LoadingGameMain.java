package catgame.dataStorage;

import javax.xml.stream.*;

import catgame.*;
import catgame.clientserver.*;
import catgame.gameObjects.*;
import catgame.gui.*;
import catgame.gui.renderpanel.*;
import catgame.gui.textfiles.*;
import catgame.logic.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class LoadingGameMain {
	private Map<Integer, GameObject> objectIDMap = new HashMap<Integer, GameObject>();
	private Map<Integer, Room> roomIDMap = new HashMap<Integer, Room>();
	private List<Integer> playerIDList = new ArrayList<Integer>();
	private LoadMasterObjects masterObjectLoader;
	private BoardData boardData;

	public LoadingGameMain(List<Integer> playerIDList) throws JDOMException,
			XMLException {
		// make obj loader
		this.masterObjectLoader = new LoadMasterObjects(this);
		this.playerIDList = playerIDList; // save ID list
		boardData = startLoading();
	}

	public BoardData startLoading() throws XMLException, JDOMException {
		SAXBuilder builder = new SAXBuilder(); // make SAXBuilder
		File xmlFile = new File("/Cat and Mouse/test.xml");
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
			room.addToInventory(masterObjectLoader.verifyElement(objElement));
		}
		return null;
	}

	private BoardCell[][] loadRoomGrid(Element childrenElement) {
		// BoardCell[][] roomGrid = new BoardCell[childrenElement.getat][];
		return null;
	}

	public Map<Integer, Room> getRoomIDMap() {
		return roomIDMap;
	}

	public Map<Integer, GameObject> getObjectIDMap() {
		return objectIDMap;
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

	public BoardData getBoardData() throws XMLException {
		if (boardData == null) {
			throw new XMLException("BoardData is null");
		}
		return boardData;
	}

}