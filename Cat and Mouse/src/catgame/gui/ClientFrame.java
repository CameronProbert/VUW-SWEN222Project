package catgame.gui;

import java.awt.event.KeyEvent;

import catgame.clientserver.NetworkHandler;
import catgame.clientserver.Update;

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
		Update up = new Update(0);
		switch (keyID) {
		case KeyEvent.VK_W:
			validAction = network.getGameMain().moveNorth(clientsUID);
			up = new Update(Update.Descriptor.NORTH, clientsUID, 0);
			break;
		case KeyEvent.VK_A:
			validAction = network.getGameMain().moveWest(clientsUID);
			up = new Update(Update.Descriptor.WEST, clientsUID, 0);
			break;
		case KeyEvent.VK_S:
			validAction = network.getGameMain().moveSouth(clientsUID);
			up = new Update(Update.Descriptor.SOUTH, clientsUID, 0);
			break;
		case KeyEvent.VK_D:
			validAction = network.getGameMain().moveEast(clientsUID);
			up = new Update(Update.Descriptor.EAST, clientsUID, 0);
			break;
		case KeyEvent.VK_SPACE:
			int attackTarget = network.getGameMain().attack(clientsUID);
			up = new Update(Update.Descriptor.ATTACK, clientsUID, attackTarget);
			break;
		}
		if (validAction) {
			network.update(up, true);
		}
	}

}
