package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import catgame.gameObjects.GameItem;
import catgame.gameObjects.PlayableCharacter;

public class InventoryPanel extends AbstractPanel {

	private static final int NUMCOLUMNS = 1;
	private static final int NUMROWS = 6;
	private static final double TOTALSLOTS = NUMCOLUMNS * NUMROWS;
	private PlayableCharacter character;
	private ArrayList<ItemPanel> panels = new ArrayList<ItemPanel>();
	private Dimension invSize;

	/**
	 * Needs to get linked to the player's inventory so it can draw it.
	 * 
	 * @param windowSize
	 * @param windowSize
	 */
	public InventoryPanel(PlayableCharacter character, Dimension invSize) {
		super();
		this.invSize = invSize;
		// this.setOpaque(true);
		createPanels();
		this.character = character;
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
			ItemPanel panel = new ItemPanel(new Point(x, y), panelSize);
			panels.add(panel);
			this.add(panel);
		}
	}

	public void setInvItems() {
		List<GameItem> items = character.getInventory();
		if (items != null) {
			for (int i = 0; i < items.size() && i < TOTALSLOTS; i++) {
				panels.get(i).setItem(items.get(i));
			}
		}
	}

}
