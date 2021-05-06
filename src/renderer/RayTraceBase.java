package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class for implementing the core ray tracing
 * method in our graphics engine
 * @author Roni Buchine
 * @author Eliezer Jacobs 
 */
public abstract class RayTraceBase {

    protected Scene scene;

    /**
     * Constructor which recieves a {@link Scene} as a paramter
     * 
     * @param scene The scene where we will be conducting the ray tracing
     */
    protected RayTraceBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract method for ray tracing
     * 
     * @param r the Ray being used to get the color of the objects in the scene
     */
    public abstract Color traceRay(Ray r);
}
