package unittests.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import geometries.Plane;
import geometries.Tube;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.ImageWriter;

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

        Ray r = new Ray(new Point3D(.5, .5, .5), new Vector(1, 1, 1));
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(2, 2, 2);
        Point3D p3 = new Point3D(3, 3, 3);
        Point3D p4 = new Point3D(4, 4, 4);

        // ============= Equivalence Tests ==============
        // TC01: point in middle of list
        List<Point3D> list = new LinkedList<>(Arrays.asList(p3, p2, p1, p4));
        assertEquals("Returned incorrect value for TC01", p1, r.findClosestPoint(list));

        // ============= Boundary Tests =============
        // TC02: point at beginning of list
        list = new LinkedList<>(Arrays.asList(p1, p2, p3, p4));
        assertEquals("Returned incorrect value for TC02", p1, r.findClosestPoint(list));

        // TC03: point at end of list
        list = new LinkedList<>(Arrays.asList(p4, p3, p2, p1));
        assertEquals("Returned incorrect value for TC03", p1, r.findClosestPoint(list));

        // TC04: empty list
        list.clear();
        assertNull("Returned incorrect value for TC04", r.findClosestPoint(list));

    }

    /**
     * Unit test for findClosestGeoPoint method in {@link Ray}
     */
    @Test
    public void testFindClosestGeoPoint() {
        Ray r = new Ray(new Point3D(.5, .5, .5), new Vector(1, 1, 1));

        //The following GeoPoints aren't real. Shouldn't matter though...
        GeoPoint p1 = new GeoPoint(new Tube(3, r), new Point3D(1, 1, 1));
        GeoPoint p2 = new GeoPoint(new Tube(3, r), new Point3D(2, 2, 2));
        GeoPoint p3 = new GeoPoint(new Tube(3, r), new Point3D(3, 3, 3));
        GeoPoint p4 = new GeoPoint(new Tube(3, r), new Point3D(4, 4, 4));

        // ============= Equivalence Tests ==============
        // TC01: point in middle of list
        List<GeoPoint> list = new LinkedList<>(Arrays.asList(p3, p2, p1, p4));
        assertEquals("Returned incorrect value for TC01", p1, r.findClosestGeoPoint(list));

        // ============= Boundary Tests =============
        // TC02: point at beginning of list
        list = new LinkedList<>(Arrays.asList(p1, p2, p3, p4));
        assertEquals("Returned incorrect value for TC02", p1, r.findClosestGeoPoint(list));

        // TC03: point at end of list
        list = new LinkedList<>(Arrays.asList(p4, p3, p2, p1));
        assertEquals("Returned incorrect value for TC03", p1, r.findClosestGeoPoint(list));

        // TC04: empty list
        list.clear();
        assertNull("Returned incorrect value for TC04", r.findClosestGeoPoint(list));
    }

    @Test
    public void testConstructRefractedRays() {
        
        double spread = 1;  //width of spread (higher is narrower)
        int rays = 500;     //number of rays to generate
        double loops = 43;  //number of times to loop in a circle (0 for random distribution)

        Plane p1 = new Plane(new Vector(0, 0, 1), new Point3D(0, 0, -250));
        GeoPoint gp = new GeoPoint(p1, Point3D.ZERO);
        ImageWriter image = new ImageWriter("refractedRays", 500, 500);
        Color white = new Color(java.awt.Color.WHITE);
        List<Ray> rayList = Ray.constructRefractedRays(gp, new Vector(0, 0, -1), spread, rays, loops);
        for (Ray r : rayList) {
            Point3D intersection = r.findClosestPoint(p1.findIntersections(r));
            int x = (int)intersection.getX() + 250;
            int y = (int)intersection.getY() + 250;
            image.writePixel(x, y, white);
        }
        image.writeToImage();
    }
}
