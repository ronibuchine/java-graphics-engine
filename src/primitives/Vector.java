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

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this.head = new Point3D(x, y, z);
    }

    public Vector(double x, double y, double z) {
        this.head = new Point3D(x, y, z);
    }

    public void setHead(Point3D p) {
        this.head = p;
    }

    public Point3D getHead() {
        return head;
    }

    public Vector add(Vector v) {
        Vector vec = new Vector(this.head.add(v.head));
        return vec;
    }

    public Vector subtract(Vector v) {
        Vector vec = this.head.subtract(v.head);              
        return vec;
    }

    public Vector scale(double n) {        
        Vector vec = new Vector(head);
        vec.head.setX(new Coordinate(vec.head.getX().getCoordinate()*n));
        vec.head.setY(new Coordinate(vec.head.getY().getCoordinate()*n));
        vec.head.setZ(new Coordinate(vec.head.getZ().getCoordinate()*n));
        
        return vec;        
    }

    /*
    * TODO: make methods for cross product, dot product, length, length squared, normalize, normalized
    */

    public boolean equals(Vector v) {
        return v.head == this.head;
    }
    
}
