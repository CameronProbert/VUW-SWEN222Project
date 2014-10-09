package catgame.clientserver;

import catgame.logic.GameUtil;

/**
 * converts legal changes to gamestate into ints which can be sent over a network
 * 
 * @author Francine
 *
 */
public class Update {

	private double code;
	private double TRILLION = 1000000000000.0;
	private double MILLION =  1000000;
	
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
	public Update(double code){
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
			this.code = 01*TRILLION + playerID*MILLION; 
			break;
		case WEST:
			this.code = 02*TRILLION + playerID*MILLION;
			break;
		case EAST:
			this.code = 03*TRILLION + playerID*MILLION;
			break;
		case SOUTH:
			this.code = 04*TRILLION + playerID*MILLION;
			break;
		case ATTACK:
			this.code = 05*TRILLION + playerID*MILLION + supID;
			break;
		case PICKUP:
			this.code = 06*TRILLION + playerID*MILLION + supID;
			break;
		case DROP://may be not used as a player cannot really remove an item
			this.code = 07*TRILLION + playerID*MILLION + supID;
			break;
		case NEWROOM:
			this.code = 8*TRILLION + playerID*MILLION + supID;
			break;
		case CONSUME:
			this.code = 9*TRILLION + playerID*MILLION + supID;
			break;

		}
	}

	/**
	 * Will decode this update and send the appropriate method calls to the network handler
	 * @param encoded
	 * @param game
	 */
	public void decode(GameUtil game){
		String str = Double.toString(code);
		String first2 = str.substring(0, 1);
		String next6 = str.substring(2, 7);
		String last6 = str.substring(8, 13);
		
		int instruction = Integer.parseInt(first2);
		int playerID = Integer.parseInt(next6);
		int lastID = Integer.parseInt(last6);
		
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

	/**
	 * 
	 * @return the update as an int
	 */
	public double getCode(){
		return code;
	}

	/**
	 * if the two codes are equal, same update
	 */
	public boolean equals(Object other){
		if(!(other instanceof Update)){
			return false;
		}
		Update o = (Update)other;
		return this.code == o.code;
	}
}
