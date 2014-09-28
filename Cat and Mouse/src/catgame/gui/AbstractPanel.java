package catgame.gui;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.JPanel;

public class AbstractPanel extends JPanel {

	public AbstractPanel(Point origin, Dimension dim) {
		this.setLocation(origin);
		this.setSize(dim);
		this.setPreferredSize(dim);
		this.setVisible(true);
	}

}
