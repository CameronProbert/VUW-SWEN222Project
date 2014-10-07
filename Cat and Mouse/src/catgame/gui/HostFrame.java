package catgame.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import catgame.clientserver.NetworkSetUp;

public class HostFrame extends AbstractFrame {

	private NetworkSetUp host;

	/**
	 * Creates a new Host frame with a given network
	 * 
	 * @param network
	 */
	public HostFrame(NetworkSetUp network) {
		super("Catgame Server");
		this.setLayout(null);
		Dimension windowSize = new Dimension(400, 400);
		this.host = network;
		this.setSize(windowSize);
		this.setPreferredSize(windowSize);
		setupButtons();
		// host.setServer();
		this.setVisible(true);
	}

	/**
	 * Sets up the buttons to be displayed on the frame. Is most likely to be
	 * removed soon.
	 */
	private void setupButtons() {
		JButton startServerButton = HelperMethods.createButton("Start Server",
				new Dimension(200, 100));
		startServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numPlayers = HelperMethods.getNumSliderDialog(
						"Select number of players", 2, 8, 4);
				host.setServer(numPlayers);
			}
		});
		startServerButton.setLocation(100, 150);
		this.add(startServerButton);
	}

	public static void main(String[] args) {
		new HostFrame(null);
	}

}
