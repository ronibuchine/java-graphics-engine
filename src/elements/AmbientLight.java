package elements;

import primitives.Color;

/**
 * Class to represent fixed intensity light source that affects all objects in the scene equally
 */
public class AmbientLight {

    private Color intensity;

    /**
     * Constructor calculates final intensity based on the attenuation factor
     * @param color
     * @param attenuation
     */
    public AmbientLight(Color color, double attenuation) {
        intensity = color.scale(attenuation);
    }

    /**
     * Getter for the Color of the ambient light
     * @return {@link Color} representing ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}
