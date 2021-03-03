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

    public static final Point3D ZERO = new Point3D(0, 0, 0);

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    /**
     * method to add two Point3Ds together
     * @param p
     * @return new Point3D with sum of the two added points
     */
    public Point3D add(Point3D p) {
        Coordinate xCoord = x.add(p.x);
        Coordinate yCoord = y.add(p.y);
        Coordinate zCoord = z.add(p.z);
        return new Point3D(xCoord, yCoord, zCoord);       
    }

    /**
     * method subtracts the parameter Point3D from the instance of the Point3D
     * @param p
     * @return a Vector with a head whose value is the difference of the two coordinates
     */
    public Vector subtract(Point3D p) {        
        Coordinate xCoord = x.subtract(p.x);
        Coordinate yCoord = y.subtract(p.y);
        Coordinate zCoord = z.subtract(p.z);
        Point3D point = new Point3D(xCoord, yCoord, zCoord);

        return new Vector(point);
    }

    /**
     * 
     * @param p
     * @return The squared distance between two Point3Ds
     */
    private double distanceSquared(Point3D p) {
        double squareDistance = x.subtract(p.x).squared() + y.subtract(p.y).squared() + z.subtract(p.z).squared();
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

    public boolean equals(Point3D p) {
        return this.x == p.x && this.y == p.y && this.z == p.z;
    }

}
