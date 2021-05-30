package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class representing a light source emitting from a single point and is pointed in a direction (does attenuate)
 */
public class SpotLight extends PointLight {

    private Vector direction;
    
    /**
     * Controls how narrow beam is (higher is narrower)
     */
    private double beam = 1;

    public SpotLight(Color intensity, Point3D point, Vector direction) {
        super(intensity, point);
        this.direction = direction.normalized();
    }

    /**
     * Sets narrowness of beam (higher is narrower)
     * @param beam
     * @return
     */
    public SpotLight setBeam(double beam) {
        this.beam = beam;
        return this;
    }

    @Override
    public Color getIntensity(Point3D p) {
        if (direction.dotProduct(getL(p)) <= 0) return Color.BLACK;
        return super.getIntensity(p).scale(Math.pow(direction.dotProduct(getL(p)), beam));
    }
}
