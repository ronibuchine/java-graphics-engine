package unittests;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

/**
 * Unit testing class for {@link Cylinder} methods.
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class CylinderTests {
    
    @Test
    /**
     * Unit test for getNormal method implemented in a {@link Cylinder}
     */
    public void testGetNormal() {
        //============ Equivalence Partitions Tests ==============
        // tests for a point p0 on side of cylinder
        Ray r = new Ray(Point3D.ZERO, new Vector(1, 0, 0));
        Cylinder c= new Cylinder(3, r, 10);
        Point3D p0 = new Point3D(8, 0, 3);
        assertEquals("getNormal() didn't return the correct Vector", c.getNormal(p0), new Vector(0, 0, 1));

        // tests for a point p1 on end of cylinder
        Point3D p1 = new Point3D(10, 1, -1);
        assertEquals("getNormal() didn't return the correct Vector on end of cylinder", c.getNormal(p1), new Vector(1, 0, 0));


        // =========== Boundary Tests =================
        // passing a point in middle of cylinder's inner ray as p0
        p0 = new Point3D(5, 0, 0);
        try {
            c.getNormal(p0);
            fail("getNormal() didn't attempt to create Zero vector");
        }
        catch (Exception e) {}

        // passing a point on end of cylinder's inner ray
        assertEquals("getNormal() didn't recognize point as on the end of cylinder", c.getNormal(Point3D.ZERO), new Vector(1, 0, 0));
    }

    @Test
    public void testFindIntersections(){
        Cylinder c = new Cylinder(5, new Ray(new Point3D(0, 0, 5), new Vector(0, 7, 0)), 30);
        assertEquals("ray inside tube, hitting cap", List.of(new Point3D(1, 0, 1)), c.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(0, -1, 0))));
        assertEquals("ray inside tube, hitting edge", List.of(new Point3D(-5, 20, 5)), c.findIntersections(new Ray(new Point3D(3, 20, 5), new Vector(-12, 0, 0))));
        assertEquals("ray outside tube, hitting edge twice", List.of(new Point3D(0, 3, 0), new Point3D(0, 3, 10)), c.findIntersections(new Ray(new Point3D(0, 3, 15), new Vector(0, 0, -1))));
        assertEquals("ray outside tube, hitting caps twice", List.of(new Point3D(0, 0, 1), new Point3D(0, 30, 1)) , c.findIntersections(new Ray(new Point3D(0, 40, 1), new Vector(0, -1, 0))));
        c = new Cylinder(3, new Ray(new Point3D(0,0,-1), new Vector(0,0,-1)), 10);
        assertEquals("ray hits cap and edge", List.of(new Point3D(0,3,-4), new Point3D(0,0,-1)) , c.findIntersections(new Ray(new Point3D(0, -1, 0), new Vector(0, 1, -1))));
    }
}
