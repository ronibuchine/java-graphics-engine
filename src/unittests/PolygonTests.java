package unittests;

import geometries.Polygon;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

/**
 * unit testing class for {@link Polygon} methods.
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class PolygonTests {
    /**
     * Test method for
     * {@link Polygon}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same {@link Plane}
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Colocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}
     */
    @Test
    public void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Intersection point inside polygon         
        Polygon p1 = new Polygon(new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(0, 1, 0));   // square with side length of 1, vertex at origin
        Ray r1 = new Ray(new Point3D(0.5, 0.5, 1), new Vector(0, 0, -1));
        assertEquals("findIntersections() returned incorrect value for TC01", List.of(new Point3D(.5, .5, 0)) , p1.findIntersections(r1));

        // TC02: Point outside polygon 
        Ray r2 = new Ray(new Point3D(2, 1, 1), new Vector(0, 0, -1));
        assertNull(p1.findIntersections(r2), "findIntersections() did not return the correct value for TC02");

        // TC03: Point outside against vertex
        Ray r3 = new Ray(new Point3D(2, 2, 1), new Vector(0, 0, -1));
        assertNull(p1.findIntersections(r3), "findIntersections() did not return the correct value for TC03");

        // ============= Boundary Tests =================

        // TC04: Point on edge
        Ray r4 = new Ray(new Point3D(.5, 0, 1), new Vector(0, 0, -1));
        assertNull(p1.findIntersections(r4), "findIntersections() did not return the correct value for TC04");

        // TC05: point on vertex
        Ray r5 = new Ray(new Point3D(.5, 0, 1), new Vector(0, 0, -1));
        assertNull(p1.findIntersections(r5), "findIntersections() did not return the correct value for TC05");

        // TC06: point on edges continuation
        Ray r6 = new Ray(new Point3D(2, 0, 1), new Vector(0, 0, -1));
        assertNull(p1.findIntersections(r6), "findIntersections() did not return the correct value for TC06");
    }
    
}
