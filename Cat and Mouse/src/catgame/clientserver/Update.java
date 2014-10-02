package catgame.clientserver;

import catgame.logic.GameUtil;

public class Update {

	private int code;
	private int TEN_MILLION = 10000000;
	
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
			this.code = 0100*TEN_MILLION + playerID*TEN_MILLION; 
			break;
		case WEST:
			this.code = 0101*TEN_MILLION + playerID*TEN_MILLION;
			break;
		case EAST:
			this.code = 0102*TEN_MILLION + playerID*TEN_MILLION;
			break;
		case SOUTH:
			this.code = 0103*TEN_MILLION + playerID*TEN_MILLION;
			break;
		case ATTACK:
			this.code = 0104*TEN_MILLION + playerID*TEN_MILLION + supID;
			break;
		case PICKUP:
			this.code = 0105*TEN_MILLION + playerID*TEN_MILLION + supID;
			break;
		case DROP://may be not used as a player cannot really remove an item
			this.code = 0106*TEN_MILLION + playerID*TEN_MILLION + supID;
			break;
		case NEWROOM:
			this.code = 0107*TEN_MILLION + playerID*TEN_MILLION + supID;
			break;
		case CONSUME:
			this.code = 0110*TEN_MILLION + playerID*TEN_MILLION + supID;
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
		String first4 = str.substring(0, 4);
		String next4 = str.substring(4,8);
		String last8 = str.substring(8, 16);
		
		int instruction = Integer.parseInt(first4);
		int playerID = Integer.parseInt(next4);
		int lastID = Integer.parseInt(last8);
		
		switch(instruction){
		case 0100: // move forward
			game.moveNorth(playerID);
			
		case 0101: // move right
			game.moveWest(playerID);
			
		case 0102: // move left
			game.moveEast(playerID);
			
		case 0103: // move back
			game.moveSouth(playerID);
			
		case 0104: // attack
			game.attack(playerID, lastID);
			
		case 0105: // add object to inventory
			game.addObjectToInventory(playerID, lastID);
			
		case 0106: // remove object from inventory
			game.removeItem(playerID, lastID);
			
		case 0107: // moved room
			game.moveToNextRoom(playerID, lastID); 
			
		case 0110: // object eaten
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
