package primitives;

/**
 * Class Vector is the basic class representing a vector in a 3-dimensional Cartesian 
 * coordinate system
 * @see Point3D
 * @see Coordinate
 * @author Roni Buchine 
 * @author Eliezer Jacobs
 */
public class Vector {

    Point3D head;

    /**
     * A constructor for Vector that takes a {@link Point3D} parameter
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
     * Vector ctor that takes in 3 {@link Coordinate}s as parameters and throws exception if they are the 0 Vector
     * @param x 
     * @param y
     * @param z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D newHead = new Point3D(x, y, z);
        if (newHead.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("Error: Zero Vector");
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
            throw new IllegalArgumentException("Error: Zero Vector");
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
     * @return A new Vector whose head is the sum of the two Vectors
     */
    public Vector add(Vector v) {
        Vector vec = new Vector(head.add(v));
        return vec;
    }

    /**
     * @param v
     * @return a new Vector whose head is the difference of the two Vectors
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
        Vector vec = new Vector(head.x.coord*n, head.y.coord*n, head.z.coord*n);        
        return vec;        
    }

    /**
     * 
     * @return Squared length of the vector
     */
    public double lengthSquared() {
        double squaredLength = head.distanceSquared(Point3D.ZERO);
        return squaredLength;
    }

    /**
     * Calculates the length by taking the square root of the head coordinates squared
     * @return Length of the Vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalizes the Vector and actually changes the head coordinate values
     * @return The new Vector itself so that it can be chained together with other objects
     */
    public Vector normalize() {
        double scalar = 1/length();
        Vector vec = this.scale(scalar);
        head = vec.head;        
        return this;
    }

    /**
     * 
     * @return a new normalized Vector without changing the actual values of the Vector
     */
    public Vector normalized() {
        Vector vec = new Vector(head);
        vec.normalize();
        return vec;
    }

    /**
     * takes two vectors and calculates the dot product of them
     * by multiplying each coordinate value individually and then summing them together
     * @param v
     * @return result of the dot product of the two vectors
     */
    public double dotProduct(Vector v) {
        double xVal = v.head.x.coord*head.x.coord;
        double yVal = v.head.y.coord*head.y.coord;
        double zVal = v.head.z.coord*head.z.coord;
        double result = xVal + yVal + zVal;
        return result;
    }

    /**
     * @param v
     * cross product takes two vectors with coordinates a, b, and c and calculates the new Vector:
     *  (V1.b*V2.c-V1.c*V2.b, V1.c*V2.a-V1.a*V2.c, V1.a*V2.b-V1.b*V2.a)
     * @return new Vector((V1.b*V2.c-V1.c*V2.b, V1.c*V2.a-V1.a*V2.c, V1.a*V2.b-V1.b*V2.a))
     */
    public Vector crossProduct(Vector v) {
        double xVal = head.y.coord*v.head.z.coord - head.z.coord*v.head.y.coord;
        double yVal = head.z.coord*v.head.x.coord - head.x.coord*v.head.z.coord;
        double zVal = head.x.coord*v.head.y.coord - head.y.coord*v.head.x.coord;
        return new Vector(xVal, yVal, zVal);
    }    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector)obj;
        return head.equals(other.head);
    }

    @Override
    public String toString() {
        return head.toString();
    }
    
}
