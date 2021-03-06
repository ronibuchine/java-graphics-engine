package primitives;

import static primitives.Util.*;

/**
 * Class Coordinate is the basic class representing a coordinate for Cartesian
 * coordinate system. The class is based on Util controlling the accuracy.
 * 
 * @author Roni Buchine 
 * @author Eliezer Jacobs
 * @version 5780B updated according to new requirements
 */
public final class Coordinate {
    /**
     * Coordinate value, intentionally "package-friendly" due to performance
     * constraints
     */
    final double coord;

    /**
     * Coordinate constructor receiving a coordinate value
     * 
     * @param coord coordinate value
     */
    public Coordinate(double coord) {
        // if it too close to zero make it zero
        this.coord = alignZero(coord);
    }

    /**
     * method that adds two Coordinates together and returns the sum
     * @param c
     * @return a Coordinate value 
     */
    public Coordinate add(Coordinate c) {
        double newCoord = coord + c.coord;
        // if its too close to 0 make it 0
        return new Coordinate(alignZero(newCoord));
    }

    /**
     * multiplies coordinate value by scalar c
     * @param n
     * @return a new Coordinate scaled by c
     */
    public Coordinate mult(double n) {
        double c = coord*n;
        return new Coordinate(c);
    }

    /**
     * subtracts the passed parameter from the coordinate value of the object and returns the new coordinate value
     * @param c
     * @return new Coordinate value
     */
    public Coordinate subtract(Coordinate c) {
        double newCoord = coord - c.coord;
        return new Coordinate(alignZero(newCoord));
    }

    /**
     * method that squares any coordinate value
     * @return the double value of a squared Coordinate
     */
    public double squared() {
        return coord*coord;
    }

    /*************** Admin *****************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Coordinate)) return false;
        Coordinate other = (Coordinate)obj;
        return isZero(coord - other.coord);
    }

    @Override
    public String toString() {
        return "" + coord;
    }
}
