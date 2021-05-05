package unittests;

import static org.junit.Assert.assertEquals;

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
        Ray r = new Ray(new Point3D(2, 2, 2), new Vector(1, 1, 1));
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p3 = new Point3D(3, 3, 3);
        Point3D p4 = new Point3D(4, 4, 4);
        Point3D p5 = new Point3D(5, 5, 5);
        List<Point3D> list = new LinkedList<>();
        list.add(p1);
        list.add(p4);
        list.add(p3);
        list.add(p5);
        
        assertEquals("Point in middle of list", p3, r.findClosestPoint(list));
    }
}
