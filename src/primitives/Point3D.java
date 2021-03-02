package primitives;

import java.lang.Math;
/**
 * Class Point3D is the basic class representing a point in 3d space of Euclidean geometry in Cartesian
 * 3-dimensional coordinate system
 * @author Roni Buchine and Eliezer Jacobs  
 */
public class Point3D {

    Coordinate x;
    Coordinate y;
    Coordinate z;

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(Coordinate x) {
        this.x = x;
    }
    
    public void setY(Coordinate y) {
        this.y = y;
    }

    public void setZ(Coordinate z) {
        this.z = z;
    }

    public Coordinate getX() {
        return this.x;
    }

    public Coordinate getY() {
        return this.y;
    }

    public Coordinate getZ() {
        return this.z;
    }

    public Vector add(Point3D p) {
        Coordinate xCoord = new Coordinate(p.x.getCoordinate()+this.x.getCoordinate());
        Coordinate yCoord = new Coordinate(p.y.getCoordinate()+this.y.getCoordinate());
        Coordinate zCoord = new Coordinate(p.z.getCoordinate()+this.z.getCoordinate());
        Point3D point = new Point3D(xCoord, yCoord, zCoord);

        return new Vector(point);
    }

    public Vector subtract(Point3D p) {
        Coordinate xCoord = new Coordinate(p.x.getCoordinate()-this.x.getCoordinate());
        Coordinate yCoord = new Coordinate(p.y.getCoordinate()-this.y.getCoordinate());
        Coordinate zCoord = new Coordinate(p.z.getCoordinate()-this.z.getCoordinate());
        Point3D point = new Point3D(xCoord, yCoord, zCoord);

        return new Vector(point);
    }

    private double distanceSquared(Point3D p) {
        double squareDistance = (this.x.getCoordinate()-p.x.getCoordinate())*(this.x.getCoordinate()-p.x.getCoordinate())
        + (this.y.getCoordinate()-p.y.getCoordinate())*(this.y.getCoordinate()-p.y.getCoordinate()) 
        + (this.z.getCoordinate()-p.z.getCoordinate())*(this.z.getCoordinate()-p.z.getCoordinate());

        return squareDistance;
    }

    public double distance(Point3D p) {
        return Math.sqrt(distanceSquared(p));
    }

    public boolean equals(Point3D p) {
        return this.x == p.x && this.y == p.y && this.z == p.z;
    }

}
