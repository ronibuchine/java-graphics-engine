package renderer;

import java.util.MissingResourceException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int j = 0; j < nX; j++) {
            for (int i = 0; i < nY; i++) {
                final int row = j;
                final int column = i;
                //j = 366; i = 233; to test line in between triangles
                exec.submit(() -> {
                    Ray pixelRay = camera.constructRayThroughPixel(nX, nY, row, column);
                    imageWriter.writePixel(row, column, rayTracer.traceRay(pixelRay));
                });
            }
        }
        exec.shutdown();
        try {
            exec.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread exception. See cause...", e);
        }
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
