package catgame.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class PanelMinimap extends PanelAbstract {

	public PanelMinimap() {
		super();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.red);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
	}

}
