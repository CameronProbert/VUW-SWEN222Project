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

public class ReceiveMassUpdate {

	private DataInputStream in ;
	private GameUtil game;

	public ReceiveMassUpdate(DataInputStream input, GameUtil game){
		this.in = input;
		this.game = game;
	}

	/**
	 * read in any player and update stats
	 */
	public void readPlayer(){
		try {
			double objectID = in.readDouble();
			double attackPower = in.readDouble();
			double health = in.readDouble();
			double level = in.readDouble();
			// double x,y = .. // receive position somehow TODO
			
			double inSize = in.readDouble();
			double[] itemIDs = new double[(int)inSize];
			for(int i=0; i<inSize; i++){
				itemIDs[i] = in.readDouble();
			}

			ObjectStorer storer = game.getStorer();
			Character ch = storer.findCharacter((int)objectID);
			if(ch==null){
				throw new IDNotFoundError();
			}
			ch.reset((int)attackPower, (int)health, (int)level);

			List<GameItem> items = new ArrayList<GameItem>();

			for(int i = 0; i< inSize; i++){
				GameItem item = storer.findItem((int)itemIDs[i]);
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
			double id = in.readDouble();
			double ownerID = in.readDouble();
			ObjectStorer storer = game.getStorer();
			GameItem item = storer.findItem((int)id);
			if(item==null){
				throw new IDNotFoundError();
			}
			GameObject owner = storer.findGameObject((int)ownerID);
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
			double id = in.readDouble();
			double lootSize = in.readDouble();
			Chest chest = game.getStorer().findChest((int)id);
			if(chest==null){
				throw new IDNotFoundError();
			}
			List<GameItem> items = new ArrayList<GameItem>();
			for(GameItem item : chest.getLoot()){
				double itemID = in.readDouble();
				items.add(game.getStorer().findItem((int)itemID));
			}
			chest.updateLoot(items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




}
