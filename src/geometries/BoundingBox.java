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

    public BoundingBox(Intersectable... geos) {
        geometries = new LinkedList<>();
        add(geos);
    }

    /**
     * Getter method for min point
     * 
     * @return min point of bounding box
     */
    public Point3D getMin() {
        return min;
    }

    /**
     * Getter method for max point of bounding box
     * 
     * @return max point of bounding box
     */
    public Point3D getMax() {
        return max;
    }

    @Override
    public void add(Intersectable... geometries) {
        for (Intersectable g : geometries) {
            min = min != null ? min(g.getMinPoint(), min) : g.getMinPoint();
            max = max != null ? max(g.getMaxPoint(), max) : g.getMaxPoint();
            this.geometries.add(g);
        }
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray r, double limit) {
        if (!getsIntersected(r))
            return null;
        return super.findGeoIntersections(r, limit);
    }

    public boolean getsIntersected(Ray r) {
        double tmin = 0;
        double tmax = Double.POSITIVE_INFINITY;
        Point3D rDir = r.getDir().getHead();
        Point3D rStart = r.getStartPoint();
        for (int i = 0; i < 3; ++i) {
            double coord = getCoord(rDir, i);
            if (coord == 0)
                continue;
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
            if (tmax <= tmin)
                return false;
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
        try {
            double x = points[0].getX();
            double y = points[0].getY();
            double z = points[0].getZ();
            for (Point3D p : points) {
                x = Double.min(p.getX(), x);
                y = Double.min(p.getY(), y);
                z = Double.min(p.getZ(), z);
            }
            return new Point3D(x, y, z);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Can't find minimum point of infinite geometry");
        }
    }

    static Point3D max(Point3D... points) {
        try {
            double x = points[0].getX();
            double y = points[0].getY();
            double z = points[0].getZ();
            for (Point3D p : points) {
                x = Double.max(p.getX(), x);
                y = Double.max(p.getY(), y);
                z = Double.max(p.getZ(), z);
            }
            return new Point3D(x, y, z);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't find maximum point of infinite geometry");
        }
    }

}