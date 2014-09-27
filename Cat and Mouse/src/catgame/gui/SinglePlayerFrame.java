package catgame.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import catgame.clientserver.NetworkSetUp;

/**
 * The GameFrame is the single player version of the game. It contains the
 * render window and gui panels to overlay the render window.
 * 
 * @author Cameron Probert
 * 
 */
public class SinglePlayerFrame extends AbstractFrame implements KeyListener {

	public SinglePlayerFrame(boolean singlePlayer) {
		super(new Dimension(1200, 600), "Catgame");
	}

	/**
	 * Sets behaviour for the frame
	 */
	protected void initialiseBehaviour() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		// this.setBackground(new Color(50,70,255));
	}

	@Override
	public void keyReleased(KeyEvent key) {
		keyAction(key);
	}

	private void keyAction(KeyEvent key) {
		
	}

	@Override
	public void keyPressed(KeyEvent key) {}

	@Override
	public void keyTyped(KeyEvent key) {}

}
