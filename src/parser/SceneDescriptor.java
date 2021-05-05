package parser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Class to hold scene information
 */
public class SceneDescriptor {
    public Map<String,String> sceneAttributes = new HashMap<>();
    public Map<String,String> ambientLightAttributes = new HashMap<>();
    public List<Map<String,String>> cylinders = new LinkedList<>();
    public List<Map<String,String>> planes = new LinkedList<>();
    public List<Map<String,String>> polygons = new LinkedList<>();
    public List<Map<String,String>> spheres = new LinkedList<>();
    public List<Map<String,String>> tubes = new LinkedList<>();

    /**
     * Creates a {@link SceneDescriptor} from XML text
     * @param xmlText
     * @return {@link SceneDescriptor}
     * @throws SAXException
     */
    public static SceneDescriptor InitializeFromXMLstring(String xmlText) throws SAXException {
        return SceneXMLParser.parse(new InputSource(new StringReader(xmlText)));
    }
}
