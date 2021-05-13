package elements;

import primitives.Color;

/**
 * Class to represent fixed intensity light source that affects all objects in the scene equally
 */
public class AmbientLight extends Light {

    /**
     * Default Constructor sets the ambient light to black
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * Constructor calculates final intensity based on the attenuation factor
     * @param color
     * @param attenuation
     */
    public AmbientLight(Color color, double attenuation) {
        super(color.scale(attenuation));
    }

}
