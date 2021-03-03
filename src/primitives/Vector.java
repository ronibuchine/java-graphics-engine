package primitives;

/**
 * Class Vector is the basic class representing a vector in a 3-dimensional Cartesian 
 * coordinate system
 * @see Point3D.java, Coordinate.java
 * @author Roni Buchine and Eliezer Jacobs
 */
public class Vector {

    Point3D head;

    /**
     * A constructor for Vector that takes a Point3D parameter
     * throws an exception if the Vector is initialized to the zero Vector
     * @param head
     */
    public Vector(Point3D head) {
        if (head.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException();
        }
        this.head = head;
    }

    /**
     * Vector ctor that takes in 3 coordinates as parameters and throws exception if they are the 0 Vector
     * @param x 
     * @param y
     * @param z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D newHead = new Point3D(x, y, z);
        if (newHead.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException();
        }
        head = newHead;
    }

    /**
     * Vector ctor that takes double type parameters and throws an exception if initialized to the 0 Vector
     * @param x
     * @param y
     * @param z
     */
    public Vector(double x, double y, double z) {
        Point3D newHead = new Point3D(x, y, z);
        if (newHead.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException();
        }
        head = newHead;
    }

    /**
     * getter for the Vector head field
     * @return Value of the vectors head
     */
    public Point3D getHead() {
        return head;
    }

    /**
     * @param v
     * @return A new Vector whos head is the sum of the two Vectors
     */
    public Vector add(Vector v) {
        Vector vec = new Vector(head.add(v));
        return vec;
    }

    /**
     * @param v
     * @return a new Vector whos head is the difference of the two Vectors
     */
    public Vector subtract(Vector v) {
        Vector vec = head.subtract(v.head);              
        return vec;
    }

    /**
     * @param n
     * @return Vector scaled by the parameter n
     */
    public Vector scale(double n) { 
        Vector vec = new Vector(head.x.mult(n), head.y.mult(n), head.z.mult(n));        
        return vec;        
    }

    /*
    * TODO: make methods for cross product, dot product, length, length squared, normalize, normalized
    */

    public boolean equals(Vector v) {
        return v.head == this.head;
    }
    
}
