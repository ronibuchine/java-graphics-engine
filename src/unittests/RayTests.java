package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import primitives.*;

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
        Ray r = new Ray(Point3D.ZERO, new Vector(1, 1, 1));
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p3 = new Point3D(3, 3, 3);
        Point3D p4 = new Point3D(4, 4, 4);
        Point3D p5 = new Point3D(5, 5, 5);

        List<Point3D> list = new LinkedList<>(Arrays.asList(p4, p3, p1, p5));
        assertEquals("Closest point is in middle of list", p1, r.findClosestPoint(list));

        list = new LinkedList<>(Arrays.asList(p1, p3, p4, p5));
        assertEquals("Closest point is first in list", p1, r.findClosestPoint(list));

        list = new LinkedList<>(Arrays.asList(p3, p4, p5, p1));
        assertEquals("Closest point is last in list", p1, r.findClosestPoint(list));
        
        list = new LinkedList<>();
        assertNull("Empty list", r.findClosestPoint(list));
    }
}
