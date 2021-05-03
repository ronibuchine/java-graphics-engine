package integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.junit.Test;

import elements.Camera;
import geometries.Sphere;
import geometries.Geometries;
import primitives.*;

public class CameraIntegrationTests {

    Camera cam = new Camera(new Point3D(1.5, -1, 1.5), new Vector(0, 1, 0), new Vector(0, 0, 1)).setViewPlaneSize(3, 3).setDistance(1);

    @Test
    public void testConstructRayThroughPixelSphere() {

        // TC01: sphere of r=1 centered before a 3x3 viewplane

        Sphere s1 = new Sphere(new Point3D(1.5, 2, 1.5), 1);

        LinkedList<Point3D> list = new LinkedList<>();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(s1).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("One Camera ray intersects sphere", 2, list.size());


        //TC02: sphere of r=2.5, overlapping view plane
        Sphere s2 = new Sphere(new Point3D(1.5, 2, 1.5), 2.5);

        list.clear();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(s2).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("All of Camera's rays intersect sphere", 18, list.size());

        //TC03: sphere of r=2, overlapping view plane except for corners
        Sphere s3 = new Sphere(new Point3D(1.5, 1.5, 1.5), 2);

        list.clear();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(s3).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("Most of Camera's rays intersect sphere", 10, list.size());

        //TC04: sphere overlaps Camera, resulting in one intersection per ray
        Sphere s4 = new Sphere(new Point3D(1.5, 0, 1.5), 4);

        list.clear();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(s4).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("All of Camera's rays intersect sphere once", 9, list.size());

        //TC05: sphere behing Camera; no intersections
        Sphere s5 = new Sphere(new Point3D(1.5, -2, 1.5), .5);

        list.clear();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(s5).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("None of Camera's rays intersect sphere", 0, list.size());
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
