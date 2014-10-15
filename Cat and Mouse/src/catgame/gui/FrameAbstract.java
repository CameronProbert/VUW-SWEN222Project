package catgame.gui;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

/**
 * The abstract frame is what all my frames implement and is included to reduce
 * code re-use. It sets desired behaviour for the frame and contains a few
 * methods that are required in most frames.
 * 
 * @author Cameron Probert
 * 
 */
@SuppressWarnings("serial")
public abstract class FrameAbstract extends JFrame {

	public FrameAbstract(String windowTitle) {
		super();
		setup();
		this.setTitle(windowTitle);
	}

	/**
	 * Sets up the frame
	 */
	protected void setup() {
		this.setLocation(new Point(50, 50));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	}

	/**
	 * Allows setting the preferred size with an x and y, rather than being
	 * required to use a dimension
	 * 
	 * @param x
	 * @param y
	 */
	protected void setPreferredSize(int x, int y) {
		this.setPreferredSize(new Dimension(x, y));
	}

	/**
	 * Hides the frame. This can be used in buttons and other internally
	 * overridden classes
	 */
	protected void hideFrame() {
		this.setVisible(false);
	}

}
