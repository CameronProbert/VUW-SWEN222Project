package catgame.clientserver;

import catgame.logic.GameMain;

public abstract class GameRunner {

	// TODO change to enum
	public static final int WAITING = 0;
	public static final int READY = 1;
	public static final int PLAYING = 2;
	public static final int GAMEOVER = 3;
	public static final int GAMEWON = 4;

	protected int gameState = 0;
	protected GameMain game;
	protected int noPlayers = 0;

	public boolean isNotOver() {
		// TODO always return true unless frame has been deleted, needs to check for this somehow
		return false;
	}

	/**
	 * sets the state of the game Playing, over etc)
	 * @param state
	 */
	public void setState(int state) {
		gameState = state;
	}

	/**
	 * receives the state of the game
	 * @return
	 */

	public int state(){
		return gameState;
	}

	/**
	 * updates game --- calls tick on each player
	 * the tick for each player will need to check the health and experience etc and act accordingly 
	 * MAY NOT BE NEEDED
	 */
	public void clockTick() {
		// TODO Auto-generated method stub

	}


	/**
	 * returns number of players
	 * @return
	 */
	public int noPlayers() {
		return noPlayers;
	}

	public GameMain getGameMain(){
		return game;
	}
}
