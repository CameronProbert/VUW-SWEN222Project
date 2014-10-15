package catgame.clientserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import catgame.logic.GameUtil;

/**
 * converts legal changes to gamestate into ints which can be sent over a network
 * 
 * @author Francine
 *
 */
public class Update {

	private int inst;
	private int playerID;
	private int otherID;
	public final static Update pauseUpdate = new Update(5,0,0);
	public final static Update unPauseUpdate = new Update(6,0,0);
	public final static Update noUpdate = new Update(0,0,0);
	
	public enum Descriptor{
		NORTH,
		SOUTH,
		EAST,
		WEST,
		ATTACK,
		PICKUP,
		DROP,
		CONSUME,
		NEWROOM		
	}

	/**
	 * make a new update given some code (16 number grammar system)
	 * @param code
	 */
	public Update(int instruction, int playerID, int otherID){
		this.setInst(instruction);
		this.playerID = playerID;
		this.otherID = otherID;
	}
	
	public Update(DataInputStream input){
		try {
			this.setInst(input.readInt());
			this.playerID = input.readInt();
			this.otherID = input.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(DataOutputStream output){
		try {
			output.writeInt(this.inst);
			output.writeInt(this.playerID);
			output.writeInt(this.otherID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * turn a string and two Ids into an update
	 * supID can mean different things for different descriptions
	 * eg for a move it will be zero
	 * for picking up an object it will describe the object picked up
	 * 
	 * @param describe - the descriptor of the message eg move, turn, attack etc
	 * @param playerID 
	 * @param otherID - ID of other object interacting
	 * 
	 */

	public Update(Descriptor describe, int playerID, int supID){
		this.playerID = playerID;
		this.otherID = supID;
		
		switch(describe){
		case NORTH:
			this.setInst(1); 
			break;
		case WEST:
			this.setInst(2);
			break;
		case EAST:
			this.setInst(3);
			break;
		case SOUTH:
			this.setInst(4);
			break;
		case ATTACK:
			this.setInst(5);
			break;
		case PICKUP:
			this.setInst(6);
			break;
		case DROP://may be not used as a player cannot really remove an item
			this.setInst(7);
			break;
		case NEWROOM:
			this.setInst(8);
			break;
		case CONSUME:
			this.setInst(9);
			break;

		}
	}

	/**
	 * Will decode this update and send the appropriate method calls to the network handler
	 * @param encoded
	 * @param game
	 */
	public void decode(GameUtil game){
		
		
		switch(getInst()){
		case 1: // move forward
			int i = game.moveUp(getPlayerID());
			System.out.println("doing the instrution returned : " + i);
			break;
		case 2: // move right
			i = game.moveLeft(getPlayerID());
			System.out.println("doing the instrution returned : " + i);
			break;
		case 3: // move left
			i = game.moveRight(getPlayerID());
			System.out.println("doing the instrution returned : " + i);
			break;
		case 4: // move back
			i = game.moveDown(getPlayerID());
			System.out.println("doing the instrution returned : " + i);
			break;
		case 5: // attack
			game.attackUpdate(getPlayerID(), getOtherID());
			break;
		case 6: // add object to inventory
			game.addObjectToInventory(getPlayerID(), getOtherID());
			break;
		case 9: // object eaten
			i = game.useItem(getPlayerID(), getOtherID());
			System.out.println("doing the instrution returned : " + i);
			break;
		}
		
	}
	

	/**
	 * if the two codes are equal, same update
	 */
	public boolean equals(Object other){
		if(!(other instanceof Update)){
			return false;
		}
		Update o = (Update)other;
		return this.getInst() == o.getInst() && this.getPlayerID() == o.getPlayerID() && this.getOtherID()==o.getOtherID();
	}

	public int getInst() {
		return inst;
	}

	private void setInst(int inst) {
		this.inst = inst;
	}

	public int getPlayerID() {
		return playerID;
	}

	public int getOtherID() {
		return otherID;
	}
	
	public String toString(){
		return this.inst + " " + this.playerID + " " + this.otherID;
	}
}
