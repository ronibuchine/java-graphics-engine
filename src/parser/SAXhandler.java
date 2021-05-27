package parser;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SAXhandler extends DefaultHandler {
    
    private SceneDescriptor scene;

    private Boolean geometries = false;
    private Boolean lights = false;
    private Map<String,String> object = new HashMap<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (geometries || lights) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                object.put(attributes.getQName(i).toLowerCase(), attributes.getValue(i).toLowerCase());
            }
        }
        switch (qName.toLowerCase()) {
            case "scene":
                scene = new SceneDescriptor();
                /*
                if (attributes.getValue("background-color") == null) {
                    throw new SAXException("scene is missing \"background-color\" attribute.");
                }*/
                scene.sceneAttributes.put("background-color", attributes.getValue("background-color"));
                break;

            case "ambient-light":
                if (attributes.getValue("color") == null) {
                    throw new SAXException("\"ambient-light\" attribute doesn't have a \"color\".");
                }
                scene.ambientLightAttributes.put("color", attributes.getValue("color"));
                break;

            case "lights":
                lights = true;
                break;

            case "directional-light":
                object.put("type", "directional");
                scene.lights.add(Map.copyOf(object));
                break;
            case "point-light":
                object.put("type", "point");
                scene.lights.add(Map.copyOf(object));
                break;
            case "spot-light":
                object.put("type", "spot");
                scene.lights.add(Map.copyOf(object));
                break;

            case "geometries":
                geometries = true;
                break;

            case "cylinder":
                scene.cylinders.add(Map.copyOf(object));
                break;

            case "plane":
                scene.planes.add(Map.copyOf(object));
                break;

            case "polygon":
            case "triangle":
                scene.polygons.add(Map.copyOf(object));
                break;

            case "sphere":
                scene.spheres.add(Map.copyOf(object));
                break;
            
            case "tube":
                scene.tubes.add(Map.copyOf(object));
                break;

            default:
                throw new SAXException("Unrecognized qName: " + qName);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (geometries || lights) {
            object.clear();
        }

        if (qName.equalsIgnoreCase("lights")) {
            lights = false;
        }

        if (qName.equalsIgnoreCase("geometries")) {
            geometries = false;
        }
    }

    public SceneDescriptor getSceneDescriptor() {
        return scene;
    }
}
