package catgame.dataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

import catgame.gameObjects.*;

public class SavingMasterObjects {
	private SavingMain main;
	private SavingHelper helper;

	public SavingMasterObjects(SavingMain main) {
		this.main = main;
		this.helper = main.getHelper();
	}

	public Element writeBoss(MasterObject obj) {
		Boss boss = (Boss) obj;
		Element bossElement = new Element("Boss");
		bossElement.setAttribute(new Attribute("id", "" + boss.getObjectID()));

		bossElement.addContent(new Element("health").setText(""
				+ boss.getHealth()));
		bossElement.addContent(helper.makeInventory(boss.getInventory()));
		bossElement.addContent(new Element("maxItems").setText("" + 6));
		bossElement.addContent(new Element("attackPower").setText(""
				+ boss.getAttackPower()));

		return bossElement;
	}

	public Element writeBush(MasterObject obj) {
		Bush bush = (Bush) obj;
		Element bushElement = new Element("bush");
		bushElement.setAttribute(new Attribute("id", "" + bush.getObjectID()));
		return bushElement;
	}

	public Element writeChest(MasterObject obj) {
		Chest chest = (Chest) obj;
		Element chestElement = new Element("Chest");
		chestElement
				.setAttribute(new Attribute("id", "" + chest.getObjectID()));

		// TODO BoardCell!!
		//String boardCellInfo = chest.getCurrentCell().getPosition().getX() + ", " + chest.getCurrentCell().getPosition().getY() + ", " +
		//Element boardCellElement = new Element("boardCell").setText()

		chestElement.addContent(helper.makeInventory(chest.openChest()));
		return chestElement;
	}
	
	public Element writeDoor(MasterObject obj){
		Door door = (Door) obj;
		Element doorElement = new Element("Door");
		doorElement.setAttribute(new Attribute("id", "" + door.getObjectID()));
		// TODO BoardCell!!
		// TODO Door entrance's room
		//doorElement.addContent(new Element("DoorEntrance").setAttribute(new Attribute("Room", "" + door.)))
		return doorElement;
	}

	public Element writeFood(MasterObject obj) {
		Food food = (Food) obj;
		Element foodElement = new Element("Food");
		foodElement.setAttribute(new Attribute("id", "" + food.getObjectID()));
		foodElement
				.addContent(new Element("heal").setText("" + food.getHeal()));

		return foodElement;
	}

	public Element writeKey(MasterObject obj) {
		Key key = (Key) obj;
		Element keyElement = new Element("Key");
		keyElement.setAttribute(new Attribute("id", "" + key.getObjectID()));
		keyElement.addContent(new Element("owner_ID").setText(""
				+ key.getOwner().getObjectID()));
		return keyElement;
	}

	public Element writeMinion(MasterObject obj) {

		return null;
	}

	public Element writeRock(MasterObject obj) {
		Rock rock = (Rock) obj;
		Element rockElement = new Element("Rock");
		rockElement.setAttribute(new Attribute("id", "" + rock.getObjectID()));

		return rockElement;
	}

	public Element writeTree(MasterObject obj) {
		Tree tree = (Tree) obj;
		Element treeElement = new Element("Tree");
		treeElement.setAttribute(new Attribute("id", "" + tree.getObjectID()));

		return treeElement;
	}

	public Element writePlayableCharacter(MasterObject obj) {
		PlayableCharacter character = (PlayableCharacter) obj;
		Element characterElement = new Element("PlayableCharacter");
		characterElement.setAttribute(new Attribute("id", ""
				+ character.getObjectID()));
		characterElement.addContent(helper.makeInventory(character
				.getInventory()));
		characterElement.addContent(new Element("maxItems").setText("" + 6));
		// characterElement.addContent(new
		// Element("In_RoomID").setText(character.getCurrentRoom().))

		return characterElement;
	}

}