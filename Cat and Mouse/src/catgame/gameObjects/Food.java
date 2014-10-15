package catgame.gameObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import catgame.gui.renderpanel.PanelRender;
import catgame.logic.Position;

/**
 * 
 * @author Dan Henton
 *
 *GameItem food Used for healing a player
 */
public class Food implements GameItem{
	private final int id;
	private final int heal;
	private GameObject owner;
	private BufferedImage picture;

	public Food(int id , int heal){
		this.id = id;
		this.heal = heal;
		try {
			picture = ImageIO.read(PanelRender.class
					.getResource("/images/Food.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getObjectID() {
		return id;
	}
	public int getHeal() {
		return heal;
	}

	public boolean use() {
		//TODO
		return false;
	}
	
	
	
	public void draw(Graphics g) {
		g.drawImage(picture, 0, 0, null);
	}
	@Override
	public GameObject getOwner() {
		return owner;
	}
	@Override
	public void setOwner(GameObject own) {
		this.owner = own;
	}
	@Override
	public boolean isUsable() {
		return true;
	}
	 
}
