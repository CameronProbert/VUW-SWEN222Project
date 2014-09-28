package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import catgame.GameObjects.PlayableCharacter;

public class StatPanel extends AbstractPanel {

	PlayableCharacter character;

	public StatPanel() {
		super(new Point(980, 280), new Dimension(200, 300));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("Health:", 10, 15);
		g.drawString("Experience:", 10, 45);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(10, 20, 180, 10);
		g.fillRect(10, 50, 180, 10);
		if (character != null) {
			g.drawString("Level: " + character.getLevel(), 10, 75);
			g.setColor(Color.green);
			g.fillRect(10, 20, 180, 10);
			g.setColor(Color.cyan);
			g.fillRect(10, 50, 180, 10);
		}
		g.setColor(Color.black);
		g.drawRect(10, 20, 180, 10);
		g.drawRect(10, 50, 180, 10);
	}
}
