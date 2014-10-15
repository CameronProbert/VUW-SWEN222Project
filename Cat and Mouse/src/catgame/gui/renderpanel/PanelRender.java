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
import catgame.logic.GameUtil;
import catgame.logic.GameUtil.Direction;
import catgame.logic.Room;

public class PanelRender extends JPanel {
	
	private GameUtil gUtil;
	private int panelWidth;
	private int panelHeight;
	private int blockWidth = 124;
	private int blockHeight = 70;
	private int xOffset;
	private int yOffset;
	
	private Image grassBlock;
	private Image tree1;
	private Image tree2;
	private Image bush1;
	private Image rock1;
	private Image corpse1;
	
	// Door images
	private Image doorClosedLeft1, doorClosedRight1, doorOpenLeft1, doorOpenRight1;

	// Hedge images
	private Image hedgeLeft1, hedgeRight1;
	
	// Fence images
	private Image fenceLeft1, fenceRight1;

	// Cat images
	private Image catFrontLeft1, catFrontRight1, catBackLeft1, catBackRight1, 
				catFrontLeft2, catFrontRight2, catBackLeft2, catBackRight2,
				catFrontLeft3, catFrontRight3, catBackLeft3, catBackRight3,
				catFrontLeft4, catFrontRight4, catBackLeft4, catBackRight4;
	
	// Chest images
	private Image chestFrontLeft1, chestFrontRight1, chestBackLeft1, chestBackRight1;
	
	// Minion images
	private Image minionFrontLeft1, minionFrontRight1, minionBackLeft1, minionBackRight1;
	
	// Boss images
	private Image bossFrontLeft1, bossFrontRight1, bossBackLeft1, bossBackRight1;

	// Classes for creating a test board
	private Room currentRoom;

