package parser;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Class to parse XML
 */
public class SceneXMLParser {
    public static SceneDescriptor parse(InputSource f) {
        try {
            SAXhandler handler = new SAXhandler();
            SAXParser reader = SAXParserFactory.newInstance().newSAXParser();
            reader.parse(f, handler);
            return handler.getSceneDescriptor();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("SAX error. See cause...", e);
        }
    }
}
