package catgame.gui;

import java.awt.Color;
import java.awt.Graphics;

import catgame.gameObjects.Boss;
import catgame.gameObjects.Bush;
import catgame.gameObjects.Chest;
import catgame.gameObjects.Door;
import catgame.gameObjects.Fence;
import catgame.gameObjects.GameObject;
import catgame.gameObjects.Hedge;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;
import catgame.gameObjects.Rock;
import catgame.gameObjects.Tree;
import catgame.gamestarting.GameRunner;
import catgame.logic.BoardCell;
import catgame.logic.GameUtil.Direction;
import catgame.logic.Room;

@SuppressWarnings("serial")
public class PanelMinimap extends PanelAbstract {

	/**
	 * Colours of each object
	 */
	private static final Color GRASS = new Color(107, 131, 33);
	private static final Color ROCK = new Color(157, 157, 157);
	private static final Color CHEST = new Color(142, 90, 147);
	private static final Color TREE = new Color(39, 137, 24);
	private static final Color HEDGE = new Color(50, 175, 31);
	private static final Color FENCE = new Color(161, 129, 82);
	private static final Color YOURCAT = new Color(255, 255, 56);
	private static final Color OTHERCATS = new Color(255, 181, 43);
	private static final Color BOSS = new Color(139, 13, 8);
	private static final Color MINION = new Color(139, 13, 8);
	private static final Color DOOR = new Color(211, 121, 24);
	private static final Color BUSH = new Color(50, 175, 31);
	private static final Color NULL = Color.black;

	private PlayableCharacter character;
	private GameRunner runner;

	public PanelMinimap(PlayableCharacter character, GameRunner runner) {
		super();
		this.character = character;
		this.runner = runner;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Room currentRoom = runner.getBoardData().getGameUtil()
				.findPlayersRoom(character.getObjectID());
		int centreX = this.getWidth() / 2;
		int centreY = this.getHeight() / 2;
		int numWidth = currentRoom.getBoardGrid().length;
		int numHeight = currentRoom.getBoardGrid()[0].length;
		int highestNum = Math.max(numWidth, numHeight);
		int sqSize = this.getWidth() / highestNum;
		int startX = centreX - (sqSize * numWidth) / 2;
		int startY = centreY - (sqSize * numHeight) / 2;
		
		Direction dir = runner.getBoardData().getGameUtil().getViewDirection();

		for (int x = 0; x < numWidth; x++) {
			for (int y = 0; y < numHeight; y++) {
				// Coordinates for square
				int xCo = startX + sqSize * y;
				int yCo = startY + sqSize * x;
				// Default colours
				Color base = NULL, outline = NULL, groundCol = NULL, objectCol = NULL;

				// First find colours
				BoardCell cell = currentRoom.getBoardGrid()[x][y];
				switch (dir) {
				case NORTH:
					cell = currentRoom.getBoardGrid()[x][y];
					break;
				case EAST:
					cell = currentRoom.getBoardGrid()[y][numWidth-x-1];
					break;
				case SOUTH:
					cell = currentRoom.getBoardGrid()[numWidth-x-1][numHeight-y-1];
					break;
				case WEST:
					cell = currentRoom.getBoardGrid()[numHeight-y-1][x];
					break;
				default:
					break;
				
				}
				String type = cell.getGroundType();
				if (type != null) {
					base = GRASS;
				}

				GameObject object = cell.getObjectOnCell();
				if (object instanceof Chest) {
					objectCol = CHEST;
				} else if (object instanceof NonPlayableCharacter) {
					NonPlayableCharacter ch = (NonPlayableCharacter) object;
					if (ch instanceof Boss) {
						if (!ch.isDead()) {
							objectCol = BOSS;
						}
					} else {
						if (!ch.isDead()) {
							objectCol = MINION;
						}
					}
				} else if (object instanceof PlayableCharacter) {
					PlayableCharacter ch = (PlayableCharacter) object;
					if (ch == character) {
						objectCol = YOURCAT;
					} else {
						objectCol = OTHERCATS;
					}
				} else if (object instanceof Rock) {
					objectCol = ROCK;
				} else if (object instanceof Tree) {
					objectCol = TREE;
				} else if (object instanceof Fence) {
					objectCol = FENCE;
				} else if (object instanceof Door) {
					Door d = (Door) object;
					if (d.getIsLocked()) {
						objectCol = DOOR;
					} else {
						objectCol = DOOR;
					}
				} else if (object instanceof Bush) {
					objectCol = BUSH;
				} else if (object instanceof Hedge) {
					objectCol = HEDGE;
				}

				// Draw mini-map Base
				g.setColor(base);
				g.fillRect(xCo, yCo, sqSize - 1, sqSize - 1);
				g.setColor(outline);
				g.drawRect(xCo, yCo, sqSize - 1, sqSize - 1);
				// Draw ground type
				g.setColor(groundCol);
				g.fillRect(xCo + 2, yCo + 2, sqSize - 4, sqSize - 4);
				// Draw object on cell
				g.setColor(objectCol);
				g.fillRect(xCo + 4, yCo + 4, sqSize - 8, sqSize - 8);
			}
		}
	}
}
