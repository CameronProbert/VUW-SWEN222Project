package catgame.DataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

import catgame.GameObjects.*;

public class SavingMasterObjects {
	private SavingMain main;
	private SavingHelperMethods helper;
	
	public SavingMasterObjects(SavingMain main) {
		this.main = main;
		this.helper = main.getHelper();
	}
	
	public Element writeBoss(MasterObject obj){
		Boss boss = (Boss) obj;
		Element bossElement = new Element("Boss");
		bossElement.setAttribute(new Attribute("id", "" + boss.getObjectID()));
		
		bossElement.addContent(new Element("health").setText("" + boss.getHealth()));
		bossElement.addContent(helper.makeInventory(boss.getInventory()));
		bossElement.addContent(new Element("maxItems").setText("" + 6));
		bossElement.addContent(new Element("attackPower").setText("" + boss.getAttackPower()));
		
		return bossElement;
	}

	public Element writeChest(MasterObject obj) {
		Chest chest = (Chest) obj;
		Element chestElement = new Element("Chest");
		chestElement.setAttribute(new Attribute("id", "" + chest.getObjectID()));
		
		// TODO BoardCell!!
		
		chestElement.addContent(helper.makeInventory(chest.openChest()));
		return chestElement;
	}

	public Element writeFood(MasterObject obj) {
		Food food = (Food) obj;
		Element foodElement = new Element("Food");
		foodElement.setAttribute(new Attribute("id", "" + food.getObjectID()));
		foodElement.addContent(new Element("heal").setText("" + food.getHeal()));
		
		return foodElement;
	}

	public Element writeKey(MasterObject obj) {

		return null;
	}

	public Element writeMinion(MasterObject obj) {

		return null;
	}

	public Element writeRock(MasterObject obj) {

		return null;
	}

	public Element writeTree(MasterObject obj) {

		return null;
	}

	public Element writePlayableCharacter(MasterObject obj) {
		PlayableCharacter character = (PlayableCharacter) obj;
		Element characterElement = new Element("PlayableCharacter");
		characterElement.setAttribute(new Attribute("id", "" + character.getObjectID()));
		
		return characterElement;
	}
	
	
}
