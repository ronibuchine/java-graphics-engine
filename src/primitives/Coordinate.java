package primitives;
/**
 * Class Coordinate is a basic coordinate class representing an x, y or z coordinate
 * @see Point3D.java
 * @author Roni Buchine and Eliezer Jacobs
 */
public class Coordinate {

    double coord;

    public Coordinate(double c) {
        this.coord = c;
    }

    public Coordinate(Coordinate c) {
        this.coord = c.coord;
    }

    public void setCoordinate(double c) {
        this.coord = c;
    }

    public double getCoordinate() {
        return this.coord;
    }
    
    public boolean equals(Coordinate c) {
        return this.coord == c.coord;
    }
}
