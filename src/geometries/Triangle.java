package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class Triangle extends Polygon {

    /**
     * Constructor for the class triangle that takes the three vertices as parameters
     * @param p0 a vertex of the triangle
     * @param p1 a vertex of the triangle
     * @param p2 a vertex of the triangle
     */
    public Triangle(Point3D p0, Point3D p1, Point3D p2) {
        super(p0, p1, p2); 
    }

    @Override
    /**
     * overrides getNormal from {@link Geometry} interface
     */
    public Vector getNormal(Point3D p0) {
        return super.getNormal(p0);
    }

}
