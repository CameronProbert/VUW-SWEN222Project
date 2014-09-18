package catgame.gui;

import java.awt.Dimension;

public class HostFrame extends AbstractFrame {

	public HostFrame() {
		super(new Dimension(600, 600), "Catgame Server");
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

}
