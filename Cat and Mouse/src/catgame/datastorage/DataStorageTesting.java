package catgame.datastorage;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;
import org.jdom2.*;

import catgame.logic.*;
import catgame.gameObjects.*;

/**
 * Main testing class for data storage. It sets up the loading and saving
 * classes as well as make a test game.
 * 
 * @author MIla
 * 
 *
 */
public class DataStorageTesting {
	private SavingMain savingMain;
	private SavingMasterObjects savingMasterObj;
	private SavingHelper savingHelper;
	private LoadingGameMain loadingMain;
	private LoadingHelper loadingHelper;
	private LoadMasterObjects loadingMasterObj;

	private TestingSavingMasterObj testSaveObj;
	private BoardData boardData;
	private File testingXML;

	public DataStorageTesting() throws IOException, XMLException, JDOMException {
		makeTestRoom();
		setUpSaving();
		setUpLoading();
		testSaveObj = new TestingSavingMasterObj(this);

		testSavingMasterObjects();
		testBoardGrid();

	}

	/**
	 * Creates all the saving classes with a temp xml file to read to
	 * 
	 * @throws IOException
	 */
	public void setUpSaving() throws IOException {
		this.testingXML = new File("testing_01.xml");
		this.savingMain = new SavingMain(boardData, testingXML);
		testingXML = savingMain.getXMLFile();
		this.savingMasterObj = savingMain.getSavingMasterObj();
		this.savingHelper = savingMain.getHelper();
	}

	/**
	 * Creates all the loading classes.
	 * 
	 * @throws JDOMException
	 * @throws XMLException
	 */
	public void setUpLoading() throws JDOMException, XMLException {
		this.loadingMain = new LoadingGameMain(true, testingXML);
		this.loadingMasterObj = loadingMain.getLoadMasterObj();
		this.loadingHelper = loadingMain.getHelper();
	}

	/**
	 * Makes the test game.
	 */
	public void makeTestRoom() {
		this.boardData = new BoardData();
		this.boardData.loadLevelOne();

		if (boardData.getAllRooms().isEmpty()) {
			System.out.println("room list is empty");
		} else if (boardData.getAllRooms().get(0).getRoomInventory().isEmpty()) {
			System.out.println("room's inventory is empty");
		}
	}

	@Test
	public void testSavingMasterObjects() throws XMLException {
		testSaveObj.testRoomInventory(boardData.getAllRooms().get(0)
				.getRoomInventory());
	}

	@Test
	public void testBoardGrid() {
		BoardCell[][] roomGrid = boardData.getAllRooms().get(0).getBoardGrid();
		Element boardElement = null;
		Throwable savingException = null;
		try {
			boardElement = savingMain.writeRoomGrid(boardData.getAllRooms()
					.get(0));
		} catch (XMLException e) {
			savingException = e;
		}
		assertTrue(savingException == null); // exception shouldn't be thrown
		assertFalse(boardElement == null);
		Throwable loadingException = null;
		BoardCell[][] loadedGrid = null;
		try {
			loadedGrid = loadingMain.loadRoomGrid(boardElement);
		} catch (XMLException e1) {
			loadingException = e1;
		}
		assertTrue(
				"loading boardGrid with element that is saved the boardgrid",
				loadingException == null);
		assertTrue(loadedGrid.length == roomGrid.length);
		assertTrue(loadedGrid[0].length == roomGrid[0].length);
		for (int y = 0; y < roomGrid.length; y++) {
			for (int x = 0; x < roomGrid[y].length; x++) {
				if (roomGrid[y][x].getObjectOnCell() == null) {
					if (loadedGrid[y][x].getObjectOnCell() == null) {
						assertTrue(roomGrid[y][x].getObjectOnCell() == loadedGrid[y][x]
								.getObjectOnCell());
					} else {
						fail();
					}
				} else {
					assertTrue(roomGrid[y][x].getObjectOnCell().getObjectID() == loadedGrid[y][x]
							.getObjectOnCell().getObjectID());
				}
			}
		}
		// testing breaking writeRoomGrid method by passing null
		try {
			boardElement = savingMain.writeRoomGrid(null);
		} catch (XMLException e) {
			savingException = e;
		}
		assertFalse(savingException == null); // exception should be thrown
	}

	@Test
	public void testRoom() {
		Room room = boardData.getAllRooms().get(0);
		Throwable exception = null;
		Element saveRoomElement = null;
		try {
			saveRoomElement = savingMain.writeRoom(room, 0);
		} catch (XMLException e) {
			exception = e;
			fail();
		}
		assertFalse(saveRoomElement == null);
		Room loadedRoom = null;
		try {
			loadedRoom = loadingMain.loadRooms(saveRoomElement);
		} catch (XMLException e) {
			exception = e;
			e.printStackTrace();
		}
		assertFalse(loadedRoom == null);
		assertEquals("Room Id's ", room.getRoomID(), loadedRoom.getRoomID());
		assertEquals("Room inventory size", room.getRoomInventory().size(),
				loadedRoom.getRoomInventory().size());
	}

	public SavingMain getSavingMain() {
		return savingMain;
	}

	public SavingMasterObjects getSavingMasterObj() {
		return savingMasterObj;
	}

	public SavingHelper getSavingHelper() {
		return savingHelper;
	}

	public static void main(String[] args) throws IOException, XMLException,
			JDOMException {
		new DataStorageTesting();
	}

}
