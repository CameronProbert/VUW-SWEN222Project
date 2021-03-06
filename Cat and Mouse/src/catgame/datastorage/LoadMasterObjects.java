package catgame.datastorage;

import java.util.*;

import catgame.gameObjects.*;
import catgame.logic.BoardData;
import catgame.logic.Room;
import catgame.logic.GameUtil.Direction;

import org.jdom2.*;

/**
 * Loads all MasterObjects from xml file.
 * 
 * @author MIla
 *
 */
public class LoadMasterObjects {
	private LoadingGameMain main;
	private BoardData boardData;

	public LoadMasterObjects(LoadingGameMain main, BoardData boardData) {
		this.main = main;
		this.boardData = boardData;
	}

	/**
	 * Determine what kind of element it is and what GameObject to make.
	 * 
	 * @param element
	 * @return MasterObject
	 * @throws XMLException
	 */
	public MasterObject verifyElement(Element element) throws XMLException {
		if (element == null) {
			throw new XMLException("element is null when trying to vertify");
		}
		int id = Integer.parseInt(element.getAttribute("id").getValue());
		if (main.getObjectIDMap().containsKey(id)) {
			return main.getObjectIDMap().get(id);
		}

		if (element.getName().equals("Boss")) {
			return loadBoss(element);
		} else if (element.getName().equals("Bush")) {
			return loadBush(element);
		} else if (element.getName().equals("Chest")) {
			return loadChest(element);
		} else if (element.getName().equals("Door")) {
			return loadDoor(element);
		} else if (element.getName().equals("Food")) {
			return loadFood(element);
		} else if (element.getName().equals("Key")) {
			return loadKey(element);
		} else if (element.getName().equals("Rock")) {
			return loadRock(element);
		} else if (element.getName().equals("Tree")) {
			return loadTree(element);
		} else if (element.getName().equals("PlayableCharacter")) {
			return loadPlayableCharacter(element);
		} else if (element.getName().equals("Minion")) {
			return loadMinion(element);
		} else if (element.getName().equals("Fence")) {
			return loadFence(element);
		} else if (element.getName().equals("Hedge")) {
			return loadHedge(element);
		}
		throw new XMLException("Cannot determine GameObject element!");
	}

	/**
	 * Makes Fence from element information
	 * 
	 * @param element
	 * @return Fence
	 */
	private Fence loadFence(Element element) {

		return new Fence(
				Integer.parseInt(element.getAttribute("id").getValue()));
	}

	/**
	 * Makes Hedge from element information
	 * 
	 * @param element
	 * @return Hedge
	 */
	private Hedge loadHedge(Element element) {
		return new Hedge(
				Integer.parseInt(element.getAttribute("id").getValue()));
	}

	/**
	 * Makes Boss from element information
	 * 
	 * @param element
	 * @return Boss
	 * @throws XMLException
	 */
	public Boss loadBoss(Element element) throws XMLException {
		int id = Integer.parseInt(element.getAttribute("id").getValue());
		int health = Integer.parseInt(element.getChild("Health").getText());
		List<GameItem> inventory = new ArrayList<GameItem>();
		for (Element inventoryElement : element.getChild("Inventory")
				.getChildren()) {
			inventory.add((GameItem) verifyElement(inventoryElement));
		}
		int attackPower = Integer.parseInt(element.getChild("AttackPower")
				.getText());
		Boss boss = new Boss(id, attackPower, health, inventory);
		boardData.getObjStorer().addNPC(id, boss);
		return boss;
	}

	/**
	 * Makes Bush from element information
	 * 
	 * @param element
	 * @return Bush
	 */
	public Bush loadBush(Element element) {
		return new Bush(Integer.parseInt(element.getAttribute("id").getValue()));
	}

	public Chest loadChest(Element element) throws XMLException {
		int ID = Integer.parseInt(element.getAttribute("id").getValue());
		List<GameItem> inventory = new ArrayList<GameItem>();
		for (Element inventoryElement : element.getChild("Inventory")
				.getChildren()) {
			inventory.add((GameItem) verifyElement(inventoryElement));
		}
		Chest chest = new Chest(ID, inventory);
		boardData.getObjStorer().addChest(ID, chest);
		return chest;
	}

	/**
	 * Makes Door from element information
	 * 
	 * @param element
	 * @return Door
	 */
	public Door loadDoor(Element element) {
		int ID = Integer.parseInt(element.getAttribute("id").getValue());
		String directionEnum = element.getChild("Direction").getText();
		Direction dir = null;
		if (directionEnum.equals("NORTH")) {
			dir = Direction.NORTH;
		} else if (directionEnum.equals("EAST")) {
			dir = Direction.EAST;
		} else if (directionEnum.equals("SOUTH")) {
			dir = Direction.SOUTH;
		} else {
			dir = Direction.WEST;
		}
		int roomID = Integer.parseInt(element.getChildText("RoomID"));
		Room currentRoom = main.getRoomIDMap().get(roomID);
		Door door = new Door(ID, dir, currentRoom);
		main.getDoorIDMap().put(ID, door);
		boardData.getObjStorer().addDoor(ID, door);
		return door;
	}

