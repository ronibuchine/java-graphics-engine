package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class representing a light source (with no source point) that shines from a direction and doesn't attenuate (aka the sun)
 */
public class DirectionalLight extends Light implements LightSource {
    
    private Vector direction;
    
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return direction;
    }

    @Override
    public double getDistance(Point3D p) {
        return Double.POSITIVE_INFINITY;
    }

    
}
