package unittests;

import static org.junit.Assert.assertEquals;
import java.util.Collections;
import org.junit.Test;
import geometries.*;
import primitives.*;

public class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}
     */
    @Test
    public void testFindIntersections() {

    // =========== Boundary Tests =================
    // an empty collection (BVA)
    Geometries geoms = new Geometries();
    assertEquals("empty collection", Collections.emptyList(), geoms.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-1, -1, -1))));

    //	no shape intersects with a body (BVA)

    geoms.add(new Cylinder(1, new Ray(new Point3D(3, 0, 0), new Vector(0, 5, 0)), 5), 
              new Sphere(new Point3D(0, 5, 0), 2),
              new Polygon(new Point3D(0, 10, 0), new Point3D(-2, 12, 0), new Point3D(-5, 5, 0), new Point3D(-3, 4, 0), new Point3D(-1, 5, 0))
    );
    assertEquals("Ray doesn't intersect any geometries", Collections.emptyList(), geoms.findIntersections(new Ray(new Point3D(0, 0, 10), new Vector(0, 1, 0))));

    //	only one shape intersects (BVA)
    
    //	all shapes intersects (BVA)

    // ============== Equivalence Partitions Tests ==============
    //	some shapes but not all intersects (EP)

    }
}
