package unittests.lights;

import java.io.File;
import java.util.List;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import scene.SceneBuilder;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class LightsTests {
	private Scene scene1 = new Scene("Test scene");
	private Scene scene2 = new Scene("Test scene") //
			.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
	private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(150, 150) //
			.setDistance(1000);
	private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200) //
			.setDistance(1000);

	private static Geometry triangle1 = new Triangle( //
			new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
	private static Geometry triangle2 = new Triangle( //
			new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
	private static Geometry sphere = new Sphere(new Point3D(0, 0, -50), 50) //
			.setEmission(new Color(java.awt.Color.BLUE)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));

	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

		ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera1) //
				.setRayTracer(new BasicRayTracer(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new PointLight(new Color(500, 300, 0), new Point3D(-50, -50, 50))//
				.setKl(0.00001).setKq(0.000001));

		ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera1) //
				.setRayTracer(new BasicRayTracer(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void sphereSpot() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2)) //
				.setKl(0.00001).setKq(0.00000001));

		ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera1) //
				.setRayTracer(new BasicRayTracer(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)), //
				triangle2.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)));
		scene2.lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new BasicRayTracer(scene2));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a point light
	 */
	@Test
	public void trianglesPoint() {
		scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)), //
				triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
		scene2.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
				.setKl(0.0005).setKq(0.0005));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new BasicRayTracer(scene2));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light
	 */
	@Test
	public void trianglesSpot() {
		scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
				triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
		scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1)) //
				.setKl(0.0001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new BasicRayTracer(scene2));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a narrow spot light
	 */
	@Test
	public void trianglesNarrowSpot() {
		scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
				triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
		scene2.lights.add(
				new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1)).setBeam(3) //
						.setKl(0.0001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesNarrowSpot", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new BasicRayTracer(scene2));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by multiple points
	 */
	@Test
	public void trianglesLights() {
		scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)), //
				triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
		scene2.lights.addAll(0, List.of(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
				.setKl(0.0005).setKq(0.0005),
				new SpotLight(new Color(400, 400, 400), new Point3D(50, 20, -140), new Vector(-1, 1, 0)),
				new DirectionalLight(new Color(0, 200, 100), new Vector(1, -1, -1)),
				new SpotLight(new Color(600, 600, 600), new Point3D(-80, -80, -140), new Vector(1, 1, -1)).setBeam(5)));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesLights", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new BasicRayTracer(scene2));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void sphereLights() {
		scene1.geometries.add(sphere);
		scene1.lights.addAll(0, List.of(new PointLight(new Color(300, 100, 0), new Point3D(-150, -50, 50))//
				.setKl(0.00001).setKq(0.000001), new DirectionalLight(new Color(300, 300, 300), new Vector(-1, -1, 0)),
				new SpotLight(new Color(500, 0, 0), new Point3D(50, -50, 50), new Vector(-1, 1, -3)).setBeam(30)));

		ImageWriter imageWriter = new ImageWriter("lightSphereLights", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera1) //
				.setRayTracer(new BasicRayTracer(scene1));
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void lightsFromXml() {
		Scene scene = new SceneBuilder("test", new File("src/unittests/xml/tubes.xml").getPath()).build();
		ImageWriter imageWriter = new ImageWriter("lightsFromXml", 500, 500);
		Render render = new Render().setImageWriter(imageWriter)
				.setCamera(new Camera(new Point3D(0, 20, 50), new Vector(0, 0, -1), new Vector(0, 1, 0)))
				.setRayTracer(new BasicRayTracer(scene));
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void sphereLightsXml() {
		Scene scene1 = new SceneBuilder("test", new File("src/unittests/xml/sphere.xml").getPath()).build();
		ImageWriter imageWriter = new ImageWriter("lightSphereLightsXML", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera1) //
				.setRayTracer(new BasicRayTracer(scene1));
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void manyShapesXml() {
		Camera camera = new Camera(new Point3D(0, 50, 100), new Vector(0, 0, -1), new Vector(0, 1, 0));
		Scene scene = new SceneBuilder("test", new File("src/unittests/xml/manyShapes.xml").getPath()).build();
		ImageWriter imageWriter = new ImageWriter("manyShapesXml", 1000, 1000);
		Render render = new Render().setImageWriter(imageWriter).setCamera(camera)
				.setRayTracer(new BasicRayTracer(scene));

		render.renderImage();
		render.writeToImage();

		camera.pitch(-17);
		render.setImageWriter(new ImageWriter("manyShapesXMLAfterMove1", 1000, 1000));
		render.renderImage();
		render.writeToImage();

		camera.move(0, 200, 0);
		camera.yaw(180);
		render.setImageWriter(new ImageWriter("manyShapesXMLAfterMove2", 1000, 1000));
		render.renderImage();
		render.writeToImage();

		camera.move(0, 50, 150);
		camera.pitch(-75);
		render.setImageWriter(new ImageWriter("manyShapesXMLAfterMove3", 1000, 1000));
		render.renderImage();
		render.writeToImage();

	}

}
