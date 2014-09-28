package catgame.gui;

import java.awt.Dimension;

import catgame.clientserver.NetworkSetUp;

public class HostFrame extends AbstractFrame {
	
	private NetworkSetUp host;

	public HostFrame() {
		super(new Dimension(600, 600), "Catgame Server");
		host = new NetworkSetUp();
		host.setServer();
	}
	
	/**
	 * Sets behaviour for the frame
	 */
	protected void initialiseBehaviour() {
		super.initialiseBehaviour();
	}

}
