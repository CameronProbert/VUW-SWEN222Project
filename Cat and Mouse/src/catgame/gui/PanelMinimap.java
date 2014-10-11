package catgame.gui;

import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class PanelMinimap extends AbstractPanel {

	public PanelMinimap() {
		super();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawRect(0, 0, WIDTH, HEIGHT);
	}

}
