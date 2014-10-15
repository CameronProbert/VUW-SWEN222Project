package catgame.datastorage;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.junit.Test;

import catgame.logic.*;
import catgame.gameObjects.*;

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

	public DataStorageTesting() throws IOException, XMLException{
		makeTestRoom();
		setUpSaving();
		testSaveObj = new TestingSavingMasterObj(this);
		
		testSavingMasterObjects();

	}

	public void setUpSaving() throws IOException {
		this.testingXML = new File("testing_01.xml");
		this.savingMain = new SavingMain(boardData, testingXML);
		this.savingMasterObj = savingMain.getSavingMasterObj();
		this.savingHelper = savingMain.getHelper();
	}

	public void makeTestRoom() {
		this.boardData = new BoardData();
		this.boardData.loadTestData();

		if (boardData.getAllRooms().isEmpty()) {
			System.out.println("room list is empty");
		} else if (boardData.getAllRooms().get(0).getRoomInventory().isEmpty()) {
			System.out.println("room's inventory is empty");
		}
	}

	@Test
	public void testSavingMasterObjects() throws XMLException {
		testSaveObj.testRoomInventory(boardData.getAllRooms().get(0).getRoomInventory());
	}
	
	public SavingMain getSavingMain(){
		return savingMain;
	}
	
	public SavingMasterObjects getSavingMasterObj(){
		return savingMasterObj;
	}
	
	public SavingHelper getSavingHelper(){
		return savingHelper;
	}
	
	public static void main(String[] args) throws IOException, XMLException{
		new DataStorageTesting();
	}
	
}
