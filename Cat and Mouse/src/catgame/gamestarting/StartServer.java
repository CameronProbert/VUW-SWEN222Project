package catgame.gamestarting;

import java.io.IOException;
import java.util.List;

import catgame.clientserver.Master;

public abstract class StartServer extends Thread {


	public abstract void run();
	
	protected abstract void setMasterIDs(List<Master> connections);
	
	protected void allowMastersStart(List<Master> connections) {
		for(Master m : connections){
			m.setStart(true);
		}
	}

	/**
	 * The following method controls a multi-user game. When a given game is
	 * over, it will simply restart the game with whatever players are
	 * remaining. However, if all players have disconnected then it will stop.
	 * 
	 * @param clk
	 * @param game
	 * @param connections
	 * @throws IOException
	 */
	protected static void multiUserGame(NetworkHandler game,
			List<Master> connections) throws IOException {						


		// loop forever
		while(atleastOneConnection(connections)) {
			game.setState(GameRunner.GameState.READY);
			pause(3000);
			game.setState(GameRunner.GameState.PLAYING);
			// now, wait for the game to finish
			while(game.state() == GameRunner.GameState.PLAYING) {
				Thread.yield();
			}
			// If we get here, then we're in game over mode
			pause(3000);
			// Reset board state
			game.setState(GameRunner.GameState.WAITING);
			//game.fromByteArray(state);			
		}
	}

	/**
	 * Check whether or not there is at least one connection alive.
	 * 
	 * @param connections
	 * @return
	 */
	private static boolean atleastOneConnection(List<Master> connections) {
		for (Master m : connections) {
			if (m.isAlive()) {
				return true;
			}			
		}
		return false;
	}

	private static void pause(int delay) {
		try {
			Thread.sleep(delay);
		} catch(InterruptedException e){			
		}
	}


	// The following two bits of code are a bit sneaky, but they help make the
	// problems more visible.
	static {
		System.setProperty("sun.awt.exception.handler", "servermain.Main");
	}

	public void handle(Throwable ex) {
		try {
			ex.printStackTrace();
			System.exit(1); } 
		catch(Throwable t) {}
	}
}
