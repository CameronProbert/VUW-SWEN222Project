package catgame.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import catgame.clientserver.NetworkSetUp;

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
public class LauncherFrame extends AbstractFrame {

	/**
	 * Creates and initialises the frame
	 */
	public LauncherFrame() {
		super(new Dimension(200, 400), "Launcher");
		initialiseBehaviour();
		addButtons();
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

	/**
	 * Adds the needed buttons to the frame
	 */
	private void addButtons() {

		BoxLayout boxLayout = new BoxLayout(this.getContentPane(),
				BoxLayout.Y_AXIS);
		this.setLayout(boxLayout);
		Dimension buttonSize = new Dimension(200, 100);

		// Create the buttons
		JButton buttonServer = createButton("Multiplayer Host", buttonSize);
		buttonServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new HostFrame();
			}
		});

		JButton buttonClient = createButton("Multiplayer Client", buttonSize);
		buttonClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				NetworkSetUp network = new NetworkSetUp();
				String url = "";
				network.setClient(url);
			}
		});

		JButton buttonSinglePlayer = createButton("Single Player", buttonSize);
		buttonSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				NetworkSetUp network = new NetworkSetUp();
				network.setSinglePlayer();
			}
		});

		JButton buttonQuit = createButton("Quit", buttonSize);
		buttonQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmQuit();
			}
		});

		// Add the buttons
		add(buttonServer);
		add(buttonClient);
		add(buttonSinglePlayer);
		add(buttonQuit);
	}

	// ================= Helper Methods ====================

	/**
	 * Creates and returns a button
	 * 
	 * @param text
	 *            The text to be on the button
	 * @param size
	 *            The size of the button
	 * @return
	 */
	public static JButton createButton(String text, Dimension size) {
		JButton button = new JButton();
		button.setPreferredSize(size);
		button.setText(text);
		button.setAlignmentX(CENTER_ALIGNMENT);
		return button;
	}

	/**
	 * Creates and returns a button
	 * 
	 * @param text
	 *            The text to be on the button
	 * @param size
	 *            The size of the button
	 * @param origin
	 *            Where to draw the button
	 * @return
	 */
	public static JButton createButton(String text, Dimension size, Point origin) {
		JButton button = new JButton();
		button.setLocation(origin);
		button.setSize(size);
		button.setText(text);
		button.setAlignmentX(CENTER_ALIGNMENT);
		return button;
	}

	/**
	 * Creates a dialog asking if the user would like to quit the program
	 */
	public static void confirmQuit() {
		if (confirmationDialog("Confirm quit", "Are you sure you want to quit?")) {
			System.exit(0);
		}
	}

	/**
	 * Creates a dialog asking the user for confirmation
	 * 
	 * @param title
	 *            The title of the dialog box
	 * @param message
	 *            The message of the dialog box
	 * @return A boolean of the user's choice
	 */
	public static boolean confirmationDialog(String title, String message) {
		return JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
				message, title, JOptionPane.OK_CANCEL_OPTION);

	}

	// ================== Main Method ======================

	/**
	 * Creates an instance of the launcher
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new LauncherFrame();
	}
}
