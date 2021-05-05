package unittests.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit testing class for {@link Ray} methods.
 * 
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class RayTests {

    /**
     * Unit test for findClosestPoint method in {@link Ray}
     */
    @Test
    public void testFindClosestPoint() {

        Ray r = new Ray(new Point3D(.5, .5, .5), new Vector(1, 1, 1));
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(2, 2, 2);
        Point3D p3 = new Point3D(3, 3, 3);
        Point3D p4 = new Point3D(4, 4, 4);

        // ============= Equivalence Tests ==============
        // TC01: point in middle of list
        List<Point3D> list = new LinkedList<>(Arrays.asList(p3, p2, p1, p4));
        assertEquals("Returned incorrect value for TC01", p1, r.findClosestPoint(list));

        // ============= Boundary Tests =============
        // TC02: point at beginning of list
        list = new LinkedList<>(Arrays.asList(p1, p2, p3, p4));
        assertEquals("Returned incorrect value for TC02", p1, r.findClosestPoint(list));

        // TC03: point at end of list
        list = new LinkedList<>(Arrays.asList(p4, p3, p2, p1));
        assertEquals("Returned incorrect value for TC03", p1, r.findClosestPoint(list));

        // TC04: empty list
        list.clear();
        assertNull("Returned incorrect value for TC04", r.findClosestPoint(list));

    }
}
