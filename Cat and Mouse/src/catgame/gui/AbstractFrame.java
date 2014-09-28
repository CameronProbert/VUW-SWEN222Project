package catgame.gui;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Point;

import javax.swing.JFrame;

public abstract class AbstractFrame extends JFrame {

	protected final Dimension windowSize;
	
	public AbstractFrame(Dimension windowSize, String windowTitle) {
		super();
		this.setLocation(new Point(50, 50));
		this.windowSize = windowSize;
		this.setSize(windowSize);
		this.setPreferredSize(windowSize);
		this.setTitle(windowTitle);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

}
