package parser;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SceneXMLParser {
    public static SceneDescriptor parse(InputSource f) throws SAXException {
        try {
            SAXhandler handler = new SAXhandler();
            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            reader.setContentHandler(handler);
            reader.parse(f);
            return handler.getSceneDescriptor();

        } catch (ParserConfigurationException | IOException e) {
            return null;
        }
    }
}
