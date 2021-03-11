package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point3D;

/**
 * @author Roni Buchine 
 * @author Eliezer Jacobs
 */
public class Cylinder extends Tube {
    
    /**
     * height paramter for the cylinder
     */
    double height;

    /**
     * Uses Tube constructor to initialize inherited radius and dir fields
     * @param radius
     * @param dir
     * @param height
     */
    public Cylinder(double radius, Ray dir, double height) {
        super(radius, dir);
        this.height = height;
    }
    
    @Override
    /**
     * overrides Geometry getNormal
     */
    public Vector getNormal(Point3D p0){
        return null;
    }
    
}
