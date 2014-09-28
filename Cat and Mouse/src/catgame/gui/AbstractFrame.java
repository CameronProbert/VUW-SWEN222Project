package catgame.gui;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public abstract class AbstractFrame extends JFrame {

	protected final Dimension windowSize;
	
	public AbstractFrame(Dimension windowSize, String windowTitle) {
		super();
		this.windowSize = windowSize;
		this.setSize(windowSize);
		this.setPreferredSize(windowSize);
		this.setTitle(windowTitle);
		initialiseBehaviour();
	}

	protected void initialiseBehaviour(){
		this.setSize(windowSize);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		// this.setBackground(new Color(50,70,255));
	}

}
