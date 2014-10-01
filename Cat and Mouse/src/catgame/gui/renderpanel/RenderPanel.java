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

import catgame.gui.ClientFrame;
import catgame.logic.Room;

public class RenderPanel extends JPanel {

	public final ClientFrame parentFrame;
	private Image grassBlock;
	private Image tree1;
	public viewDirection viewDir = viewDirection.NORTH;
	
	//Test 2D array for grassBlocks
	private int[][] testMap = {
			{1, 1, 1, 1, 1, 1, 1, 1}, 
			{1, 0, 0, 1, 1, 1, 1, 1}, 
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 0, 0, 0, 1},
			{1, 1, 1, 1, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1}
	};
	
	private int[][] testObjects = {
			{1, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
	};
	
	public enum viewDirection{
		NORTH, SOUTH, EAST, WEST;
	}
	
	public RenderPanel(Dimension windowSize, ClientFrame parentFrame){
		this.parentFrame = parentFrame;
		
		setLayout(null);
		setSize(parentFrame.getWidth(), parentFrame.getHeight());
		setBackground(Color.DARK_GRAY);
		setVisible(true);
		
		setupImages();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		redraw(g);	
	}
	
	public void setupImages(){
		try {
			grassBlock = ImageIO.read(RenderPanel.class.getResource("/images/Grass1.png"));
			tree1 = ImageIO.read(RenderPanel.class.getResource("/images/Tree1.png"));
		} catch (IOException e) {
			System.out.println("There was an issue loading image files, check file locations are correct.");
			e.printStackTrace();
		}
	}
	
//	public void drawGrass(Graphics g){
//		g.setColor(Color.DARK_GRAY);
//		g.fillRect(0, 0, 1200, 600);
//		int startX1 = 19 + 500;
//		int startY1 = 20 + 200;
//		int startX2 = 81 + 500;
//		int startY2 = 55 + 200;
//		int incrX = 124;
//		int incrY = 70;
//		g.drawImage(grassBlock, startX1, startY1, null);
//		g.drawImage(grassBlock, startX1+incrX, startY1, null);
//		g.drawImage(grassBlock, startX1+incrX*2, startY1, null);
//		g.drawImage(grassBlock, startX1+incrX*3, startY1, null);
//		g.drawImage(grassBlock, startX1+incrX*4, startY1, null);
//		
//		g.drawImage(grassBlock, startX2, startY2, null);
//		g.drawImage(grassBlock, startX2+incrX, startY2, null);
//		g.drawImage(grassBlock, startX2+incrX*2, startY2, null);
//		g.drawImage(grassBlock, startX2+incrX*3, startY2, null);
//		
//		g.drawImage(grassBlock, startX1+incrX, startY1+incrY, null);
//		g.drawImage(grassBlock, startX1+incrX*2, startY1+incrY, null);
//		g.drawImage(grassBlock, startX1+incrX*3, startY1+incrY, null);
//		
//		g.drawImage(grassBlock, startX2+incrX, startY2+incrY, null);
//		g.drawImage(grassBlock, startX2+incrX*2, startY2+incrY, null);
//		
//		g.drawImage(grassBlock, startX1+incrX*2, startY1+incrY*2, null);
//	}
	
	//testing method for drawing grass blocks form 2D array
	public void testDrawGrass(Graphics g){
		int startX1 = 19 + parentFrame.getWidth() / 2;
		int startY1 = 20 + 400;
		int startX2 = 81 + parentFrame.getWidth() / 2;
		int startY2 = 55 + 400;
		int incrX = 124;
		int incrY = 70;
		int xCount = 10;
		int yCount = 10;
		g.fillRect(parentFrame.getWidth() / 2, 200, 20, 20);
		for (int i = 0; i < testMap.length; i++){
			for (int j = testMap[0].length; j > 0; j--){
				//System.out.println("i: " + i + " j: " + j);
				if (testMap[i][j-1] == 1){
					g.drawImage(grassBlock, 
							startX1 + (j * incrX / 2) + (i * incrX / 2), 
							startY1 + (i * incrY / 2) - (j * incrY / 2), 
							null);
				}
				g.setColor(Color.BLUE);
				if (i == 0){
					g.fillRect(
							startX1 + (j * incrX / 2) + (i * incrX / 2), 
							startY1 + (i * incrY / 2) - (j * incrY / 2), 
							xCount, 
							xCount);
					xCount = xCount + 2;
				}
				g.setColor(Color.RED);
				if (j == testMap[0].length){
					g.fillRect(
							startX1 + ((j+1) * incrX / 2) + ((i+1) * incrX / 2), 
							startY1 + (i * incrY / 2) - (j * incrY / 2), 
							yCount, 
							yCount);
					yCount = yCount + 2;
				}
				
			}
		}
	}
	
	public void testDrawTrees(Graphics g){
		g.drawImage(tree1, 200, 200, null);
	}
	
	public void redraw(Graphics g){
		//drawGrass(g);
		testDrawGrass(g);
		testDrawTrees(g);
	}
	
	public viewDirection getViewDir(){
		return viewDir;
	}

	public void setViewDir(viewDirection vd){
		viewDir = vd;
	}
	
	
	
	


}
