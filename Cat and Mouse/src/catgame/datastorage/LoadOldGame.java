package catgame.datastorage;

import java.io.File;



import org.jdom2.JDOMException;

import catgame.logic.BoardData;

/**
 * Loads an old game from a previous save. When an old game is run, it reads the
 * standard starting xml file INCLUDING the players that might be in the xml
 * file.
 * 
 * @author MIla
 *
 */
public class LoadOldGame {

	private BoardData boardData;
	private File xmlFile;
	private LoadingGameMain main;

	public LoadOldGame(File xmlFile) throws JDOMException, XMLException {
		this.xmlFile = xmlFile;
		this.main = new LoadingGameMain(true, xmlFile);
		boardData = main.getBoardData();
	}

	public BoardData getBoardData() {
		return boardData;
	}

	public static void main(String[] args) throws JDOMException, XMLException {
		new LoadOldGame(new File("TestingMultiple.xml"));
	}
}
