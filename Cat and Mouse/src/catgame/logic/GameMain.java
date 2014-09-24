package catgame.logic;

import java.util.HashMap;

public class GameMain {

	HashMap gameMap = new HashMap();
	
	public GameMain(){
		
	}
	//TODO
	public boolean moveNorth(int playerID){
		return false; //TODO gameMap.get(playerID).move();a
	}
	
	public boolean moveSouth(int playerID){
		return false;
	}
	public boolean moveEast(int playerID){
		return false;
	}
	
	public boolean moveWest(int playerID){
		return false;
	}
	
	public boolean aHach(int playerID, int IDaHached){
		//SOMETHING??
		return false;
	}
	
	public boolean addObjectToInventory(int playerID, int ObjectID){
		return false; //gameMap.get(playerID).addItem(object);
	}
	
	public boolean removeItem(int playerID, int ObjectID){
		return false;
	}
	
	public boolean useItem(int playerID, int objectID){
		return false;
	}
	
	public boolean moveToNextRoom(int playerID, int roomID){
		return false;
	}
}

