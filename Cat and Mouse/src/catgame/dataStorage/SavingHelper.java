package catgame.dataStorage;
import java.util.List;

import org.jdom2.Element;

import catgame.gameObjects.*;
import catgame.logic.BoardCell;

public class SavingHelper {
	private SavingMain main;
	private SavingMasterObjects masterObj;
	
	
	public SavingHelper(SavingMain main, SavingMasterObjects masterObj){
		this.main = main;
		this.masterObj = masterObj;
	}
	
	public Element writeMasterObject(MasterObject obj) {
		if(obj instanceof Boss){
			return masterObj.writeBoss(obj);
		}
		if(obj instanceof Chest){
			return masterObj.writeChest(obj);
		}
		if(obj instanceof Food){
			return masterObj.writeFood(obj);
		}
		if(obj instanceof PlayableCharacter){
			return masterObj.writePlayableCharacter(obj);
		}
		return null;
	}
	
	public Element makeInventory(List<GameItem> list){
		Element inventoryList = new Element("Inventory");
		for(GameItem item: list){
			inventoryList.addContent(writeMasterObject(item));
		}
		return inventoryList;
	}
	
	public String makeBoardCell(BoardCell cell) throws XMLException{
		if(cell.getPosition() == null){
			throw new XMLException("BoardCell Position is null");
		}
		String boardCellInfo = cell.getPosition().getX() + ", " + cell.getPosition().getY() + ", ";
		if (cell.getObjectOnCell() == null) {
			boardCellInfo += "null, ";
		} else {
			boardCellInfo += cell.getObjectOnCell()
					.getObjectID() + ", ";
		}
		if (cell.getGroundType() == null
				|| cell.getGroundType().equals("")) {
			boardCellInfo += "null";
		} else {
			boardCellInfo += cell.getGroundType();
		}
		
		return boardCellInfo;
	}
}
