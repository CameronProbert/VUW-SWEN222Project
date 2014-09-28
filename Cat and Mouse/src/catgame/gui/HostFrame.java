package catgame.gui;

import java.awt.Dimension;

import catgame.clientserver.NetworkSetUp;

public class HostFrame extends AbstractFrame {
	
	private NetworkSetUp host;

	// TODO LOTS
	public HostFrame() {
		super(new Dimension(600, 600), "Catgame Server");
	}

}
