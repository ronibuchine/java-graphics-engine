package unittests.geometries;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import geometries.BoundingBox;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;

/**
 * Testing class for Bounding Box performance enhancements
 * 
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class BoundingBoxTests {

    Triangle t1 = new Triangle(Point3D.ZERO, new Point3D(10, 10, 0), new Point3D(10, 0, 10));
    Sphere s1 = new Sphere(Point3D.ZERO, 5);

    BoundingBox b1 = new BoundingBox();

    /**
     * unit test for creating a bounding box around a single geometry
     */
    @Test
    public void singleGeometryBoxTest() {

        b1.add(s1);

        assertEquals("Single geometry test for bounding box failed to retrieve min point", b1.getMin(),
                new Point3D(-5, -5, -5));
        assertEquals("Single geometry test for bounding box failed to retrieve max point", b1.getMax(),
                new Point3D(5, 5, 5));

    }

    /**
     * unit test for creating a bounding box around multiple geometries
     */
    @Test
    public void multipleGeometryBoxTest() {

        b1.add(t1);

        assertEquals("Mulitple geometry test for bounding box failed to retrieve min point", b1.getMin(),
                new Point3D(-5, -5, -5));
        assertEquals("Multiple geometry test for bounding box failed to retrieve min point", b1.getMax(),
                new Point3D(10, 10, 10));
    }
}
