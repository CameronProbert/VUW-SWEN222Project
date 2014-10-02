package catgame.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import catgame.GameObjects.*;
import catgame.clientserver.*;
import catgame.gui.renderpanel.*;
import catgame.logic.*;

/**
 * The ClientFrame is the multi-player version of the game. It contains the
 * render window and gui panels to overlay the render window. It also has a
 * networkhandler that it sends updates to.
 * 
 * @author Cameron Probert
 * 
 */
public class ClientFrame extends AbstractFrame implements KeyListener {

	private static final double FRAMEHEIGHTMODIFIER = 600.0 / 768;
	private static final double ASPECTRATIO = 2;

	private GameRunner runner;
	private int clientsUID;
	private RenderPanel renderPanel;
	private boolean isClient;
	private StatPanel statPanel;
	private Dimension windowSize;

	public ClientFrame(GameRunner network, int UID, boolean isClient,
			PlayableCharacter character) {
		super("Cat and Mouse");
		this.setDimensions();
		this.setLayout(null);
		this.addKeyListener(this);
		this.addPanels(character);
		this.clientsUID = UID;
		this.runner = network;
		this.isClient = isClient;
		this.setup();
	}

	private void setDimensions() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize == null) {
			System.out.println("screenSize is null");
		}
		System.out.println(screenSize.getHeight());
		int windowHeight = (int) (screenSize.getHeight() * FRAMEHEIGHTMODIFIER);
		int windowWidth = (int) (windowHeight * ASPECTRATIO);
		System.out.printf("Width: %d | Height: %d", windowWidth, windowHeight);
		windowSize = new Dimension(windowWidth, windowHeight);
		this.setSize(windowSize);
		this.setPreferredSize(windowSize);
	}

	private void addPanels(PlayableCharacter character) {
		renderPanel = new RenderPanel(windowSize, this);

		// Create dimensions
		int panelHeight = (int) (1.0 / 2 * windowSize.getHeight());
		int statPanelWidth = (int) (1.0 / 6 * windowSize.getWidth());
		int invPanelWidth = (int) (1.0 / 24 * windowSize.getWidth());

		Dimension statPanelDim = new Dimension(statPanelWidth, panelHeight);
		Dimension invPanelDim = new Dimension(invPanelWidth, panelHeight);

		// Create locations
		int invLocationX = (int) (1.0 / 60 * windowSize.getWidth());
		int invPanelLocationY = (int) (3.0 / 8 * windowSize.getHeight());
		int statLocationX = (int) (49.0 / 60 * windowSize.getWidth());
		int statPanelLocationY = (int) (7.0 / 16 * windowSize.getHeight());

		InventoryPanel invPanel = new InventoryPanel(character, invPanelDim);
		invPanel.setLocation(invLocationX, invPanelLocationY);
		invPanel.setSize(invPanelDim);
		invPanel.setPreferredSize(invPanelDim);

		statPanel = new StatPanel(character);
		statPanel.setLocation(statLocationX, statPanelLocationY);
		statPanel.setSize(statPanelDim);
		statPanel.setPreferredSize(statPanelDim);

		this.add(invPanel);
		this.add(statPanel);
		this.add(renderPanel);
	}

	// @Override
	// public void redraw(){
	//
	// }

	/**
	 * NOTE: may need to check when moving if moved to another room!! TODO
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		int keyID = key.getKeyCode();
		int validAction = 0;
		Update up = new Update(0);
		switch (keyID) {
		case KeyEvent.VK_W:
			validAction = runner.getGameUtill().moveNorth(clientsUID);
			up = new Update(Update.Descriptor.NORTH, clientsUID, 0);
			break;
		case KeyEvent.VK_A:
			validAction = runner.getGameUtill().moveWest(clientsUID);
			up = new Update(Update.Descriptor.WEST, clientsUID, 0);
			break;
		case KeyEvent.VK_S:
			validAction = runner.getGameUtill().moveSouth(clientsUID);
			up = new Update(Update.Descriptor.SOUTH, clientsUID, 0);
			break;
		case KeyEvent.VK_D:
			validAction = runner.getGameUtill().moveEast(clientsUID);
			up = new Update(Update.Descriptor.EAST, clientsUID, 0);
			break;
		case KeyEvent.VK_SPACE:
			int attacked = runner.getGameUtill().attack(clientsUID);
			if (attacked > 0) {
				validAction = 1;
				up = new Update(Update.Descriptor.ATTACK, clientsUID, attacked);
			}
			break;
		case KeyEvent.VK_E: // open a chest
			Chest chest = runner.getGameUtill().getChest(clientsUID);
			if (chest != null) {
				validAction = 1;
				// TODO open dialog box to choose items out of chest (using the
				// chest object)
				// int objectID = openChest(chest); // return the id of the item
				// the character selected
				// up = new Update(Update.Descriptor.PICKUP, clientsUID,
				// objectID);
			}
			break;
		case KeyEvent.VK_M:
			statPanel.modifyChar();
			break;
		}
		if (validAction > 0 && isClient) {
			NetworkHandler net = (NetworkHandler) runner;
			net.update(up, true);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		ArrayList<GameItem> items = new ArrayList<GameItem>();
		items.add(new Food(2, 30));
		items.add(new Key(3));
		items.add(new Food(2, 30));

		PlayableCharacter character = new PlayableCharacter(1, null, " ", 3, 5,
				items);
		new ClientFrame(null, 0, false, character);
	}

}
