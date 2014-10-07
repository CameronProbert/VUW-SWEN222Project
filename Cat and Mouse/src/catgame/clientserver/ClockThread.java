package catgame.clientserver;


/**
 * Keeps game ticking, may be an obsolete class, would need it to move non playable characters if the game
 * was extended that way
 * @author Francine
 *
 */
public class ClockThread extends Thread{


	private final int delay; // delay between pulses in us
	private final GameRunner game;
	//private final BoardFrame display;
	
	public ClockThread(int delay, GameRunner game){//, BoardFrame display) {
		this.delay = delay;
		this.game = game;
		//this.display = display;
	}
	
	public void run() {
		while(true) {
			// Loop forever			
			try {
				Thread.sleep(delay);
				game.clockTick();
				//if(display != null) {
				//	display.repaint();
				//}
			} catch(InterruptedException e) {
				// should never happen
			}			
		}
	}
}
