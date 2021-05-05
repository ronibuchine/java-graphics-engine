package unittests.geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.util.List;

/**
 * Unit testing class for {@link Sphere} methods.
 * 
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class SphereTests {

    @Test
    /**
     * Unit tests for {@link Sphere} getNormal method
     */
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // tests for a point p0 intersected with s1
        Sphere s1 = new Sphere(new Point3D(0, 0, 0), 5);
        Point3D p0 = new Point3D(0, 0, 5);
        assertEquals("getNormal() didn't return the correct normal Vector", s1.getNormal(p0),
                new Vector(0, 0, 5).normalized());

        // =========== Boundary Tests =================
        // passing the center of the sphere as P0
        try {
            s1.getNormal(s1.getCenter());
            fail("getNormal() didn't attempt to construct the zero Vector");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere",
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals("Ray starts in sphere",
                sphere.findIntersections(new Ray(new Point3D(1, 0, .5), new Vector(0, 0, 3))),
                List.of(new Point3D(1, 0, 1)));
        // TC04: Ray starts after the sphere (0 points)
        assertNull("Ray starts after sphere",
                sphere.findIntersections(new Ray(new Point3D(2, 1, 1), new Vector(1, 1, 1))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 points)
        Sphere s0 = new Sphere(new Point3D(1, 0, 0), 1);
        Ray r11 = new Ray(new Point3D(Math.sqrt(2) / 2 + 1, -Math.sqrt(2) / 2, 0), new Vector(0, 1, 0));
        assertEquals("findIntersctions() did not return the correct result for TC11",
                List.of(new Point3D(Math.sqrt(2) / 2 + 1, Math.sqrt(2) / 2, 0)), s0.findIntersections(r11));

        // TC12: Ray starts at sphere and goes outside (0 points)
        Ray r12 = new Ray(new Point3D(Math.sqrt(2) / 2 + 1, Math.sqrt(2) / 2, 0), new Vector(0, 1, 0));
        assertNull("findIntersections() did not return the correct result for TC12", s0.findIntersections(r12));

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        Sphere s1 = new Sphere(new Point3D(1, 0, 0), 1);
        Ray r13 = new Ray(new Point3D(1, -2, 0), new Vector(0, 1, 0));
        assertEquals("findIntersections() did not return the correct value for TC13",
                List.of(new Point3D(1, -1, 0), new Point3D(1, 1, 0)), s1.findIntersections(r13));

        // TC14: Ray starts at sphere and goes inside (1 points)
        Ray r14 = new Ray(new Point3D(1, -1, 0), new Vector(0, 1, 0));
        assertEquals("findIntersections() did not return the correct value for TC14", List.of(new Point3D(1, 1, 0)),
                s1.findIntersections(r14));

        // TC15: Ray starts inside (1 points)
        Ray r15 = new Ray(new Point3D(1, -.5, 0), new Vector(0, 1, 0));
        assertEquals("findIntersections() did not return the correct value for TC15", List.of(new Point3D(1, 1, 0)),
                s1.findIntersections(r15));

        // TC16: Ray starts at the center (1 points)
        Ray r16 = new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0));
        assertEquals("findIntersections() did not return the correct value for TC16", List.of(new Point3D(1, 1, 0)),
                s1.findIntersections(r16));

        // TC17: Ray starts at sphere and goes outside (0 points)
        Ray r17 = new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0));
        assertNull("findIntersections() did not return the correct value for TC17", s1.findIntersections(r17));

        // TC18: Ray starts after sphere (0 points)
        Ray r18 = new Ray(new Point3D(1, 2, 0), new Vector(0, 1, 0));
        assertNull("findIntersections() did not return the correct value for TC18", s1.findIntersections(r18));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        Sphere s2 = new Sphere(Point3D.ZERO, 1);
        Ray r19 = new Ray(new Point3D(1, 2, 0), new Vector(0, 1, 0));
        assertNull("findIntersections() did not return the correct value for TC19", s2.findIntersections(r19));

        // TC20: Ray starts at the tangent point
        Ray r20 = new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0));
        assertNull("findIntersections() did not return the correct value for TC20", s2.findIntersections(r20));

        // TC21: Ray starts after the tangent point
        Ray r21 = new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0));
        assertNull("findIntersections() did not return the correct value for TC21", s2.findIntersections(r21));

        // **** Group: Special cases

        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        Ray r22 = new Ray(new Point3D(0, -2, 0), new Vector(-1, 0, 0));
        assertNull("findIntersections() did not return the correct value for TC22", s2.findIntersections(r22));
    }
}
