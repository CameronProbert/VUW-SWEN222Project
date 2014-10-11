package catgame.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import catgame.clientserver.ServerNewGame;
import catgame.clientserver.ServerOldGame;
import catgame.gui.renderpanel.RenderPanel;

public class HostFrame extends AbstractFrame {

	private JButton startServerButton;
	private JButton loadServerButton;
	private JPanel bgPanel;
	protected BufferedImage backGround;

	/**
	 * Creates a new Host frame
	 * 
	 * @param network
	 */
	public HostFrame() {
		super("Catgame Server");
		try {
			backGround = ImageIO.read(RenderPanel.class
					.getResource("/images/Tree1.png")); // TODO Cat background
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setLayout(null);
		Dimension windowSize = new Dimension(400, 400);
		this.setSize(windowSize);
		this.setPreferredSize(windowSize);
		setupButtons();
		setupBG();
		// host.setServer();
		this.setVisible(true);
	}

	private void setupBG() {
		bgPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(backGround, 0, 0, getWidth(), getHeight(), null);
			}
		};
		Dimension panelSize = new Dimension(this.getWidth(), this.getHeight());
		bgPanel.setSize(panelSize);
		bgPanel.setPreferredSize(panelSize);
		this.add(bgPanel);
	}

	/**
	 * Sets up the buttons to be displayed on the frame. Is most likely to be
	 * removed soon.
	 */
	private void setupButtons() {
		startServerButton = HelperMethods.createButton("New Game",
				new Dimension(200, 100));
		startServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numPlayers = HelperMethods.getNumSliderDialog(
						"Select number of players", 2, 4, 2);
				ServerNewGame host = new ServerNewGame();
				host.setServer(numPlayers);
				hideButtons();
			}
		});
		loadServerButton = HelperMethods.createButton("Load Game",
				new Dimension(200, 100));
		loadServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = getFileName();
				ServerOldGame host = new ServerOldGame();
				host.setServer(fileName);
				hideButtons();
			}
		});
		startServerButton.setLocation(100, 150);
		loadServerButton.setLocation(100, 250);
		this.add(startServerButton);
		this.add(loadServerButton);
	}
	
	protected void hideButtons(){
		startServerButton.setVisible(false);
		loadServerButton.setVisible(false);
	}

	protected String getFileName() {
		return HelperMethods.openFile(".catgame (Saved game)", this, "catgame");
	}

	public static void main(String[] args) {
		new HostFrame();
	}

}
