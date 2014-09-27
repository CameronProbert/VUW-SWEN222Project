package catgame.clientserver;

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
