package unittests.geometries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import geometries.Cylinder;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit testing class for {@link Cylinder} methods.
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class CylinderTests {
    
    @Test
    public void testGetNormal() {
        //============ Equivalence Partitions Tests ==============
        // tests for a point p0 on side of cylinder
        Ray r = new Ray(new Vector(1, 0, 0), Point3D.ZERO);
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

}
