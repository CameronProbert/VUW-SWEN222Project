package catgame.DataStorage;
import java.util.List;

import org.jdom2.Element;

import catgame.GameObjects.*;

public class SavingHelperMethods {
	private SavingMain main;
	private SavingMasterObjects masterObj;
	
	
	public SavingHelperMethods(SavingMain main, SavingMasterObjects masterObj){
		this.main = main;
		this.masterObj = masterObj;
	}
	
	public Element writeMasterObject(MasterObject obj) {
		if(obj instanceof Boss){
			return masterObj.writeBoss(obj);
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
}
