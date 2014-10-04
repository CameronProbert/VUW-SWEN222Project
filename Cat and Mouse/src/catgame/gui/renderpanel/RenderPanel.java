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
	
	//Cat images
	private Image catFrontLeft1;
	private Image catFrontRight1;
	private Image catBackLeft1;
	private Image catBackRight1;
	
	public static Direction viewDirection = Direction.NORTH;
	
	//Classes for creating a test board
	RoomBuilder buildBoard;
	Room testRoom;
	
	//Test 2D array for grassBlocks
	private int[][] testMap = {
			{1, 1, 1, 1, 1, 1, 1, 1}, 
			{1, 1, 1, 1, 1, 1, 1, 1}, 
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 0, 0, 0, 1},
			{1, 1, 1, 1, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1}
	};

	
	private int[][] testObjects = {
			{1, 3, 1, 0, 0, 0, 0, 0},
			{3, 3, 3, 0, 0, 0, 2, 0},
			{1, 3, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 2, 0},
			{0, 0, 4, 4, 3, 0, 3, 0},
	};
	
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
	

	
	//testing method for drawing grass blocks form 2D array
	public void testDrawGrass(Graphics g){
		int startX1 = 19 + parentFrame.getWidth() / 4;
		int startY1 = 20 + 400;
		int incrX = 124;
		int incrY = 70;
		for (int i = 0; i < testRoom.getBoardGrid().length; i++){
			for (int j = testRoom.getBoardGrid()[0].length; j > 0; j--){
				if (testRoom.getBoardGrid()[i][j-1].getGroundType() == "Grass"){
					g.drawImage(grassBlock, 
							startX1 + (j * incrX / 2) + (i * incrX / 2), 
							startY1 + (i * incrY / 2) - (j * incrY / 2), 
							null);
				}
			}
		}
	}
	
	public void getObjectsFromArray(Graphics g){
		for (int i = 0; i < testRoom.getBoardGrid().length; i++){
			for (int j = testRoom.getBoardGrid()[0].length; j > 0; j--){
				if (testRoom.getBoardGrid()[i][j-1].getObjectOnCell() instanceof Tree){
					int startX = parentFrame.getWidth() / 4 - 30;
					int startY = 45 + 200;
					drawObjectsFromArray(g, tree1, startX, startY, i, j);
				}
				else if (testRoom.getBoardGrid()[i][j-1].getObjectOnCell() instanceof Tree){
					int startX = parentFrame.getWidth() / 4;
					int startY = 85 + 200;
					drawObjectsFromArray(g, tree2, startX, startY, i, j);
				}
				else if (testRoom.getBoardGrid()[i][j-1].getObjectOnCell() instanceof Bush){
					int startX = parentFrame.getWidth() / 4 + 40;
					int startY = 85 + 300;
					drawObjectsFromArray(g, bush1, startX, startY, i, j);
				}
				else if (testRoom.getBoardGrid()[i][j-1].getObjectOnCell() instanceof Rock){
					int startX = parentFrame.getWidth() / 4 + 60;
					int startY = 85 + 340;
					drawObjectsFromArray(g, rock1, startX, startY, i, j);
				}
				else if (testRoom.getBoardGrid()[i][j-1].getObjectOnCell() instanceof PlayableCharacter){
					int startX = parentFrame.getWidth() / 4 + 60;
					int startY = 85 + 340;
					drawObjectsFromArray(g, catFrontLeft1, startX, startY, i, j);
				}
			}
		}
	}
	
	public void drawObjectsFromArray(Graphics g, Image image, int startX, int startY, int i, int j){
		int incrX = 124;
		int incrY = 70;
		g.drawImage(image, 
				startX + (j * incrX / 2) + (i * incrX / 2), 
				startY + (i * incrY / 2) - (j * incrY / 2), 
				null);
	}
	
	
//	//testing method for drawing grass blocks form 2D array
//	public void testDrawGrass(Graphics g){
//		int startX1 = 19 + parentFrame.getWidth() / 4;
//		int startY1 = 20 + 400;
//		int incrX = 124;
//		int incrY = 70;
//		for (int i = 0; i < testMap.length; i++){
//			for (int j = testMap[0].length; j > 0; j--){
//				if (testMap[i][j-1] == 1){
//					g.drawImage(grassBlock, 
//							startX1 + (j * incrX / 2) + (i * incrX / 2), 
//							startY1 + (i * incrY / 2) - (j * incrY / 2), 
//							null);
//				}
//			}
//		}
//	}
	
//	//This method runs through the testObjects 2D Array checking if there is an object at each space. 
//	//When an object is found at a space in the Array, the drawObjectsFromArray() method is called to draw the object
//	//in the correct position.
//	public void getObjectsFromArray(Graphics g){
//		for (int i = 0; i < testMap.length; i++){
//			for (int j = testMap[0].length; j > 0; j--){
//				if (testObjects[i][j-1] == 1){
//					int startX = parentFrame.getWidth() / 4 - 30;
//					int startY = 45 + 200;
//					drawObjectsFromArray(g, tree1, startX, startY, i, j);
//				}
//				else if (testObjects[i][j-1] == 2){
//					int startX = parentFrame.getWidth() / 4;
//					int startY = 85 + 200;
//					drawObjectsFromArray(g, tree2, startX, startY, i, j);
//				}
//				else if (testObjects[i][j-1] == 3){
//					int startX = parentFrame.getWidth() / 4 + 40;
//					int startY = 85 + 300;
//					drawObjectsFromArray(g, bush1, startX, startY, i, j);
//				}
//				else if (testObjects[i][j-1] == 4){
//					int startX = parentFrame.getWidth() / 4 + 60;
//					int startY = 85 + 340;
//					drawObjectsFromArray(g, rock1, startX, startY, i, j);
//				}
//			}
//		}
//	}
//	
//	public void drawObjectsFromArray(Graphics g, Image image, int startX, int startY, int i, int j){
//		int incrX = 124;
//		int incrY = 70;
//		g.drawImage(image, 
//				startX + (j * incrX / 2) + (i * incrX / 2), 
//				startY + (i * incrY / 2) - (j * incrY / 2), 
//				null);
//	}
	
	public void redraw(Graphics g){
		g.fillRect(0, 0, parentFrame.getWidth(), parentFrame.getHeight());
		testDrawGrass(g);
		getObjectsFromArray(g);
	}
	
	
	
	
	


}
