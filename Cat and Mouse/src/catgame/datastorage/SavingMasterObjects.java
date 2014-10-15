package catgame.datastorage;

import java.io.CharConversionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

import catgame.gameObjects.*;

/**
 * Writes MasterObjects of the game to an xml file. Each MasterObject needs
 * different things to save to the xml. We are using JDOM v2.0.5 from
 * http://www.jdom.org/
 * 
 * @author MIla
 *
 */
public class SavingMasterObjects {
	private SavingMain main;
	private SavingHelper helper;

	public SavingMasterObjects(SavingMain main) {
		this.main = main;
		this.helper = new SavingHelper(main, this);
	}

	/**
	 * Writes Boss information to xml
	 * 
	 * @param obj
	 * @return Element 
	 * @throws XMLException
	 */
	public Element writeBoss(MasterObject obj) throws XMLException {
		Boss boss = (Boss) obj;
		Element bossElement = new Element("Boss");
		bossElement.setAttribute(new Attribute("id", "" + boss.getObjectID()));
		bossElement.addContent(new Element("Health").setText(""
				+ boss.getHealth()));
		bossElement.addContent(helper.makeInventory(boss.getInventory()));
		bossElement.addContent(new Element("MaxItems").setText("" + 6));
		bossElement.addContent(new Element("AttackPower").setText(""
				+ boss.getAttackPower()));

		return bossElement;
	}

	/**
	 *  Writes Bush information to xml
	 * @param 
	 * @return Element
	 */
	public Element writeBush(MasterObject obj) {
		Bush bush = (Bush) obj;
		Element bushElement = new Element("Bush");
		bushElement.setAttribute(new Attribute("id", "" + bush.getObjectID()));
		return bushElement;
	}

	/**
	 *  Writes Chest information to xml
	 * @param obj
	 * @return Element
	 * @throws XMLException
	 */
	public Element writeChest(MasterObject obj) throws XMLException {
		Chest chest = (Chest) obj;
		Element chestElement = new Element("Chest");
		chestElement
				.setAttribute(new Attribute("id", "" + chest.getObjectID()));
		chestElement.addContent(helper.makeInventory(chest.openChest()));
		return chestElement;
	}

	/**
	 *  Writes Door information to xml
	 * @param obj
	 * @return Element
	 */
	public Element writeDoor(MasterObject obj) {
		Door door = (Door) obj;
		Element doorElement = new Element("Door");
		doorElement.setAttribute(new Attribute("id", "" + door.getObjectID()));
		doorElement.addContent(new Element("Direction").setText(door
				.getDoorsWallEdge().name()));
		doorElement.addContent(new Element("RoomID").setText(""
				+ door.getRoom().getRoomID()));
		Element doorEntranceElement = new Element("EntranceDoor");
		if (door.enterDoor() == null) {
			doorEntranceElement.setText("null");
			main.getDoorLinksMap().put(door.getObjectID(), null);
		} else {
			doorEntranceElement.setText("" + door.enterDoor().getObjectID());
			main.getDoorLinksMap().put(door.getObjectID(),
					door.enterDoor().getObjectID());
		}
		doorElement.addContent(doorEntranceElement);
		doorElement.addContent(new Element("isLocked").setText(""
				+ door.getIsLocked()));

		return doorElement;
	}

	/**
	 *  Writes Food information to xml
	 * @param obj
	 * @return Element
	 */
	public Element writeFood(MasterObject obj) {
		Food food = (Food) obj;
		Element foodElement = new Element("Food");
		foodElement.setAttribute(new Attribute("id", "" + food.getObjectID()));
		foodElement
				.addContent(new Element("Heal").setText("" + food.getHeal()));

		return foodElement;
	}

