package geometries;

import primitives.Vector;
import primitives.Point3D;

/**
 * @author Roni Buchine 
 * @author Eliezer Jacobs
 */
public class Plane implements Geometry {

    /**
     * a {@link Point3D} on the {@link Plane}
     */
    private Point3D p0;

    /**
     * One of the normal {@link Vector}s for the {@link Plane}
     */
    private Vector normal;
    
    /**
     * Ctor which takes 3 {@link Point3D}s, calculates {@link Vector}s in the {@link Plane} and assigns the normal to be 
     * the cross product of the 2 {@link Vector}s and choose p0 to be the first input parameter
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        Vector vec1 = p1.subtract(p2);
        Vector vec2 = p2.subtract(p3);
        this.normal = vec1.crossProduct(vec2);
        this.normal.normalize();
        this.p0 = p1;
    }

    /**
     * Constructs a {@link Plane} with the passed normal {@link Vector} and {@link Point3D} p0
     * @param normal
     * @param p0
     */
    public Plane(Vector normal, Point3D p0) {
        this.normal = normal;
        this.normal.normalize();
        this.p0 = p0;
    }

    /**
     * Passes a {@link Point3D} as a paramter and returns the normalized normal {@link Vector} of the {@link Plane}
     * @return normalized normal {@link Vector} that defines the {@link Plane}
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    /**
     * Overrides the {@link Geometry} interface getNormal and implements it for a plane object
     * @param {@link Point3D} p as an intersection point on the plane
     * @return Normal vector for the plane
     */
    public Vector getNormal(Point3D p) {
        return getNormal();
    }
}
