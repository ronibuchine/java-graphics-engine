package unittests;

import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

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

     @Test
     /**
      * Test for finding intersections with a {@link Plane}
      */
     public void testFindIntersections() {
         
         // ============== Equivalence Tests ====================
         
         // TC01: ray intersects the plane 
         Plane p1 = new Plane(new Vector(0, 0, 1), new Point3D(1, 2, 0));
         Ray r1 = new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, -1));
         assertEquals("findIntersection() did not return the correct list for TC01",List.of(new Point3D(0, 0, 0)) , p1.findIntersections(r1));

         // TC02: ray doesnt intersect the plane
         Ray r2 = new Ray(new Point3D(-1,-1,-1), new Vector(0, 0, -1));
         assertNull(p1.findIntersections(r2), "findIntersection() did not return correct value for TC02");

         // ============== Boundary Tests =================

         // TC03: ray is parallel to plane 
         Ray r3 = new Ray(new Point3D(1, 1, 1), new Vector(1, 0, 0));
         assertNull("findIntersection() did not return the correct value for TC03", p1.findIntersections(r3));

         // TC04: ray is orthogonal to plane before plane
         Ray r4 = new Ray(new Point3D(1, 1, 1), new Vector(0, 0, -1));
         assertEquals("findIntersection() did not return the correct value for TC04", List.of(new Point3D(1, 1, 0)), p1.findIntersections(r4));

         // TC05: ray is orthogonal to plane after plane
         Ray r5 = new Ray(new Point3D(-1, -1, -1), new Vector(0, 0, -1));
         assertNull("findIntersections() did not return the correct value for TC05", p1.findIntersections(r5));

         // TC06: ray is orthogonal to plane inside plane
         Ray r6 = new Ray(new Point3D(3, 4, 0), new Vector(0, 0, -1));
         assertNull(p1.findIntersections(r6), "findIntersection() did not return the correct value for TC06");

         // TC07: ray isn't orthogonal to plane or parallel but starts in plane
         Ray r7 = new Ray(new Point3D(1, 2, 0), new Vector(1, 2, -3));
         assertNull("findIntersections() did not return the correct value for TC07", p1.findIntersections(r7));

         // TC08: ray begins at same reference point of plane
         Plane p2 = new Plane(new Vector(1, 1, 1), new Point3D(1, 2, 3));
         Ray r8 = new Ray(new Point3D(1, 2, 3), new Vector(0, 0, 1));
         assertNull(p2.findIntersections(r8), "Didn't return null when for TC08");
     }
    
}
