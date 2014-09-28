package catgame.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;

import catgame.GameObjects.Chest;
import catgame.GameObjects.Food;
import catgame.GameObjects.GameItem;
import catgame.GameObjects.Key;
import catgame.GameObjects.PlayableCharacter;
import catgame.clientserver.NetworkHandler;
import catgame.clientserver.Update;
import catgame.gui.renderpanel.RenderPanel;
import catgame.logic.BoardCell;

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
	private boolean isClient;
	private StatPanel statPanel;

	public ClientFrame(NetworkHandler network, int UID, boolean isClient, PlayableCharacter character) {
		super(new Dimension(1200, 600), "Cat and Mouse");
		this.setLayout(null);
		addKeyListener(this);
		this.addPanels(character);
		this.clientsUID = UID;
		this.network = network;
		this.isClient = isClient;
	}

	private void addPanels(PlayableCharacter character) {
		renderPanel = new RenderPanel();
		InventoryPanel invPanel = new InventoryPanel(character);
		statPanel = new StatPanel(character);
		this.add(renderPanel);
		this.add(invPanel);
		this.add(statPanel);
	}

	/**
	 * NOTE: may need to check when moving if moved to another room!! TODO
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		int keyID = key.getKeyCode();
		System.out.println(keyID);
		System.out.println(KeyEvent.VK_M);
		int validAction = 0;
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
			int attacked = network.getGameMain().attack(clientsUID);
			if(attacked>0){
				validAction = 1;
				up = new Update(Update.Descriptor.ATTACK, clientsUID, attacked);
			}
			break;
		case KeyEvent.VK_E: //  open a chest
			Chest chest = network.getGameMain().getChest(clientsUID);
			if(chest!=null){
				validAction = 1;
				// TODO open dialog box to choose items out of chest (using the chest object)
				// int objectID = openChest(chest); // return the id of the item the character selected
				// up = new Update(Update.Descriptor.PICKUP, clientsUID, objectID);
			}
			break;
		case KeyEvent.VK_M:
			statPanel.modifyChar();
			break;
		}
		if (validAction > 0 && isClient) {
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
		ArrayList<GameItem> items = new ArrayList<GameItem>();
		items.add(new Food(2, 30));
		items.add(new Key(3));
		items.add(new Food(2, 30));
		PlayableCharacter character = new PlayableCharacter(1, null, 3,
				5, 50, items);
		new ClientFrame(null, 0, false, character);
	}

}
