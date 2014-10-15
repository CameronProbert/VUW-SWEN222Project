package catgame.clientserver;

import java.io.DataOutputStream;
import java.io.IOException;

import catgame.gameObjects.*;
import catgame.gameObjects.Character;
import catgame.logic.BoardCell;
import catgame.logic.BoardData;
import catgame.logic.Position;
import catgame.logic.Room;


/**
 * Handles sending a mass update from the server to the client, to keep game consistent
 * 
 * @author Francine
 *
 */
public class SendMassUpdate {

	private DataOutputStream out;

	public SendMassUpdate(DataOutputStream out){
		this.out = out;
	}

	/**
	 * Used to send a character's details, used for playable and non playable
	 * @param objectID
	 * @param ch
	 */
	public void sendCharacter(int objectID, Character ch, BoardData boardData){
		try {
			out.writeInt(objectID);
			out.writeInt(ch.getHealth());
			int inSize = ch.getInventory().size();
			out.writeInt(inSize);
			for(GameItem item : ch.getInventory()){
				out.writeInt(item.getObjectID());
			}

			if(ch instanceof PlayableCharacter){
				writePos(objectID, (PlayableCharacter)ch, boardData);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writePos(int objectID, PlayableCharacter ch,
			BoardData boardData) {

		Room r = boardData.getGameUtil().findPlayersRoom(objectID);
		BoardCell cell = r.getCharactorCell(objectID);
		if(cell!=null){
			try {
				out.writeInt(r.getRoomID());
				Position p = cell.getPosition();
				out.writeInt(p.getX());
				out.writeInt(p.getY());
				out.writeInt(ch.getFacingDirection().getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}

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
			out.writeInt(objectID);
			out.writeInt(chest.getLoot().size());
			for(GameItem item : chest.getLoot()){
				out.writeInt(item.getObjectID());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendDoor(int i, Door d) {
		try {
			out.writeInt(i);
			if(d.getIsLocked()){
				out.writeInt(0);
			}else{
				out.writeInt(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
