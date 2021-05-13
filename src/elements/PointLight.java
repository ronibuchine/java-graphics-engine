package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class representing a point of light that shines in all directions
 */
public class PointLight extends Light implements LightSource {

    private Point3D position;
    protected double kC = 1;
    protected double kL = 0;
    protected double kQ = 0;

    public PointLight(Color intensity, Point3D point) {
        super(intensity);
        position = point;
    }

    /**
     * Setter method for the attenuation coeffients of Point Light
     * @param kC
     * @param kL
     * @param kQ
     * @return
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity().reduce(kC + kL*getL(p).length() + kQ*getL(p).length()*getL(p).length());
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalized();
    }
}
