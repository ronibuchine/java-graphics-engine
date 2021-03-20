package primitives;

import java.util.StringJoiner;

/**
 * Class Ray is the basic ray class to represent a camera ray in a scene in 3-dimensional space
 * @see Point3D.java, Vector.java
 * @author Roni Buchine 
 * @author Eliezer Jacobs
 */
public class Ray {

    /**
     * Ray takes a direction {@link Vector} as a parameter
     */
    Vector dir;

    /**
     * the origin {@link Point3D} of the Ray
     */
    Point3D p0;

    /**
     * ctor normalizes the {@link Vector} and creates the object with that {@link Vector} and {@link Point3D}
     * @param dir
     * @param p0
     */
    public Ray(Vector dir, Point3D p0) {
        this.dir = dir.normalized();
        this.p0 = p0;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray)obj;
        return dir.equals(other.dir) && p0.equals(other.p0);
    }

    @Override
    public String toString() {
        StringJoiner ray = new StringJoiner(": ", "{", "}");
        ray.add(p0.toString()).add(dir.toString());
        return ray.toString();
    }
    
}
