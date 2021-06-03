/**
 * 
 */
package unittests.lights;

import org.junit.Test;

import elements.*;
import geometries.Cylinder;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Tube;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -50), 50) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point3D(0, 0, -50), 25) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setCamera(camera) //
				.setRayTracer(new BasicRayTracer(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(2500, 2500).setDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point3D(-950, -900, -1000), 400) //
						.setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
				new Sphere(new Point3D(-950, -900, -1000), 200) //
						.setEmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(670, 670, 3000)) //
								.setEmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setKr(1)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(-1500, -1500, -2000)) //
								.setEmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setKr(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new BasicRayTracer(scene));

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point3D(60, 50, -50), 30) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new BasicRayTracer(scene));

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void manyShapes() {

		Camera camera = new Camera(new Point3D(0, 0, 300), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(300);

		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -50), 15) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(100).setKt(0.8)),
				new Cylinder(10, new Ray(new Point3D(-30, 10, 20), new Vector(1, 1, 1)), 30)
						.setEmission(new Color(java.awt.Color.green))
						.setMaterial(new Material().setKd(.7).setKs(.7).setShininess(50).setKt(.1).setKr(.3)),
				new Sphere(new Point3D(50, -20, -50), 25) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(100).setKr(.8)),
				new Plane(new Vector(0, 1, 0), new Point3D(0, -50, 0))
						.setMaterial(new Material().setKd(.5).setKt(.3).setKr(.1)));
		scene.lights.add(new PointLight(new Color(200, 200, 200), new Point3D(10, 80, 40)));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("manyShapes", 1000, 1000)) //
				.setCamera(camera) //
				.setRayTracer(new BasicRayTracer(scene)).setMultithreading(0).setDebugPrint();
		render.renderImage();
		render.writeToImage();

		camera.move(0, 100, 50);
		camera.pitch(-10);
		render.setImageWriter(new ImageWriter("manyShapesRotated1", 1000, 1000));
		render.renderImage();
		render.writeToImage();

		camera.yaw(30);
		camera.move(100, -100, 0);
		render.setImageWriter(new ImageWriter("manyShapesRotated2", 1000, 1000));
		render.renderImage();
		render.writeToImage();

		camera.move(0, 0, 300);
		camera.pitch(-50);
		render.setImageWriter(new ImageWriter("manyShapesRotated3", 1000, 1000));
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void testDiffusion() {
		Camera camera = new Camera(new Point3D(0, 0, 300), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(300);
		scene.geometries.add(new Sphere(new Point3D(25, 0, -50), 15) //
				.setEmission(new Color(140, 25, 25)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(200).setKt(0.05)),
				new Sphere(new Point3D(-25, 0, -50), 15) //
						.setEmission(new Color(50, 50, 200)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(200).setKt(0.05)),
				new Plane(new Vector(0, 1, 0), new Point3D(0, -50, 0)).setEmission(new Color(50, 50, 50))
						.setMaterial(new Material().setKd(.5).setKt(.3).setKr(.1)),
				new Polygon(new Point3D(-25, -80, -25), new Point3D(25, -80, -25), new Point3D(25, 100, -25),
						new Point3D(-25, 100, -25)).setEmission(new Color(134, 140, 140))
								.setMaterial(new Material().setKt(.8).setGloss(5)));

		scene.lights.add(new PointLight(new Color(200, 200, 200), new Point3D(60, 70, -60)));
		scene.lights.add(new PointLight(new Color(200, 200, 200), new Point3D(-60, 70, -60)));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("diffusedglass", 1000, 1000)) //
				.setCamera(camera) //
				.setRayTracer(new BasicRayTracer(scene)).setMultithreading(0).setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void testGlossiness() {
		Camera camera = new Camera(new Point3D(0, 0, 300), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(300);
		scene.geometries.add(
				new Plane(new Vector(0, 1, 0), new Point3D(0, -50, 0)).setEmission(new Color(200, 0, 0))
						.setMaterial(new Material().setKd(.5)),
				new Tube(10, new Ray(Point3D.ZERO, new Vector(0, 10, 0))).setEmission(new Color(0, 0, 255))
						.setMaterial(new Material().setKd(.5).setKr(.8).setGloss(3)));

		scene.lights.add(new PointLight(new Color(200, 200, 200), new Point3D(0, 50, -60)));

		scene.lights.add(new PointLight(new Color(200, 200, 200), new Point3D(0, 50, -60)));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("glossiness", 1000, 1000)) //
				.setCamera(camera) //
				.setRayTracer(new BasicRayTracer(scene)).setMultithreading(0).setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}
}