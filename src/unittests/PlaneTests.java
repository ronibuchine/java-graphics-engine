package unittests;

import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.assertEquals;

/**
 * Unit testing class for {@link Plane} methods.
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class PlaneTests {

    @Test
    /**
     * Unit test for getNormal method in {@link Plane}
     */
     public void testGetNormal() {
         Plane p = new Plane(new Vector(0,0,1), new Point3D(0,0,0));
         assertEquals(new Vector(0, 0, 1), p.getNormal(new Point3D(5, 7, 3)));
         
     }
    
}
