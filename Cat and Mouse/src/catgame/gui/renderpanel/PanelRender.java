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
import catgame.gui.FrameClient;
import catgame.logic.GameUtil;
import catgame.logic.GameUtil.Direction;
import catgame.logic.ObjectStorer;
import catgame.logic.Room;
import catgame.logic.RoomBuilder;

public class PanelRender extends JPanel {
	
	private String playableCharID;
	private GameUtil gUtil;
	private int panelWidth;
	private int panelHeight;
	
	private Image grassBlock;
	private Image tree1;
	private Image tree2;
	private Image bush1;
	private Image rock1;
	private Image corpse1;
	
	private Image doorLeft1;
	private Image doorRight1;

	private int blockWidth = 124;
	private int blockHeight = 70;

	// Cat images
	private Image catFrontLeft1;
	private Image catFrontRight1;
	private Image catBackLeft1;
	private Image catBackRight1;
	
	// Chest images
	private Image chestFrontLeft1;
	private Image chestFrontRight1;
	private Image chestBackLeft1;
	private Image chestBackRight1;
	
	// Minion images
	private Image minionFrontLeft1;
	private Image minionFrontRight1;
	private Image minionBackLeft1;
	private Image minionBackRight1;
	
	// Boss images
	private Image bossFrontLeft1;
	private Image bossFrontRight1;
	private Image bossBackLeft1;
	private Image bossBackRight1;

	// Classes for creating a test board
	private RoomBuilder buildBoard;
	private Room currentRoom;

