package catgame.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import catgame.gameObjects.PlayableCharacter;
import catgame.gamestarting.GameRunner;
import catgame.logic.Room;

public class PanelMinimap extends PanelAbstract {

	private PlayableCharacter character;
	private GameRunner runner;

	public PanelMinimap(PlayableCharacter character, GameRunner runner) {
		super();
		this.character = character;
		this.runner = runner;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Room currentRoom = runner.getBoardData().getGameUtil().findPlayersRoom(character.getObjectID());
		int centreX = this.getWidth()/2;
		int centreY = this.getHeight()/2;
		int numWidth = currentRoom.getBoardGrid().length;
		int numHeight = currentRoom.getBoardGrid()[0].length;
		int highestNum = Math.max(numWidth, numHeight);
		int sqSize = this.getWidth()/highestNum;
		System.out.println("Redrawing minimap");
		g.setColor(Color.red);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
	}

}
