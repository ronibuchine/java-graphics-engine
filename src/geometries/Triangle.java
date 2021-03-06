package geometries;

import primitives.Point3D;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class Triangle extends Polygon {

    public Triangle(Point3D p0, Point3D p1, Point3D p2) {
        super(p0, p1, p2); //apparently this calls the SuperClass's constructor
    }

}
