package catgame.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends AbstractFrame {
	
	public GameFrame(){
		super(new Dimension(1200, 600), "Catgame");
		initialiseBehaviour();
	}
	
	/**
	 * Sets behaviour for the frame
	 */
	protected void initialiseBehaviour() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		// this.setBackground(new Color(50,70,255));
	}
	
}
