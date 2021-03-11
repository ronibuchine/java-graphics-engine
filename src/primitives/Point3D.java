package primitives;

import java.lang.Math;
import java.util.StringJoiner;

/**
 * Class Point3D is the basic class representing a point in 3d space of Euclidean geometry in Cartesian
 * 3-dimensional coordinate system
 * @author Roni Buchine 
 * @author Eliezer Jacobs  
 */
public class Point3D {

    /**
     * X Coordinate in a 3-dimensional coordinate system
     */
    Coordinate x;

    /**
     * Y Coordinate in a 3-dimensional coordinate system
     */
    Coordinate y;

    /**
     * Z Coordinate in a 3-dimensional coordinate system
     */
    Coordinate z;

    /**
     * A constant value representing the origin of the x, y, z plane
     */
    public static final Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * Constructor that takes three Coordinates as parameters and initializes the new Point3D
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructor that takes three double values and initializes the cooridnates with those values
     * @param x double value
     * @param y double value
     * @param z double value
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    /**
     * method to add a Vector to a Point3D
     * @param p
     * @return new Vector with head as sum of the two added points
     */
    public Point3D add(Vector v) {
        double xCoord = x.coord + v.head.x.coord;
        double yCoord = y.coord + v.head.y.coord;
        double zCoord = z.coord + v.head.z.coord;
        return new Point3D(xCoord, yCoord, zCoord);       
    }

    /**
     * method subtracts the parameter Point3D from the instance of the Point3D
     * @param p
     * @return a Vector with a head whose value is the difference of the two coordinates
     */
    public Vector subtract(Point3D p) {        
        double xCoord = x.coord - p.x.coord;
        double yCoord = y.coord - p.y.coord;
        double zCoord = z.coord - p.z.coord;
        Point3D point = new Point3D(xCoord, yCoord, zCoord);

        return new Vector(point);
    }

    /**
     * @param p the point we use to calculate the distance squared
     * @return The squared distance between two Point3Ds
     */
    public double distanceSquared(Point3D p) {
        double squareDistance = (x.coord-p.x.coord)*(x.coord-p.x.coord) + (y.coord - p.y.coord)*(y.coord - p.y.coord) + (z.coord-p.z.coord)*(z.coord-p.z.coord);
        return squareDistance;
    }

    /**
     * 
     * @param p
     * @return The distance between 2 Point3Ds
     */
    public double distance(Point3D p) {
        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D)obj;
        return x.equals(other.x) && y.equals(other.y) && z.equals(other.z);
    }

    @Override
    public String toString() {
        StringJoiner allCoords = new StringJoiner(", ", "(", ")");
        allCoords.add(x.toString()).add((y.toString())).add((z.toString()));
        return allCoords.toString();
    }

}
