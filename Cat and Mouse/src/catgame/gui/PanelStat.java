package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import catgame.gameObjects.PlayableCharacter;
import catgame.gamestarting.GameRunner;
import catgame.gui.renderpanel.PanelRender;

/**
 * Contains the minimap and displays the characters health and the game's
 * viewing direction
 * 
 * @author Cameron Probert
 * 
 */
@SuppressWarnings("serial")
public class PanelStat extends PanelAbstract {

	private static final double MARGINPERCENT = 0.1;

	private PlayableCharacter character;
	private BufferedImage backGround;
	private int margin;
	private PanelMinimap map;

	private GameRunner runner;

	/**
	 * Creates the panel and loads in the background
	 * 
	 * @param character
	 */
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
	 * 
	 * @param runner
	 */
	public void addMinimap(GameRunner runner) {
		this.runner = runner;
		margin = (int) (this.getWidth() * MARGINPERCENT);
		map = new PanelMinimap(character, runner);

		// Set the size of the mini-map
		int mapSize = this.getWidth() - margin * 2;
		Dimension mapDim = new Dimension(mapSize, mapSize);
		map.setSize(mapDim);
		map.setPreferredSize(mapDim);

		// Set the placement of the mini-map
		int mapX = margin;
		int mapY = this.getHeight() - mapSize - margin * 2;
		map.setLocation(mapX, mapY);

		this.add(map);
	}

	/**
	 * For testing purposes. Increases the health of the player.
	 */
	public void increaseHP() {
		if (character.getHealth() < 91) {
			character.changeHealth(10);
		} else {
			character.changeHealth(100 - character.getHealth());
		}
		repaint();
	}

	/**
	 * Paints the background, strings and hpBar
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backGround != null) {
			g.drawImage(backGround, 0, 0, this.getWidth(), this.getHeight(),
					null);
		}
		g.setFont(new Font("Ariel", 0, 14));
		drawHPBar(g, 2 * margin);
		g.setColor(Color.white);
		g.drawString("Currently oriented: ", margin, (int) (4.1 * margin));
		g.setFont(new Font("Ariel", Font.BOLD, 14));
		g.drawString(runner.getBoardData().getGameUtil().getViewDirection()
				.toString(), margin, (int) (4.8 * margin));
	}

	/**
	 * Draws the HP Bar
	 * 
	 * @param g
	 */
	public void drawHPBar(Graphics g, int yPos) {
		int HPWidth = this.getWidth() - 2 * margin;
		int HPHeight = this.getHeight() / 15;
		int HPX = margin;
		int HPY = yPos;

		g.setColor(Color.white);
		g.drawString("Health:", HPX, HPY - 3);
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
