package catgame.clientserver;

import java.io.DataOutputStream;
import java.io.IOException;

import catgame.gameObjects.*;
import catgame.gameObjects.Character;


/**
 * Handles sending a mass update from the server to the client, to keep game consistent
 * 
 * @author Francine
 *
 */
public class BroadcastMessage {

	private DataOutputStream out;

	public BroadcastMessage(DataOutputStream out){
		this.out = out;
	}

	/**
	 * Used to send a character's details, used for playable and non playable
	 * @param objectID
	 * @param ch
	 */
	public void sendCharacter(int objectID, Character ch){
		try {
			out.writeDouble(objectID);
			out.writeDouble(ch.getAttackPower());
			out.writeDouble(ch.getHealth());
			// out.write(ch.getCurrentCell().); // send position somehow TODO
			int inSize = ch.getInventory().size();
			out.writeDouble(inSize);
			for(GameItem item : ch.getInventory()){
				out.writeDouble(item.getObjectID());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send each game item (food key etc)
	 * The items are updated as to who holds them as well as anything that can hold, their
	 * inventory is updated so the back and forth link btween the objects is consistent
	 * 
	 * @param objectID
	 * @param item
	 */
	public void sendItem(int objectID, GameItem item){
		// TODO send the location of each item that must be held, in terms of what is the id that is holding it
		try {
			out.writeDouble(objectID);
			out.writeDouble(item.getOwner().getObjectID());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Updates chests loot
	 * 
	 * @param objectID
	 * @param chest
	 */
	public void sendChest(int objectID, Chest chest){
		try {
			out.writeDouble(objectID);
			out.writeDouble(chest.getLoot().size());
			for(GameItem item : chest.getLoot()){
				out.writeDouble(item.getObjectID());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
