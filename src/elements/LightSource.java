package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Interface to represent a light source
 */
public interface LightSource {
    
    /**
     * Getter to retrieve light source's affect on a given {@link Point3D}
     * @param p {@link Point3D}
     * @return {@link Color}
     */
    public Color getIntensity(Point3D p);

    /**
     * Calculates vector from the light source to a given {@link Point3D}
     * @param p {@link Point3D}
     * @return {@link Vector} from light source to p
     */
    public Vector getL(Point3D p);

    /**
     * Calculates distance from a point to the light source
     * @param p
     * @return The distance from p to light source
     */
    public double getDistance(Point3D p);

}
