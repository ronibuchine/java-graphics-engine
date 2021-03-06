package geometries;

import primitives.*;

/**
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
     * @param radius
     * @param dir
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
