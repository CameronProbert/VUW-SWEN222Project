package catgame.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import catgame.clientserver.Slave;
import catgame.clientserver.SlaveReceiver;
import catgame.gameObjects.PlayableCharacter;
import catgame.gamestarting.NetworkHandler;
import catgame.gamestarting.SinglePlayerHandler;

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
public class FrameLauncher extends FrameAbstract {

	protected int port = 32768;

	/**
	 * Creates and initialises the frame
	 */
	public FrameLauncher() {
		super("Launcher");
		Dimension launcherSize = new Dimension(200, 400);
		this.setSize(launcherSize);
		this.setPreferredSize(launcherSize);
		addButtons();
		this.setVisible(true);
	}

	/**
	 * Adds the needed buttons to the frame
	 */
	private void addButtons() {

		// BoxLayout boxLayout = new BoxLayout(this.getContentPane(),
		// BoxLayout.Y_AXIS);
		// this.setLayout(boxLayout);
		Box box = new Box(BoxLayout.X_AXIS);
		box = Box.createVerticalBox();
		this.add(box);

		Dimension buttonSize = new Dimension(200, 100);

		// Create the buttons
		JButton buttonServer = HelperMethods.createButton("Multiplayer Host",
				buttonSize);
		buttonServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new FrameHost();
			}
		});
		buttonServer.setAlignmentX(CENTER_ALIGNMENT);

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
					
					FrameClient frame = new FrameClient(net, true, slave, 0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonClient.setAlignmentX(CENTER_ALIGNMENT);

		JButton buttonSinglePlayer = HelperMethods.createButton(
				"Single Player", buttonSize);
		buttonSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				singlePlayerPressed();
			}
		});
		buttonSinglePlayer.setAlignmentX(CENTER_ALIGNMENT);

		JButton buttonQuit = HelperMethods.createButton("Quit", buttonSize);
		buttonQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelperMethods.confirmQuit();
			}
		});
		buttonQuit.setAlignmentX(CENTER_ALIGNMENT);

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
	protected void singlePlayerPressed() {
		Dimension frameSize = new Dimension(200, 200);
		Dimension buttonSize = new Dimension(200, 100);
		final JFrame frame = new JFrame("New Game or Load Game?");
		frame.setLayout(null);
		frame.setPreferredSize(frameSize);
		frame.setSize(frameSize);
		JButton newButton = HelperMethods.createButton("New Game",
				buttonSize);
		JButton loadButton = HelperMethods.createButton("Load Game",
				buttonSize);
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startGame(false);
				frame.setVisible(false);
			}
		});
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startGame(true);
				frame.setVisible(false);
			}
		});
		newButton.setLocation(0, 0);
		loadButton.setLocation(0, 100);
		frame.add(newButton);
		frame.add(loadButton);
		frame.pack();
		frame.setVisible(true);
	}
//
//	/**
//	 * Opens a panel that
//	 * @param slaveR
//	 */
//	protected void waitForPlayers(SlaveReceiver slaveR) {
//		Dimension frameSize = new Dimension(200, 100);
//		JFrame frame = new JFrame("Loading");
//		frame.setPreferredSize(frameSize);
//		frame.setSize(frameSize);
//		frame.setLayout(null);
//		JPanel panel = new JPanel() {
//			@Override
//			public void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				g.setColor(Color.red);
//				g.drawString("Waiting for other players,  please wait...", 10,
//						10);
//			}
//		};
//		panel.setPreferredSize(frameSize);
//		panel.setSize(frameSize);
//		panel.setMinimumSize(frameSize);
//		panel.setMinimumSize(frameSize);
//		panel.setLocation(0, 0);
//		panel.setBackground(Color.black);
//
//		frame.add(panel);
//		frame.pack();
//		panel.setVisible(true);
//		frame.setVisible(true);
//		frame.repaint();
//		while (!slaveR.isReady()) {
//			System.out.printf("");
//		}
//		// TODO get rid of loading frame
//		frame.setVisible(false);
//	}

	protected void startGame(boolean loadPlayer) {
		String filename = "no file";
		if (loadPlayer) {
			filename = HelperMethods.chooseCatgameFile(this);
			if (filename == null){
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
