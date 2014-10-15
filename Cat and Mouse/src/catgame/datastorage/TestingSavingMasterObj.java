package catgame.datastorage;

import java.util.List;

import static org.junit.Assert.*;

import org.jdom2.*;
import org.junit.Test;

import catgame.gameObjects.*;
import catgame.logic.*;

public class TestingSavingMasterObj {

	private DataStorageTesting testingMain;
	private SavingMasterObjects savingMasterObj;

	public TestingSavingMasterObj(DataStorageTesting testingMain) {
		this.testingMain = testingMain;
		this.savingMasterObj = new SavingMasterObjects(
				testingMain.getSavingMain());
	}

	public void testRoomInventory(List<GameObject> inventory)
			throws XMLException {
		Element testElement = null;
		for (int i = 0; i < inventory.size(); i++) {
			GameObject obj = inventory.get(i);
			if (obj instanceof Boss) {
				testElement = testBoss((Boss) obj);
			} else if (obj instanceof Bush) {
				testElement = testBush((Bush) obj);
			} else if (obj instanceof Door) {
				testElement = testDoor((Door) obj);
			} else if (obj instanceof Fence) {
				testElement = testFence((Fence) obj);
			} else if (obj instanceof Hedge) {
				testElement = testHedge((Hedge) obj);
			} else if (obj instanceof Key) {
				testElement = testKey((Key) obj);
			} else if (obj instanceof Minion) {
				testElement = testMinion((Minion) obj);
			} else if (obj instanceof Chest) {
				testElement = testChest((Chest) obj);
			} else if (obj instanceof Food) {
				testElement = testFood((Food) obj);
			} else if (obj instanceof PlayableCharacter) {
				testElement = testPlayableCharacter((PlayableCharacter) obj);
			} else if (obj instanceof Minion) {
				testElement = testMinion((Minion) obj);
			} else if (obj instanceof Rock) {
				testElement = testRock((Rock) obj);
			} else if (obj instanceof Tree) {
				testElement = testTree((Tree) obj);
			}
			if (testElement != null) {
				testElement(testElement, obj);
			}
			testElement = null;
		}
	}

	@Test
	private void testElement(Element testElement, GameObject obj) {
		assertTrue(testElement != null);
		assertTrue(!testElement.getAttributes().isEmpty()
				|| !testElement.getAttribute("id").getName().equals("id"));
		assertEquals("GameObject " + obj.toString() + " ID ",
				obj.getObjectID(),
				Integer.parseInt(testElement.getAttribute("id").getValue()));
	}

	@Test
	private Element testTree(Tree obj) {
		Element element = savingMasterObj.writeTree(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Tree"));
		return element;
	}

	@Test
	private Element testRock(Rock obj) {
		Element element = savingMasterObj.writeRock(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Rock"));
		return element;
	}

	@Test
	private Element testPlayableCharacter(PlayableCharacter obj)
			throws XMLException {
		Element element = savingMasterObj.writePlayableCharacter(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("PlayableCharacter"));
		assertTrue(element.getChild("Direction") != null);
		assertTrue(element.getChild("Direction").getText()
				.equals(obj.getFacingDirection().name()));
		assertFalse(element.getChild("AttackPower") == null);
		assertEquals(obj.getAttackPower(),
				Integer.parseInt(element.getChild("AttackPower").getText()));
		assertFalse(element.getChild("Health") == null);
		assertEquals(obj.getHealth(),
				Integer.parseInt(element.getChild("Health").getText()));
		assertFalse(element.getChild("Inventory") == null);
		assertTrue(element.getChild("Inventory").getChildren().size() == obj
				.getInventory().size());
		return element;
	}

	@Test
	private Element testFood(Food obj) {
		Element element = savingMasterObj.writeFood(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Food"));
		assertTrue(element.getChild("Heal") != null);
		assertEquals(Integer.parseInt(element.getChild("Heal").getText()),
				obj.getHeal());
		return element;
	}

	@Test
	private Element testChest(Chest obj) throws XMLException {
		Element element = savingMasterObj.writeChest(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Chest"));
		assertFalse(element.getChild("Inventory") == null);
		assertTrue(element.getChild("Inventory").getChildren().size() == obj
				.getLoot().size());
		return element;
	}

	@Test
	private Element testMinion(Minion obj) throws XMLException {
		Element element = savingMasterObj.writeMinion(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Minion"));
		assertFalse(element.getChild("RoomID") == null);
		assertTrue(Integer.parseInt(element.getChild("RoomID").getText()) == obj
				.getCurrentRoom().getRoomID());
		assertFalse(element.getChild("Health") == null);
		assertEquals(obj.getHealth(),
				Integer.parseInt(element.getChild("Health").getText()));
		assertFalse(element.getChild("Inventory") == null);
		assertTrue(element.getChild("Inventory").getChildren().size() == obj
				.getInventory().size());
		return element;
	}

	@Test
	private Element testKey(Key obj) {
		Element element = savingMasterObj.writeKey(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Key"));
		return element;
	}

	@Test
	private Element testHedge(Hedge obj) {
		Element element = savingMasterObj.writeHedge(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Hedge"));
		assertTrue(element.getChild("Direction") != null);
		assertTrue(element.getChild("Direction").getText()
				.equals(obj.getDirection().name()));
		return element;
	}

	@Test
	private Element testFence(Fence obj) {
		Element element = savingMasterObj.writeFence(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Fence"));
		return element;
	}

	@Test
	private Element testDoor(Door obj) {
		Element element = savingMasterObj.writeDoor(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Door"));
		assertTrue(element.getChild("Direction") != null);
		assertTrue(element.getChild("Direction").getText()
				.equals(obj.getDoorsWallEdge().name()));
		assertFalse(element.getChild("RoomID") == null);
		assertTrue(Integer.parseInt(element.getChild("RoomID").getText()) == obj
				.getRoom().getRoomID());
		assertFalse(element.getChild("isLocked") == null);
		// TODO test door link
		assertTrue(Boolean.parseBoolean(element.getChild("isLocked").getText()) == obj.getIsLocked());
		return element;
	}

	@Test
	private Element testBush(Bush obj) {
		Element element = savingMasterObj.writeBush(obj);
		assertTrue(element != null);
		assertTrue(element.getName().equals("Bush"));
		return element;
	}

	@Test
	private Element testBoss(Boss obj) throws XMLException {
		Element bossElement = savingMasterObj.writeBoss(obj);
		assertTrue(bossElement != null);
		assertTrue(bossElement.getName().equals("Boss")); 
		assertFalse(bossElement.getChildren().isEmpty());
		assertFalse(bossElement.getChild("Health") == null); 
		assertEquals(obj.getHealth(),
				Integer.parseInt(bossElement.getChild("Health").getText()));
		assertFalse(bossElement.getChild("MaxItems") == null);
		assertEquals(6,
				Integer.parseInt(bossElement.getChild("MaxItems").getText()));
		assertFalse(bossElement.getChild("AttackPower") == null);
		assertEquals(obj.getAttackPower(),
				Integer.parseInt(bossElement.getChild("AttackPower").getText()));
		// TODO better test inventory
		assertFalse(bossElement.getChild("Inventory") == null);
		assertTrue(bossElement.getChild("Inventory").getChildren().size() == obj
				.getInventory().size());
		return bossElement;
	} 
	
	public static void main(String[] args){ 
//		DataStorageTesting maintesting= new DataStorageTesting();
//		new TestingSavingMasterObj(maintesting);
	}
	
	
}