	public PanelRender(Dimension windowSize, GameUtil gUtil, Room room) {
		this.panelWidth = (int)(windowSize.getWidth());
		this.panelHeight = (int)(windowSize.getHeight());
		this.gUtil = gUtil;
		this.currentRoom = room;
		

		setLayout(null);
		setSize((int)(windowSize.getWidth()), (int)(windowSize.getHeight()));
		setBackground(Color.DARK_GRAY);
		setVisible(true);

		setOffsetValues();
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
	
	public void setOffsetValues(){
		xOffset = 250;
		yOffset = 300;
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
			
			// Load door images
			doorClosedLeft1 = ImageIO.read(PanelRender.class.getResource("/images/ClosedGateLeft1.png"));
			doorClosedRight1 = ImageIO.read(PanelRender.class.getResource("/images/ClosedGateRight1.png"));
			doorOpenLeft1 = ImageIO.read(PanelRender.class.getResource("/images/OpenGateLeft1.png"));
			doorOpenRight1 = ImageIO.read(PanelRender.class.getResource("/images/OpenGateRight1.png"));			
			
			// Load hedge images
			hedgeLeft1 = ImageIO.read(PanelRender.class.getResource("/images/HedgeLeft1.png"));
			hedgeRight1 = ImageIO.read(PanelRender.class.getResource("/images/HedgeRight1.png"));
			
			// Load hedge images
			hedgeLeft1 = ImageIO.read(PanelRender.class.getResource("/images/FenceLeft1.png"));
			hedgeRight1 = ImageIO.read(PanelRender.class.getResource("/images/FenceRight1.png"));
			
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
			
			catFrontLeft2 = ImageIO.read(PanelRender.class.getResource("/images/CatFrontLeft2.png"));
			catFrontRight2 = ImageIO.read(PanelRender.class.getResource("/images/CatFrontRight2.png"));
			catBackLeft2 = ImageIO.read(PanelRender.class.getResource("/images/CatBackLeft2.png"));
			catBackRight2 = ImageIO.read(PanelRender.class.getResource("/images/CatBackRight2.png"));
			
			catFrontLeft3 = ImageIO.read(PanelRender.class.getResource("/images/CatFrontLeft3.png"));
			catFrontRight3 = ImageIO.read(PanelRender.class.getResource("/images/CatFrontRight3.png"));
			catBackLeft3 = ImageIO.read(PanelRender.class.getResource("/images/CatBackLeft3.png"));
			catBackRight3 = ImageIO.read(PanelRender.class.getResource("/images/CatBackRight3.png"));
			
			catFrontLeft4 = ImageIO.read(PanelRender.class.getResource("/images/CatFrontLeft4.png"));
			catFrontRight4 = ImageIO.read(PanelRender.class.getResource("/images/CatFrontRight4.png"));
			catBackLeft4 = ImageIO.read(PanelRender.class.getResource("/images/CatBackLeft4.png"));
			catBackRight4 = ImageIO.read(PanelRender.class.getResource("/images/CatBackRight4.png"));
			
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
				} else if (gUtil.getViewDirection() == Direction.EAST) {
					sendX = x;
					sendY = y;
					determineAndDrawGround(g, sendY, sendX, y, x);
					determineAndDrawObject(g, sendY, sendX, y, x);
				} else if (gUtil.getViewDirection() == Direction.SOUTH) {
					sendX = currentRoom.getBoardGrid().length - y - 1;
					sendY = x;
					determineAndDrawGround(g, sendY, sendX, y, x);
					determineAndDrawObject(g, sendY, sendX, y, x);
				} else if (gUtil.getViewDirection() == Direction.WEST) {
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
	public void determineAndDrawGround(Graphics g, int sendY, int sendX, int y,	int x) {
		int startX = xOffset + 19;
		int startY = yOffset + 20;
		if (currentRoom.getBoardGrid()[sendY][sendX].getGroundType() == null) {
			return;
		}
		if (currentRoom.getBoardGrid()[sendY][sendX].getGroundType().equals("Grass")) {
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
			startX = xOffset + 40;
			startY = yOffset - 15;
			drawObject(g, bush1, sendY, sendX, y, x, startY, startX);
		}
		else if (objTypeID.equals(GameUtil.TREE+"")){
			drawTree(g, sendY, sendX, y, x, objImageID);
		}
		else if (objTypeID.equals(GameUtil.ROCK+"")){
			startX = xOffset + 60;
			startY = yOffset + 25;
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
		else if (objTypeID.equals(GameUtil.HEDGEL+"") ||
				objTypeID.equals(GameUtil.HEDGER+"")){
			drawHedge(g, sendY, sendX, y, x, objTypeID);
		}
		else if (objTypeID.equals(GameUtil.FENCEL+"") ||
				objTypeID.equals(GameUtil.FENCER+"")){
			drawFence(g, sendY, sendX, y, x, objTypeID);
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
				startX + (x * blockWidth / 2) + (y * blockWidth / 2), 
				startY + (y * blockHeight / 2) - (x * blockHeight / 2), 
				null);
	}

	public void redraw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, panelWidth, panelHeight);
		drawGroundAndObjects(g);
	}
	
	public int directionTranslate(Direction boardOrientation, Direction playerDirection) {
		return (boardOrientation.getValue() + playerDirection.getValue()) % 4;
	}
	
	public Direction directionTranslator(Direction viewDirection, Direction charDirection){
		switch (viewDirection){
		case NORTH:
			switch (charDirection){
			case NORTH: return Direction.NORTH;
			case EAST: return Direction.EAST;
			case SOUTH: return Direction.SOUTH;
			case WEST: return Direction.WEST;
			default: System.out.println("Error");
			break;
			}
		case EAST:
			switch (charDirection){
			case EAST: return Direction.NORTH;
			case SOUTH: return Direction.EAST;
			case WEST: return Direction.SOUTH;
			case NORTH: return Direction.WEST;
			default: System.out.println("Error");
			break;
			}
		case SOUTH:
			switch (charDirection){	
			case SOUTH: return Direction.NORTH;
			case WEST: return Direction.EAST;
			case NORTH: return Direction.SOUTH;
			case EAST: return Direction.WEST;
			default: System.out.println("Error");
			break;
			}
		case WEST:
			switch (charDirection){	
			case WEST: return Direction.NORTH;
			case NORTH: return Direction.EAST;
			case EAST: return Direction.SOUTH;
			case SOUTH: return Direction.WEST;
			default: System.out.println("Error");
			break;
			}
		default: System.out.println("Error");
		break;
		}
		return null;
	}	
	
	//
	//
	//
	//Helper methods for drawing various objects
	//
	//
	//
	
	public void drawTree(Graphics g, int sendY, int sendX, int y, int x, String objImageID){
		if (objImageID.equals("10")){
			int startX = xOffset - 30;
			int startY = yOffset - 155;
			drawObject(g, tree1, sendY, sendX, y, x, startY, startX);
		}
		else if (objImageID.equals("11")){
			int treeStartX = xOffset;
			int treeStartY = yOffset - 115;
			drawObject(g, tree2, sendY, sendX, y, x, treeStartY, treeStartX);
		}
	}

	public void drawChest(Graphics g, int sendY, int sendX, int y, int x){
		int startX = xOffset + 50;
		int startY = yOffset;
		switch (gUtil.getViewDirection()) {
		case NORTH: drawObject(g, chestFrontLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case WEST: drawObject(g, chestFrontRight1, sendY, sendX, y, x, startY, startX);
			break;
		case SOUTH: drawObject(g, chestBackLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case EAST: drawObject(g, chestBackRight1, sendY, sendX, y, x, startY, startX);
			break;
		default:
			System.out.println("There was a drawing error");
			break;				
		}
	}
	
	public void drawPlayableChar(Graphics g, int sendY, int sendX, int y, int x){
		Image backRight, backLeft, frontRight, frontLeft;
		PlayableCharacter character = (PlayableCharacter) currentRoom.getBoardGrid()[sendY][sendX].getObjectOnCell();
		String charID = character.getObjectID()+"";
		String charNumberID = charID.substring(4,6);
		System.out.println("charID: " + charID);
		System.out.println("charImageID: " + charNumberID);
		switch (charNumberID){
		case "10":
			backRight = catBackRight1;
			backLeft = catBackLeft1;
			frontRight = catFrontRight1;
			frontLeft = catFrontLeft1;
			break;
		case "11":
			backRight = catBackRight2;
			backLeft = catBackLeft2;
			frontRight = catFrontRight2;
			frontLeft = catFrontLeft2;
			break;
		case "12":
			backRight = catBackRight3;
			backLeft = catBackLeft3;
			frontRight = catFrontRight3;
			frontLeft = catFrontLeft3;
			break;
		case "13":
			backRight = catBackRight4;
			backLeft = catBackLeft4;
			frontRight = catFrontRight4;
			frontLeft = catFrontLeft4;
			break;
		default:
			System.out.println("There was an error determining which player cat to draw");
			return;
		}
		Direction drawDirection = directionTranslator(gUtil.getViewDirection(), character.getFacingDirection());
		int startX = xOffset + 27;
		int startY = yOffset - 5;
		if (((PlayableCharacter)currentRoom.getBoardGrid()[sendY][sendX].getObjectOnCell()).isDead()){
			drawObject(g, corpse1, sendY, sendX, y, x, startY+25, startX+25);
			return;
		}			
		switch (drawDirection) {
		case NORTH: drawObject(g, backRight, sendY, sendX, y, x, startY, startX);
			break;
		case EAST: drawObject(g, frontRight, sendY, sendX, y, x, startY, startX);
			break;
		case SOUTH: drawObject(g, frontLeft, sendY, sendX, y, x, startY, startX);
			break;
		case WEST: drawObject(g, backLeft, sendY, sendX, y, x, startY, startX);
			break;
		default: System.out.println("There was a problem drawing a character");
		}
	}
	
	public void drawMinion(Graphics g, int sendY, int sendX, int y, int x){
		int startX = xOffset + 60;
		int startY = yOffset;
		if (((Minion)currentRoom.getBoardGrid()[sendY][sendX].getObjectOnCell()).isDead()){
			drawObject(g, corpse1, sendY, sendX, y, x, startY+20, startX-10);
			return;
		}
		switch (gUtil.getViewDirection()) {
		case NORTH: drawObject(g, minionFrontLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case WEST: drawObject(g, minionBackLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case SOUTH: drawObject(g, minionBackRight1, sendY, sendX, y, x, startY, startX);
			break;
		case EAST: drawObject(g, minionFrontRight1, sendY, sendX, y, x, startY, startX);
			break;
		default: System.out.println("There was a drawing error");
			break;				
		}
	}
	
	public void drawBoss(Graphics g, int sendY, int sendX, int y, int x){
		int startX = xOffset + 25;
		int startY = yOffset - 50;
		if (((Boss)currentRoom.getBoardGrid()[sendY][sendX].getObjectOnCell()).isDead()){
			drawObject(g, corpse1, sendY, sendX, y, x, startY+75, startX+35);
			return;
		}
		switch (gUtil.getViewDirection()) {
		case NORTH: drawObject(g, bossFrontLeft1, sendY, sendX, y, x, startY, startX);
			break;
		case WEST: drawObject(g, bossBackLeft1, sendY, sendX, y, x, startY, startX+20);
			break;
		case SOUTH: drawObject(g, bossBackRight1, sendY, sendX, y, x, startY, startX-20);
			break;
		case EAST: drawObject(g, bossFrontRight1, sendY, sendX, y, x, startY, startX);
			break;
		default: System.out.println("There was a drawing error");
			break;				
		}		
	}
	
	public void drawHedge(Graphics g, int sendY, int sendX, int y, int x, String hedgeType){		
		int startX = panelWidth / 4 + 50;
		int startY = 50 + 340;
		Image img = fenceLeft1;
		switch (gUtil.getViewDirection()) {
		case NORTH:
			if (hedgeType == "30"){ img = hedgeLeft1; } 
			else { img = hedgeRight1; }
			drawObject(g, img, sendY, sendX, y, x, startY, startX);
			break;
		case EAST:
			if (hedgeType == "30"){ img = hedgeRight1; }
			else { img = hedgeLeft1; }
			drawObject(g, img, sendY, sendX, y, x, startY, startX);
			break;
		case SOUTH:
			if (hedgeType == "30"){ img = hedgeLeft1; } 
			else {img = hedgeRight1; }
			drawObject(g, img, sendY, sendX, y, x, startY, startX);
			break;
		case WEST:
			if (hedgeType == "30"){	img = hedgeRight1; } 
			else { img = hedgeLeft1; }
			drawObject(g, img, sendY, sendX, y, x, startY, startX);
			break;
		default:
			System.out.println("Something went wrong");
			break;
		}
	}
	
	public void drawFence(Graphics g, int sendY, int sendX, int y, int x, String fenceType){
		int startX = panelWidth / 4 + 50;
		int startY = 50 + 340;
		Image img = fenceLeft1;
		switch (gUtil.getViewDirection()) {
		case NORTH:
			if (fenceType == "34"){	img = fenceLeft1; } 
			else { img = fenceRight1; }
			drawObject(g, img, sendY, sendX, y, x, startY, startX);
			break;
		case EAST:
			if (fenceType == "34"){ img = fenceRight1; } 
			else { img = fenceLeft1; }
			drawObject(g, img, sendY, sendX, y, x, startY, startX);
			break;
		case SOUTH:
			if (fenceType == "34"){ img = fenceLeft1; } 
			else { img = fenceRight1; }
			drawObject(g, img, sendY, sendX, y, x, startY, startX);
			break;
		case WEST:
			if (fenceType == "34"){ img = fenceRight1; } 
			else { img = fenceLeft1; }
			drawObject(g, img, sendY, sendX, y, x, startY, startX);
			break;
		default:
			System.out.println("Something went wrong");
			break;
		}
	}
	
	public void drawDoor(Graphics g, int sendY, int sendX, int y, int x, String objTypeID){
		Image leftDoor;
		Image rightDoor;
		int startLeftX = xOffset + 50;
		int startLeftY = yOffset - 10;
		int startRightX = xOffset + 50;
		int startRightY = yOffset - 10;
		if (((Door) currentRoom.getBoardGrid()[sendY][sendX].getObjectOnCell()).getIsLocked() == true){
			leftDoor = doorClosedLeft1;
			rightDoor = doorClosedRight1;
		}
		else {
			leftDoor = doorOpenLeft1;
			rightDoor = doorOpenRight1;
			startLeftX = xOffset + 50 - 10;
			startRightX = xOffset + 50 - 30;
		}		
		switch (gUtil.getViewDirection()) {
		case NORTH:
			switch (objTypeID) {
			case "40": drawObject(g, rightDoor, sendY, sendX, y, x, startRightY, startRightX);
				break;
			case "41": drawObject(g, leftDoor, sendY, sendX, y, x, startLeftY, startLeftX);
				break;
			case "42": drawObject(g, rightDoor, sendY, sendX, y, x, startRightY, startRightX);
				break;
			case "43": drawObject(g, leftDoor, sendY, sendX, y, x, startLeftY, startLeftX);
				break;
			}			
			break;
		case WEST:
			switch (objTypeID) {
			case "40": drawObject(g, leftDoor, sendY, sendX, y, x, startLeftY, startLeftX);
				break;
			case "41": drawObject(g, rightDoor, sendY, sendX, y, x, startRightY, startRightX);
				break;
			case "42": drawObject(g, leftDoor, sendY, sendX, y, x, startLeftY, startLeftX);
				break;
			case "43": drawObject(g, rightDoor, sendY, sendX, y, x, startRightY, startRightX);
				break;
		}			
		break;
		case SOUTH:
			switch (objTypeID) {
			case "40": drawObject(g, rightDoor, sendY, sendX, y, x, startRightY, startRightX);
				break;
			case "41": drawObject(g, leftDoor, sendY, sendX, y, x, startLeftY, startLeftX);
				break;
			case "42": drawObject(g, rightDoor, sendY, sendX, y, x, startRightY, startRightX);
				break;
			case "43": drawObject(g, leftDoor, sendY, sendX, y, x, startLeftY, startLeftX);
				break;
		}			
		break;
		case EAST:
			switch (objTypeID) {
			case "40": drawObject(g, leftDoor, sendY, sendX, y, x, startLeftY, startLeftX);
				break;
			case "41": drawObject(g, rightDoor, sendY, sendX, y, x, startRightY, startRightX);
				break;
			case "42": drawObject(g, leftDoor, sendY, sendX, y, x, startLeftY, startLeftX);
				break;
			case "43": drawObject(g, rightDoor, sendY, sendX, y, x, startRightY, startRightX);
				break;
		}			
		break;
		default:
			System.out.println("There was a drawing error");
			break;				
		}
	}
}
