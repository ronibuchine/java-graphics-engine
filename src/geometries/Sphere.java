package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Class implements a {@link Sphere} in 3-dimensional coordinate space.
 * @author Roni Buchine 
 * @author Eliezer Jacobs
 */
public class Sphere implements Geometry {

    /**
     * Center of the sphere
     */
    private Point3D center;

    /**
     * radius of the sphere
     */
    private double radius;

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

    @Override
    /**
     * calculates normal {@link Vector} from a given {@link Point3D} on the sphere
     * @param p0 point passed to get the normal from
     * @return the normal {@link Vector} from the given {@link Point3D} on the sphere
     */
    public Vector getNormal(Point3D p0) {        
        return null;
    }
    
}
