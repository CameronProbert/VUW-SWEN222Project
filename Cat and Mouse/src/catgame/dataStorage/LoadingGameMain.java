package catgame.dataStorage;

import javax.xml.stream.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class LoadingGameMain {
    
    
    public LoadingGameMain() throws JDOMException{
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("/Cat and Mouse/test.xml");
        
        
            try {
                Document document = (Document) builder.build(xmlFile);
                Element root = document.getRootElement();
                List<Element> list = root.getChildren();
                
                for(Element element: list){
                    
                }
                
            } catch (IOException e) {
                
                e.printStackTrace();
            }
        
    }
    
    private void loadRooms(Element room) throws DataConversionException{
        int id = room.getAttribute("id").getIntValue();
        List<Element> roomInventory = room.getChildren();
        
    }
}