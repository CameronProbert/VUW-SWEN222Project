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
	private Map<Integer, MasterObject> objectIDMap = new HashMap<Integer, MasterObject>();

	public LoadingGameMain() throws JDOMException, XMLException {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("/Cat and Mouse/test.xml");
		try {
			Document document = (Document) builder.build(xmlFile);
			Element root = document.getRootElement();
			if (root == null || root.getChildren().isEmpty()) {
				throw new XMLException("XML file is empty at root");
			}
			loadBoardData(root);

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void loadBoardData(Element root) throws XMLException,
			DataConversionException {
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

	}

	private Room loadRooms(Element roomElement) throws DataConversionException,
			XMLException {
		if (roomElement == null) {
			throw new XMLException("roomElement is null");
		}

		int id = roomElement.getAttribute("id").getIntValue();
		Room room = new Room(id);
		
		for (Element childrenElement : roomElement.getChildren()) {
			if (childrenElement.getName().equals("Inventory")) {
				room.addToInventory(createRoomInventory(childrenElement, room));
			}
			else if(childrenElement.getName().equals("boardGrid")){
				room.loadBoardCellToRoom(loadRoomGrid(childrenElement));
			}
		}
		
		return room;
	}

	private BoardCell[][] loadRoomGrid(Element childrenElement) {
		
		return null;
	}

	private GameObject createRoomInventory(Element childrenElement, Room room) {
		
		return null;
	}
}