	/**
	 *  Writes Key information to xml
	 * @param obj
	 * @return Element
	 */
	public Element writeKey(MasterObject obj) {
		Key key = (Key) obj;
		Element keyElement = new Element("Key");
		keyElement.setAttribute(new Attribute("id", "" + key.getObjectID()));
		// TODO remove owner ID stuff
		if (key.getOwner() == null) {
			keyElement.addContent(new Element("owner_ID").setText("null"));
		} else {
			keyElement.addContent(new Element("owner_ID").setText(""
					+ key.getOwner().getObjectID()));
		}

		return keyElement;
	}

	/**
	 *  Writes Minion information to xml
	 * @param obj
	 * @return Element
	 * @throws XMLException
	 */
	public Element writeMinion(MasterObject obj) throws XMLException {
		Minion minion = (Minion) obj;
		Element minionElement = new Element("Minion");
		minionElement.setAttribute(new Attribute("id", ""
				+ minion.getObjectID()));
		minionElement.addContent(new Element("RoomID").setText(""
				+ minion.getCurrentRoom().getRoomID()));
		minionElement.addContent(new Element("Health").setText(""
				+ minion.getHealth()));
		minionElement.addContent(new Element("AttackPower").setText(""
				+ minion.getAttackPower()));
		minionElement.addContent(helper.makeInventory(minion.getInventory()));

		return minionElement;
	}

	/**
	 *  Writes Rock information to xml
	 * @param obj
	 * @return Element
	 */
	public Element writeRock(MasterObject obj) {
		Rock rock = (Rock) obj;
		Element rockElement = new Element("Rock");
		rockElement.setAttribute(new Attribute("id", "" + rock.getObjectID()));

		return rockElement;
	}

	/**
	 *  Writes Tree information to xml
	 * @param obj
	 * @return Element
	 */
	public Element writeTree(MasterObject obj) {
		Tree tree = (Tree) obj;
		Element treeElement = new Element("Tree");
		treeElement.setAttribute(new Attribute("id", "" + tree.getObjectID()));

		return treeElement;
	}

	/**
	 *  Writes PlayableCharacter information to xml
	 * @param obj
	 * @return Element
	 * @throws XMLException
	 */
	public Element writePlayableCharacter(MasterObject obj) throws XMLException {
		PlayableCharacter character = (PlayableCharacter) obj;
		Element characterElement = new Element("PlayableCharacter");
		characterElement.setAttribute(new Attribute("id", ""
				+ character.getObjectID()));

		// -------------- Room_ID --------------
		// characterElement.addContent(new Element("RoomID").setText(""
		// + character.getCurrentRoom().getRoomID()));
		// ------------------------------------------------
		// ------------------- Direction ------------------
		characterElement.addContent(new Element("Direction").setText(character
				.getFacingDirection().name()));

		// ------------------------------------------------
		// ----------------- AttackPower ------------------
		characterElement.addContent(new Element("AttackPower").setText(""
				+ character.getAttackPower()));
		// ------------------------------------------------
		// ----------------- AttackPower ------------------
		characterElement.addContent(new Element("Health").setText(""
				+ character.getHealth()));
		// ------------------------------------------------
		// -------------- Inventory List ------------------
		characterElement.addContent(helper.makeInventory(character
				.getInventory()));
		// ------------------------------------------------
		return characterElement;
	}

	/**
	 *  Writes Fence information to xml
	 * @param obj
	 * @return Element
	 */
	public Element writeFence(MasterObject obj) {
		Fence fence = (Fence) obj;
		Element fenceElement = new Element("Fence");
		fenceElement
				.setAttribute(new Attribute("id", "" + fence.getObjectID()));
		return fenceElement;
	}

	/**
	 *  Writes Hedge information to xml
	 * @param obj
	 * @return Element
	 */
	public Element writeHedge(MasterObject obj) {
		Hedge hedge = (Hedge) obj;
		Element hedgeElement = new Element("Hedge");
		hedgeElement
				.setAttribute(new Attribute("id", "" + hedge.getObjectID()));

		return hedgeElement;
	}

}
