package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Vector;

/**
 * Unit testing class for {@link Sphere} methods.
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class SphereTests {
    
    @Test
    /**
     * Unit tests for {@link Sphere} getNormal method
     */
    public void testGetNormal() {
        
        //============ Equivalence Partitions Tests ==============
        // tests for a point p0 intersected with s1
        Sphere s1 = new Sphere(new Point3D(0, 0, 0), 5);
        Point3D p0 = new Point3D(0, 0, 5);
        assertEquals("getNormal() didn't return the correct normal Vector", s1.getNormal(p0), new Vector(0, 0, 5).normalized());

        // =========== Boundary Tests =================
        // passing the center of the sphere as P0
        try{
            s1.getNormal(s1.getCenter());
            fail("getNormal() didn't attempt to construct the zero Vector");
        } catch (Exception e) {}
    }
}
