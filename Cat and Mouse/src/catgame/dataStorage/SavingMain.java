package catgame.dataStorage;

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

	public SavingMain(BoardData boardData) throws IOException {
		masterObj = new SavingMasterObjects(this);
		helper = new SavingHelper(this, masterObj);

		this.boardData = boardData;
		document = new Document();
		root = new Element("CatGame");
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
		xmlOutputter.output(document,
				new FileOutputStream(new File("test.xml")));
	}

	public Element writeBoardData() {
		Element boardDataElement = new Element("BoardDate");
		List<Room> boardDataRooms = boardData.getAllRooms();
		boardDataElement.setAttribute(new Attribute("allRooms", ""
				+ boardDataRooms.size()));

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

	private Element writeRoom(Room room, int id) throws XMLException {
		Element roomElement = new Element("Room_" + id);
		roomElement.setAttribute(new Attribute("id", "" + id));
		if (room == null) {
			throw new XMLException("Room_" + id + " is null");
		}

		// BoardCell[][] --> need to figure out how to save this :/
		// roomElement.addContent(new
		// Element("boardGrid").setText(room.getBoardGrid().));

		Element boardGrid = new Element("boardGrid");
		BoardCell[][] roomGrid = room.getBoardGrid();

		for (int y = 0; y < roomGrid.length; y++) {
			Element rowBoardCellElement = new Element("Row" + y);
			String cellValuesOfRow = "[";
			for (int x = 0; x < roomGrid[y].length; x++) {
				String boardCellValues = "("
						+ roomGrid[y][x].getPosition().getX() + ", "
						+ roomGrid[y][x].getPosition().getX() + ", ";
				if (roomGrid[y][x].getObjectOnCell() == null) {
					boardCellValues += "null, ";
				} else {
					boardCellValues += roomGrid[y][x].getObjectOnCell()
							.getObjectID() + ", ";
				}
				if (roomGrid[y][x].getGroundType() == null
						|| roomGrid[y][x].getGroundType().equals("")) {
					boardCellValues += "null)";
				} else {
					boardCellValues += roomGrid[y][x].getGroundType() + ")";
				}
				cellValuesOfRow += boardCellValues; // TODO add space or not??
			}
			rowBoardCellElement.setText(cellValuesOfRow += "]");
			boardGrid.addContent(rowBoardCellElement);
		}

		// Inventory
		for (MasterObject obj : room.getRoomInventory()) {
			roomElement.addContent(helper.writeMasterObject(obj));
		}
		return roomElement;
	}

	public SavingHelper getHelper() {
		return helper;
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
		BoardCell[][] boardCell = new BoardCell[2][2];
		boardCell[0][0] = new BoardCell(new Position(0, 0), new Rock(23), null);
		boardCell[0][1] = new BoardCell(new Position(0, 1), new Rock(24), null);
		boardCell[1][0] = new BoardCell(new Position(1, 0), new Tree(25), null);
		boardCell[1][1] = new BoardCell(new Position(1, 1), new Tree(26), null);

		BoardData boardData = new BoardData();
		Room room = new Room(1, boardCell);
		room.getRoomInventory().add(new Food(55, 5));
		boardData.getAllRooms().add(room);

		new SavingMain(boardData);
	}
}
