package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.List;

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
    /**
     * Method that finds all the points of intersection on the sphere
     * @param r a Ray that intersects with the Sphere
     * @return a list of all the points of intersection
     */
    public List<Point3D> findIntersections(Ray r) {
        Vector u = center.subtract(r.getStartPoint());              //vector from ray start to center of sphere
        double projLength = alignZero(r.getDir().dotProduct(u));    //length of projection of u on r
        if (projLength <= 0) return null;                           //ray is on or behind the sphere
        double distToCenter = alignZero(Math.sqrt(u.lengthSquared() - projLength*projLength)); //distance of projected point from center of sphere
        if (distToCenter > radius) return null;                     //projected point is further than sphere's radius
        double distToSide = alignZero(Math.sqrt(radius*radius - distToCenter*distToCenter));  //distance from projected point to side of sphere
        if (isZero(distToSide)) return null;                        //ignore tangent point 
        return List.of(r.getPoint(projLength + distToSide),r.getPoint(projLength - distToSide));
    }
}
