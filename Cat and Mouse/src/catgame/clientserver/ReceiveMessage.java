package catgame.clientserver;

import java.io.DataInputStream;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import catgame.gameObjects.Character;
import catgame.gameObjects.Chest;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.GameObject;
import catgame.gameObjects.PlayableCharacter;
import catgame.logic.GameUtil;
import catgame.logic.ObjectStorer;

/**
 * handles reading the mass update 
 * writes the up dated information straight to game logic
 * @author Francine
 *
 */

public class ReceiveMessage {

	private DataInputStream in ;
	private GameUtil game;

	public ReceiveMessage(DataInputStream input, GameUtil game){
		this.in = input;
		this.game = game;
	}

	/**
	 * read in any player and update stats
	 */
	public void readPlayer(){
		try {
			int objectID = in.readInt();
			int attackPower = in.readInt();
			int health = in.readInt();
			int level = in.readInt();
			// int x,y = .. // receive position somehow TODO
			
			int inSize = in.readInt();
			int[] itemIDs = new int[inSize];
			for(int i=0; i<inSize; i++){
				itemIDs[i] = in.readInt();
			}

			ObjectStorer storer = game.getStorer();
			Character ch = storer.findCharacter(objectID);
			if(ch==null){
				throw new IDNotFoundError();
			}
			ch.reset(attackPower, health, level);

			List<GameItem> items = new ArrayList<GameItem>();

			for(int i = 0; i< inSize; i++){
				GameItem item = storer.findItem(itemIDs[i]);
				items.add(item);
			}

			ch.resetItems(items);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * rea in item and where it is stored - update
	 */
	public void readItem(){
		try {
			int id = in.readInt();
			int ownerID = in.readInt();
			ObjectStorer storer = game.getStorer();
			GameItem item = storer.findItem(id);
			if(item==null){
				throw new IDNotFoundError();
			}
			GameObject owner = storer.findGameObject(ownerID);
			item.setOwner(owner);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** read in chest and update loot
	 * 
	 */
	public void readChest(){
		try {
			int id = in.readInt();
			int lootSize = in.readInt();
			Chest chest = game.getStorer().findChest(id);
			if(chest==null){
				throw new IDNotFoundError();
			}
			List<GameItem> items = new ArrayList<GameItem>();
			for(GameItem item : chest.getLoot()){
				int itemID = in.readInt();
				items.add(game.getStorer().findItem(itemID));
			}
			chest.updateLoot(items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




}
