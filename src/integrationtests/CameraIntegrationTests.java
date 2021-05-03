package integrationtests;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Geometries;
import geometries.Plane;
import primitives.*;

public class CameraIntegrationTests {

    Camera cam = new Camera(new Point3D(1.5, -1, 1.5), new Vector(0, 1, 0), new Vector(0, 0, 1)).setViewPlaneSize(3, 3).setDistance(1);
    LinkedList<Point3D> list = new LinkedList<>();

    @Test
    public void testConstructRayThroughPixelSphere() {

        // TC01: sphere of r=1 centered before a 3x3 viewplane

        Sphere s1 = new Sphere(new Point3D(1.5, 2, 1.5), 1);

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

        //TC01: plane is parallel to view plane
        Plane p1 = new Plane(new Point3D(0, 3, 0), new Point3D(-3, 3, 5), new Point3D(5, 3, -1));

        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(p1).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("All of Camera's rays hit plane", 9, list.size());

        //TC02: plane slopes slightly away from view plane
        Plane p2 = new Plane(new Point3D(0, 2, 0), new Point3D(3, 2, 0), new Point3D(1.5, 0, 4));

        list.clear();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(p2).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("All of Camera's rays hit plane", 9, list.size());
        
        //TC02: plane slopes strongly away from view plane
        Plane p3 = new Plane(new Point3D(0, 5, 0), new Point3D(3, 5, 0), new Point3D(1.5, -1, 4));

        list.clear();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(p3).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("Some of Camera's rays hit plane", 6, list.size());
    }

    @Test
    public void testConstructRayThroughPixelTriangle() {
        
        //TC01: middle ray hits Triangle
        Triangle t1 = new Triangle(new Point3D(1, 0, 1), new Point3D(2, 0, 1), new Point3D(1.5, 0, 2));
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(t1).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("Camera's middle ray hits triangle", 1, list.size());

        //TC02: middle and top-center ray hit Triangle
        list.clear();
        Triangle t2 = new Triangle(new Point3D(1, 0, 1), new Point3D(2, 0, 1), new Point3D(1.5, 0, 3));
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; i++) {
                list.addAll(new Geometries(t2).findIntersections(cam.constructRayThroughPixel(3, 3, j, i)));
            }
        }
        assertEquals("Some of Camera's rays hit plane", 2, list.size());
    }

}
