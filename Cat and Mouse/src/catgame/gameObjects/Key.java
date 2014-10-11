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
 */
public class Key implements GameItem{	
	private final int id;
	private GameObject owner;
	private BufferedImage picture;

	public Key(int id){
		this.id = id;
		try {
			picture = ImageIO.read(PanelRender.class
					.getResource("/images/key.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getObjectID() {
		return id;
	}

	public boolean use() {
		// TODO Auto-generated method stub
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
		return false;
	}

}
