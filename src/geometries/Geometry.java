package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * an interface used by the classes in the {@link Geometries} package to implement methods that are package wide
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public interface Geometry extends Intersectable {

    /**
     * Abstract method used to get the normal vector for a given geometry.
     * @param p the point used to calculate the normal vector
     * @return The normal {@link Vector} at the {@link Point3D} p
     */
    public Vector getNormal(Point3D p);
    
}
