package catgame.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import catgame.clientserver.Slave;
import catgame.gamestarting.NetworkHandler;
import catgame.gamestarting.SinglePlayerHandler;
import catgame.gui.renderpanel.PanelRender;

/**
 * The LauncherFrame will be the first instance of the program that the users
 * will see. It will ask them what part of the game they would like to run. The
 * options they can choose are a host or client for multiplayer, a singleplayer
 * instance or they can close the program. Once they have selected their choice
 * this frame will disappear.
 * 
 * @author Cameron Probert
 * 
 */
@SuppressWarnings("serial")
public class FrameLauncher extends FrameAbstract {

	protected static final int BUTTONWIDTH = 200;
	private static final int BUTTONHEIGHT = 75;
	protected int port = 32768;
	private JPanel bgPanel;
	protected BufferedImage backGround;
	private Box box;

	/**
	 * Creates and initialises the frame
	 */
	public FrameLauncher() {
		super("Cat and Mouse");
		try {
			backGround = ImageIO.read(PanelRender.class
					.getResource("/images/CatBG.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Dimension launcherSize = new Dimension(650, 400);
		this.setLayout(null);
		this.setSize(launcherSize);
		this.setPreferredSize(launcherSize);
		this.addButtons();
		this.createBG();
		this.setVisible(true);
	}

	private void createBG() {
		bgPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backGround, 0, 0, getWidth(), getHeight(), null);
			}
		};
		Dimension panelSize = new Dimension(this.getWidth() - BUTTONWIDTH,
				this.getHeight());
		bgPanel.setSize(panelSize);
		bgPanel.setPreferredSize(panelSize);
		this.add(bgPanel);
	}

	/**
	 * Adds the needed buttons to the frame
	 */
	private void addButtons() {

		// BoxLayout boxLayout = new BoxLayout(this.getContentPane(),
		// BoxLayout.Y_AXIS);
		// this.setLayout(boxLayout);
		box = new Box(BoxLayout.X_AXIS);
		box = Box.createVerticalBox();
		box.setLocation(this.getWidth() - BUTTONWIDTH,
				(this.getHeight() - 4 * BUTTONHEIGHT) / 2);
		Dimension boxSize = new Dimension(BUTTONWIDTH, 4 * BUTTONHEIGHT);
		box.setPreferredSize(boxSize);
		box.setSize(boxSize);
		this.add(box);

		Dimension buttonSize = new Dimension(BUTTONWIDTH, BUTTONHEIGHT);

		// Create the buttons
		JButton buttonServer = HelperMethods.createButton("Multiplayer Host",
				buttonSize);
		buttonServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new FrameHost();
			}
		});
		buttonServer.setAlignmentX(RIGHT_ALIGNMENT);

		JButton buttonClient = HelperMethods.createButton("Multiplayer Client",
				buttonSize);
		buttonClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				String url = HelperMethods.stringInputDialog("Enter URL",
						"localhost");
				try {
					Socket s = new Socket(url, port);
					Slave slave = new Slave(s);
					NetworkHandler net = new NetworkHandler(
							NetworkHandler.Type.CLIENT);

					new FrameClient(net, true, slave, 0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonClient.setAlignmentX(RIGHT_ALIGNMENT);

		JButton buttonSinglePlayer = HelperMethods.createButton(
				"Single Player", buttonSize);
		buttonSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//setVisible(false);
				singlePlayerPressed();
			}
		});
		buttonSinglePlayer.setAlignmentX(RIGHT_ALIGNMENT);

		JButton buttonQuit = HelperMethods.createButton("Quit", buttonSize);
		buttonQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelperMethods.confirmQuit();
			}
		});
		buttonQuit.setAlignmentX(RIGHT_ALIGNMENT);

		// Add the buttons
		box.add(buttonServer);
		box.add(buttonClient);
		box.add(buttonSinglePlayer);
		box.add(buttonQuit);
		this.pack();
	}

	/**
	 * Called when the single player button is pressed in the launcher
	 */
	public void singlePlayerPressed() {
		this.remove(box);
		this.createBG();
		box = new Box(BoxLayout.X_AXIS);
		box = Box.createVerticalBox();
		box.setLocation(this.getWidth() - BUTTONWIDTH,
				(this.getHeight() - 2 * BUTTONHEIGHT) / 2);
		Dimension boxSize = new Dimension(BUTTONWIDTH, 2 * BUTTONHEIGHT);
		box.setPreferredSize(boxSize);
		box.setSize(boxSize);
		Dimension buttonSize = new Dimension(BUTTONWIDTH, BUTTONHEIGHT);
		JButton newButton = HelperMethods.createButton("New Game", buttonSize);
		JButton loadButton = HelperMethods
				.createButton("Load Game", buttonSize);
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startGame(false);
				hideFrame();
			}
		});
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startGame(true);
				hideFrame();
			}
		});
		newButton.setLocation(0, 0);
		loadButton.setLocation(0, 100);
		box.add(newButton);
		box.add(loadButton);
		box.setVisible(true);
		this.add(box);
		this.revalidate();
		this.repaint();
	}
	
	protected void startGame(boolean loadPlayer) {
		String filename = "no file";
		if (loadPlayer) {
			filename = HelperMethods.chooseCatgameFile(this);
			if (filename == null) {
				System.exit(0);
			}
		}
		new SinglePlayerHandler(filename);
	}

	// ================== Main Method ======================

	/**
	 * Creates an instance of the launcher
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new FrameLauncher();
	}
}
