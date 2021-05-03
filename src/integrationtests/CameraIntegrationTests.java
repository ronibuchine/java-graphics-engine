package integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.junit.Test;

import elements.Camera;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class CameraIntegrationTests {

    static final double ACCURACY = 0.0000000001;

    @Test
    public void testConstructRayThroughPixelSphere() {

        // TC01: sphere of r=1 centered before a 3x3 viewplane
        Camera cam = new Camera(new Point3D(1.5, -1, 1.5), new Vector(0, 1, 0), new Vector(0, 0, 1));
        cam.setViewPlaneSize(3, 3);
        cam.setDistance(1);

        Sphere s1 = new Sphere(new Point3D(1.5, 2, 1.5), 1);

        LinkedList<Ray> raylist = new LinkedList<>();
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                raylist.add(cam.constructRayThroughPixel(3, 3, j, i));
            }
        }

        LinkedList<Point3D> returnlist = new LinkedList<>();
        for (Ray ray : raylist) {
            returnlist.addAll(s1.findIntersections(ray));
        }
        assertEquals(2, returnlist.size(), ACCURACY);

    }

    @Test
    public void testConstructRayThroughPixelPlane() {
        fail("unimplemented");
    }

    @Test
    public void testConstructRayThroughPixelTriangle() {
        fail("unimplemented");
    }

}
