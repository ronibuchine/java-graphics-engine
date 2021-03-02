package primitives;
/**
 * Class Ray is the basic ray class to represent a camera ray in a scene in 3-dimensional space
 * @see Point3D.java, Vector.java
 * @author Roni Buchine and Eliezer Jacobs
 */
public class Ray {

    Vector dir;
    Point3D p0;

    public Ray(Vector dir, Point3D p0) {
        this.dir = dir;
        this.p0 = p0;
    }

    public boolean equals(Ray r) {
        return r.dir == this.dir && r.p0 == this.p0;
    }
    
}
