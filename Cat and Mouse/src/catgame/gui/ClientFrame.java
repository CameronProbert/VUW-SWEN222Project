package catgame.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import catgame.clientserver.NetworkHandler;
import catgame.clientserver.Update;
import catgame.gui.renderpanel.RenderPanel;

/**
 * The ClientFrame is the multi-player version of the game. It contains the
 * render window and gui panels to overlay the render window. It also has a
 * networkhandler that it sends updates to.
 * 
 * @author Cameron Probert
 * 
 */
public class ClientFrame extends AbstractFrame implements KeyListener {

	private NetworkHandler network;
	private int clientsUID;
	private RenderPanel renderPanel;

	public ClientFrame(NetworkHandler network, int UID) {
		super(new Dimension(1200, 600), "Cat and Mouse");
		System.out.println("ClientFrame constructor");
		this.addPanels();
		this.clientsUID = UID;
		this.network = network;
	}

	private void addPanels() {
		System.out.println("ClientFrame addPanels");
		renderPanel = new RenderPanel();
		InventoryPanel invPanel = new InventoryPanel(null);
		this.add(renderPanel);
		this.add(invPanel);
	}

	@Override
	protected void initialiseBehaviour() {
		super.initialiseBehaviour();
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
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

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		new ClientFrame(null, 0);
	}

}
