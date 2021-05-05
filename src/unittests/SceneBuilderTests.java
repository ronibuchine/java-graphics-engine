package unittests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

import geometries.*;
import org.xml.sax.SAXException;
import primitives.*;
import scene.*;

public class SceneBuilderTests {
    @Test
    public void testLoadSceneFromFile() throws SAXException, FileNotFoundException {
        SceneBuilder sb = new SceneBuilder("test", System.getProperty("user.dir") + "/images/basicRenderTestTwoColors.xml");
        Scene scene1 = sb.loadScene();
        Geometries geometries = new Geometries(
            new Sphere(new Point3D(0,0,-100), 50),
            new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)),
            new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)),
            new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)),
            new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))
        );
        Ray ray = new Ray(Point3D.ZERO, new Vector(0, 0, -1));
        assertEquals("xml imported scene", scene1.geometries.findIntersections(ray), geometries.findIntersections(ray));
    }
}
