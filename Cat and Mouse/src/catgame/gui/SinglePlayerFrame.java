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

	protected int clientsUID;
	
	public SinglePlayerFrame(int UID) {
		super(new Dimension(1200, 600), "Catgame");
		this.clientsUID = UID;
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

	/**
	 * keyAction handles key presses in the game
	 * @param key
	 */
	protected void keyAction(KeyEvent key) {
		int keyID = key.getID();
		switch (keyID){
		case KeyEvent.VK_W:
			
			break;
		case KeyEvent.VK_A:
			
			break;
		case KeyEvent.VK_S:
			
			break;
		case KeyEvent.VK_D:
			
			break;
		case KeyEvent.VK_SPACE:
			
			break;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent key) {}

	@Override
	public void keyTyped(KeyEvent key) {}

}
