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

	public GameObject loadBoss(Element element) {
		int id = Integer.parseInt(element.getAttribute("id").getValue());

		int health = Integer.parseInt(element.getChild("health").getText());

		// TODO load list of items that Boss has
		List<GameItem> inventory = new ArrayList<GameItem>(); // <--- FILL THIS

		int attackPower = Integer.parseInt(element.getChild("attackPower")
				.getText());
		return new Boss(id, attackPower, health, inventory);
	}

	public GameObject loadBush(Element element) {
		return new Bush(Integer.parseInt(element.getAttribute("id").getValue()));
	}

	public GameObject loadChest(Element element) throws XMLException {
		int ID = Integer.parseInt(element.getAttribute("id").getValue());
		List<GameItem> inventory = new ArrayList<GameItem>();
		for(Element inventoryElement: element.getChild("Inventory").getChildren()){
			inventory.add((GameItem) verifyElement(inventoryElement));
			// TODO Check casting!!
		}
		return new Chest(ID, inventory);
	}

	public GameObject loadDoor(Element element) {

		return null;
	}

	public GameObject loadFood(Element element) {
		int heal = Integer.parseInt(element.getChild("heal").getText());
		// TODO check that the casting below is okay to use!!
		return (GameObject) new Food(Integer.parseInt(element
				.getAttribute("id").getValue()), heal);
	}

	public GameObject loadKey(Element element) {
		Key key = new Key(Integer.parseInt(element.getAttribute("id")
				.getValue()));
		if (!element.getChild("owner_ID").getText().equals("null")) {
			int ownerID = Integer.parseInt(element.getChild("owner_ID")
					.getText());
			if (main.getObjectIDMap().containsKey(ownerID)) {
				key.setOwner(main.getObjectIDMap().get(ownerID));
			} else {
				// TODO if player isn't created yet, put on stack of
				// "to do stuff" until player is created
			}
		} else {
			key.setOwner(null);
		}
		// TODO check that the casting below is okay to use!!
		return (GameObject) key;
	}

	public GameObject loadMinion(Element element) {

		return null;
	}

	/**
	 * PlayableCharacter(int ownerID, int ID, Room currentRoom, Direction
	 * direction, int attackPower, int health, List<GameItem> items) {
	 * 
	 * @param element
	 * @return
	 * @throws XMLException
	 */
	public GameObject loadPlayableCharacter(Element element)
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
		int attackPower = Integer.parseInt(element.getAttribute("attackPower")
				.getValue());
		int health = Integer
				.parseInt(element.getAttribute("health").getValue());
		List<GameItem> inventoryList = new ArrayList<GameItem>();
		List<Element> inventoryElementsList = element.getChild("Inventory")
				.getChildren();
		for (Element inventoryElement : inventoryElementsList) {
			inventoryList.add((GameItem) verifyElement(inventoryElement));
		}
		int roomID = Integer
				.parseInt(element.getAttribute("RoomID").getValue());
		Room currentRoom = main.getRoomIDMap().get(roomID);
		return new PlayableCharacter(ID, currentRoom, dir, attackPower, health,
				inventoryList);
	}

	public GameObject loadRock(Element element) {

		return new Rock(Integer.parseInt(element.getAttribute("id").getValue()));
	}

	public GameObject loadTree(Element element) {

		return new Tree(Integer.parseInt(element.getAttribute("id").getValue()));
	}
}
