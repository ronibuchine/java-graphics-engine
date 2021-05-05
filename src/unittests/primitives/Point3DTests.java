package unittests.primitives;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static primitives.Point3D.ZERO;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs Unit tests for the {@link Point3D} class
 */
public class Point3DTests {

    final double ACCURACY = .000001;

    @Test
    /**
     * Unit test for add method in {@link Point3D}
     */
    public void testAdd() {

        // test adding a non-zero vector to the zero point
        Vector v1 = new Vector(1, 1, 1);
        assertEquals("add() didn't return the correct value for adding the non-zero vector to the zero point.",
                new Point3D(1, 1, 1), ZERO.add(v1));

        // test adding the zero vector to a non-zero point
        Point3D p1 = new Point3D(1, 1, 1);

        // test adding a non-zero vector to a non-zero point
        Vector v2 = new Vector(-1, -1, -1);
        assertEquals("add() didn't return the correct value.", ZERO, p1.add(v2));

    }

    @Test
    /**
     * Unit test for distance method in {@link Point3D}
     */
    public void testDistance() {

        assertEquals("distance() didn't return 0 between the origin and itself", 0, ZERO.distance(ZERO), ACCURACY);

        Point3D p1 = new Point3D(3, 4, 0);

        assertEquals("distance() didn't return 0 between non-origin point and itself", 0, p1.distance(p1), ACCURACY);
        assertEquals("distance() didn't return the correct distance from the origin.", 5, ZERO.distance(p1), ACCURACY);

        Point3D p2 = new Point3D(6, 7, 0);
        Point3D p3 = new Point3D(3, 3, 0);
        assertEquals("distance() didn't return the correct distance between two non-zero points.", 5, p2.distance(p3),
                ACCURACY);
    }

    @Test
    /**
     * Unit test for subtract method in {@link Point3D}
     */
    public void testSubtract() {

        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(9, 7, 5);

        // =========== Equivalence Partition Tests ===========
        assertEquals("subtract() didn't return the correct Vector", new Vector(-8, -5, -2), p1.subtract(p2));

        // =========== Boundary Tests =================
        // subtracting a point from itself - should construct the zero vector
        try {
            p1.subtract(p1);
            fail("no exception thrown for subtracting identical points");
        } catch (Exception e) {
        }
    }
}
