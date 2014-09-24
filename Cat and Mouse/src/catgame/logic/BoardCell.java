package catgame.logic;

import java.awt.Graphics;

import catgame.GameObjects.GameObject;

public class BoardCell {
	Position cellPosition;
	GameObject objectOnCell;
	String groundFile;
	
	public BoardCell(Position position, GameObject object , String groundFile){
		this.cellPosition = position;
		this.objectOnCell = object;
		this.groundFile = groundFile;
	}
	
	public void drawCell(Graphics g){
		g.drawImage(groundFile, cellPosition.getX() , cellPosition.getY())
	}
	
}
