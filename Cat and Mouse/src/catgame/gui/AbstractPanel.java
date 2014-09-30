package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * The AbstractPanel is an intermediary panel for the rest of the panels,
 * its purpose is to reduce code and ensure correct behaviours.
 */
public class AbstractPanel extends JPanel {


	public AbstractPanel() {
		this.setLayout(null);
		this.setVisible(true);
		this.setBackground(new Color((int) (Math.random() * 255), (int) (Math
				.random() * 255), (int) (Math.random() * 255), 100));
	}

	/**
	 * Makes the panel draw itself.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
