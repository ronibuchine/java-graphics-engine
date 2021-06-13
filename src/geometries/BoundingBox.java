package geometries;

import primitives.Point3D;
import primitives.Ray;

import static primitives.Util.alignZero;

import java.util.LinkedList;
import java.util.List;

/**
 * BoundingBox is an Axis Aligned Bounding Box (AABB) Created to enhance
 * performance of rendering
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
     * Constructor for Bounding Box
     * 
     * @param geos geometries encapsulated by the Bounding Box
     */
    public BoundingBox(Intersectable... geos) {
        geometries = new LinkedList<>();
        add(geos);
    }

    /**
     * Getter method for min point
     * 
     * @return min point of bounding box
     */
    @Override
    public Point3D getMinPoint() {
        return min;
    }

    /**
     * Getter method for max point of bounding box
     * 
     * @return max point of bounding box
     */
    @Override
    public Point3D getMaxPoint() {
        return max;
    }

    /**
     * Adds {@link Intersectable}s to the bounding box and recalculates the min and
     * max points
     * 
     * @param geometries to be added
     */
    @Override
    public void add(Intersectable... geometries) {
        for (Intersectable g : geometries) {
            min = min != null ? min(g.getMinPoint(), min) : g.getMinPoint();
            max = max != null ? max(g.getMaxPoint(), max) : g.getMaxPoint();
            this.geometries.add(g);
        }
    }

    /**
     * Overridden method to avoid tracing intersections of objects inside bounding
     * box if it isn't intersected
     *
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray r, double limit) {
        if (!getsIntersected(r))
            return null;
        return super.findGeoIntersections(r, limit);
    }

    /**
     * Method that checks if the bounding box gets intersected at all
     * 
     * @param r intersecting {@link Ray}
     * @return true if it gets intersected
     * @return false if it isn't intersected
     */
    public boolean getsIntersected(Ray r) {
        double tmin = 0;
        double tmax = Double.POSITIVE_INFINITY;
        Point3D rDir = r.getDir().getHead();
        Point3D rStart = r.getStartPoint();
        for (int i = 0; i < 3; ++i) {
            double coord = rDir.getCoord(i);
            if (coord == 0)
                continue;
            double invD = alignZero(1 / coord);
            double t0 = alignZero((min.getCoord(i) - rStart.getCoord(i)) * invD);
            double t1 = alignZero((max.getCoord(i) - rStart.getCoord(i)) * invD);
            if (invD < 0) {
                //double temp = t1;
                //t1 = t0;
                //t0 = temp;
                tmin = t1 > tmin ? t1 : tmin;
                tmax = t0 < tmax ? t0 : tmax;
            } else {
                tmin = t0 > tmin ? t0 : tmin;
                tmax = t1 < tmax ? t1 : tmax;
            }
            if (tmax < tmin)
                return false;
        }
        return true;
    }

    /**
     * finds the min x, y and z values from a list of {@link Point3D}s
     * 
     * @param points
     * @return a {@link Point3D} with the min x, y and z values
     */
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

    /**
     * finds the max x, y and z values from a list of {@link Point3D}s
     * 
     * @param points
     * @return a {@link Point3D} with the max x, y and z values
     */
    static Point3D max(Point3D... points) {
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

    @Override
    public BoundingBox createHierarchy() {
        int longestAxis = calcLongestAxis(this);
        double middle = ((this.getMaxPoint().getCoord(longestAxis) - this.getMinPoint().getCoord(longestAxis)) / 2) + this.getMinPoint().getCoord(longestAxis);

        BoundingBox b0 = new BoundingBox(splitByAxis(this, longestAxis, middle, true).toArray(new Intersectable[0]));
        BoundingBox b1 = new BoundingBox(splitByAxis(this, longestAxis, middle, false).toArray(new Intersectable[0]));
        if (b0.geometries.isEmpty() || b1.geometries.isEmpty()) return this;
        return new BoundingBox(b0.createHierarchy(), b1.createHierarchy());
    }
}