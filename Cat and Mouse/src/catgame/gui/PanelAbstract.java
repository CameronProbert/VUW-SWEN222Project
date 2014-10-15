package catgame.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * The AbstractPanel is an intermediary panel for the rest of the panels,
 * its purpose is to reduce code and ensure correct behaviours.
 */
@SuppressWarnings("serial")
public class PanelAbstract extends JPanel {

	/**
	 * Creates the abstract panel
	 */
	public PanelAbstract() {
		super();
		this.setLayout(null);
		this.setVisible(true);
		this.setBackground(new Color((int) (Math.random() * 255), (int) (Math
				.random() * 255), (int) (Math.random() * 255), 0));
	}

	/**
	 * Makes the panel draw itself.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
