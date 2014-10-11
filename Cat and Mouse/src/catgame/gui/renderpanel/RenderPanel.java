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
import catgame.logic.GameUtil;
import catgame.logic.GameUtil.Direction;
import catgame.logic.ObjectStorer;
import catgame.logic.Room;
import catgame.logic.RoomBuilder;

public class RenderPanel extends JPanel {

	public final ClientFrame parentFrame;
	private Image grassBlock;
	private Image tree1;
	private Image tree2;
	private Image bush1;
	private Image rock1;

	private int blockWidth = 124;
	private int blockHeight = 70;

	// Cat images
	private Image catFrontLeft1;
	private Image catFrontRight1;
	private Image catBackLeft1;
	private Image catBackRight1;

	// Classes for creating a test board
	private RoomBuilder buildBoard;
	private Room testRoom;

	public RenderPanel(Dimension windowSize, ClientFrame parentFrame) {
		this.parentFrame = parentFrame;

		setLayout(null);
		setSize(parentFrame.getWidth(), parentFrame.getHeight());
		setBackground(Color.DARK_GRAY);
		setVisible(true);

		// Roombuilder testing
		buildBoard = new RoomBuilder();
		ObjectStorer objStorer = new ObjectStorer();
		testRoom = buildBoard.loadRoom(objStorer);
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

	public void setupImages() {
		try {
			// Load object Images
			grassBlock = ImageIO.read(RenderPanel.class
					.getResource("/images/Grass1.png"));
			tree1 = ImageIO.read(RenderPanel.class
					.getResource("/images/Tree1.png"));
			tree2 = ImageIO.read(RenderPanel.class
					.getResource("/images/Tree2.png"));
			bush1 = ImageIO.read(RenderPanel.class
					.getResource("/images/Bush1.png"));
			rock1 = ImageIO.read(RenderPanel.class
					.getResource("/images/Rock1.png"));
			// Load cat Images
			catFrontLeft1 = ImageIO.read(RenderPanel.class
					.getResource("/images/CatFrontLeft1.png"));
			catFrontRight1 = ImageIO.read(RenderPanel.class
					.getResource("/images/CatFrontRight1.png"));
			catBackLeft1 = ImageIO.read(RenderPanel.class
					.getResource("/images/CatBackLeft1.png"));
			catBackRight1 = ImageIO.read(RenderPanel.class
					.getResource("/images/CatBackRight1.png"));
		} catch (IOException e) {
			System.out
					.println("There was an issue loading image files, check file locations are correct.");
			e.printStackTrace();
		}
	}

	/**
	 * Draws ground blocks and objects based on the current viewDirection. This
	 * is done by first determining the current viewDirection, calling
	 * determineAndDrawGround() which determines the type of groundBlock at the
	 * current position of on the map, then calls a relevant draw method to draw
	 * the groundBlock at that position.
	 * 
	 * 
	 * 
	 * @param g
	 */
	public void drawGroundAndObjects(Graphics g) {
		int sendX;
		int sendY;
		for (int y = 0; y < testRoom.getBoardGrid().length; y++) {
			for (int x = testRoom.getBoardGrid()[0].length - 1; x >= 0; x--) {
				if (GameUtil.VIEWDIRECTION == Direction.NORTH) {
					sendX = y;
					sendY = testRoom.getBoardGrid()[0].length - x - 1;
					determineAndDrawGround(g, sendY, sendX, y, x);
					determineAndDrawObject(g, sendY, sendX, y, x);
				} else if (GameUtil.VIEWDIRECTION == Direction.WEST) {
					sendX = x;
					sendY = y;
					determineAndDrawGround(g, sendY, sendX, y, x);
					determineAndDrawObject(g, sendY, sendX, y, x);
				} else if (GameUtil.VIEWDIRECTION == Direction.SOUTH) {
					sendX = testRoom.getBoardGrid().length - y - 1;
					sendY = x;
					determineAndDrawGround(g, sendY, sendX, y, x);
					determineAndDrawObject(g, sendY, sendX, y, x);
				} else if (GameUtil.VIEWDIRECTION == Direction.EAST) {
					sendX = testRoom.getBoardGrid()[0].length - x - 1;
					sendY = testRoom.getBoardGrid().length - y - 1;
					determineAndDrawGround(g, sendY, sendX, y, x);
					determineAndDrawObject(g, sendY, sendX, y, x);
				}
			}
		}
	}

	/**
	 * Determines ground type at current block, then calls the drawGround()
	 * method, passing it the relevant image and positioning values.
	 * 
	 * @param g
	 * @param sendY
	 * @param sendX
	 * @param y
	 * @param x
	 */
	public void determineAndDrawGround(Graphics g, int sendY, int sendX, int y,
			int x) {
		int startX = 19 + parentFrame.getWidth() / 4;
		int startY = 20 + 400;
		if (testRoom.getBoardGrid()[sendY][sendX].getGroundType() == "Grass") {
			drawGround(g, grassBlock, sendY, sendX, y, x, startY, startX);
		}
	}

	/**
	 * Determines object type at current block, then calls the drawObject()
	 * method, passing it the relevant image and positioning values.
	 * 
	 * @param g
	 * @param sendY
	 * @param sendX
	 * @param y
	 * @param x
	 */
	public void determineAndDrawObject(Graphics g, int sendY, int sendX, int y,
			int x) {

		// -----------------------game object
		// instanceof: MoveAble-------||------NonMovabke
		// "" PlayerAbleCharacter----NPC------------tree||rock...etc
		// -----------------------Boss||Minion

		if (testRoom.getBoardGrid()[sendY][sendX].getObjectOnCell() == null) {
			return;
		}
		MasterObject currentObj = testRoom.getBoardGrid()[sendY][sendX]
				.getObjectOnCell();
		String currentObjID = currentObj.getObjectID() + "";
		currentObjID = currentObjID.substring(0, 2);
		System.out.println(currentObjID);

		int startX, startY;
		switch (currentObjID) {
		
		case GameUtil.BUSH+"":
			startX = parentFrame.getWidth() / 4 + 40;
			startY = 85 + 300;
			drawObject(g, bush1, sendY, sendX, y, x, startY, startX);
			break;
		case GameUtil.TREE+"":
			startX = parentFrame.getWidth() / 4 - 30;
			startY = 45 + 200;
			drawObject(g, tree1, sendY, sendX, y, x, startY, startX);
			break;
		case GameUtil.ROCK+"":
			startX = parentFrame.getWidth() / 4 + 60;
			startY = 85 + 340;
			drawObject(g, rock1, sendY, sendX, y, x, startY, startX);
			break;
		case GameUtil.PLAYABLECHARACTER+"":
			PlayableCharacter character = (PlayableCharacter) testRoom.getBoardGrid()[sendY][sendX]
					.getObjectOnCell();
			int drawDirection = testRoom.translateForGrid(GameUtil.viewDirection, character.getFacingDirection());
			startX = parentFrame.getWidth() / 4 + 37;
			startY = 85 + 305;
			
			switch (drawDirection) {
			case 0:
				drawObject(g, catBackRight1, sendY, sendX, y, x, startY, startX);
				break;
			case 1:
				drawObject(g, catFrontRight1, sendY, sendX, y, x, startY, startX);
				break;
			case 2:
				drawObject(g, catFrontLeft1, sendY, sendX, y, x, startY, startX);
				break;
			case 3:
				drawObject(g, catBackLeft1, sendY, sendX, y, x, startY, startX);
				break;				
			}
		}
//		if (testRoom.getBoardGrid()[sendY][sendX].getObjectOnCell() instanceof Tree) {
//			int treeStartX = parentFrame.getWidth() / 4 - 30;
//			int treeStartY = 45 + 200;
//			drawObject(g, tree1, sendY, sendX, y, x, treeStartY, treeStartX);
//		} else if (testRoom.getBoardGrid()[sendY][sendX].getObjectOnCell() instanceof Tree) {
//			int treeStartX = parentFrame.getWidth() / 4;
//			int treeStartY = 85 + 200;
//			drawObject(g, tree1, sendY, sendX, y, x, treeStartY, treeStartX);
//		} else if (testRoom.getBoardGrid()[sendY][sendX].getObjectOnCell() instanceof Bush) {
//			int bushStartX = parentFrame.getWidth() / 4 + 40;
//			int bushStartY = 85 + 300;
//			drawObject(g, bush1, sendY, sendX, y, x, bushStartY, bushStartX);
//		} else if (testRoom.getBoardGrid()[sendY][sendX].getObjectOnCell() instanceof Rock) {
//			int rockStartX = parentFrame.getWidth() / 4 + 60;
//			int rockStartY = 85 + 340;
//			drawObject(g, rock1, sendY, sendX, y, x, rockStartY, rockStartX);
//		}
	}

	/**
	 * Draws a groundBlock image at a position specified by the given
	 * positioning values.
	 * 
	 * @param g
	 * @param image
	 * @param sendY
	 * @param sendX
	 * @param y
	 * @param x
	 * @param startY
	 * @param startX
	 */
	public void drawGround(Graphics g, Image image, int sendY, int sendX,
			int y, int x, int startY, int startX) {
		g.drawImage(image,
				startX + (x * blockWidth / 2) + (y * blockWidth / 2), startY
						+ (y * blockHeight / 2) - (x * blockHeight / 2), null);
	}

	/**
	 * Draws an object image at a position specified by the given positioning
	 * values.
	 * 
	 * @param g
	 * @param image
	 * @param sendY
	 * @param sendX
	 * @param y
	 * @param x
	 * @param startY
	 * @param startX
	 */
	public void drawObject(Graphics g, Image image, int sendY, int sendX,
			int y, int x, int startY, int startX) {
		g.drawImage(image,
				startX + (x * blockWidth / 2) + (y * blockWidth / 2), startY
						+ (y * blockHeight / 2) - (x * blockHeight / 2), null);
	}

	public void drawCharacters(Graphics g) {
		g.drawImage(catFrontLeft1, 710, 325, null);
	}

	public void redraw(Graphics g) {
		g.fillRect(0, 0, parentFrame.getWidth(), parentFrame.getHeight());
		drawGroundAndObjects(g);
		//drawCharacters(g);
	}

}
