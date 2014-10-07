package catgame.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import catgame.clientserver.*;
import catgame.gameObjects.*;
import catgame.gui.renderpanel.*;
import catgame.logic.*;
import catgame.logic.GameUtil.Direction;

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
	private SlaveReceiver slaveR;
	private Slave slave;

	/**
	 * Creates a new Client frame.
	 * @param network
	 * @param UID
	 * @param isClient
	 * @param character
	 */
	public ClientFrame(GameRunner network, int UID, boolean isClient,
			PlayableCharacter character, Slave slave) {
		super("Cat and Mouse");
		this.setDimensions();
		this.setLayout(null);
		this.addKeyListener(this);
		this.addPanels(character);
		this.addMenus();
		this.clientsUID = UID;
		this.runner = network;
		this.isClient = isClient;
		if(slave!=null){

			this.slaveR = new SlaveReceiver(slave, runner);
			slaveR.run();
		}
		this.slave = slave;
		this.setVisible(true);
	}

	/**
	 * Creates and adds menu items to the frame
	 */
	private void addMenus() {
		JMenuBar gameMenu = new JMenuBar();

		JMenu menuFile = new JMenu("File");
		gameMenu.add(menuFile);

		JMenu menuHelp = new JMenu("Help");
		gameMenu.add(menuHelp);

		addFileItems(menuFile);
		addHelpItems(menuHelp);

		this.setJMenuBar(gameMenu);
	}

	/**
	 * Creates the Help Menu items to be displayed in the given menu
	 * 
	 * @param menu
	 */
	private void addHelpItems(JMenu menu) {
		// Creates the load menu item
		JMenuItem optionControls = new JMenuItem("Controls");
		optionControls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayControls();
			}
		});

		// Creates the load menu item
		JMenuItem optionAbout = new JMenuItem("About");
		optionAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayAbout();
			}
		});
		menu.add(optionControls);
		menu.add(optionAbout);
	}

	/**
	 * Creates the File Menu items to be displayed in the given menu
	 * 
	 * @param menu
	 */
	private void addFileItems(JMenu menu) {
		// Creates the load menu item
		JMenuItem optionLoad = new JMenuItem("Load");
		optionLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO load();
			}
		});

		// Creates the save menu item
		JMenuItem optionSave = new JMenuItem("Save");
		optionSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO save();
			}
		});

		// Creates the quit menu item
		JMenuItem optionQuit = new JMenuItem("Quit");
		optionQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelperMethods.confirmQuit();
			}
		});

		menu.add(optionLoad);
		menu.add(optionSave);
		menu.add(optionQuit);
	}

	/**
	 * Reads in the Controls.txt file and displays it on a message pane
	 */
	protected void displayControls() {
		try {
			InputStream in = this.getClass().getResourceAsStream(
					"textfiles/Controls.txt");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String controlText = reader.readLine();
			while (reader.ready()) {
				controlText += "\n" + reader.readLine();
			}
			HelperMethods.textDialog("Controls", controlText);
		} catch (IOException e) {
			System.out.println("Could not read Controls.txt");
			e.printStackTrace();
		}
	}

	/**
	 * Reads in the About.txt file and displays it on a message pane
	 */
	protected void displayAbout() {
		try {
			InputStream in = getClass().getResourceAsStream(
					"textfiles/About.txt");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String controlText = reader.readLine();
			while (reader.ready()) {
				controlText += "\n" + reader.readLine();
			}
			HelperMethods.textDialog("About", controlText);
		} catch (IOException e) {
			System.out.println("Could not read Controls.txt");
			e.printStackTrace();
		}
	}

	/**
	 * Gets the dimensions of the screen and creates a dimension for the frame.
	 * Sets the frame to this size.
	 */
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

	/**
	 * Adds all the panels to the frame
	 * @param character
	 */
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
			validAction = runner.getGameUtill().moveUp(clientsUID);
			up = new Update(Update.Descriptor.NORTH, clientsUID, 0);
			break;
		case KeyEvent.VK_A:
			validAction = runner.getGameUtill().moveLeft(clientsUID);
			up = new Update(Update.Descriptor.WEST, clientsUID, 0);
			break;
		case KeyEvent.VK_S:
			validAction = runner.getGameUtill().moveDown(clientsUID);
			up = new Update(Update.Descriptor.SOUTH, clientsUID, 0);
			break;
		case KeyEvent.VK_D:
			validAction = runner.getGameUtill().moveRight(clientsUID);
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
			slave.sendUpdate(up);
		}
	}

	/**
	 * Unneeded for the game
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Unneeded for the game
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Testing main method
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<GameItem> items = new ArrayList<GameItem>();
		items.add(new Food(2, 30));
		items.add(new Key(3));
		items.add(new Food(2, 30));

		PlayableCharacter character = new PlayableCharacter(1, 10, null, Direction.NORTH, 3, 5,
				items);
		new ClientFrame(null, 0, false, character, null);
	}

}
