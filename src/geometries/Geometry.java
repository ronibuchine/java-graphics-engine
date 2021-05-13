package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * an abstract class used by the classes in the {@link Geometries} package to implement methods that are package wide
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public abstract class Geometry implements Intersectable {

    protected Color emission = Color.BLACK;

    /**
     * Getter method for {@link Geometry}'s {@link Color}
     * @return {@link Color}
     */
    public Color getEmission() {
        return emission;
    }
    /**
     * Setter method for {@link Geometry}'s {@link Color}
     * @param c The new {@link Color} of {@link Geometry}
     * @return The {@link Geometry}
     */
    public Geometry setEmission(Color c) {
        emission = c;
        return this;
    }

    /**
     * Abstract method used to get the normal vector for a given geometry.
     * @param p the point used to calculate the normal vector
     * @return The normal {@link Vector} at the {@link Point3D} p
     */
    public abstract Vector getNormal(Point3D p);
    
}
