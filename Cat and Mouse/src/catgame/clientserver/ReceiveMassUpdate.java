package catgame.clientserver;

import java.io.DataInputStream;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import catgame.gameObjects.Character;
import catgame.gameObjects.Chest;
import catgame.gameObjects.Door;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.GameObject;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;
import catgame.logic.BoardData;
import catgame.logic.GameUtil;
import catgame.logic.GameUtil.Direction;
import catgame.logic.ObjectStorer;
import catgame.logic.Position;
import catgame.logic.Room;

/**
 * handles reading the mass update 
 * writes the up dated information straight to game logic
 * @author Francine
 *
 */

public class ReceiveMassUpdate {

	private DataInputStream in ;
	private BoardData data;

	public ReceiveMassUpdate(DataInputStream input, BoardData data){
		this.in = input;
		this.data = data;
	}

	public void readNonPlayChar(){
		try {
			int objectID = in.readInt();
			int health = in.readInt();


			ObjectStorer storer = data.getObjStorer();
			NonPlayableCharacter ch = storer.findNonPlayCharacter((int)objectID);

			int inSize = in.readInt();
			int[] itemIDs = new int[(int)inSize];
			for(int i=0; i<inSize; i++){
				itemIDs[i] = in.readInt();
			}
			if(ch==null){
				throw new IDNotFoundError();
			}
			ch.reset(0, health, 0);

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
	 * read in any player and update stats
	 */
	public void readPlayer(){
		try {
			int objectID = in.readInt();
			int health = in.readInt();


			ObjectStorer storer = data.getObjStorer();
			Character ch = storer.findCharacter((int)objectID);

			int inSize = in.readInt();
			int[] itemIDs = new int[(int)inSize];
			for(int i=0; i<inSize; i++){
				itemIDs[i] = in.readInt();
			}
			if(ch==null){
				throw new IDNotFoundError();
			}
			ch.reset(0, (int)health, 0);

			List<GameItem> items = new ArrayList<GameItem>();

			for(int i = 0; i< inSize; i++){
				GameItem item = storer.findItem((int)itemIDs[i]);
				items.add(item);
			}

			ch.resetItems(items);
			doMove(ch);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doMove(Character ch) {
		int roomID;
		try {
			roomID = in.readInt();
			int x = in.readInt();
			int y = in.readInt();
			Position p = new Position(x,y);
			int dir = in.readInt();

			Direction direct = Direction.NORTH;

			switch(dir){
			case 0:
				direct = Direction.NORTH;
				break;
			case 1:
				direct = Direction.EAST;
				break;
			case 2:
				direct = Direction.SOUTH;
				break;
			case 3:
				direct = Direction.WEST;
				break;
			}
			Room r = data.getGameUtil().findPlayersRoom(ch.getObjectID());
			r.forcePlayerMove(ch.getObjectID(), p, direct);

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
			Chest chest = data.getObjStorer().findChest(id);
			if(chest==null){
				throw new IDNotFoundError();
			}
			List<GameItem> items = new ArrayList<GameItem>();
			for(int i=0; i<lootSize; i++){
				int itemID = in.readInt();
				items.add(data.getObjStorer().findItem((int)itemID));
			}
			chest.updateLoot(items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readDoor(){
		try {
			int id = in.readInt();
			int isLocked = in.readInt();

			Door door = data.getObjStorer().findDoor(id);
			if(isLocked==0){
				door.setIsLocked(true);
			}
			else{
				door.setIsLocked(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




}
