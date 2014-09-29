package catgame.DataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

import catgame.GameObjects.*;
import catgame.gui.LauncherFrame;

public class Writer {
	private Boss tempBoss;
	private Document document;
	private Element root;
	

	public Writer() throws FileNotFoundException, IOException {
		document = new Document();
		root = new Element("CatGame");
		document.setRootElement(root);
		write();
	}

	/** 
	 *  In testing phase! Creates an xml file and 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void write() throws FileNotFoundException, IOException {

		// adding Boss ID and health field to the file
		Element bossElem = new Element("Boss");
		bossElem.setAttribute("id", "" + tempBoss.getObjectID());
		bossElem.addContent(new Element("health").setText(""
				+ tempBoss.getHealth()));

		// updating document
		document.getRootElement().addContent(bossElem);

		// Outputting xml file
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(document, new FileOutputStream(new File("test.xml")));
	}
	
	public void writeBoardData() throws FileNotFoundException, IOException {
			Element boardData = new Element("BoardDate");
			
			//for(Room room: boardData.)
			
	}

	/**
	 * Main for testing writing out xml files without evoking the whole game.
	 * Will be removed in the future.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		new Writer();
	}
}