	public PanelRender(Dimension windowSize, String playableCharID, GameUtil gUtil, Room room) {
		this.panelWidth = (int)(windowSize.getWidth());
		this.panelHeight = (int)(windowSize.getHeight());
		this.playableCharID = playableCharID;
		this.gUtil = gUtil;
		this.currentRoom = room;

		setLayout(null);
		setSize((int)(windowSize.getWidth()), (int)(windowSize.getHeight()));
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

	public void setupImages() {
		try {
			// Load object images
			grassBlock = ImageIO.read(PanelRender.class.getResource("/images/Grass1.png"));
			tree1 = ImageIO.read(PanelRender.class.getResource("/images/Tree1.png"));
			tree2 = ImageIO.read(PanelRender.class.getResource("/images/Tree2.png"));
			bush1 = ImageIO.read(PanelRender.class.getResource("/images/Bush1.png"));
			rock1 = ImageIO.read(PanelRender.class.getResource("/images/Rock1.png"));
			corpse1 = ImageIO.read(PanelRender.class.getResource("/images/Corpse1.png"));
			
			doorLeft1 = ImageIO.read(PanelRender.class.getResource("/images/ClosedGateLeft1.png"));
			doorRight1 = ImageIO.read(PanelRender.class.getResource("/images/ClosedGateRight1.png"));
			
			// Load chest images
			chestFrontLeft1 = ImageIO.read(PanelRender.class.getResource("/images/ChestFrontLeft1.png"));
			chestFrontRight1 = ImageIO.read(PanelRender.class.getResource("/images/ChestFrontRight1.png"));
			chestBackLeft1 = ImageIO.read(PanelRender.class.getResource("/images/ChestBackLeft1.png"));
			chestBackRight1 = ImageIO.read(PanelRender.class.getResource("/images/ChestBackRight1.png"));
			
			// Load minion images
			minionFrontLeft1 = ImageIO.read(PanelRender.class.getResource("/images/RatMinionFrontLeft1.png"));
			minionFrontRight1 = ImageIO.read(PanelRender.class.getResource("/images/RatMinionFrontRight1.png"));
			minionBackLeft1 = ImageIO.read(PanelRender.class.getResource("/images/RatMinionBackLeft1.png"));
			minionBackRight1 = ImageIO.read(PanelRender.class.getResource("/images/RatMinionBackRight1.png"));
			
			// Load boss images
			bossFrontLeft1 = ImageIO.read(PanelRender.class.getResource("/images/RatBossFrontLeft1.png"));
			bossFrontRight1 = ImageIO.read(PanelRender.class.getResource("/images/RatBossFrontRight1.png"));
			bossBackLeft1 = ImageIO.read(PanelRender.class.getResource("/images/RatBossBackLeft1.png"));
			bossBackRight1 = ImageIO.read(PanelRender.class.getResource("/images/RatBossBackRight1.png"));
			
			// Load cat images
			catFrontLeft1 = ImageIO.read(PanelRender.class.getResource("/images/CatFrontLeft1.png"));
			catFrontRight1 = ImageIO.read(PanelRender.class.getResource("/images/CatFrontRight1.png"));
			catBackLeft1 = ImageIO.read(PanelRender.class.getResource("/images/CatBackLeft1.png"));
			catBackRight1 = ImageIO.read(PanelRender.class.getResource("/images/CatBackRight1.png"));
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
		for (int y = 0; y < currentRoom.getBoardGrid().length; y++) {
			for (int x = currentRoom.getBoardGrid()[0].length - 1; x >= 0; x--) {
				if (gUtil.getViewDirection() == Direction.NORTH) {
					sendX = y;
					sendY = currentRoom.getBoardGrid()[0].length - x - 1;
					determineAndDrawGround(g, sendY, sendX, y, x);
					determineAndDrawObject(g, sendY, sendX, y, x);
				} else if (gUtil.getViewDirection() == Direction.WEST) {
					sendX = x;
					sendY = y;
					determineAndDrawGround(g, sendY, sendX, y, x);
					determineAndDrawObject(g, sendY, sendX, y, x);
				} else if (gUtil.getViewDirection() == Direction.SOUTH) {
					sendX = currentRoom.getBoardGrid().length - y - 1;
					sendY = x;
					determineAndDrawGround(g, sendY, sendX, y, x);
					determineAndDrawObject(g, sendY, sendX, y, x);
				} else if (gUtil.getViewDirection() == Direction.EAST) {
					sendX = currentRoom.getBoardGrid()[0].length - x - 1;
					sendY = currentRoom.getBoardGrid().length - y - 1;
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
		int startX = 19 + panelWidth / 4;
		int startY = 20 + 400;
		if (currentRoom.getBoardGrid()[sendY][sendX].getGroundType() == "Grass") {
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
	public void determineAndDrawObject(Graphics g, int sendY, int sendX, int y,	int x) {
		//Returns if current cell doesn't contain an object or character
		if (currentRoom.getBoardGrid()[sendY][sendX].getObjectOnCell() == null) {
			return;
		}
		String objFullID = currentRoom.getBoardGrid()[sendY][sendX]
				.getObjectOnCell().getObjectID()+"";
		String objTypeID = objFullID.substring(0, 2);
		String objImageID = objFullID.substring(2, 4);
		
		int startX, startY;
		if (objTypeID.equals(GameUtil.BUSH+"")){
			startX = panelWidth / 4 + 40;
			startY = 85 + 300;
			drawObject(g, bush1, sendY, sendX, y, x, startY, startX);
		}
		else if (objTypeID.equals(GameUtil.TREE+"")){
			drawTree(g, sendY, sendX, y, x, objImageID);
		}
		else if (objTypeID.equals(GameUtil.ROCK+"")){
			startX = panelWidth / 4 + 60;
			startY = 85 + 340;
			drawObject(g, rock1, sendY, sendX, y, x, startY, startX);
		}
		else if (objTypeID.equals(GameUtil.CHESTONE+"") || 
				objTypeID.equals(GameUtil.CHESTTWO+"")){
			drawChest(g, sendY, sendX, y, x);
		}
		else if (objTypeID.equals(GameUtil.DOORN+"") ||
				objTypeID.equals(GameUtil.DOORS+"") ||
				objTypeID.equals(GameUtil.DOORE+"") ||
				objTypeID.equals(GameUtil.DOORW+"")){
			drawDoor(g, sendY, sendX, y, x, objTypeID);			
		}
		else if (objTypeID.equals(GameUtil.HEDGEN+"") ||
				objTypeID.equals(GameUtil.HEDGES+"") ||
				objTypeID.equals(GameUtil.HEDGEE+"") ||
				objTypeID.equals(GameUtil.HEDGEW+"")){
			
		}
		else if (objTypeID.equals(GameUtil.PLAYABLECHARACTER+"")){
			drawPlayableChar(g, sendY, sendX, y, x);
		}
		else if (objTypeID.equals(GameUtil.MINIONONE+"")){
			drawMinion(g, sendY, sendX, y, x);
		}
		else if (objTypeID.equals(GameUtil.BOSSONE+"")){
			drawBoss(g, sendY, sendX, y, x);
		}
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

	public void redraw(Graphics g) {
		g.fillRect(0, 0, panelWidth, panelHeight);
		drawGroundAndObjects(g);
	}
	
	public void drawTree(Graphics g, int sendY, int sendX, int y, int x, String objImageID){
		if (objImageID.equals("10")){
			int startX = panelWidth / 4 - 30;
			int startY = 45 + 200;
			drawObject(g, tree1, sendY, sendX, y, x, startY, startX);
		}
		else if (objImageID.equals("11")){
			int treeStartX = panelWidth / 4;
			int treeStartY = 85 + 200;
			drawObject(g, tree2, sendY, sendX, y, x, treeStartY, treeStartX);
		}
	}

	public void drawChest(Graphics g, int sendY, int sendX, int y, int x){
		int startX = panelWidth / 4 + 50;
		int startY = 60 + 340;
		switch (gUtil.getViewDirection()) {
		case NORTH:
			drawObject(g, chestFrontLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case EAST:
			drawObject(g, chestFrontRight1, sendY, sendX, y, x, startY, startX);
			break;
		case SOUTH:
			drawObject(g, chestBackLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case WEST:
			drawObject(g, chestBackRight1, sendY, sendX, y, x, startY, startX);
			break;
		default:
			System.out.println("There was a drawing error");
			break;				
		}
	}
	
	public void drawPlayableChar(Graphics g, int sendY, int sendX, int y, int x){
		PlayableCharacter character = (PlayableCharacter) currentRoom.getBoardGrid()[sendY][sendX]
				.getObjectOnCell();
		int drawDirection = currentRoom.directionTranslator(gUtil.getViewDirection(), character.getFacingDirection());
		int startX = panelWidth / 4 + 37;
		int startY = 85 + 305;
		
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
	
	public void drawMinion(Graphics g, int sendY, int sendX, int y, int x){
		int startX = panelWidth / 4 + 50;
		int startY = 60 + 340;
		switch (gUtil.getViewDirection()) {
		case NORTH:
			drawObject(g, minionFrontLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case EAST:
			drawObject(g, minionBackLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case SOUTH:
			drawObject(g, minionBackRight1, sendY, sendX, y, x, startY, startX);
			break;
		case WEST:
			drawObject(g, minionFrontRight1, sendY, sendX, y, x, startY, startX);
			break;
		default:
			System.out.println("There was a drawing error");
			break;				
		}
	}
	
	public void drawBoss(Graphics g, int sendY, int sendX, int y, int x){
		int startX = panelWidth / 4 + 50;
		int startY = 60 + 340;
		switch (gUtil.getViewDirection()) {
		case NORTH:
			drawObject(g, bossFrontLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case EAST:
			drawObject(g, bossBackLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case SOUTH:
			drawObject(g, bossBackRight1, sendY, sendX, y, x, startY, startX);
			break;
		case WEST:
			drawObject(g, bossFrontRight1, sendY, sendX, y, x, startY, startX);
			break;
		default:
			System.out.println("There was a drawing error");
			break;				
		}
	}
	
	public void drawDoor(Graphics g, int sendY, int sendX, int y, int x, String objTypeID){
		int startX = panelWidth / 4 + 50;
		int startY = 50 + 340;
		switch (gUtil.getViewDirection()) {
		case NORTH:
			switch (objTypeID) {
				case "40":
					drawObject(g, doorRight1, sendY, sendX, y, x, startY, startX);
					break;
				case "41":
					drawObject(g, doorLeft1, sendY, sendX, y, x, startY, startX);
					break;
				case "42":
					drawObject(g, doorRight1, sendY, sendX, y, x, startY, startX);
					break;
				case "43":
					drawObject(g, doorLeft1, sendY, sendX, y, x, startY, startX);
					break;
			}			
			break;
		case EAST:
			switch (objTypeID) {
			case "40":
				drawObject(g, doorLeft1, sendY, sendX, y, x, startY, startX);
				break;
			case "41":
				drawObject(g, doorRight1, sendY, sendX, y, x, startY, startX);
				break;
			case "42":
				drawObject(g, doorLeft1, sendY, sendX, y, x, startY, startX);
				break;
			case "43":
				drawObject(g, doorRight1, sendY, sendX, y, x, startY, startX);
				break;
		}			
		break;
		case SOUTH:
			switch (objTypeID) {
			case "40":
				drawObject(g, doorRight1, sendY, sendX, y, x, startY, startX);
				break;
			case "41":
				drawObject(g, doorLeft1, sendY, sendX, y, x, startY, startX);
				break;
			case "42":
				drawObject(g, doorRight1, sendY, sendX, y, x, startY, startX);
				break;
			case "43":
				drawObject(g, doorLeft1, sendY, sendX, y, x, startY, startX);
				break;
		}			
		break;
		case WEST:
			switch (objTypeID) {
			case "40":
				drawObject(g, doorLeft1, sendY, sendX, y, x, startY, startX);
				break;
			case "41":
				drawObject(g, doorRight1, sendY, sendX, y, x, startY, startX);
				break;
			case "42":
				drawObject(g, doorLeft1, sendY, sendX, y, x, startY, startX);
				break;
			case "43":
				drawObject(g, doorRight1, sendY, sendX, y, x, startY, startX);
				break;
		}			
		break;
		default:
			System.out.println("There was a drawing error");
			break;				
		}
	}
}
