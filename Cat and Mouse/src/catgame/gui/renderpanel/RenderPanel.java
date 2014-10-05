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

import catgame.gameObjects.*;
import catgame.gui.ClientFrame;
import catgame.logic.GameUtil.Direction;
import catgame.logic.Room;
import catgame.logic.RoomBuilder;

public class RenderPanel extends JPanel {

	public final ClientFrame parentFrame;
	private Image grassBlock;
	private Image tree1;
	private Image tree2;
	private Image bush1;
	private Image rock1;
	
	private Image hedgeRight1;
	
	private int blockWidth = 124;
	private int blockHeight = 70;
	
	//Cat images
	private Image catFrontLeft1;
	private Image catFrontRight1;
	private Image catBackLeft1;
	private Image catBackRight1;
	
	public static Direction viewDirection = Direction.EAST;
	
	//Classes for creating a test board
	private RoomBuilder buildBoard;
	private Room testRoom;
	
	
	public RenderPanel(Dimension windowSize, ClientFrame parentFrame){
		this.parentFrame = parentFrame;
		
		setLayout(null);
		setSize(parentFrame.getWidth(), parentFrame.getHeight());
		setBackground(Color.DARK_GRAY);
		setVisible(true);
		
		//Roombuilder testing
		buildBoard = new RoomBuilder("SwenProjectRoomTestOne.csv");
		testRoom = new Room(1, buildBoard.getBoardFile());
		testRoom.printBoard();
		
		
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
			//Load object Images
			grassBlock = ImageIO.read(RenderPanel.class.getResource("/images/Grass1.png"));
			tree1 = ImageIO.read(RenderPanel.class.getResource("/images/Tree1.png"));
			tree2 = ImageIO.read(RenderPanel.class.getResource("/images/Tree2.png"));
			bush1 = ImageIO.read(RenderPanel.class.getResource("/images/Bush1.png"));
			rock1 = ImageIO.read(RenderPanel.class.getResource("/images/Rock1.png"));
			hedgeRight1 = ImageIO.read(RenderPanel.class.getResource("/images/HedgeRight1.png"));
			//Load cat Images
			catFrontLeft1 = ImageIO.read(RenderPanel.class.getResource("/images/CatFrontLeft1.png"));
			catFrontRight1 = ImageIO.read(RenderPanel.class.getResource("/images/CatFrontRight1.png"));
			catBackLeft1 = ImageIO.read(RenderPanel.class.getResource("/images/CatBackLeft1.png"));
			catBackRight1 = ImageIO.read(RenderPanel.class.getResource("/images/CatBackRight1.png"));
		} catch (IOException e) {
			System.out.println("There was an issue loading image files, check file locations are correct.");
			e.printStackTrace();
		}
	}	

	
	//TODO see if theses two methods are necessary as viewDirection is public static
	public Direction getViewDiriection(){
		return viewDirection;
	}

	public void setViewDir(Direction vd){
		viewDirection = vd;
	}
	

	
	/**
	 * Draws ground blocks and objects based on the current viewDirection.
	 * This is done by first determining the current viewDirection,
	 * calling determineAndDrawGround() which determines the type of groundBlock at the 
	 * current position of on the map, then calls a relevant draw method to draw the groundBlock at that position.
	 * 
	 * 
	 * 
	 * @param g
	 */
	public void drawgroundAndObjects(Graphics g){		
		int xRender;
		int yRender;
		for (int y = 0; y < testRoom.getBoardGrid().length; y++){
			for (int x = testRoom.getBoardGrid()[0].length-1; x >= 0; x--){
				if (viewDirection == Direction.NORTH){
					xRender = testRoom.getBoardGrid()[0].length-x-1;
					yRender = y;
					determineAndDrawGround(g, yRender, xRender, y, x);
					determineAndDrawObject(g, yRender, xRender, y, x);					
				}
				else if (viewDirection == Direction.WEST){
					xRender = x;
					yRender = y;
					determineAndDrawGround(g, yRender, xRender, y, x);
					determineAndDrawObject(g, yRender, xRender, y, x);
				}
				else if (viewDirection == Direction.SOUTH){
					yRender = testRoom.getBoardGrid().length-y-1;
					xRender = x;
					determineAndDrawGround(g, yRender, xRender, y, x);
					determineAndDrawObject(g, yRender, xRender, y, x);
				}
				else if (viewDirection == Direction.EAST){
					yRender = testRoom.getBoardGrid().length-y-1;
					xRender = testRoom.getBoardGrid()[0].length-x-1;
					determineAndDrawGround(g, yRender, xRender, y, x);
					determineAndDrawObject(g, yRender, xRender, y, x);
				}
			}
		}
	}
	
	/**
	 * Determines ground type at current block, then calls the drawGround() method, 
	 * passing it the relevant image and positioning values.
	 * 
	 * @param g
	 * @param yRender
	 * @param xRender
	 * @param y
	 * @param x
	 */
	public void determineAndDrawGround(Graphics g, int yRender, int xRender, int y, int x){
		int startX = 19 + parentFrame.getWidth() / 4;
		int startY = 20 + 400;
		if (testRoom.getBoardGrid()[yRender][xRender].getGroundType() == "Grass"){
			drawGround(g, grassBlock, yRender, xRender, y, x, startY, startX);
		}	
	}
	
	/**
	 * Determines object type at current block, then calls the drawObject() method, 
	 * passing it the relevant image and positioning values.
	 * 
	 * @param g
	 * @param yRender
	 * @param xRender
	 * @param y
	 * @param x
	 */	
	public void determineAndDrawObject(Graphics g, int yRender, int xRender, int y, int x){
		if (testRoom.getBoardGrid()[yRender][xRender].getObjectOnCell() instanceof Tree){
			int treeStartX = parentFrame.getWidth() / 4 - 30;
			int treeStartY = 45 + 200;
			drawObject(g, tree1, yRender, xRender, y, x, treeStartY, treeStartX);
		}
		else if (testRoom.getBoardGrid()[yRender][xRender].getObjectOnCell() instanceof Tree){
			int treeStartX = parentFrame.getWidth() / 4;
			int treeStartY = 85 + 200;
			drawObject(g, tree1, yRender, xRender, y, x, treeStartY, treeStartX);
		}
		else if (testRoom.getBoardGrid()[yRender][xRender].getObjectOnCell() instanceof Bush){
			int bushStartX = parentFrame.getWidth() / 4 + 40;
			int bushStartY = 85 + 300;
			drawObject(g, bush1, yRender, xRender, y, x, bushStartY, bushStartX);
		}
		else if (testRoom.getBoardGrid()[yRender][xRender].getObjectOnCell() instanceof Rock){
			int rockStartX = parentFrame.getWidth() / 4 + 60;
			int rockStartY = 85 + 340;
			drawObject(g, rock1, yRender, xRender, y, x, rockStartY, rockStartX);
		}
		else if (testRoom.getBoardGrid()[yRender][xRender].getObjectOnCell() instanceof Hedge){
			int rockStartX = parentFrame.getWidth() / 4 + 40;
			int rockStartY = 85 + 285;
			drawObject(g, hedgeRight1, yRender, xRender, y, x, rockStartY, rockStartX);
		}
	}
	
	/**
	 * Draws a groundBlock image at a position specified by the given positioning values.
	 * 
	 * @param g
	 * @param image
	 * @param yRender
	 * @param xRender
	 * @param y
	 * @param x
	 * @param startY
	 * @param startX
	 */
	public void drawGround(Graphics g, Image image, int yRender, int xRender, int y, int x, int startY, int startX){
		g.drawImage(image, 
				startX + (x * blockWidth / 2) + (y * blockWidth / 2), 
				startY + (y * blockHeight / 2) - (x * blockHeight / 2), 
				null);
	}
	
	/**
	 * Draws an object image at a position specified by the given positioning values.
	 * 
	 * @param g
	 * @param image
	 * @param yRender
	 * @param xRender
	 * @param y
	 * @param x
	 * @param startY
	 * @param startX
	 */
	public void drawObject(Graphics g, Image image, int yRender, int xRender, int y, int x, int startY, int startX){
		g.drawImage(image, 
				startX + (x * blockWidth / 2) + (y * blockWidth / 2), 
				startY + (y * blockHeight / 2) - (x * blockHeight / 2), 
				null);
	}

	
	public void drawCharacters(Graphics g){
		g.drawImage(catFrontLeft1, 710, 325, null);
	}

	
	public void redraw(Graphics g){
		g.fillRect(0, 0, parentFrame.getWidth(), parentFrame.getHeight());
		drawgroundAndObjects(g);
		drawCharacters(g);
	}
	
	

	


}
