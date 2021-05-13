package elements;

import primitives.Color;

/**
 * Abstract class to represent light sources
 */
abstract class Light {
    
    private Color intensity;

    /**
     * Constructor that sets light's intensity
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter method to retrieve light's intensity
     * @return
     */
    public Color getIntensity() {
        return intensity;
    }
}
