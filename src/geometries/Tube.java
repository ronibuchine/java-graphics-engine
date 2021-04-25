package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.List;


/**
 * Class representing a Tube in a 3-dimensional coordinate system
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class Tube implements Geometry {
    
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

    @Override
    /**
     * Overrides the getNormal in Geometry interface to calculate the normal for a given intersection point on a Tube
     * @param p0 the intersection point
     * @return normal {@link Vector} from the intersection point p0 on the {@link Tube}
     */
    public Vector getNormal(Point3D p0){
        double t = dir.getDir().dotProduct(p0.subtract(dir.getStartPoint()));
        Point3D o = dir.getStartPoint().add(dir.getDir().scale(t));
        return p0.subtract(o).normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray r) {
        double a = alignZero(r.getDir().getHead().getX()*r.getDir().getHead().getX() + r.getDir().getHead().getY()*r.getDir().getHead().getY());
        double b = alignZero(2*(r.getStartPoint().getX()*r.getDir().getHead().getX() + r.getStartPoint().getY()*r.getDir().getHead().getY()));
        double c = alignZero(r.getStartPoint().getX()*r.getStartPoint().getX() + r.getStartPoint().getY()*r.getStartPoint().getY() - radius*radius);
        if (b*b - 4*a*c <= 0) return null;                      //check discriminant
        double scalar1 = alignZero((-b + b*b - 4*a*c) / 2*a);   //positive answer
        double scalar2 = alignZero((-b - b*b - 4*a*c) / 2*a);   //negative answer
        if (scalar1 <= 0 || scalar2 <= 0) return null;          //check if scalar is negative (point is behind ray) or zero (point is at start of ray)
        return List.of(r.getPoint(scalar1), r.getPoint(scalar2));
    }
}
