package catgame.logic;

import java.util.ArrayList;
import java.util.HashMap;
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

	public ObjectStorer(){
		
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
	
	public void addItems(int objId, GameItem item){
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

	public NonPlayableCharacter findNCP(int i) {
		return this.nonPlayableChs.get(i);
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
		if(playableChs.size()==0) System.out.println("playableChs not populated\n\n");
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

}
