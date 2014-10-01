package catgame.clientserver;

import java.io.DataOutputStream;
import java.io.IOException;

import catgame.GameObjects.*;
import catgame.GameObjects.Character;

public class BroadcastMessage {

	private DataOutputStream out;

	public BroadcastMessage(DataOutputStream out){
		this.out = out;
	}

	public void sendCharacter(int objectID, Character ch){
		try {
			out.writeInt(objectID);
			out.writeInt(ch.getAttackPower());
			out.writeInt(ch.getHealth());
			if(ch instanceof PlayableCharacter){
				PlayableCharacter playC = (PlayableCharacter) ch;
				out.writeInt(playC.getXp());
			}
			else{
				out.writeInt(0);
			}
			out.writeInt(ch.getLevel());
			// out.write(ch.getCurrentCell().); // send position somehow TODO
			int inSize = ch.getInventory().size();
			out.writeInt(inSize);
			for(GameItem item : ch.getInventory()){
				out.writeInt(item.getObjectID());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendItem(int objectID, GameItem item){
		// TODO send the location of each item that must be held, in terms of what is the id that is holding it
	}
	
	public void sendChest(int objectID, Chest chest){
		// TODO send the location of each chest
		// TODO send contents of chest
	}

}
