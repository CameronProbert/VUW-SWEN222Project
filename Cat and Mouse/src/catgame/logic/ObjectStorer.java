package catgame.logic;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import catgame.clientserver.IDNotFoundError;
import catgame.gameObjects.Chest;
import catgame.gameObjects.Food;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.GameObject;
import catgame.gameObjects.Key;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;
import catgame.logic.GameUtil.Direction;

/**
 * This class stores all the GameObjects, in various ways for quick lookup. This
 * class is mostly used by the server client doing a mass update and by the main
 * when doing logical calculations
 * 
 * @author Francine
 *
 */

public class ObjectStorer {

	private HashMap<Integer, PlayableCharacter> playableChs = new HashMap<Integer, PlayableCharacter>();
	private HashMap<Integer, NonPlayableCharacter> nonPlayableChs = new HashMap<Integer, NonPlayableCharacter>();
	private HashMap<Integer, Chest> chests = new HashMap<Integer, Chest>();
	private HashMap<Integer, GameItem> items = new HashMap<Integer, GameItem>();

	public ObjectStorer() {

	}

	public void addplayableChs(int objId, PlayableCharacter player) {
		System.out.println("playable ch added \n\n\n");
		playableChs.put(objId, player);
	}

	public void addNPC(int objId, NonPlayableCharacter npc) {
		nonPlayableChs.put(objId, npc);
	}

	public void addChest(int objId, Chest chest) {
		chests.put(objId, chest);
	}

	public void addItems(int objId, GameItem item) {
		items.put(objId, item);
	}

	/**
	 * returns how many NCP characters there are
	 * 
	 * @return
	 */
	public int getNumNCPs() {
		return this.nonPlayableChs.keySet().size();
	}

	public Set<Integer> getNCPIDs() {
		return this.nonPlayableChs.keySet();
	}

	public NonPlayableCharacter findNCP(int npcID) {
		return this.nonPlayableChs.get(npcID);
	}

	public int getNumChests() {
		return this.chests.keySet().size();
	}

	public Set<Integer> getChestIDs() {
		return this.chests.keySet();
	}

	public Chest findChest(int i) {
		return this.chests.get(i);
	}

	public int getNumItems() {
		return this.items.keySet().size();
	}

	public Set<Integer> getItemIDs() {
		return this.items.keySet();
	}

	public int getNumChars() {
		return this.playableChs.keySet().size();
	}

	public Set<Integer> getCharIDs() {
		return this.playableChs.keySet();
	}

	public PlayableCharacter findCharacter(int objectID) {
		if (playableChs.size() == 0)
			System.out.println("\n\n\nplayableChs not populated\n\n\n");
		for(int i: playableChs.keySet()){
			System.out.println("I have the id : " + i);
		}
		return playableChs.get(objectID);

	}

	public GameItem findItem(int objectID) {
		return this.items.get(objectID);
	}

	public void addPlayer(int playerID) {
		// TODO:
		// take a players ID and make the new character associated with them
		// will need to work out:
		// what the character id will be
		// what room they are in and what direction they are facing
		// their attack power
		// their health
		// any items they may start with
	}

	public GameObject findGameObject(int ownerID) {
		PlayableCharacter ch = this.playableChs.get(ownerID);
		Chest chest = this.chests.get(ownerID);
		NonPlayableCharacter nch = this.nonPlayableChs.get(ownerID);
		if (ch != null)
			return ch;
		if (chest != null)
			return chest;
		if (nch != null)
			return nch;
		throw new IDNotFoundError();
	}

	public List<Integer> getPlayerIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		for (PlayableCharacter ch : this.playableChs.values()) {
			ids.add(ch.getObjectID());
		}
		return ids;
	}

	public GameItem findItemInGame(int itemID) {
		GameObject itemHolder = null;
		Collection<NonPlayableCharacter> npcs = nonPlayableChs.values(); 
		Collection<Chest> chestsCol = chests.values(); 
		
		for (NonPlayableCharacter npc : npcs) {
			List<GameItem> npcInv = npc.getInventory();
			for (GameItem item : npcInv) {
				if (item.getObjectID() == itemID) {
					System.err.println("FOUND ITEM");
					itemHolder = (GameObject) npc;
				}
			}
		}
		// IF WE HAVENT FOUND THE HOLDER YET GO THROUGH THE CHESTS
		if (itemHolder == null) {
			for (Chest chest : chestsCol) {
				List<GameItem> chestloot = chest.getLoot();
				for (GameItem item : chestloot) {
					if (item.getObjectID() == itemID) {
						itemHolder = (GameObject) chest;
					}
				}
			}
		}
		if (itemHolder != null) {
			if (itemHolder instanceof NonPlayableCharacter) {
				for (int i = 0; i < ((NonPlayableCharacter) itemHolder).getInventory().size(); i++) {
					if (((NonPlayableCharacter) itemHolder).getInventory().get(i).getObjectID() == itemID) {
						return ((NonPlayableCharacter) itemHolder).getInventory().remove(i);
					}
				}
			} else {
				for (int i = 0; i < ((Chest) itemHolder).getLoot().size(); i++) {
					if (((Chest) itemHolder).getLoot().get(i).getObjectID() == itemID) {
						return ((Chest) itemHolder).getLoot().remove(i);
					}
				}
			}
		}
		System.err.println("findItemInGame RETURNING NULL");
		return null;
	}
}
