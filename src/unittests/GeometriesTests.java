package unittests;

import static org.junit.Assert.assertEquals;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import geometries.*;
import primitives.*;

/**
 * Unit testing class for the {@link Geometries} class.
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}
     */
    @Test
    public void testFindIntersections() {

    // =========== Boundary Tests =================
    // an empty collection (BVA)
    Geometries geoms1 = new Geometries();
    assertEquals("empty collection", Collections.emptyList(), geoms1.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-1, -1, -1))));

    //	no shape intersects with a body (BVA)
    geoms1.add(new Cylinder(1, new Ray(new Point3D(3, 0, 0), new Vector(0, 5, 0)), 5), 
              new Sphere(new Point3D(0, 5, 0), 2),
              new Polygon(new Point3D(0, 10, 0), new Point3D(-2, 12, 0), new Point3D(-5, 5, 0), new Point3D(-3, 4, 0), new Point3D(-1, 5, 0)),
              new Triangle(new Point3D(0, 5, 8), new Point3D(3, 2, 5), new Point3D(0, 0, 3))
    );
    assertEquals("Ray doesn't intersect any geometries", Collections.emptyList(), geoms1.findIntersections(new Ray(new Point3D(0, 0, 10), new Vector(0, 1, 0))));

    //	only one shape intersects (BVA)
    geoms1.add(new Polygon(new Point3D(0, 20, 0), new Point3D(5, 20, 5), new Point3D(5, 20, 10), new Point3D(0, 20, 20), new Point3D(-5, 20, 5)));
    assertEquals("Ray doesn't intersect any geometries", List.of(new Point3D(0, 20, 10)), geoms1.findIntersections(new Ray(new Point3D(0, 0, 10), new Vector(0, 1, 0))));

    //	all shapes intersects (BVA)
    Geometries geoms2 = new Geometries(new Polygon(new Point3D(0, 20, 0), new Point3D(5, 20, 5), new Point3D(5, 20, 10), new Point3D(0, 20, 20), new Point3D(-5, 20, 5)),
                           new Sphere(new Point3D(0, 10, 10), 3),
                           new Cylinder(5, new Ray(new Point3D(2, 25, 9), new Vector(0, 1, 0)), 8)
    );
    assertEquals("Ray intersects all geometries", List.of(new Point3D(0, 20, 10), new Point3D(0, 7, 10), new Point3D(0, 13, 10), new Point3D(0, 25, 10), new Point3D(0, 33, 10)), geoms2.findIntersections(new Ray(new Point3D(0, 0, 10), new Vector(0, 1, 0))));

    // ============== Equivalence Partitions Tests ==============
    //	some shapes but not all intersects (EP)
    Geometries allGeoms = new Geometries(geoms1, geoms2);
            //One of the Polygons is in both Geometries collections so it's intersection point will show up twice
    assertEquals("Ray intersects all geometries", List.of(new Point3D(0, 20, 10), new Point3D(0, 20, 10), new Point3D(0, 7, 10), new Point3D(0, 13, 10), new Point3D(0, 25, 10), new Point3D(0, 33, 10)), allGeoms.findIntersections(new Ray(new Point3D(0, 0, 10), new Vector(0, 1, 0))));
    }
}
