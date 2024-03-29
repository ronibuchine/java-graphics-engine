package primitives;
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
    final Coordinate x;

    /**
     * Y Coordinate in a 3-dimensional coordinate system
     */
    final Coordinate y;

    /**
     * Z Coordinate in a 3-dimensional coordinate system
     */
    final Coordinate z;

    /**
     * A constant value representing the origin of the x, y, z plane
     */
    public static final Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * Constructor that takes three {@link Coordinate}s as parameters and initializes the new Point3D
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
     * getter function for x-coordinate
     * @return x coordinate
     */
    public double getX() {return x.coord;}

    /**
     * getter function for y-coordinates
     * @return y coordinate
     */
    public double getY() {return y.coord;}

    /**
     * getter function for z coordinates
     * @return z coordinate
     */
    public double getZ() {return z.coord;}

    /**
     * helper function to get specific coordinnate from a {@link Point3D}
     */
    public double getCoord(int c) {
        switch (c) {
            case 0:
                return getX();
            case 1:
                return getY();
            case 2:
                return getZ();
            default:
                throw new IllegalArgumentException("Coordinate must be 0, 1, or 2");
        }
    }

    /**
     * Constructor that takes three double values and initializes the {@link Coordinate}s with those values
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
     * method to add a {@link Vector} to a {@link Point3D}
     * @param v
     * @return new {@link Vector} with head as sum of the two added points
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
     * @return a {@link Vector} with a head whose value is the difference of the two {@link Coordinate}s
     */
    public Vector subtract(Point3D p) {        
        double xCoord = x.coord - p.x.coord;
        double yCoord = y.coord - p.y.coord;
        double zCoord = z.coord - p.z.coord;
        //Point3D point = new Point3D(xCoord, yCoord, zCoord);

        return new Vector(xCoord, yCoord, zCoord);
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

    
    /**
     * finds the min x, y and z values from a list of {@link Point3D}s
     * 
     * @param points
     * @return a {@link Point3D} with the min x, y and z values
     */
    public static Point3D min(Point3D... points) {
        double x = points[0].getX();
        double y = points[0].getY();
        double z = points[0].getZ();
        for (Point3D p : points) {
            x = Double.min(p.getX(), x);
            y = Double.min(p.getY(), y);
            z = Double.min(p.getZ(), z);
        }
        return new Point3D(x, y, z);
    }

    /**
     * finds the max x, y and z values from a list of {@link Point3D}s
     * 
     * @param points
     * @return a {@link Point3D} with the max x, y and z values
     */
    public static Point3D max(Point3D... points) {
        double x = points[0].getX();
        double y = points[0].getY();
        double z = points[0].getZ();
        for (Point3D p : points) {
            x = Double.max(p.getX(), x);
            y = Double.max(p.getY(), y);
            z = Double.max(p.getZ(), z);
        }
        return new Point3D(x, y, z);
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
