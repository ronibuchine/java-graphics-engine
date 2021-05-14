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
        double effects = Math.pow((direction.dotProduct(getL(p)) > 0 ? direction.dotProduct(getL(p)) : 0) / (kC + kL*getL(p).length() + kQ*getL(p).length()*getL(p).length()), beam);
        return super.getIntensity().scale(effects);
        //return super.getIntensity().scale((direction.dotProduct(getL(p)) > 0 ? direction.dotProduct(getL(p)) : 0)).reduce(kC + kL*getL(p).length() + kQ*getL(p).length()*getL(p).length());
    }
}
