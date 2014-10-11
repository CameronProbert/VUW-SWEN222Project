package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import catgame.gameObjects.GameItem;
import catgame.gameObjects.PlayableCharacter;
import catgame.gui.renderpanel.PanelRender;

public class PanelInventory extends PanelAbstract {

	private static final int NUMCOLUMNS = 1;
	private static final int NUMROWS = 6;
	private static final double TOTALSLOTS = NUMCOLUMNS * NUMROWS;
	private PlayableCharacter character;
	private ArrayList<PanelItem> panels = new ArrayList<PanelItem>();
	private Dimension invSize;
	private BufferedImage backGround;
	private FrameClient frame;

	/**
	 * Needs to get linked to the player's inventory so it can draw it.
	 * 
	 * @param windowSize
	 * @param windowSize
	 */
	public PanelInventory(PlayableCharacter character, Dimension invSize, FrameClient frame) {
		super();
		this.invSize = invSize;
		this.frame = frame;
		this.character = character;
		try {
			backGround = ImageIO.read(PanelRender.class
					.getResource("/images/Tree1.png")); //TODO Inventory BG
		} catch (IOException e) {
			e.printStackTrace();
		}
		// this.setOpaque(true);
		createPanels();
	}

	/**
	 * Creates each of the itemPanels
	 */
	public void createPanels() {
		// Width and height of each panel
		int itemWidth = (int) (invSize.getWidth() / NUMCOLUMNS);
		int itemHeight = (int) (invSize.getHeight() / NUMROWS);
		Dimension panelSize = new Dimension(itemWidth, itemHeight);
		for (int i = 0; i < TOTALSLOTS; i++) {
			int columnNum = i % NUMCOLUMNS;
			int rowNum = i / NUMCOLUMNS;
			int x = columnNum * itemWidth;
			int y = rowNum * itemHeight;
			PanelItem panel = new PanelItem(new Point(x, y), panelSize, frame);
			panels.add(panel);
			this.add(panel);
		}
		setInvItems();
	}

	public void setInvItems() {
		List<GameItem> items = character.getInventory();
		if (items != null) {
			for (int i = 0; i < items.size() && i < TOTALSLOTS; i++) {
				panels.get(i).setItem(items.get(i));
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backGround != null) {
			g.drawImage(backGround, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

}
