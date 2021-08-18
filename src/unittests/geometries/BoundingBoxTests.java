package unittests.geometries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.BoundingBox;
import geometries.Cylinder;
import geometries.Geometries;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * Testing class for Bounding Box performance enhancements
 * 
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class BoundingBoxTests {

        private Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(50, 0, 0), .6));
        Triangle t1 = new Triangle(Point3D.ZERO, new Point3D(10, 10, 0), new Point3D(10, 0, 10));
        Sphere s1 = new Sphere(Point3D.ZERO, 5);
        Plane p1 = new Plane(new Vector(0, 0, 1), Point3D.ZERO);

        BoundingBox b1 = new BoundingBox();

        /**
         * unit test for creating a bounding box around a single geometry
         */
        @Test
        public void singleGeometryBoxTest() {

                b1.add(s1);

                assertEquals("Single geometry test for bounding box failed to retrieve min point", b1.getMinPoint(),
                                new Point3D(-5, -5, -5));
                assertEquals("Single geometry test for bounding box failed to retrieve max point", b1.getMaxPoint(),
                                new Point3D(5, 5, 5));

        }

        /**
         * unit test for creating a bounding box around multiple geometries
         */
        @Test
        public void multipleGeometryBoxTest() {

                b1.add(s1, t1);

                assertEquals("Mulitple geometry test for bounding box failed to retrieve min point", b1.getMinPoint(),
                                new Point3D(-5, -5, -5));
                assertEquals("Multiple geometry test for bounding box failed to retrieve min point", b1.getMaxPoint(),
                                new Point3D(10, 10, 10));

                try {
                        b1.add(new Geometries(p1));
                        fail("Did not throw exception when trying to add unbounded geometry");
                } catch (IllegalArgumentException e) {
                        assertTrue(true);
                }
        }

        /**
         * unit test to check whether a ray intersects the bounding box
         */
        @Test
        public void boxIntersectionTest() {

                b1.add(new Geometries(s1, t1));
                Ray ray = new Ray(new Point3D(0, -50, 0), new Vector(0, 1, 0));

                assertTrue(b1.getsIntersected(ray));

        }

        @Test
        public void performanceTestManyObjects() {

                Camera camera = new Camera(new Point3D(0, 120, 400), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                                .setViewPlaneSize(150, 150).setDistance(300);
                camera.pitch(-10);

                scene.geometries.add(
                                new Plane(new Vector(0, 1, 0), Point3D.ZERO).setEmission(new Color(50, 150, 100))
                                                .setMaterial(new Material().setKd(.7).setKs(.7).setShininess(100)),
                                // medium podium
                                new Cylinder(15, new Ray(new Point3D(0, 0, -50), new Vector(0, 1, 0)), 1)
                                                .setEmission(new Color(30, 80, 90))
                                                .setMaterial(new Material().setKd(.7).setKs(.7).setShininess(100)),
                                new Cylinder(5, new Ray(new Point3D(0, 1, -50), new Vector(0, 1, 0)), 20)
                                                .setEmission(new Color(30, 80, 90))
                                                .setMaterial(new Material().setKd(.7).setKs(.7).setShininess(100)),
                                new Cylinder(25, new Ray(new Point3D(0, 21, -50), new Vector(0, 1, 0)), 3)
                                                .setEmission(new Color(10, 10, 90))
                                                .setMaterial(new Material().setKd(.5).setKs(.5).setShininess(50)
                                                                .setKt(.8).setKr(.3)),
                                new Sphere(new Point3D(0, 34, -50), 10).setEmission(new Color(30, 80, 90))
                                                .setMaterial(new Material().setKd(.8).setKs(.8).setShininess(100)),
                                // short podium
                                new Cylinder(15, new Ray(new Point3D(-35, 0, 50), new Vector(0, 1, 0)), 1)
                                                .setEmission(new Color(30, 80, 90))
                                                .setMaterial(new Material().setKd(.7).setKs(.7).setShininess(100)),
                                new Cylinder(5, new Ray(new Point3D(-35, 1, 50), new Vector(0, 1, 0)), 10)
                                                .setEmission(new Color(30, 80, 90))
                                                .setMaterial(new Material().setKd(.7).setKs(.7).setShininess(100)),
                                new Cylinder(25, new Ray(new Point3D(-35, 11, 50), new Vector(0, 1, 0)), 3)
                                                .setEmission(new Color(10, 10, 90))
                                                .setMaterial(new Material().setKd(.5).setKs(.5).setShininess(50)
                                                                .setKt(.8).setKr(.3)),
                                new Sphere(new Point3D(-35, 24, 50), 10).setEmission(new Color(130, 80, 90))
                                                .setMaterial(new Material().setKd(.7).setKs(.7).setShininess(100)),
                                // tall podium
                                new Cylinder(15, new Ray(new Point3D(35, 0, 50), new Vector(0, 1, 0)), 1)
                                                .setEmission(new Color(30, 80, 90))
                                                .setMaterial(new Material().setKd(.7).setKs(.7).setShininess(100)),
                                new Cylinder(5, new Ray(new Point3D(35, 1, 50), new Vector(0, 1, 0)), 30)
                                                .setEmission(new Color(30, 80, 90))
                                                .setMaterial(new Material().setKd(.7).setKs(.7).setShininess(100)),
                                new Cylinder(25, new Ray(new Point3D(35, 31, 50), new Vector(0, 1, 0)), 3)
                                                .setEmission(new Color(10, 10, 90))
                                                .setMaterial(new Material().setKd(.5).setKs(.5).setShininess(50)
                                                                .setKt(.8).setKr(.3)),
                                new Sphere(new Point3D(35, 44, 50), 10).setEmission(new Color(70, 180, 6))
                                                .setMaterial(new Material().setKd(.7).setKs(.7).setShininess(100)),
                                // outer sphere

                                new Polygon(new Point3D(-80, 0, 0), new Point3D(-80, 80, 0), new Point3D(80, 80, 0),
                                                new Point3D(80, 0, 0)).setEmission(new Color(0, 0, 70)).setMaterial(
                                                                new Material().setKd(.5).setKs(.5).setShininess(50)
                                                                                .setKt(.8).setGloss(20)),
                                new Sphere(new Point3D(100, 110, -100), 38).setEmission(new Color(50, 50, 50))
                                                .setMaterial(new Material().setKd(.5).setKs(.5).setShininess(70)
                                                                .setKr(.4).setGloss(20)));

                // scene.lights.add(new PointLight(new Color(100, 100, 100), new Point3D(-100,
                // 30, -200)));
                // scene.lights.add(new PointLight(new Color(100, 100, 100), new Point3D(500,
                // 350, 450)));
                scene.lights.add(new PointLight(new Color(100, 100, 100), new Point3D(32, 32, 55)));
                scene.lights.add(new PointLight(new Color(100, 100, 100), new Point3D(-32, 12, 55)));
                scene.lights.add(new PointLight(new Color(100, 100, 100), new Point3D(1, 22, -50)));

                // unoptimized 1714 seconds
                Render render = new Render().setImageWriter(new ImageWriter("podiums", 1000, 1000)).setCamera(camera)
                                .setCamera(camera).setRayTracer(new BasicRayTracer(scene).setRayCount(50))
                                .setDebugPrint();
                render.renderImage();
                render.writeToImage();

                // threading 1228 seconds
                render = new Render().setImageWriter(new ImageWriter("podiums", 1000, 1000)).setCamera(camera)
                                .setCamera(camera).setRayTracer(new BasicRayTracer(scene).setRayCount(50))
                                .setMultithreading(3).setDebugPrint();
                render.renderImage();
                render.writeToImage();

                // full optimization, threading + AABB 660 seconds
                render = new Render().setImageWriter(new ImageWriter("podiums", 1000, 1000)) // .setCamera(camera)
                                .setCamera(camera) // //
                                .setRayTracer(new BasicRayTracer(scene.createHierarchy()).setRayCount(50))
                                .setMultithreading(3).setDebugPrint();
                render.renderImage();
                render.writeToImage();

        }
}
