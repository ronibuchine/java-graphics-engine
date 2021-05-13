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
     * Returns a list of {@link  Geometry}s that a {@link Ray} intersects
     * @param r The ray
     * @return A {@link List} of {@link Point3D}s
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                               : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }
    
    List<GeoPoint> findGeoIntersections(Ray r);
}
