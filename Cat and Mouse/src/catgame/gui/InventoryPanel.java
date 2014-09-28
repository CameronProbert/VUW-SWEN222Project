package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import catgame.GameObjects.GameItem;
import catgame.GameObjects.PlayableCharacter;

public class InventoryPanel extends AbstractPanel {

	private PlayableCharacter character;
	private ArrayList<ItemPanel> panels = new ArrayList<ItemPanel>();

	/**
	 * Needs to get linked to the player's inventory so it can draw it.
	 */
	public InventoryPanel(PlayableCharacter character) {
		super(new Point(20, 280), new Dimension(200, 300));
		//this.setOpaque(true);
		createPanels();
		this.character = character;
	}

	/**
	 * Creates each of the itemPanels
	 */
	public void createPanels() {
		Dimension panelSize = new Dimension(100, 100);
		int numPanels = 6;
		for (int i = 0; i < numPanels ; i++){
			int x = (i % 2) * 100;
			int y = (i / 2) * 100;
			ItemPanel panel = new ItemPanel(new Point(x, y), panelSize);
			panels.add(panel);
			this.add(panel);
		}
	}

	public void setInvItems() {
		ArrayList<GameItem> items = character.getInventory();
		if (items != null) {
			for (int i = 0; i < items.size() && i < 6; i++) {
				panels.get(i).setItem(items.get(i));
			}
		}
	}

}
