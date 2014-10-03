package catgame.clientserver;

import catgame.logic.GameUtil;

public class Update {

	private int code;
	private int ONE_MILLION = 1000000;
	private int TEN_THOUSAND =  10000;
	
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
	public Update(int code){
		this.code = code;
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
		switch(describe){
		case NORTH:
			this.code = 01*ONE_MILLION + playerID*TEN_THOUSAND; 
			break;
		case WEST:
			this.code = 02*ONE_MILLION + playerID*TEN_THOUSAND;
			break;
		case EAST:
			this.code = 03*ONE_MILLION + playerID*TEN_THOUSAND;
			break;
		case SOUTH:
			this.code = 04*ONE_MILLION + playerID*TEN_THOUSAND;
			break;
		case ATTACK:
			this.code = 05*ONE_MILLION + playerID*TEN_THOUSAND + supID;
			break;
		case PICKUP:
			this.code = 06*ONE_MILLION + playerID*TEN_THOUSAND + supID;
			break;
		case DROP://may be not used as a player cannot really remove an item
			this.code = 07*ONE_MILLION + playerID*TEN_THOUSAND + supID;
			break;
		case NEWROOM:
			this.code = 8*ONE_MILLION + playerID*TEN_THOUSAND + supID;
			break;
		case CONSUME:
			this.code = 9*ONE_MILLION + playerID*TEN_THOUSAND + supID;
			break;

		}
	}

	/**
	 * Will decode this update and send the appropriate method calls to the network handler
	 * @param encoded
	 * @param game
	 */
	public void decode(GameUtil game){
		String str = Integer.toString(code);
		String first2 = str.substring(0, 1);
		String next2 = str.substring(2,3);
		String last4 = str.substring(4, 7);
		
		int instruction = Integer.parseInt(first2);
		int playerID = Integer.parseInt(next2);
		int lastID = Integer.parseInt(last4);
		
		switch(instruction){
		case 1: // move forward
			game.moveUp(playerID);
			
		case 2: // move right
			game.moveLeft(playerID);
			
		case 3: // move left
			game.moveRight(playerID);
			
		case 4: // move back
			game.moveDown(playerID);
			
		case 5: // attack
			game.attack(playerID, lastID);
			
		case 6: // add object to inventory
			game.addObjectToInventory(playerID, lastID);
			
		case 7: // remove object from inventory
			game.removeItem(playerID, lastID);
			
		case 8: // moved room
			game.moveToNextRoom(playerID, lastID); 
			
		case 9: // object eaten
			game.useItem(playerID, lastID);
			
		}
		
	}

	public int getCode(){
		return code;
	}

	public boolean equals(Object other){
		if(!(other instanceof Update)){
			return false;
		}
		Update o = (Update)other;
		return this.code == o.code;
	}
}
