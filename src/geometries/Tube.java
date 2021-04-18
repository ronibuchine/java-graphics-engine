package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point3D;

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
     * direction {@link Vector} to show where the {@link Tube} is facing in 3-dimensional space
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
    

}
