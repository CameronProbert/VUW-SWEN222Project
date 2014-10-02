package catgame.logic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageIO;

import catgame.GameObjects.GameObject;
import catgame.GameObjects.PlayableCharacter;

public class BoardCell {
	private Position cellPosition;
	private GameObject objectOnCell;
	private String groundType;
	private Image groundImage;
	private Image objectImage[] = new Image[4];

	public BoardCell(Position position, GameObject object, String groundType) {
		this.cellPosition = position;
		this.objectOnCell = object;
		this.groundType = groundType;

		// try {
		// //TODO FIX this for however it is going to be draw
		// //groundImage = ImageIO.read(new File(groundFile + ".png"));
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * draw the cells and there objects
	 * 
	 * @param g
	 */
	public void drawCellBase(Graphics g) {
		g.drawImage(groundImage, cellPosition.getX(), cellPosition.getY(), null);
	}

	/**
	 * Draw the object on the cell
	 * 
	 * @param g
	 */
	public void drawCellObject(Graphics g) {
		if (objectOnCell instanceof PlayableCharacter) {
			if (((PlayableCharacter) objectOnCell).getFacingDirection().equals("NORTH")) {
				g.drawImage(objectImage[0], cellPosition.getX(), cellPosition.getY(), null);
			} else if (((PlayableCharacter) objectOnCell).getFacingDirection().equals("SOUTH")) {
				g.drawImage(objectImage[1], cellPosition.getX(), cellPosition.getY(), null);
			} else if (((PlayableCharacter) objectOnCell).getFacingDirection().equals("EAST")) {
				g.drawImage(objectImage[2], cellPosition.getX(), cellPosition.getY(), null);
			} else if (((PlayableCharacter) objectOnCell).getFacingDirection().equals("WEST")) {
				g.drawImage(objectImage[3], cellPosition.getX(), cellPosition.getY(), null);
			}
		}
	}

	public Position getPosition() {
		return this.cellPosition;
	}
	
	public String getGroundType(){
		return groundType;
	}
	
	public GameObject getObjectOnCell(){
		return objectOnCell;
	}

	public String toString() {
		if (groundType == null) {
			return " ";
		}else if (objectOnCell == null) {
			return this.groundType;
		}else{
			return this.objectOnCell.toString();
		}
	}
}