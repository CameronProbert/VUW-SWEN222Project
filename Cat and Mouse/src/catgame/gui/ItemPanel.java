package catgame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import catgame.GameObjects.GameItem;

public class ItemPanel extends AbstractPanel implements MouseListener {
	
	private GameItem item;

	public ItemPanel(Point origin, Dimension dim) {
		super(origin, dim);
		//this.setOpaque(true);
		System.out.println("ItemPanel Constructor");
		this.setBackground(new Color((int) (Math.random() * 255),(int) (Math.random() * 255),(int) (Math.random() * 255)));
	}
	
	public void setItem(GameItem item){
		this.item = item;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		if (item != null){
			// TODO item.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
