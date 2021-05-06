package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.InputSource;

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
     */
    public static SceneDescriptor InitializeFromXMLstring(String xmlText) {
        return SceneXMLParser.parse(new InputSource(new StringReader(xmlText)));
    }
    public static SceneDescriptor InitializeFromXMLfile(File f) {
        try {
            return SceneXMLParser.parse(new InputSource(new FileInputStream(f)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
    }
}
