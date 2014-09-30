package catgame.DataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

import catgame.GameObjects.*;
import catgame.logic.*;

public class SavingMain {
	private Document document;
	private Element root;
	private BoardData boardData;

	public SavingMain(BoardData boardData) throws IOException {
		this.boardData = boardData;
		document = new Document();
		root = new Element("CatGame");
		document.setRootElement(root);
		write();
	}

	/** 
	 *  Creates an xml file 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void write() throws IOException {
		writeBoardData();
		// Outputting xml file
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(document, new FileOutputStream(new File("test.xml")));
	}
	
	public void writeBoardData(){
			Element boardDataElement = new Element("BoardDate");
			ArrayList<Room> boardDataRooms = boardData.getAllRooms();
			boardDataElement.setAttribute(new Attribute("allRooms", "" + boardDataRooms.size())); 

			for(int id = 0; id < boardDataRooms.size(); id++){
				try {
					boardDataElement.addContent(writeRoom(boardDataRooms.get(id), id));
				} catch (XMLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	}

	private Element writeRoom(Room room, int id) throws XMLException {
		Element roomElement = new Element("Room_" + id);
		roomElement.setAttribute(new Attribute("id", ""+id));
		if(room == null){ throw new XMLException("Room_"+ id + " is null");}
		
		// BoardCell[][] --> need to figure out how to save this :/
		//roomElement.addContent(new Element("boardGrid").setText(room.getBoardGrid().));
		
		// Inventory
		for(MasterObject obj : room.getRoomInventory()){
			roomElement.addContent(writeMasterObject(obj));
		}
		return roomElement;
	}

	private Element writeMasterObject(MasterObject obj) {
		if(obj instanceof Boss){
			return writeBoss(obj);
		}
		return null;
	}

	private Element writeBoss(MasterObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

//	/**
//	 * Main for testing writing out xml files without evoking the whole game.
//	 * Will be removed in the future.
//	 * 
//	 * @param args
//	 * @throws FileNotFoundException
//	 * @throws IOException
//	 */
//	public static void main(String[] args) throws FileNotFoundException,
//			IOException {
//		new Writer();
//	}
}
