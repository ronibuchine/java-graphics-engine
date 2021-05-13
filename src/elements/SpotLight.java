package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class representing a light source emitting from a single point and is pointed in a direction (does attenuate)
 */
public class SpotLight extends PointLight {

    private Vector direction;

    public SpotLight(Color intensity, Point3D point, Vector direction) {
        super(intensity, point);
        this.direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity().scale((direction.dotProduct(getL(p)) > 0 ? direction.dotProduct(getL(p)) : 0)).reduce(kC + kL*getL(p).length() + kQ*getL(p).length()*getL(p).length());
    }
}
