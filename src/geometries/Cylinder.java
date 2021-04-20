package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point3D;

/**
 * Class used to represent a cylinder in 3-dimensional space
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
     * @return the height of the cylinder
     */
    public double getHeight() {
        return height;
    }
    
    @Override
    /**
     * Overrides {@link Geometry} getNormal
     * @param p0 a point that intersects with the Cylinder
     * @return tubeNorm the normal vector inherited from {@link Tube}
     * @return dir.getDir() the direction vector of the cylinder
     */
    public Vector getNormal(Point3D p0) {
        if (p0 == dir.getStartPoint()) return dir.getDir();
        double t = dir.getDir().dotProduct(p0.subtract(dir.getStartPoint()));
        if (t == 0 || t == height) return dir.getDir();
        Vector tubeNorm = super.getNormal(p0);
        return tubeNorm;
    }
    
}
