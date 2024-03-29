package geometries;

import primitives.Point3D;

/**
 * A class that represents a 2-dimensional triangle in a 3-dimensional coordinate system
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

}
