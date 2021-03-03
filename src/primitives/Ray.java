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

    public Ray(Ray r) {
        this.dir = r.dir;
        this.p0 = r.p0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray)obj;
        return dir.equals(other.dir) && p0.equals(other.p0);
    }
    
}
