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
		super();
		this.setSize(dim);
		this.setPreferredSize(dim);
		this.setLocation(origin);
		//this.setOpaque(true);
	}
	
	public void setItem(GameItem item){
		System.out.println("Setting item to " + item.toString());
		this.item = item;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.red);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
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
