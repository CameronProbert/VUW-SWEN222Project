package catgame.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import catgame.clientserver.*;
import catgame.datastorage.SavingMain;
import catgame.gameObjects.*;
import catgame.gamestarting.GameRunner;
import catgame.gui.renderpanel.*;
import catgame.logic.*;

/**
 * The ClientFrame is the main game window. It contains the render window and
 * gui panels to overlay the render window. It also has a networkhandler that it
 * sends updates to.
 * 
 * @author Cameron Probert
 * 
 */
@SuppressWarnings("serial")
public class FrameClient extends FrameAbstract implements KeyListener {

	private static final int FRAMEWIDTH = 1300;
	private static final int FRAMEHEIGHT = 700;

	private GameRunner runner;
	private int clientsUID;
	private PanelRender renderPanel;
	private boolean isClient;
	private PanelStat statPanel;
	private Dimension windowSize;
	private SlaveReceiver slaveR;
	private Slave slave;
	private PlayableCharacter character;
	private PanelInventory invPanel;
	private String state = "running";
	private JPanel panelBG;
	private Image imageBG;

	/**
	 * Creates a new Client frame.
	 * 
	 * @param network
	 * @param UID
	 * @param isClient
	 * @param character
	 */
	public FrameClient(GameRunner network, boolean isClient, Slave slave, int ID) {
		super("Cat and Mouse");
		this.setDimensions();
		this.setLayout(null);
		this.addKeyListener(this);
		this.addMenus();
		this.runner = network;
		this.isClient = isClient;
		this.clientsUID = ID;
		this.slave = slave;
		if (!isClient) {
			character = runner.getBoardData().getObjStorer()
					.findCharacter(clientsUID);
			this.addPanels(character);
		} else {
			SlaveReceiver slaveR = new SlaveReceiver(slave, runner, this);
			slaveR.run();
			// waitForPlayers(slaveR);
			try {
				imageBG = ImageIO.read(PanelRender.class
						.getResource("/images/CatBGLoading.png"));
				panelBG = new JPanel() {
					@Override
					public void paintComponent(Graphics g) {
						g.drawImage(imageBG, 0, 0, panelBG.getWidth(),
								panelBG.getHeight(), null);
					}
				};
				panelBG.setSize(windowSize);
				panelBG.setPreferredSize(windowSize);
				panelBG.setLocation(0, 0);
				add(panelBG);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Starts the client if it is in a multiplayer game. This should be called
	 * only after everyone has loaded in
	 * 
	 * @param uid
	 */
	public void startMyClient(int uid) {
		clientsUID = uid;
		character = runner.getBoardData().getObjStorer()
				.findCharacter(clientsUID);
		System.out.println("my uid is : " + clientsUID);
		if (character == null)
			System.out.println("Charcter is null when in client frame");
		this.addPanels(character);
		if (panelBG != null) {
			panelBG.setVisible(false);
		}
		this.pack();
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
		// Creates the save menu item
		JMenuItem optionSave = new JMenuItem("Save");
		optionSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		// Creates the quit menu item
		JMenuItem optionQuit = new JMenuItem("Quit");
		optionQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelperMethods.confirmQuit();
			}
		});

		menu.add(optionSave);
		menu.add(optionQuit);
	}

