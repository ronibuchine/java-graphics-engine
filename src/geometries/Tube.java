package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point3D;

/**
 * Class that represents an infinite tube in a 3-dimensional coordinate system
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class Tube implements Geometry {
    
    /**
     * radius field for the Tube
     */
    double radius;

    /**
     * direction vector to show where the Tube is facing in 3-dimensional space
     */
    Ray dir;

    /**
     * constructor which takes a radius and direction Ray and initializes the Tube
     * @param radius the radius of the given tube
     * @param dir the direction Ray in which the tube is going
     */
    public Tube(double radius, Ray dir) {
        this.radius = radius;
        this.dir = dir;
    }

    @Override
    public Vector getNormal(Point3D p0){
        return null;
    }
    

}
