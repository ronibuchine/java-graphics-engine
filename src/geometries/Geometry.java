package geometries;

import primitives.*;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public interface Geometry {

    /**
     * abstract method used to get the normal vector for a given geometry.
     * @param p the point used to calculate the normal vector
     * @return The normal vector at the point p
     */
    public Vector getNormal(Point3D p);
    
}
