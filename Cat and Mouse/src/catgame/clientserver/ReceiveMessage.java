package catgame.clientserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import catgame.GameObjects.GameItem;
import catgame.GameObjects.PlayableCharacter;
import catgame.logic.GameUtill;
import catgame.GameObjects.Character;

public class ReceiveMessage {

	private DataInputStream in ;
	private GameUtill game;

	public ReceiveMessage(DataInputStream input, GameUtill game){
		this.in = input;
		this.game = game;
	}

	public void readPlayer(){
		try {
			int objectID = in.readInt();
			int attackPower = in.readInt();
			int health = in.readInt();
			int XP = in.readInt();
			int level = in.readInt();
			// int x,y = .. // receive position somehow TODO
			int inSize = in.readInt();
			int[] itemIDs = new int[inSize];
			for(int i=0; i<inSize; i++){
				itemIDs[i] = in.readInt();
			}

			Character ch = game.findCharacter(objectID);

			ch.reset(attackPower, health, level);

			if(ch instanceof PlayableCharacter){
				PlayableCharacter playCh = (PlayableCharacter) ch;
				playCh.resetXP(XP);
			}

			List<GameItem> items = new ArrayList<GameItem>();

			for(int i = 0; i< inSize; i++){
				GameItem item = game.findItem(itemIDs[i]);
				items.add(item);
			}

			ch.resetItems(items);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readItem(){
		// TODO get id and then place
	}
	
	public void readChest(){
		// TODO get id and check inventory
	}




}
