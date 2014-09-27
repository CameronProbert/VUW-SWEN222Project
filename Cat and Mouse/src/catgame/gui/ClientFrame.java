package catgame.gui;

import java.awt.event.KeyEvent;

import catgame.clientserver.NetworkHandler;

/**
 * The ClientFrame is the multi-player version of the game. It contains the
 * render window and gui panels to overlay the render window. It also has a
 * networkhandler that it sends updates to.
 * 
 * @author Cameron Probert
 * 
 */
public class ClientFrame extends SinglePlayerFrame {

	private NetworkHandler network;

	public ClientFrame(NetworkHandler network, int UID) {
		super(UID);
		this.network = network;
	}

	/**
	 * keyAction handles key presses in the game and also sends an update to the
	 * network of the key press
	 */
	@Override
	protected void keyAction(KeyEvent key) {
		int keyID = key.getID();
		boolean validAction = false;
		switch (keyID){
		case KeyEvent.VK_W:
			validAction = network.getGameMain().moveNorth(clientsUID);
			break;
		case KeyEvent.VK_A:
			validAction = network.getGameMain().moveWest(clientsUID);
			break;
		case KeyEvent.VK_S:
			validAction = network.getGameMain().moveSouth(clientsUID);
			break;
		case KeyEvent.VK_D:
			validAction = network.getGameMain().moveEast(clientsUID);
			break;
		case KeyEvent.VK_SPACE:
			validAction = network.getGameMain().attack(clientsUID);
			break;
		}
	}

}
