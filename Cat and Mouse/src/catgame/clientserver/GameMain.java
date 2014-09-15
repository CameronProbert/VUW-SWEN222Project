package catgame.clientserver;

public class GameMain {

	/***
	 * add a a player
	 * @return the uid of the player added
	 */
	public int registerPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * @return a byte array of the state of the game
	 * NOTE: may need to be changed?
	 * 
	 */
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * set up a game for one person on single player
	 * @param playerID
	 */
	public void setupSinglePlayer(int playerID) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @return whether game is over or not
	 */
	public boolean isNotOver() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * remove player given their user id
	 * @param uid
	 */
	public void disconnectPlayer(int uid) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * update board given byte array
	 * @param data
	 */
	public void fromByteArray(byte[] data) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * updates game --- calls tick on each player
	 * the tick for each player will need to check the health and experience etc and act accordingly 
	 */
	public void clockTick() {
		// TODO Auto-generated method stub
		
	}

}
