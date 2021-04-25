package geometries;

import primitives.*;
import java.util.List;

/**
 * Interface Class to represent "intersectable" {@link Geometry}s
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public interface Intersectable {
    /**
     * Returns a list of {@link  Geometry}s that a {@link Ray} intersects
     * @param r The ray
     * @return A {@link List} of {@link Point3D}s
     */
    List<Point3D> findIntersections(Ray r);
}
