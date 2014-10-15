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
import catgame.gamestarting.GameRunner;
import catgame.gui.renderpanel.PanelRender;

public class PanelStat extends PanelAbstract {

	private static final double MARGINPERCENT = 0.1;
	
	private PlayableCharacter character;
	private BufferedImage backGround;
	private int margin;
	private PanelMinimap map;

	public PanelStat(PlayableCharacter character) {
		super();
		try {
			backGround = ImageIO.read(PanelRender.class
					.getResource("/images/StatPanel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.character = character;
	}
	
	/**
	 * Adds the minimap panel
	 * @param runner 
	 */
	public void addMinimap(GameRunner runner) {
		margin = (int) (this.getWidth()*MARGINPERCENT);
		System.out.println("Adding minimap");
		map = new PanelMinimap(character, runner);
		
		// Set the size of the mini-map
		int mapSize = this.getWidth()-margin*2;
		System.out.println(mapSize);
		Dimension mapDim = new Dimension(mapSize, mapSize);
		map.setSize(mapDim);
		map.setPreferredSize(mapDim);
		
		// Set the placement of the mini-map
		int mapX = margin;
		int mapY = this.getHeight()-mapSize-margin*2;
		System.out.println(mapX + ":" + mapY);
		map.setLocation(mapX, mapY);
		
		this.add(map);
	}
	
	/**
	 * For testing purposes
	 */
	public void increaseHP() {
		if (character.getHealth()<91){
			character.changeHealth(10);
		} else {
			character.changeHealth(100-character.getHealth());
		}
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backGround != null) {
			g.drawImage(backGround, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		drawHPBar(g);
	}
	
	/**
	 * Draws the HP Bar
	 * @param g
	 */
	public void drawHPBar(Graphics g){
		int HPWidth = this.getWidth()-2*margin;
		int HPHeight = this.getHeight()/15;
		int HPX = margin;
		int HPY = 2*margin;
		
		g.setColor(Color.white);
		g.drawString("Health:", HPX, HPY-3);
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
