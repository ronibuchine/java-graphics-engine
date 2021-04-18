package unittests.geometries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import geometries.Tube;
import primitives.*;

/**
 * Unit testing class for {@link Tube} methods
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class TubeTests {
    
    @Test
    /**
     * Tests the functionality of the getNormal method for a {@link Tube}
     */
    public void testGetNormal() {
        //============ Equivalence Partitions Tests ==============
        // tests for a point p0 on side of testTube
        Ray r = new Ray(new Vector(1, 0, 0), Point3D.ZERO);
        Tube testTube = new Tube(5, r);
        Point3D p0 = new Point3D(1, 1, 0);
        assertEquals("getNormal() didn't return the correct Vector", testTube.getNormal(p0), new Vector(0, 1, 0));

        // =========== Boundary Tests =================
        // passing a point on tube's inner ray as p0
        p0 = new Point3D(-5, 0, 0);
        try {
            testTube.getNormal(p0);
            fail("getNormal() didn't attempt to create Zero vector");
        }
        catch (Exception e) {
            assertTrue(true);
        }
    }
}
