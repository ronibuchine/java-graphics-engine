package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.sqrt;


/**
 * Class representing a Tube in a 3-dimensional coordinate system
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class Tube extends Geometry {
    
    /**
     * radius field for the {@link Tube}
     */
    protected double radius;

    /**
     * direction {@link Vector} representing the direction that the {@link Tube} is facing in 3-dimensional space
     */
    protected Ray dir;

    /**
     * constructor which takes a radius and direction {@link Ray} and initializes the Tube
     * @param radius the radius of the given tube
     * @param dir the direction {@link Ray} in which the tube is going
     */
    public Tube(double radius, Ray dir) {
        this.radius = radius;
        this.dir = dir;
    }

    /**
     * Overrides the getNormal in Geometry interface to calculate the normal for a given intersection point on a Tube
     * @param p0 the intersection point
     * @return normal {@link Vector} from the intersection point p0 on the {@link Tube}
     */
    @Override
    public Vector getNormal(Point3D p0){
        double t = dir.getDir().dotProduct(p0.subtract(dir.getStartPoint()));
        Point3D o = dir.getStartPoint().add(dir.getDir().scale(t));
        return p0.subtract(o).normalize();
    }

    /**
     * Implementation of findGeoIntersections for Intersectable {@link Tube}
     * @param r The Ray
     * @return {@link List} of {@link GeoPoint}s
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray r, double limit) {

        Vector AB = dir.getDir();
        Vector AO = r.getStartPoint().subtract(dir.getStartPoint());
        Vector AOxAB;
        Vector VxAB;
        try {
            VxAB = r.getDir().crossProduct(AB);
        }
        catch (IllegalArgumentException e) {
            return null;                        //ray is parallel to tube
        }
        double a = VxAB.dotProduct(VxAB);
        double b;
        double c;
        try {
            AOxAB = AO.crossProduct(AB);
            b = 2 * (VxAB.dotProduct(AOxAB));
            c = AOxAB.dotProduct(AOxAB) - radius*radius;
        }
        catch (IllegalArgumentException e) {
            b = 0;
            c = -radius*radius;
        }
        if (b*b - 4*a*c <= 0) return null;                      //check discriminant
        double scalar1 = alignZero((-b + sqrt(b*b - 4*a*c)) / (2*a));   //positive answer
        double scalar2 = alignZero((-b - sqrt(b*b - 4*a*c)) / (2*a));   //negative answer
        if (scalar1 <= 0 && scalar2 <= 0) return null;          //check if scalars are negative (point is behind ray) or zero (point is at start of ray)
        if (scalar1 > limit && scalar2 > limit) return null;          //check if scalars are beyond upper boundary
        List<GeoPoint> list = new ArrayList<>();
        if (scalar1 > 0 && scalar1 <= limit) list.add(new GeoPoint(this, r.getPoint(scalar1)));
        if (scalar2 > 0 && scalar2 <= limit) list.add(new GeoPoint(this, r.getPoint(scalar2)));
        return list;
    }

    @Override
    public Point3D getMinPoint() {
        // Tube has no min point
        throw new IllegalArgumentException("Tube has no minimum point");
    }

    @Override
    public Point3D getMaxPoint() {
        // Tube has no max point
        throw new IllegalArgumentException("Tube has no maximum point");
    }

}
