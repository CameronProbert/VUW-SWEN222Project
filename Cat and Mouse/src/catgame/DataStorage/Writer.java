package catgame.DataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

public class Writer {
	
	public Writer() throws FileNotFoundException, IOException{
		write();
	}

	public static void write() throws FileNotFoundException, IOException{
		Document document = new Document();
		Element root = new Element("document");
		root.setAttribute("file", "file.xml");
		root.addContent(new Element ("style"));
		document.setRootElement(root);
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(document, new FileOutputStream(new File("test.xml")));
		
	}
}
