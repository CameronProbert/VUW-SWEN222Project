package catgame.gui.renderpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {
	
	private final int panelWidth = 1200;
	private final int panelHeight = 600;
	
	public RenderPanel(Dimension windowSize){
		setPreferredSize(windowSize);
		setBackground(Color.DARK_GRAY);
		
		
	}
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		redraw(g);
	}
	
	public void redraw(Graphics g){
		g.clearRect(0, 0, panelWidth, panelHeight);
		
		
	}

}
