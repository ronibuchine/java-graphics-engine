package unittests.scene;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import geometries.*;
import primitives.*;
import scene.*;

/**
 * Unit testing class for {@link SceneBuilder}
 */
public class SceneBuilderTests {
    @Test
    public void testLoadSceneFromFile() {
        SceneBuilder sb = new SceneBuilder("test", new File("src/unittests/xml/basicRenderTestTwoColors.xml").getPath());
        Scene scene1 = sb.build();
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
