package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import catgame.gameObjects.GameItem;

@SuppressWarnings("serial")
public class PanelItem extends PanelAbstract implements MouseListener {

	private GameItem item;
	private PanelInventory panel;
	private String state = "running";

	public PanelItem(Point origin, Dimension dim, PanelInventory panel) {
		super();
		this.panel = panel;
		this.addMouseListener(this);
		this.setSize(dim);
		this.setPreferredSize(dim);
		this.setLocation(origin);
		// this.setOpaque(true);
	}

	public void setItem(GameItem item) {
		this.item = item;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (item != null) {
			item.draw(g);
		}
	}

	/**
	 * Actions to perform on mouse click
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Item clicked");
		if(state.equals("paused")) return;
		
		if (item != null) {
			if (item.isUsable()) {
				System.out.println("Usable item");
				System.out.println(item.getClass().getSimpleName());
				useItem();
			} else {
				HelperMethods.textDialog(item.getClass().getSimpleName(),
						"Take this to the right door to use it");
			}
		}
		repaint();
	}

	/**
	 * Uses the item contained in the itemPanel
	 */
	private void useItem() {
		if (HelperMethods.confirmationDialog("", "Do you want to use the "
				+ item.getClass().getSimpleName() + "?")) {
			panel.itemUsed(this.item);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setBackground(new Color(150, 100, 200, 100));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setBackground(new Color(0, 0, 0, 0));
		this.getParent().getParent().repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void setState(String state){
		this.state = state;
	}

}
