package geometries;

import primitives.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface Class to represent "intersectable" {@link Geometry}s
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public interface Intersectable {

    /**
     * Helper Class to encapsulate a {@link Point3D} with its {@link Geometry}
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;
        /**
         * Constructor for {@link GeoPoint}. Sets its {@link Geometry} and {@link Point3D}
         * @param g
         * @param p
         */
        public GeoPoint(Geometry g, Point3D p) {
            geometry = g;
            point = p;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof GeoPoint)) return false;
            GeoPoint other = (GeoPoint)obj;
            return geometry.equals(other.geometry) && point.equals(other.point);
        }
    }
    
    /**
     * @param ray
     * @return
     */
    default List<Point3D> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }
    /**
     * Returns a list of {@link  Geometry}s that a {@link Ray} intersects within a given distance
     * @param r The ray
     * @return A {@link List} of {@link Point3D}s
     */
    default List<Point3D> findIntersections(Ray ray, double limit) {
        var geoList = findGeoIntersections(ray, limit);
        return geoList == null ? null
                               : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

    /**
     * Returns a list of {@link GeoPoint}s that {@Ray} r intersects
     * @param r
     * @return
     */
    default List<GeoPoint> findGeoIntersections(Ray r) {
        return findGeoIntersections(r, Double.POSITIVE_INFINITY);
    }
    
    /**
     * Returns a list of {@link Geometry}s that {@Ray} r intersects and is within a given distance
     * @param r
     * @param limit Upper boundary on distance to intersection point
     * @return
     */
    List<GeoPoint> findGeoIntersections(Ray r, double limit);

    
    /**
     * Abstract method to calculate Geometry's min point
     * @return
     */
    public abstract Point3D getMinPoint();
    /**
     * Abstract method to calculate Geometry's max point
     * @return
     */
    public abstract Point3D getMaxPoint();
    default Point3D getMiddle() {
        Point3D min = getMinPoint();
        Point3D max = getMaxPoint();
        return new Point3D(
            (min.getX() + max.getX()) / 2,
            (min.getY() + max.getY()) / 2,
            (min.getZ() + max.getZ()) / 2);
    }
}
