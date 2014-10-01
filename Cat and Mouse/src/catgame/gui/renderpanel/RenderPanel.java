package catgame.gui.renderpanel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import catgame.logic.Room;

public class RenderPanel extends JPanel {
	
	private final int panelWidth = 500;
	private final int panelHeight = 600;
	public viewDirection viewDir = viewDirection.NORTH;
	//public Room currentRoom;
	
	
	private Image grassBlock;
	
	public enum viewDirection{
		NORTH, SOUTH, EAST, WEST;
	}
	
	//Will need to be passed the current Room
	public RenderPanel(Dimension windowSize){
		setLayout(null);
		setPreferredSize(windowSize);
		setSize(windowSize);
		setBackground(Color.BLUE);
		setVisible(true);
		//this.currentRoom = currentRoom;
//		try {
//			setupImages();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		repaint();
		
	}
	
	public void setupImages() throws IOException{
		grassBlock = ImageIO.read(RenderPanel.class.getResource("/images/Grass1.png"));
	}
	
	public void drawGrass(Graphics g){
		g.drawImage(grassBlock, 20, 20, null);
	}
	
	public viewDirection getViewDir(){
		return viewDir;
	}
	
	public void setViewDir(viewDirection vd){
		viewDir = vd;
	}
	
	
	
	public void paint(Graphics g){
		super.paintComponent(g);
	}
	
//	@Override
//	public void paint(Graphics g){
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		redraw(g);
//	}
	
	public void redraw(Graphics g){
		//g.clearRect(0, 0, panelWidth, panelHeight);
		g.setColor(Color.BLUE);
		g.drawRect(20, 20, 80, 80);
		drawGrass(g);
		
	}

}