	/**
	 * Saves the current game
	 */
	private void save() {
		JFileChooser chooser = new JFileChooser();
		int state = chooser.showSaveDialog(null);
		if (state == JFileChooser.APPROVE_OPTION) {
			String filename = chooser.getSelectedFile().getAbsolutePath();
			File file = new File(filename);
			setState("paused");
			this.invPanel.setPanelsState("paused");
			if (slave != null)
				slave.sendUpdate(Update.pauseUpdate);
			try {
				new SavingMain(runner.getBoardData(), file);
				if (slave != null)
					slave.sendUpdate(Update.unPauseUpdate);
				setState("running");
				this.invPanel.setPanelsState("running");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setState(String state) {
		this.state = state;
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
		windowSize = new Dimension(FRAMEWIDTH, FRAMEHEIGHT);
		this.setSize(windowSize);
		this.setPreferredSize(windowSize);
	}

	/**
	 * Adds all the panels to the frame
	 * 
	 * @param character
	 */
	private void addPanels(PlayableCharacter character) {

		// Create dimensions
		int statPanelWidth = 200;
		int statPanelHeight = 300;
		int invPanelWidth = 65;
		int invPanelHeight = 390;

		Dimension statPanelDim = new Dimension(statPanelWidth, statPanelHeight);
		Dimension invPanelDim = new Dimension(invPanelWidth, invPanelHeight);

		// Create locations
		int margin = (int) (1.0 / 60 * windowSize.getWidth());
		int invLocationX = margin;
		int invPanelLocationY = (int) (windowSize.getHeight() - invPanelHeight - margin * 2);
		int statLocationX = (int) (windowSize.getWidth() - statPanelWidth - margin);
		int statPanelLocationY = (int) (windowSize.getHeight()
				- statPanelHeight - margin * 2);

		invPanel = new PanelInventory(character, invPanelDim, this);
		invPanel.setLocation(invLocationX, invPanelLocationY);
		invPanel.setSize(invPanelDim);
		invPanel.setPreferredSize(invPanelDim);

		statPanel = new PanelStat(character);
		statPanel.setLocation(statLocationX, statPanelLocationY);
		statPanel.setSize(statPanelDim);
		statPanel.setPreferredSize(statPanelDim);
		statPanel.addMinimap(runner);

		this.add(invPanel);
		this.add(statPanel);
		initialiseRenderPanel();
	}

	/**
	 * Creates a render panel that draws the current room, and adds it to the
	 * frame. First removes the old render panel if it exists.
	 */
	public void initialiseRenderPanel() {
		if (renderPanel != null) {
			this.remove(renderPanel);
		}
		Room currentRoom = runner.getBoardData().getGameUtil()
				.findPlayersRoom(clientsUID);
		renderPanel = new PanelRender(windowSize, runner.getBoardData()
				.getGameUtil(), currentRoom);
		this.add(renderPanel);
	}

	/**
	 * NOTE: may need to check when moving if moved to another room!! TODO
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		if (slaveR != null && !slaveR.isReady())
			return;
		if (this.state.equals("paused"))
			return;

		int keyID = key.getKeyCode();
		int validAction = 0;
		// Starts with a blank update and then changes what the update
		// is if an action occurs.
		Update up = Update.noUpdate;
		switch (keyID) {
		// WASD are all move directions
		case KeyEvent.VK_W:
			validAction = runner.getBoardData().getGameUtil()
					.moveUp(clientsUID);
			up = new Update(Update.Descriptor.NORTH, clientsUID, 0);
			break;
		case KeyEvent.VK_A:
			validAction = runner.getBoardData().getGameUtil()
					.moveLeft(clientsUID);
			up = new Update(Update.Descriptor.WEST, clientsUID, 0);
			break;
		case KeyEvent.VK_S:
			validAction = runner.getBoardData().getGameUtil()
					.moveDown(clientsUID);
			up = new Update(Update.Descriptor.SOUTH, clientsUID, 0);
			break;
		case KeyEvent.VK_D:
			validAction = runner.getBoardData().getGameUtil()
					.moveRight(clientsUID);
			up = new Update(Update.Descriptor.EAST, clientsUID, 0);
			break;
		case KeyEvent.VK_SPACE:
			// Attack pressed
			int attacked = runner.getBoardData().getGameUtil()
					.action(clientsUID);
			if (attacked > 0) {
				validAction = 1;
				up = new Update(Update.Descriptor.ATTACK, clientsUID, attacked);
			}
			break;
		case KeyEvent.VK_LEFT:
			runner.getBoardData().getGameUtil().lookLeft();
			break;
		case KeyEvent.VK_RIGHT:
			runner.getBoardData().getGameUtil().lookRight();
			break;
		case KeyEvent.VK_E:
			// Interact with the object in front of you
			// First, get the object ahead of us:
			GameObject object = runner.getBoardData().getGameUtil()
					.getObjectAhead(clientsUID);
			// If it is not null then...
			if (object != null) {
				// Check what it is and react accordingly
				if (object instanceof Chest) {
					// Chest, so ask what item the user wants to take
					Chest chest = (Chest) object;
					GameItem item = HelperMethods.showRadioList(
							"What item do you want to take?", chest.getLoot(),
							true);
					if (item != null) {
						runner.getBoardData()
								.getGameUtil()
								.addObjectToInventory(clientsUID,
										item.getObjectID());
						up = new Update(Update.Descriptor.PICKUP, clientsUID,
								item.getObjectID());
						validAction = 1;
					}
				} else if (object instanceof NonPlayableCharacter) {
					// NPC, so if it is dead then ask what item the user wants
					// to take, otherwise display inspect string
					NonPlayableCharacter ch = (NonPlayableCharacter) object;
					if (ch.isDead()) {
						GameItem item = HelperMethods.showRadioList(
								"What item do you want to take?",
								ch.getInventory(), true);
						if (item != null) {
							runner.getBoardData()
									.getGameUtil()
									.addObjectToInventory(clientsUID,
											item.getObjectID());
							up = new Update(Update.Descriptor.PICKUP,
									clientsUID, item.getObjectID());
							validAction = 1;
						}
					} else {
						HelperMethods
								.textDialog("",
										"The rat is still alive! (Space bar to attack)");
					}
				}
				// The rest of the cases just display an information string
				else if (object instanceof PlayableCharacter) {
					HelperMethods.textDialog("",
							"Another cat to help you find your kitten!");
				} else if (object instanceof Rock) {
					HelperMethods.textDialog("", "It is a rock");
				} else if (object instanceof Tree) {
					HelperMethods.textDialog("", "It is a tree");
				} else if (object instanceof Fence) {
					HelperMethods.textDialog("", "It is a fence");
				} else if (object instanceof Door) {
					Door d = (Door) object;
					if (d.getIsLocked()) {
						HelperMethods
								.textDialog("",
										"It is a locked door, walk through it with a key to unlock it!");
					} else {
						HelperMethods.textDialog("", "It is an open door");
					}
				} else if (object instanceof Bush) {
					HelperMethods.textDialog("", "It is a bush");
				} else if (object instanceof Hedge) {
					HelperMethods.textDialog("", "It is a hedge");
				}
			} else {
				// If there was nothing ahead of you
				HelperMethods.textDialog("", "There is nothing ahead of you");
			}
			break;
		case KeyEvent.VK_M:
			// Increase HP Cheat
			statPanel.increaseHP();
			break;
		}
		if (validAction > 0 && isClient) {
			// Send the update
			slave.sendUpdate(up);
		}
		// Ensure the inventory panel's items are correct
		invPanel.resetInvItems();
		repaint();
	}

	/**
	 * Uses the given item
	 * 
	 * @param item
	 */
	public void itemUsed(GameItem item) {
		int validAction = 0;
		Update up = Update.noUpdate;
		validAction = runner.getBoardData().getGameUtil()
				.useItem(clientsUID, item.getObjectID());
		up = new Update(Update.Descriptor.CONSUME, clientsUID,
				item.getObjectID());
		if (validAction > 0 && isClient) {
			slave.sendUpdate(up);
		}
		System.out.println("Valid Action = " + validAction);
		if (validAction > 0) {
			invPanel.resetInvItems();
		}
	}

	/**
	 * Unneeded for the game
	 */
	@Override
	public void keyReleased(KeyEvent key) {
	}

	/**
	 * Unneeded for the game
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
