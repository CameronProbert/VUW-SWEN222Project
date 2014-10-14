package catgame.datastorage;

import java.util.*;

import catgame.gameObjects.*;
import catgame.logic.Room;
import catgame.logic.GameUtil.Direction;

import org.jdom2.*;

public class LoadMasterObjects {
	private LoadingGameMain main;

	public LoadMasterObjects(LoadingGameMain main) {
		this.main = main;
	}

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
		}
		throw new XMLException("Cannot determine GameObject element!");
	}

	public Boss loadBoss(Element element) {
		int id = Integer.parseInt(element.getAttribute("id").getValue());

		int health = Integer.parseInt(element.getChild("health").getText());

		// TODO load list of items that Boss has
		List<GameItem> inventory = new ArrayList<GameItem>(); // <--- FILL THIS

		int attackPower = Integer.parseInt(element.getChild("attackPower")
				.getText());
		return new Boss(id, attackPower, health, inventory);
	}

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
		return new Chest(ID, inventory);
	}

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
		
		return door;
	}

	public Food loadFood(Element element) {
		int heal = Integer.parseInt(element.getChild("heal").getText());
		// TODO check that the casting below is okay to use!!
		return new Food(
				Integer.parseInt(element.getAttribute("id").getValue()), heal);
	}

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

		return key;
	}

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
		return new Minion(id, currentRoom, attackPower, health, inventoryList);
	}

	/**
	 * PlayableCharacter(int ownerID, int ID, Room currentRoom, Direction
	 * direction, int attackPower, int health, List<GameItem> items) {
	 * 
	 * @param element
	 * @return
	 * @throws XMLException
	 */
	public PlayableCharacter loadPlayableCharacter(Element element)
			throws XMLException {
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
		int attackPower = Integer.parseInt(element.getChildText("attackPower"));
		int health = Integer.parseInt(element.getChildText("health"));
		List<GameItem> inventoryList = new ArrayList<GameItem>();
		List<Element> inventoryElementsList = element.getChild("Inventory")
				.getChildren();
		for (Element inventoryElement : inventoryElementsList) {
			inventoryList.add((GameItem) verifyElement(inventoryElement));
		}
		// int roomID = Integer
		// .parseInt(element.getChildText("RoomID"));
		// Room currentRoom = main.getRoomIDMap().get(roomID);
		PlayableCharacter character = new PlayableCharacter(ID, dir,
				attackPower, health, inventoryList);
		
		return character;
	}

	public Rock loadRock(Element element) {

		return new Rock(Integer.parseInt(element.getAttribute("id").getValue()));
	}

	public Tree loadTree(Element element) {

		return new Tree(Integer.parseInt(element.getAttribute("id").getValue()));
	}
}
