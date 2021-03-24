package unittests.primitives;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import primitives.Point3D;
import static primitives.Point3D.ZERO;
import primitives.Vector;


/**
 * @author Roni Buchine
 * @author Eliezer Jacobs
 * Unit tests for the {@link Point3D} class
 */
public class Point3DTests {

    @Test
    public void testAdd() {
 
       
        // test adding a non-zero vector to the zero point
        Vector v1 = new Vector(1, 1, 1);
        assertEquals("add() didn't return the correct value for adding the non-zero vector to the zero point.", new Point3D(1, 1, 1), ZERO.add(v1));
       
        // test adding the zero vector to a non-zero point
        Point3D p1 = new Point3D(1, 1, 1);       

        // test adding a non-zero vector to a non-zero point
        Vector v2 = new Vector(-1, -1, -1);
        assertEquals("add() didn't return the correct value.", ZERO, p1.add(v2));
        
    }
    
    @Test
    public void testDistance() {

        assertEquals("distance() didn't return 0 between the origin and itself", 0, ZERO.distance(ZERO), 0.000001);

        Point3D p1 = new Point3D(3, 4, 0);

        assertEquals("distance() didn't return 0 between non-origin point and itself", 0, p1.distance(p1), 0.000001);
        assertEquals("distance() didn't return the correct distance from the origin.", 5, ZERO.distance(p1), 0.000001);

        Point3D p2 = new Point3D(6, 7, 0);
        Point3D p3 = new Point3D(3, 3, 0);
        assertEquals("distance() didn't return the correct distance between two non-zero points.", 5, p2.distance(p3), 0.000001);
    }
}
