package unittests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Vector;

/**
 * Unit testing class for {@link Triangle} class methods
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class TriangleTests {

    @Test
    /**
     * Tests functionality of getNormal implemented by the {@link Triangle} class
     */
    public void testGetNormal() {

        //============= Equivalence Partition Tests =============
        Triangle t1 = new Triangle(new Point3D(0,0,0), new Point3D(0,1,0), new Point3D(0,0,1));
        Point3D p0 = new Point3D(0, 0, 0);
        assertEquals("getNormal() didn't return the correct Vector", new Vector(1, 0, 0), t1.getNormal(p0));
        
        Triangle t2 = new Triangle(new Point3D(0,0,0), new Point3D(0, -1, 0), new Point3D(0, 0, 1));
        assertEquals("getNormal() didnt return the correct negative normal Vector", new Vector(-1, 0, 0), t2.getNormal(p0));

    }
    
}
