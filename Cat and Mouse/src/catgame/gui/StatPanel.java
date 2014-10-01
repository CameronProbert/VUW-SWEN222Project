package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import catgame.GameObjects.PlayableCharacter;

public class StatPanel extends AbstractPanel {

	private PlayableCharacter character;

	public StatPanel(PlayableCharacter character) {
		super(new Point(980, 280), new Dimension(200, 300));
		this.character = character;
//		JButton button = LauncherFrame.createButton("Change State",
//				new Dimension(50, 30));
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				modifyChar();
//			}
//		});
//		button.setLocation(20, 250);
//		this.add(button);

	}

	/**
	 * For testing purposes
	 */
	public void modifyChar() {
		if (character.getHealth() < 20) {
			character.changeHealth((int) (Math.random() * 20));
		} else if (character.getHealth() > 80) {
			character.changeHealth((int) (Math.random() * -20));
		} else {
			character.changeHealth((int) (Math.random() * 40) - 20);
		}
		
		//character.addXp((int) (Math.random()*29)+1);
		repaint();
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
			//g.drawString("Level: " + character.getLevel(), 10, 75);
			int hp = character.getHealth();
			Color hpCol = new Color((int) (255 - (hp / 100.0 * 255)),
					(int) (hp / 100.0 * 255), 0);
			g.setColor(hpCol);
			g.fillRect(10, 20, (int) (hp / 100f * 180f), 10);
			g.setColor(Color.cyan);
			g.fillRect(10, 50, (int) (character.getXp() / 100f * 180f), 10);
		}
		g.setColor(Color.black);
		g.drawRect(10, 20, 180, 10);
		g.drawRect(10, 50, 180, 10);
	}
}
