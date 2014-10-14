package catgame.datastorage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import catgame.logic.BoardData;

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
		
	}
}
