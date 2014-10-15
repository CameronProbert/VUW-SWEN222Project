package catgame.gui;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public abstract class FrameAbstract extends JFrame {
	
	public FrameAbstract(String windowTitle) {
		super();
		setup();
		this.setTitle(windowTitle);
	}
	
	protected void setup(){
		this.setLocation(new Point(50, 50));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	protected void setPreferredSize(int x, int y){
		this.setPreferredSize(new Dimension(x, y));
	}
	
	protected void hideFrame(){
		this.setVisible(false);
	}

}
