package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.List;

/**
 * A class that represents a 2-dimensional plane in 3-dimensional coordinate system
 * @author Roni Buchine 
 * @author Eliezer Jacobs
 */
public class Plane extends Geometry {

    /**
     * a {@link Point3D} on the {@link Plane}
     */
    private Point3D p0;

    /**
     * One of the normal {@link Vector}s for the {@link Plane}
     */
    private Vector normal;
    
    /**
     * Ctor which takes 3 {@link Point3D}s, calculates {@link Vector}s in the {@link Plane} and assigns the normal to be 
     * the cross product of the 2 {@link Vector}s and assigns p0 to be the first input parameter
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        Vector vec1 = p1.subtract(p2);
        Vector vec2 = p2.subtract(p3);
        this.normal = vec1.crossProduct(vec2).normalized();        
        this.p0 = p1;
    }

    /**
     * Constructs a {@link Plane} with the passed normal {@link Vector} and {@link Point3D} p0
     * @param normal
     * @param p0
     */
    public Plane(Vector normal, Point3D p0) {
        this.normal = normal.normalized();
        this.p0 = p0;
    }

    /**
     * Getter for the normal {@link Vector} of the {@link Plane}
     * @return normalized normal {@link Vector} that defines the {@link Plane}
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    /**
     * Overrides the {@link Geometry} interface getNormal and implements it for a plane object
     * @param p {@link Point3D} as an intersection point on the plane
     * @return Normal vector for the plane
     */
    public Vector getNormal(Point3D p) {
        return getNormal();
    }

    /**
     * Implements findIntersections for Intersectable {@link Plane}
     * @param r The Ray
     * @return {@link List} containing {@link GeoPoint} if intersection exists (returns null if not)
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray r, double limit) {
        Vector rayToPlane;
        try {
            rayToPlane = p0.subtract(r.getStartPoint()); //a vector from the ray start point to some point on the plane
        }
        catch (IllegalArgumentException e) { return null; } //ray starts at plane's representative point
        double denominator = normal.dotProduct(r.getDir());
        if (isZero(denominator)) return null; //ray is parallel to plane
        double t = alignZero(normal.dotProduct(rayToPlane) / denominator);
        if (t > 0 && alignZero(t - limit) <= 0) return List.of(new GeoPoint(this, r.getPoint(t)));
        else return null; //Ray starts on plane, is behind the plane, or is further than limit
    }

    @Override
    public Point3D getMinPoint() {
        // Plane has no min point
        throw new IllegalArgumentException("Plane has no minimum point");
    }

    @Override
    public Point3D getMaxPoint() {
        // Plane has no max point
        throw new IllegalArgumentException("Plane has no minimum point");
    }

}
