package catgame.clientserver;

import catgame.logic.GameUtil;

/**
 * abstract class that holds important game state and game state modifiers
 * @author Francine
 *
 */
public abstract class GameRunner {
	
	public enum GameState{
		WAITING,
		READY,
		PLAYING,
		GAMEOVER,
		GAMEWON
	}

	protected GameState gameState = GameState.WAITING;
	protected GameUtil game;
	protected int noPlayers = 0;

	public boolean isNotOver() {
		// TODO always return true unless frame has been deleted, needs to check for this somehow
		return false;
	}

	/**
	 * sets the state of the game Playing, over etc)
	 * @param state
	 */
	public void setState(GameState state) {
		gameState = state;
	}

	/**
	 * receives the state of the game
	 * @return
	 */

	public GameState state(){
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

	/** return the main utilization class for the game
	 * 
	 * @return
	 */
	public GameUtil getGameUtill(){
		return game;
	}
	
	public void setGameUtil(){
		this.game = game;
	}
}
