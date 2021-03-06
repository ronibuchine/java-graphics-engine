package geometries;

import primitives.*;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class Triangle extends Polygon implements Geometry {

    public Triangle(Point3D p0, Point3D p1, Point3D p2) {
        super(p0, p1, p2); //apparently this calls the SuperClass's constructor
    }

    @Override
    /**
     * overrides getNormal from Geometry interface
     */
    public Vector getNormal(Point3D p0) {
        return null;
    }

}
