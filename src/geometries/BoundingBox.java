package geometries;

import primitives.Point3D;
import primitives.Ray;

import static primitives.Util.alignZero;

import java.util.LinkedList;
import java.util.List;

/**
 * BoundingBox is an Axis Aligned Bounding Box (AABB)
 * 
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class BoundingBox extends Geometries {

    /**
     * Represents the two extreme points of the BoundingBox
     */
    private Point3D min, max;
    /**
     * Holds a list of internal Geometries
     */
    private Geometries geos;

    public BoundingBox(Geometry... geos) {
        geometries = new LinkedList<>();
        add(geos);
    }

    @Override
    public void add(Geometry... geometries) {
        for (Geometry g : geometries) {
            if (g instanceof Plane || g instanceof Tube) throw new IllegalArgumentException("Planes and Tubes may not be placed in a BoundingBox");
            this.geometries.add(g);
            min = min(g.getMinPoint(), min);
            max = max(g.getMaxPoint(), max);
        }
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray r, double limit) {
        if (!testIntersection(r)) return null;
        return geos.findGeoIntersections(r, limit);
    }

    public boolean testIntersection(Ray r) {
        double tmin = 0;
        double tmax = Double.POSITIVE_INFINITY;
        Point3D rDir = r.getDir().getHead();
        Point3D rStart = r.getStartPoint();
        for (int i = 0; i < 3; ++i) {
            double coord = getCoord(rDir, i);
            if (coord == 0) continue;
            double invD = alignZero(1 / coord);
            double t0 = alignZero((getCoord(min, i) - getCoord(rStart, i)) * invD);
            double t1 = alignZero((getCoord(max, i) - getCoord(rStart, i)) * invD);
            if (invD < 0) {
                double temp = t1;
                t1 = t0;
                t0 = temp;
            }
            tmin = t0 > tmin ? t0 : tmin;
            tmax = t1 < tmax ? t1 : tmax;
            if (tmax <= tmin) return false;
        }
        return true;
    }

    private double getCoord(Point3D p, int c) {
        switch (c) {
            case 1:
                return p.getX();
            case 2:
                return p.getY();
            case 3:
                return p.getZ();
            default:
                return 0;
        }
    }

    static Point3D min(Point3D... points) {
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
    static Point3D max(Point3D...points) {
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

}