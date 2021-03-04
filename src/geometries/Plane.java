package geometries;

import primitives.*;

/**
 * @author Roni Buchine and Eliezer Jacobs
 */
public class Plane implements Geometry {

    Point3D p0;

    Vector normal;
    
    /**
     * Ctor which takes 3 points, calculates vectors in the plane and assigns the normal to be 
     * the cross product of the 2 vectors and choose p0 to be the first input parameter
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
     * Constructs a Plane wiwth the passed normal vector and point p0
     * @param normal
     * @param p0
     */
    public Plane(Vector normal, Point3D p0) {
        this.normal = normal;
        this.normal.normalize();
        this.p0 = p0;
    }

    /**
     * passes a point as a paramter and returns the normalized normal Vector of the plane
     * @param p
     * @return normalized normal vector that defines the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return normal;
    }
}
