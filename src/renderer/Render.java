package renderer;

import java.util.MissingResourceException;
import elements.Camera;
import scene.Scene;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs Render class used to render the images in our scene
 */
public class Render {

    ImageWriter imageWriter;

    Scene scene;

    Camera camera;

    BasicRayTracer rayTracer;

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
        this.scene = scene;
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
     * currently only checks if Render fields are null or not
     */
    public void renderImage() {
        if (imageWriter == null || scene == null || camera == null || rayTracer == null)
            throw new MissingResourceException("One of the Rendering components is null", null, null);
        throw new UnsupportedOperationException();
    }

    /**
     * method that prints grid lines to be rendered
     * 
     * @param interval to make the lines
     * @param color    color of the grid lines
     */
    public void printGrid(int interval) {
        imageWriter.printGrid(interval);
    }

    /**
     * Method calls {@link ImageWriter} method of writeToImage() assuming that it is
     * not a null value
     */
    public void writeToImage() {
        if (imageWriter != null) {
            imageWriter.writeToImage();
        } else
            throw new MissingResourceException(null, null, null);
    }
}
