package scene;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import elements.AmbientLight;
import geometries.*;
import parser.*;
import primitives.*;

/**
 * Class for building scene from XML
 */
public class SceneBuilder {
    private SceneDescriptor sceneDesc;
    private Scene scene;
    String filePath;

    /**
     * Constructs SceneBuilder
     * @param name name of {@link Scene}
     */
    public SceneBuilder(String name) {
        this(name, "");
    }
    /**
     * Constructs SceneBuilder with filePath
     * @param name
     * @param filePath
     */
    public SceneBuilder(String name, String filePath) {
        scene = new Scene(name);
        this.filePath = filePath;
        sceneDesc = SceneDescriptor.InitializeFromXMLfile(new File(filePath));
    }


    /**
     * Loads Scene from path initialized in constructor
     * @return {@link Scene}
     */
    public Scene build() {

        loadSceneAttributes();
        loadAmbientLight();
        loadGeometries();

        return scene;
    }
    
    /**
     * Loads Scene from File passed as paramter
     * @param f XML {@link File}
     * @return {@link Scene}
     */
    public Scene loadSceneFromFile(File f) {
        sceneDesc = SceneDescriptor.InitializeFromXMLfile(f);

        loadSceneAttributes();
        loadAmbientLight();
        loadGeometries();

        return scene;
    }

    public void loadSceneAttributes() {
        scene.setBackground(parseColor(sceneDesc.sceneAttributes.get("background-color")));
    }

    public void loadAmbientLight() {
        scene.setAmbientLight(new AmbientLight(parseColor(sceneDesc.ambientLightAttributes.get("color")), 1));
    }

    public void loadGeometries() {

        Geometries geometries = new Geometries();

        //add cylinders to Scene
        for (Map<String,String> cylinder : sceneDesc.cylinders) {
            Point3D point = parsePoint(cylinder.get("point"));
            Vector direction = new Vector(parsePoint(cylinder.get("direction")));
            double radius = Double.parseDouble(cylinder.get("radius"));
            double height = Double.parseDouble(cylinder.get("height"));
            geometries.add(new Cylinder(radius, new Ray(point, direction), height));
        }

        //add planes to Scene
        for (Map<String,String> plane : sceneDesc.planes) {
            if (plane.containsKey("normal")) {
                Vector normal = new Vector(parsePoint(plane.get("normal")));
                Point3D point = parsePoint(plane.get("point"));
                geometries.add(new Plane(normal, point));
            } else {
                Point3D p0 = parsePoint(plane.get("p0"));
                Point3D p1 = parsePoint(plane.get("p1"));
                Point3D p2 = parsePoint(plane.get("p2"));
                geometries.add(new Plane(p0, p1, p2));
            }
        }

        //add polygons to Scene
        for (Map<String,String> polygon : sceneDesc.polygons) {
            List<Point3D> list = new LinkedList<>();
            for (String value : polygon.values()) {
                list.add(parsePoint(value));
            }
            geometries.add(new Polygon(list.toArray(new Point3D[list.size()])));
        }

        //add spheres to Scene
        for (Map<String,String> sphere : sceneDesc.spheres) {
            Point3D center = parsePoint(sphere.get("center"));
            double radius = Double.parseDouble(sphere.get("radius"));
            geometries.add(new Sphere(center, radius));
        }

        //add tubes to Scene
        for (Map<String,String> tube : sceneDesc.tubes) {
            Point3D point = parsePoint(tube.get("point"));
            Vector direction = new Vector(parsePoint(tube.get("direction")));
            double radius = Double.parseDouble(tube.get("radius"));
            geometries.add(new Tube(radius, new Ray(point, direction)));
        }

        scene.setGeometries(geometries);
    }

    private double[] parseNumbers(String s) {
        String[] c = s.split(" ");
        double[] doubleArray = { Double.parseDouble(c[0]), Double.parseDouble(c[1]), Double.parseDouble(c[2]) };
        return doubleArray;
    }
    private Color parseColor(String s) {
        double[] d = parseNumbers(s);
        return new Color(d[0], d[1], d[2]);
    }
    private Point3D parsePoint(String s) {
        double[] d = parseNumbers(s);
        return new Point3D(d[0], d[1], d[2]);
    }
}
