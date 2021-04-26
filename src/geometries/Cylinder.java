package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point3D;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

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
        double t = alignZero(dir.getDir().dotProduct(p0.subtract(dir.getStartPoint())));
        if (t == 0 || isZero(t - height)) return dir.getDir();
        return super.getNormal(p0);
    }

    /**
     * private helper function to determine if a {@link Point3D} is inside the cylinder
     * @return {@link boolean}
     */
    private boolean isInside(Point3D p) {
        //checks that angle between center ray and vector from start to p is acute and that angle between center ray and vector from end to p is obtuse
        return (dir.getDir().dotProduct(p.subtract(dir.getStartPoint())) > 0 && dir.getDir().dotProduct(p.subtract(dir.getPoint(height))) < 0);
    }

    /**
     * Implements findIntersections for Intersectable {@link Cylinder}
     * @param r The Ray
     * @return {@link List} of {@link Point3D}s where the {@link Ray} intersects
     */
    @Override
    public List<Point3D> findIntersections(Ray r) {
        List<Point3D> list = super.findIntersections(r);
        if (list != null) {
            for (Point3D p : list) {
                if (!isInside(p)) list.remove(p);
            }
            if (list.size() == 2) return list;
        }
        if (list == null) list = new ArrayList<>();
        List<Point3D> cap1 = new Plane(dir.getDir(), dir.getStartPoint()).findIntersections(r);
        List<Point3D> cap2 = new Plane(dir.getDir(), dir.getPoint(height)).findIntersections(r);
        if (cap1 != null && cap1.get(0).distance(dir.getStartPoint()) <= radius) list.add(cap1.get(0));
        if (cap2 != null && cap2.get(0).distance(dir.getPoint(height)) <= radius) list.add(cap2.get(0));
        return list;
    }
}
