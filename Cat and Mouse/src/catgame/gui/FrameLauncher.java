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

import catgame.clientserver.NetworkHandler;
import catgame.clientserver.SinglePlayerHandler;
import catgame.clientserver.Slave;
import catgame.gameObjects.PlayableCharacter;

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
		JButton buttonServer = HelperMethods.createButton("Multiplayer Host", buttonSize);
		buttonServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new FrameHost();
			}
		});
		buttonServer.setAlignmentX(CENTER_ALIGNMENT);

		JButton buttonClient = HelperMethods.createButton("Multiplayer Client", buttonSize);
		buttonClient.addActionListener(new ActionListener() {
			// Action performed on clicking button
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				String url = HelperMethods.stringInputDialog("Enter URL", "");
				try {
					Socket s = new Socket(url, port );
					Slave slave = new Slave(s);
					NetworkHandler net = new NetworkHandler(NetworkHandler.Type.CLIENT);
					FrameClient frame = new FrameClient(net, true, slave);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonClient.setAlignmentX(CENTER_ALIGNMENT);

		JButton buttonSinglePlayer = HelperMethods.createButton("Single Player", buttonSize);
		buttonSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				// TODO make a chooser, choose new game or load game
				// TODO if (new game) ----> pass file to SinglPlayerHandler
				// TODO else if (load game) ----> make them choose a file, pass the string of that file to SinglPlayerHandler
				
				String file = "no file";
				new SinglePlayerHandler(file);
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
		// this.pack();
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
