package catgame.dataStorage;

import javax.xml.stream.*;

import catgame.*;
import catgame.clientserver.*;
import catgame.gameObjects.*;
import catgame.gui.*;
import catgame.gui.renderpanel.*;
import catgame.gui.textfiles.*;
import catgame.logic.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class LoadingGameMain {
    private Map<Integer, MasterObject> objectIDMap = new HashMap<Integer,MasterObject>();
    
    public LoadingGameMain() throws JDOMException, XMLException{
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("/Cat and Mouse/test.xml");
        
            try {
                Document document = (Document) builder.build(xmlFile);
                Element root = document.getRootElement();
                Element boardData = root.getChildren().get(0);
                if(boardData == null){
                	throw new XMLException("XML file is empty");
                }
                List<Room> allRooms = new ArrayList<Room>();
                for(Element roomElement: boardData.getChildren()){
                    allRooms.add(loadRooms(roomElement));
                }
                BoardData board = new BoardData();
                board.populateRooms(allRooms);
                
            } catch (IOException e) {
                
                e.printStackTrace();
            }
        
    }
    
    private Room loadRooms(Element roomElement) throws DataConversionException{
        int id = roomElement.getAttribute("id").getIntValue();
        Room room = new Room(id);
        List<Element> roomInventory = roomElement.getChildren();
        
        return room;
    }
}