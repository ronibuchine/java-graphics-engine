package renderer;

import java.util.MissingResourceException;
import elements.Camera;
import primitives.Color;
import scene.Scene;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs Render class used to render the images in our scene
 */
public class Render {

    ImageWriter imageWriter;

    Scene scene;

    Camera camera;

    RayTracerBasic rayTracer;

    /**
     * sets the image writer field
     * 
     * @param imageWriter
     */
    public void setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
    }

    /**
     * Sets the scene field
     * 
     * @param scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Sets the camera field
     * 
     * @param camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Sets the ray tracer field
     * 
     * @param rayTracer
     */
    public void setRayTracer(RayTracerBasic rayTracer) {
        this.rayTracer = rayTracer;
    }

    /**
     * currently only checks if Render fields are null or not
     */
    public void renderImage() throws MissingResourceException, UnsupportedOperationException {
        if (imageWriter == null || scene == null || camera == null || rayTracer == null)
            throw new MissingResourceException(null, null, null);
        throw new UnsupportedOperationException();
    }

    /**
     * method that prints grid lines to be rendered
     * 
     * @param interval to make the lines
     * @param color    color of the grid lines
     */
    public void printGrid(int interval, Color color) {
        // TODO: implementation
    }

    /**
     * Method calls {@link ImageWriter} method of writeToImage() assuming that it is
     * not a null value
     */
    public void writeToImage() throws MissingResourceException {
        if (imageWriter != null) {
            imageWriter.writeToImage();
        }
        throw new MissingResourceException(null, null, null);
    }
}
