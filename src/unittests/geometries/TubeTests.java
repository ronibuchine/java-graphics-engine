package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

/**
 * Unit testing class for {@link Tube} methods
 * 
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class TubeTests {

    @Test
    /**
     * Tests the functionality of the getNormal method for a {@link Tube}
     */
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // tests for a point p0 on side of testTube
        Ray r = new Ray(Point3D.ZERO, new Vector(1, 0, 0));
        Tube testTube = new Tube(5, r);
        Point3D p0 = new Point3D(1, 1, 0);
        assertEquals("getNormal() didn't return the correct Vector", testTube.getNormal(p0), new Vector(0, 1, 0));

        // =========== Boundary Tests =================
        // passing a point on tube's inner ray as p0
        p0 = new Point3D(-5, 0, 0);
        try {
            testTube.getNormal(p0);
            fail("getNormal() didn't attempt to create Zero vector");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Tube tube = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(9, 0, 0)));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the tube (0 points)
        assertNull("Ray's line out of tube",
                tube.findIntersections(new Ray(new Point3D(0, 5, 0), new Vector(0, 2, 0))));

        // TC02: Ray starts before and crosses the tube (2 points)
        List<Point3D> result = tube.findIntersections(new Ray(new Point3D(3, 0, 5), new Vector(0, 0, -1)));
        if (result.get(0).getZ() < result.get(1).getZ())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses tube", List.of(new Point3D(3, 0, 1), new Point3D(3, 0, -1)), result);

        // TC03: Ray starts inside the tube (and parallel to it) (0 points)
        assertNull("Ray's line inside and parallel to tube",
                tube.findIntersections(new Ray(new Point3D(-2, .2, .2), new Vector(-1, 0, 0))));
        // TC04: Ray starts inside the tube (1 point)
        assertEquals("Ray starts inside tube", List.of(new Point3D(-3, 0, 1)),
                tube.findIntersections(new Ray(new Point3D(-3, 0, 0), new Vector(0, 0, 1))));

        Tube tube1 = new Tube(1, new Ray(new Point3D(0, 0, -5), new Vector(0, 0, 1)));
        assertEquals("Ray starts outside tube", List.of(new Point3D(-1, 0, -4), new Point3D(1, 0, -2)),
                tube1.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(-1, 0, -1))));
        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the tube (but not the center)
        // TC11: Ray starts at tube and goes inside (1 points)
        // TC12: Ray starts at tube and goes outside (0 points)

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the tube (2 points)
        // TC14: Ray starts at tube and goes inside (1 points)
        // TC15: Ray starts inside (1 points)
        // TC16: Ray starts at the center (1 points)
        // TC17: Ray starts at tube and goes outside (0 points)
        // TC18: Ray starts after tube (0 points)

        // **** Group: Ray's line is tangent to the tube (all tests 0 points)
        // TC19: Ray starts before the tangent point
        // TC20: Ray starts at the tangent point
        // TC21: Ray starts after the tangent point

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to tube's center
        // line
    }

    @Test
    public void tubeFindIntersections() {
        Tube t = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(1, 0, 0)));
        Ray r = new Ray(new Point3D(3, -3, -3), new Vector(0, 1, 1));
        assertEquals("Didn't find any intersections", t.findIntersections(r).size(), 2);
    }
}
