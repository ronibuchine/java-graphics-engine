package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * an interfacce used by the classes in the geometries package to implement methods that are package wide
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public interface Geometry {

    /**
     * Abstract method used to get the normal vector for a given geometry.
     * @param p the point used to calculate the normal vector
     * @return The normal vector at the point p
     */
    public Vector getNormal(Point3D p);
    
}
