package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;
import static primitives.Util.*;

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
     * Radius of the sphere
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
     * Getter for the center of the sphere
     * @return the center field of the sphere
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * Getter for the radius of the sphere
     * @return radius field of the sphere
     */
    public double getRadius() {
        return radius;
    }

    @Override
    /**
     * calculates normal {@link Vector} from a given {@link Point3D} on the sphere
     * @param p0 intersection point to get the normal from
     * @return the normal {@link Vector} from the given {@link Point3D} on the sphere
     */
    public Vector getNormal(Point3D p0) {        
        return p0.subtract(center).normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray r) {
        Vector u = center.subtract(r.getStartPoint()); //vector from ray start to center of sphere
        double t = alignZero(r.getDir().dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - t*t));
        if (d > radius) return null;
        List<Point3D> list = new LinkedList<>();
        double length = alignZero(Math.sqrt(radius*radius - d*d));
        list.add(r.getPoint(t + length));
        if (isZero(length)) return list;
        list.add(r.getPoint(t - length));
        return list;
    }
    
}
