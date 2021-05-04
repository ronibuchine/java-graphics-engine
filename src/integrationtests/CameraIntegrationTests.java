package integrationtests;

import static org.junit.Assert.assertEquals;
import java.util.LinkedList;
import org.junit.Test;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Plane;
import primitives.*;

/**
 * Itegration Test class for {@link Camera} tests, specifically testing
 * findIntersections() and constructRayThroughPixel()
 */
public class CameraIntegrationTests {

    /**
     * Method which constructs the list of all intersections through the scene
     * geometries
     * 
     * @param geo list of {@link Geometry Geometries} of the scene
     */
    private void constructList(Geometry... geo) {
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(geo).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
    }

    Camera cam = new Camera(new Point3D(1.5, -1, 1.5), new Vector(0, 1, 0), new Vector(0, 0, 1)).setViewPlaneSize(3, 3)
            .setDistance(1);
    LinkedList<Point3D> list = new LinkedList<>();

    /**
     * Integration test cases to test for how many intersections are generated for a
     * sphere in a scene with the camera
     */
    @Test
    public void testConstructRayThroughPixelSphere() {

        // TC01: sphere of r=1 centered before a 3x3 viewplane

        Sphere s1 = new Sphere(new Point3D(1.5, 2, 1.5), 1);
        constructList(s1);

        assertEquals("One Camera ray intersects sphere", 2, list.size());

        // TC02: sphere of r=2.5, overlapping view plane
        Sphere s2 = new Sphere(new Point3D(1.5, 2, 1.5), 2.5);

        list.clear();
        constructList(s2);

        assertEquals("All of Camera's rays intersect sphere", 18, list.size());

        // TC03: sphere of r=2, overlapping view plane except for corners
        Sphere s3 = new Sphere(new Point3D(1.5, 1.5, 1.5), 2);

        list.clear();
        constructList(s3);

        assertEquals("Most of Camera's rays intersect sphere", 10, list.size());

        // TC04: sphere overlaps Camera, resulting in one intersection per ray
        Sphere s4 = new Sphere(new Point3D(1.5, 0, 1.5), 4);

        list.clear();
        constructList(s4);

        assertEquals("All of Camera's rays intersect sphere once", 9, list.size());

        // TC05: sphere behing Camera; no intersections
        Sphere s5 = new Sphere(new Point3D(1.5, -2, 1.5), .5);

        list.clear();
        constructList(s5);

        assertEquals("None of Camera's rays intersect sphere", 0, list.size());
    }

    /**
     * Integration test cases to test for how many intersections are generated for a
     * plane in a scene with the camera
     */
    @Test
    public void testConstructRayThroughPixelPlane() {

        // TC01: plane is parallel to view plane
        Plane p1 = new Plane(new Point3D(0, 3, 0), new Point3D(-3, 3, 5), new Point3D(5, 3, -1));

        constructList(p1);
        assertEquals("All of Camera's rays hit plane", 9, list.size());

        // TC02: plane slopes slightly away from view plane
        Plane p2 = new Plane(new Point3D(0, 2, 0), new Point3D(3, 2, 0), new Point3D(1.5, 0, 4));

        list.clear();
        constructList(p2);
        assertEquals("All of Camera's rays hit plane", 9, list.size());

        // TC02: plane slopes strongly away from view plane
        Plane p3 = new Plane(new Point3D(0, 5, 0), new Point3D(3, 5, 0), new Point3D(1.5, -1, 4));

        list.clear();
        constructList(p3);
        assertEquals("Some of Camera's rays hit plane", 6, list.size());
    }

    /**
     * Integration test cases to test for how many intersections are generated for a
     * triangle in a scene with the camera
     */
    @Test
    public void testConstructRayThroughPixelTriangle() {

        // TC01: middle ray hits Triangle
        Triangle t1 = new Triangle(new Point3D(1, 0, 1), new Point3D(2, 0, 1), new Point3D(1.5, 0, 2));
        constructList(t1);
        assertEquals("Camera's middle ray hits triangle", 1, list.size());

        // TC02: middle and top-center ray hit Triangle
        list.clear();
        Triangle t2 = new Triangle(new Point3D(1, 0, 1), new Point3D(2, 0, 1), new Point3D(1.5, 0, 3));
        constructList(t2);
        assertEquals("Camera's middle and top-center rays hit triangle", 2, list.size());
    }

}
