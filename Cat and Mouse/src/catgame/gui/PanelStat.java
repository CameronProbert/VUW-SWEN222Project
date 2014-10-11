package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import catgame.gameObjects.PlayableCharacter;
import catgame.gui.renderpanel.PanelRender;

public class PanelStat extends PanelAbstract {

	private PlayableCharacter character;
	private BufferedImage backGround;

	public PanelStat(PlayableCharacter character) {
		super();
		try {
			backGround = ImageIO.read(PanelRender.class
					.getResource("/images/Tree1.png")); //TODO StatBG
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.character = character;
		this.addMinimap();
	}

	private void addMinimap() {
		PanelMinimap map = new PanelMinimap();
		
		// Set the size of the mini-map
		Dimension mapSize = new Dimension(this.getWidth(), (int) (this.getHeight()*0.7));
		map.setSize(mapSize);
		map.setPreferredSize(mapSize);
		
		// Set the placement of the mini-map
		int mapX = this.getX();
		int mapY = (int) (this.getY()+this.getHeight()*0.3);
		map.setLocation(mapX, mapY);
		
		this.add(map);
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

	// TODO SCALE
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backGround != null) {
			g.drawImage(backGround, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		g.setColor(Color.red);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		drawHPBar(g);
	}
	
	public void drawHPBar(Graphics g){
		int HPWidth = this.getWidth()*9/10;
		int HPHeight = this.getHeight()/15;
		int HPX = this.getWidth()/20;
		int HPY = this.getHeight()/15;
		
		g.setColor(Color.black);
		g.drawString("Health:", 10, 15);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(HPX, HPY, HPWidth, HPHeight);
		if (character != null) {
			int hp = character.getHealth();
			Color hpCol = new Color((int) (255 - (hp / 100.0 * 255)),
					(int) (hp / 100.0 * 255), 0);
			g.setColor(hpCol);
			g.fillRect(HPX, HPY, (int) (hp / 100f * HPWidth), HPHeight);
		}
		g.setColor(Color.black);
		g.drawRect(HPX, HPY, HPWidth, HPHeight);
	}
}
