package geometries;

import primitives.*;
/**
 * @author Roni Buchine 
 * @author Eliezer Jacobs
 */
public class Sphere {

    /**
     * Center of the sphere
     */
    Point3D center;

    /**
     * radius of the sphere
     */
    double radius;

    /**
     * Constructor that takes center and radius parameters
     * @param center the center of the sphere to be constructed
     * @param radius the radius of the sphere to be constructed
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * getter for the center of the sphere
     * @return the center field of the sphere
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * getter for the radius of the sphere
     * @return radius field of the sphere
     */
    public double getRadius() {
        return radius;
    }

    /**
     * calculates normal vector from a given point on the sphere
     * @param p0 point passed to get the normal from
     * @return the normal Vector from the given point
     */
    public Vector getNormal(Point3D p0) {        
        return center.subtract(p0).normalized();
    }
    
}
