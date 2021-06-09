package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implements a {@link Sphere} in 3-dimensional coordinate space.
 * @author Roni Buchine 
 * @author Eliezer Jacobs
 */
public class Sphere extends Geometry {

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

    /**
     * Method that finds all the {@link GeoPoint}s of intersection on the sphere
     * @param r a Ray that intersects with the Sphere
     * @return a {@link List} of all {@link GeoPoint}s of intersection
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray r, double limit) {
        Vector u;
        double projLength;
        try {
            u = center.subtract(r.getStartPoint());              //vector from ray start to center of sphere
            projLength = alignZero(r.getDir().dotProduct(u));    //length of projection of u on r
        }
        catch (IllegalArgumentException e) {
            projLength = 0;
        }
        double distToCenter = Math.sqrt(alignZero(r.getStartPoint().distanceSquared(center) - projLength*projLength)); //distance of projected point from center of sphere
        if (distToCenter >= radius) return null;                     //projected point is further or on sphere's radius
        double distToSide = alignZero(Math.sqrt(radius*radius - distToCenter*distToCenter));  //distance from projected point to side of sphere
        if (projLength + distToSide <= 0 && projLength - distToSide <= 0) return null;
        List<GeoPoint> list = new ArrayList<>();
        if (projLength - distToSide > 0 && alignZero(projLength - distToSide - limit) <= 0) list.add(new GeoPoint(this, r.getPoint(projLength - distToSide)));
        if (projLength + distToSide > 0 && alignZero(projLength + distToSide - limit) <= 0) list.add(new GeoPoint(this, r.getPoint(projLength + distToSide)));
        return list;
    }

    @Override
    public Point3D getMinPoint() {
        return new Point3D(center.getX() - radius, center.getY() - radius, center.getZ() - radius);
    }

    @Override
    public Point3D getMaxPoint() {
        return new Point3D(center.getX() + radius, center.getY() + radius, center.getZ() + radius);
    }
    
}
