package catgame.logic;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import catgame.GameObjects.GameObject;

public class BoardCell {
	Position cellPosition;
	GameObject objectOnCell;
	Image groundImage;
	Image objectImage;
	
	public BoardCell(Position position, GameObject object , String groundFile){
		this.cellPosition = position;
		this.objectOnCell = object;
		
		try {
			groundImage = ImageIO.read(new File(groundFile));
			objectImage = ImageIO.read(new File(objectOnCell.getObjectID()+""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * draw the cells and there objects
	 * @param g
	 */ 
	public void drawCellBase(Graphics g){
		g.drawImage(groundImage, cellPosition.getX() , cellPosition.getY(), null);
	}
	
	/**
	 * Draw the object on the cell
	 * @param g
	 */
	public void drawCellObject(Graphics g){
		g.drawImage(objectImage, cellPosition.getX() , cellPosition.getY(), null);
	}
}
