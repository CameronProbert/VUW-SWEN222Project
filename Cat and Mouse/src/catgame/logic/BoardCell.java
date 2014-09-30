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
	private Image groundImage;
	private Image objectImage[] = new Image[4];

	public BoardCell(Position position, GameObject object, String groundFile) {
		this.cellPosition = position;
		this.objectOnCell = object;
		try {
			//TODO FIX this for however it is going to be draw
			
			groundImage = ImageIO.read(new File(groundFile + ".png"));
			objectImage[0] = ImageIO.read(new File("N" + objectOnCell.getObjectID() + ".png"));
			objectImage[1] = ImageIO.read(new File("S" + objectOnCell.getObjectID() + ".png"));
			objectImage[2] = ImageIO.read(new File("E" + objectOnCell.getObjectID() + ".png"));
			objectImage[3] = ImageIO.read(new File("W" + objectOnCell.getObjectID() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
}