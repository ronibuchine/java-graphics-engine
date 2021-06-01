package renderer;

import java.util.MissingResourceException;
import java.util.stream.IntStream;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs Render class used to render the images in our scene
 */
public class Render {

    ImageWriter imageWriter;

    Camera camera;

    RayTraceBase rayTracer;

    /**
     * sets the image writer field
     * 
     * @param imageWriter
     * @return this The object itself
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the scene field
     * 
     * @param scene
     * @return this The object itself
     */
    public Render setScene(Scene scene) {
        rayTracer.scene = scene;
        return this;
    }

    /**
     * Sets the camera field
     * 
     * @param camera
     * @return this The object itself
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Sets the ray tracer field
     * 
     * @param rayTracer
     * @return this The object itself
     */
    public Render setRayTracer(BasicRayTracer rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Calculates Color of rays from camera and writes them onto the ImageWriter
     */
    public void renderImage() {

        if (imageWriter == null || camera == null || rayTracer == null)
            throw new MissingResourceException("One of the Rendering components is null", null, null);

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        IntStream.range(0, nY).forEach(column -> 
            IntStream.range(0, nX).forEach(row -> {
                Ray pixelRay = camera.constructRayThroughPixel(nX, nY, row, column);
                imageWriter.writePixel(row, column, rayTracer.traceRay(pixelRay));
            })
        );
    }

    /**
     * method that prints grid lines to be rendered
     * RUN THIS AFTER {@link Render#renderImage()}
     * 
     * @param interval to make the lines
     * @param color    color of the grid lines
     */
    public void printGrid(int interval, Color color) {
        imageWriter.printGrid(interval, color);
    }

    /**
     * Method calls {@link ImageWriter} method of writeToImage() assuming that it is
     * not a null value
     */
    public void writeToImage() {
        if (imageWriter != null) {
            imageWriter.writeToImage();
        } else
            throw new MissingResourceException("imageWriter has a null value", null, null);
    }
}
