package scene;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import elements.AmbientLight;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
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
        loadLightSources();
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
        loadLightSources();
        loadGeometries();

        return scene;
    }

    public void loadSceneAttributes() {
        scene.setBackground(parseColor(sceneDesc.sceneAttributes.get("background-color")));
    }

    public void loadAmbientLight() {
        if (sceneDesc.ambientLightAttributes.get("color") == null) return;
        scene.setAmbientLight(new AmbientLight(parseColor(sceneDesc.ambientLightAttributes.get("color")), 1));
    }

    public void loadLightSources() {
        //add point light to Scene
        for (Map<String,String> light : sceneDesc.lights) {
            Color color = parseColor(light.get("color"));
            Point3D point;
            Vector direction;
            double kC = 1, kL = 0, kQ = 0, beam = 1;
            if (light.get("kc") != null) kC = Double.parseDouble(light.get("kc"));
            if (light.get("kl") != null) kL = Double.parseDouble(light.get("kl"));
            if (light.get("kq") != null) kQ = Double.parseDouble(light.get("kq"));
            if (light.get("beam") != null) beam = Double.parseDouble(light.get("beam"));
            
            switch(light.get("type")) {
                case "point":
                    point = parsePoint(light.get("point"));
                    scene.lights.add(new PointLight(color, point).setKc(kC).setKl(kL).setKq(kQ));
                    break;

                case "directional":
                    direction = new Vector(parsePoint(light.get("direction")));
                    scene.lights.add(new DirectionalLight(color, direction));
                    break;

                case "spot":
                    point = parsePoint(light.get("point"));
                    direction = new Vector(parsePoint(light.get("direction")));
                    scene.lights.add(new SpotLight(color, point, direction).setBeam(beam).setKc(kC).setKl(kL).setKq(kQ));
            }
        }
    }

    public void loadGeometries() {

        Geometries geometries = new Geometries();

        //add cylinders to Scene
        for (Map<String,String> cylinder : sceneDesc.cylinders) {
            Point3D point = parsePoint(cylinder.get("point"));
            Vector direction = new Vector(parsePoint(cylinder.get("direction")));
            double radius = Double.parseDouble(cylinder.get("radius"));
            double height = Double.parseDouble(cylinder.get("height"));
            Color color = parseColor(cylinder.get("color"));
            geometries.add(new Cylinder(radius, new Ray(point, direction), height)
                .setEmission(color).setMaterial(parseMaterial(cylinder)));
        }

        //add planes to Scene
        for (Map<String,String> plane : sceneDesc.planes) {
            Color color = new Color(parseColor(plane.get("color")));
            if (plane.containsKey("normal")) {
                Vector normal = new Vector(parsePoint(plane.get("normal")));
                Point3D point = parsePoint(plane.get("point"));
                geometries.add(new Plane(normal, point).setEmission(color)
                    .setEmission(color).setMaterial(parseMaterial(plane)));
            } else {
                Point3D p0 = parsePoint(plane.get("p0"));
                Point3D p1 = parsePoint(plane.get("p1"));
                Point3D p2 = parsePoint(plane.get("p2"));
                geometries.add(new Plane(p0, p1, p2).setEmission(color)
                    .setEmission(color).setMaterial(parseMaterial(plane)));
            }
        }

        //add polygons to Scene
        for (Map<String,String> polygon : sceneDesc.polygons) {
            List<Point3D> list = new LinkedList<>();
            for (Map.Entry<String,String> attr : polygon.entrySet()) {
                if (attr.getKey().startsWith("p")) list.add(parsePoint(attr.getValue()));
            }
            Color color = parseColor(polygon.get("color"));
            geometries.add(new Polygon(list.toArray(new Point3D[list.size()])).setEmission(color)
                .setEmission(color).setMaterial(parseMaterial(polygon)));
        }

        //add spheres to Scene
        for (Map<String,String> sphere : sceneDesc.spheres) {
            Point3D center = parsePoint(sphere.get("center"));
            double radius = Double.parseDouble(sphere.get("radius"));
            Color color = parseColor(sphere.get("color"));
            geometries.add(new Sphere(center, radius).setEmission(color)
                .setEmission(color).setMaterial(parseMaterial(sphere)));
        }

        //add tubes to Scene
        for (Map<String,String> tube : sceneDesc.tubes) {
            Point3D point = parsePoint(tube.get("point"));
            Vector direction = new Vector(parsePoint(tube.get("direction")));
            double radius = Double.parseDouble(tube.get("radius"));
            Color color = parseColor(tube.get("color"));
            geometries.add(new Tube(radius, new Ray(point, direction)).setEmission(color)
                .setEmission(color).setMaterial(parseMaterial(tube)));
        }

        scene.setGeometries(geometries);
    }

    private double[] parseNumbers(String s) {
        if (s == null) return null;
        String[] c = s.split(" ");
        return new double[] { Double.parseDouble(c[0]), Double.parseDouble(c[1]), Double.parseDouble(c[2]) };
    }
    private Color parseColor(String s) {
        if (s == null) return null;
        double[] d = parseNumbers(s);
        return new Color(d[0], d[1], d[2]);
    }
    private Point3D parsePoint(String s) {
        double[] d = parseNumbers(s);
        return new Point3D(d[0], d[1], d[2]);
    }

    private Material parseMaterial(Map<String,String> geo) {
        double kD = 0, kS = 0;
        int shininess = 0;
        if (geo.get("kd") != null) kD = Double.parseDouble(geo.get("kd"));
        if (geo.get("ks") != null) kS = Double.parseDouble(geo.get("ks"));
        if (geo.get("shininess") != null) shininess = Integer.parseInt(geo.get("shininess"));
        return new Material().setKd(kD).setKs(kS).setShininess(shininess);
    }
}
