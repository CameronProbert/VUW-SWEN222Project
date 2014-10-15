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

	private BoardData boardData;
	private File testingXML;

	public DataStorageTesting() throws IOException{
		makeTestRoom();
		setUpSaving();

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
	public void testSavingMasterObjects() {
		List<GameObject> roomInventory = boardData.getAllRooms().get(0).getRoomInventory();
		for(int i = 0; i < roomInventory.size(); i++){
			GameObject obj = roomInventory.get(i);
			//if(obj instanceof )
		}
	}
	
	public static void main(String[] args){
		
	}
}
