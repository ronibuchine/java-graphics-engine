package unittests.renderer;

import java.io.File;

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
public class RenderTests {
	private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setDistance(100) //
			.setViewPlaneSize(500, 500);

	/**
	 * Produce a scene with basic 3D model and render it into a jpeg image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {

		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
																													// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
																													// right
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
																														// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
																													// right

		// TC01: first rendering test (non XML)
		ImageWriter imageWriter = new ImageWriter("TC01", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setScene(scene) //
				.setCamera(camera) //
				.setRayTracer(new BasicRayTracer(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.YELLOW));
		render.writeToImage();
	}

	/**
	 * Test for XML based scene - for bonus
	 */
	@Test
	public void basicRenderXml() {
		Scene scene = new Scene("XML Test scene");
		// enter XML file name and parse from XML file into scene object
		SceneBuilder sceneBuilder = new SceneBuilder("XML Test scene",
				new File("src/unittests/xml/basicRenderTestTwoColors.xml").getPath());
		scene = sceneBuilder.build();

		// XML_TC01: parse from XML file
		ImageWriter imageWriter = new ImageWriter("XML_TC01", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setScene(scene) //
				.setCamera(camera) //
				.setRayTracer(new BasicRayTracer(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.YELLOW));
		render.writeToImage();
	}

	@Test
	public void testTubeRender() {
		SceneBuilder builder = new SceneBuilder("XML Tube test scene",
				new File("src/unittests/xml/tubes.xml").getPath());
		Scene scene = builder.build();

		// TC02: xml test with tubes and cylinders
		Render render = new Render().setImageWriter(new ImageWriter("XML_TC02", 1000, 1000))
				.setRayTracer(new BasicRayTracer(scene))
				.setCamera(new Camera(new Point3D(0, 25, 50), new Vector(0, 0, -1), new Vector(0, 1, 0)))
				.setScene(scene);

		render.renderImage();
		render.writeToImage();
	}

}