	/**
	 * Makes Food from element information
	 * 
	 * @param element
	 * @return Food
	 */
	public Food loadFood(Element element) {
		int heal = Integer.parseInt(element.getChild("Heal").getText());
		Food food = new Food(Integer.parseInt(element.getAttribute("id")
				.getValue()), heal);
		boardData.getObjStorer().addItems(food.getObjectID(), food);
		return food;
	}

	/**
	 * Makes Key from element information
	 * 
	 * @param element
	 * @return Key
	 */
	public Key loadKey(Element element) {
		Key key = new Key(Integer.parseInt(element.getAttribute("id")
				.getValue()));
		if (!element.getChild("owner_ID").getText().equals("null")) {
			int ownerID = Integer.parseInt(element.getChild("owner_ID")
					.getText());
			if (main.getObjectIDMap().containsKey(ownerID)) {
				if (main.getObjectIDMap().get(ownerID) instanceof GameObject) {
					System.out.println("okay");
				}
				key.setOwner((GameObject) main.getObjectIDMap().get(ownerID));
			} else {
				// TODO if player isn't created yet, put on stack of
				// "to do stuff" until player is created
			}
		} else {
			key.setOwner(null);
		}
		boardData.getObjStorer().addItems(key.getObjectID(), key);
		return key;
	}

	/**
	 * Makes Minion from element information
	 * 
	 * @param element
	 * @return Minion
	 * @throws XMLException
	 */
	public Minion loadMinion(Element element) throws XMLException {
		int id = Integer.parseInt(element.getAttribute("id").getValue());
		int roomID = Integer.parseInt(element.getChildText("RoomID"));
		Room currentRoom = main.getRoomIDMap().get(roomID);
		int attackPower = Integer.parseInt(element.getChildText("AttackPower"));
		int health = Integer.parseInt(element.getChildText("Health"));
		List<GameItem> inventoryList = new ArrayList<GameItem>();
		List<Element> inventoryElementsList = element.getChild("Inventory")
				.getChildren();
		for (Element inventoryElement : inventoryElementsList) {
			inventoryList.add((GameItem) verifyElement(inventoryElement));
		}
		Minion minion = new Minion(id, currentRoom, attackPower, health,
				inventoryList);
		boardData.getObjStorer().addNPC(id, minion);
		return minion;
	}

	/**
	 * Makes PlayableCharacter from element information Only gets to this method
	 * if we are reading xml for an old game
	 * 
	 * @param element
	 * @return PlayableCharacter
	 * @throws XMLException
	 */
	public PlayableCharacter loadPlayableCharacter(Element element)
			throws XMLException {
		// get ID
		int ID = Integer.parseInt(element.getAttribute("id").getValue());
		// get Direction
		String directionEnum = element.getChild("Direction").getText();
		Direction dir = null;
		if (directionEnum.equals("NORTH")) {
			dir = Direction.NORTH;
		} else if (directionEnum.equals("EAST")) {
			dir = Direction.EAST;
		} else if (directionEnum.equals("SOUTH")) {
			dir = Direction.SOUTH;
		} else {
			dir = Direction.WEST;
		}
		// get attackPower
		int attackPower = Integer.parseInt(element.getChildText("AttackPower"));
		// get health
		int health = Integer.parseInt(element.getChildText("Health"));
		// make inventory
		List<GameItem> inventoryList = new ArrayList<GameItem>();
		List<Element> inventoryElementsList = element.getChild("Inventory")
				.getChildren();
		for (Element inventoryElement : inventoryElementsList) {
			inventoryList.add((GameItem) verifyElement(inventoryElement));
		}

		// create Player
		PlayableCharacter character = new PlayableCharacter(ID, dir,
				attackPower, health, inventoryList);
		boardData.getObjStorer().addplayableChs(character.getObjectID(),
				character);
		return character;
	}

	/**
	 * Makes Rock from element information
	 * 
	 * @param element
	 * @return Rock
	 */
	public Rock loadRock(Element element) {

		return new Rock(Integer.parseInt(element.getAttribute("id").getValue()));
	}

	/**
	 * Makes Tree from element information
	 * 
	 * @param element
	 * @return Tree
	 */
	public Tree loadTree(Element element) {

		return new Tree(Integer.parseInt(element.getAttribute("id").getValue()));
	}
}
