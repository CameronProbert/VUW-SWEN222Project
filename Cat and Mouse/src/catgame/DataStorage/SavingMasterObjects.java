package catgame.DataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

import catgame.GameObjects.*;

public class SavingMasterObjects {
	private SavingMain main;
	public SavingMasterObjects(SavingMain main) {
		this.main = main;
	}
	
	public Element writeBoss(MasterObject obj){
		Boss boss = (Boss) obj;
		Element bossElement = new Element("Boss");
		bossElement.setAttribute(new Attribute("id", "" + boss.getObjectID()));
		
		bossElement.addContent(new Element("health").setText("" + boss.getHealth()));
		// TODO boss inventory
		bossElement.addContent(new Element("maxItems").setText("" + 6));
		bossElement.addContent(new Element("attackPower").setText("" + boss.getAttackPower()));
		
		return bossElement;
	}

	public Element writeChest(MasterObject obj) {
		
		return null;
	}

	public Element writeFood(MasterObject obj) {

		return null;
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

		return null;
	}
}
