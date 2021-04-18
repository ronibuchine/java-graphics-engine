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
     * height paramter for the {@link Cylinder}
     */
    private double height;

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

    /**
     * getter for the field height
     */
    public double getHeight() {
        return height;
    }
    
    @Override
    /**
     * overrides {@link Geometry} getNormal
     */
    public Vector getNormal(Point3D p0){
        Vector norm = super.getNormal(p0);
        if (norm.length() == height) return dir.getDir();
        else return norm;
    }
    
}
