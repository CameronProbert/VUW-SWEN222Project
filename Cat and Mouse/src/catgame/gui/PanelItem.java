package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import catgame.gameObjects.GameItem;

public class PanelItem extends PanelAbstract implements MouseListener {

	private GameItem item;
	private FrameClient frame;

	public PanelItem(Point origin, Dimension dim, FrameClient frame) {
		super();
		this.frame = frame;
		this.addMouseListener(this);
		this.setSize(dim);
		this.setPreferredSize(dim);
		this.setLocation(origin);
		// this.setOpaque(true);
	}

	public void setItem(GameItem item) {
		System.out.println("Setting item to " + item.toString());
		this.item = item;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
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
		if (item != null) {
			if (item.isUsable()) {
				System.out.println("Usable item");
				System.out.println(item.getClass().getSimpleName());
				useItem();
			} else {
				HelperMethods.textDialog(item.getClass().getSimpleName(), "Take this to the right door to use it");
			}
		}
	}

	/**
	 * Uses the item contained in the itemPanel
	 */
	private void useItem() {
		frame.itemUsed(this.item);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
