package primitives;
/**
 * Class Vector is the basic class representing a vector in a 3-dimensional Cartesian 
 * coordinate system
 * @see Point3D.java, Coordinate.java
 * @author Roni Buchine and Eliezer Jacobs
 */
public class Vector {

    Point3D head;

    public Vector(Point3D head) {
        this.head = head;
    }

    public boolean equals(Vector v) {
        return v.head == this.head;
    }
    
}
