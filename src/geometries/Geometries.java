package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.Point3D;
import primitives.Ray;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs
 * Composite class which contains all of the scene's {@link Geometry Geometries}
 */
public class Geometries implements Intersectable {
    
    protected List<Intersectable> geometries;
    
    /**
     * Default constructor for Geometries which creates an empty list
     */
    public Geometries() {
        this.geometries = new LinkedList<>();   //efficient for adding elements. no need for random element access, only iteration
    }

    /**
     * Constructor which takes in a dynamic amount of {@link Intersectable}s and stores them in a list
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<>(Arrays.asList(geometries));
    }

    /**
     * Method that adds lists of {@link Geometry Geometries} to the list of scene geometries
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(List.of(geometries));
    }

    /**
     * Finds all intersection points of a {@link Ray} and the list of {@link Geometries}
     * @param r the intersecting {@link Ray}
     * @return {@link List} of {@link Point3D}s that {@link Ray} intersects
     */
    @Override
    public List<Point3D> findIntersections(Ray r, double limit) {
        List<Point3D> list = new LinkedList<>();
        for (Intersectable geom : geometries) {
            List<Point3D> temp = geom.findIntersections(r, limit);
            if (temp != null) list.addAll(temp);
        }
        if (list.isEmpty()) {
            return null;
        }
        return list;
    } 

    /**
     * Finds all intersection {@link GeoPoint}s of a {@link Ray} and the list of {@link Geometries} within a given distance
     * @param r the intersecting {@link Ray}
     * @param limit
     * @return {@link List} of {@link GeoPoint}s that {@link Ray} intersects
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray r, double limit) {
        List<GeoPoint> list = new LinkedList<>();
        for (Intersectable geom : geometries) {
            List<GeoPoint> temp = geom.findGeoIntersections(r, limit);
            if (temp != null) list.addAll(temp);
        }
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public Point3D getMinPoint() {
        List<Point3D> points = new LinkedList<>();
        geometries.forEach(g -> points.add(g.getMinPoint()));
        return BoundingBox.min(points.toArray(new Point3D[0]));
    }

    @Override
    public Point3D getMaxPoint() {
        List<Point3D> points = new LinkedList<>();
        geometries.forEach(g -> points.add(g.getMaxPoint()));
        return BoundingBox.max(points.toArray(new Point3D[0]));
    } 

    /**
     * Method that returns a Geometries object with all finite Geometry's organized in {@link BoundingBox}s
     * @return
     */
    /*
    public Geometries createHierarchy() {
        Geometries g = new Geometries();
        Geometries f = this.flatten();

        //remove all infinite Geometry's
        for (Intersectable i : f.geometries) {
            try {
                i.getMinPoint();
            } catch (IllegalArgumentException e) {
                g.add(i);
                f.geometries.remove(i);
            }
        }

        //sort out the finite Geometry's into regions
        BoundingBox[] regions = new BoundingBox[8];
        Point3D center = f.getMiddle();
        for (Intersectable i : f.geometries) {
            int n = 0;
            n += i.getMiddle().getX() < center.getX() ? 0 : 1;
            n += i.getMiddle().getY() < center.getY() ? 0 : 2;
            n += i.getMiddle().getZ() < center.getZ() ? 0 : 4;
            if (regions[n] == null) regions[n] = new BoundingBox(i);
            else regions[n].add(i);
        }
        for (BoundingBox i : regions) {
            if (i != null) g.add(i);
        }
        return g;
    }*/

    public Geometries createHierarchy() {
        Geometries g = new Geometries();
        BoundingBox f = new BoundingBox();

        //remove all infinite Geometry's
        for (Intersectable i : this.flatten().geometries) {
            try {
                i.getMinPoint();
                f.add(i);
            } catch (IllegalArgumentException e) {
                g.add(i);
            }
        }

        if (!f.geometries.isEmpty()) f = f.createHierarchy();
        if (g.geometries.isEmpty()) return f;
        if (!f.geometries.isEmpty()) g.add(f);
        return g;
    }

    /**
     * Helper function that creates a new Geometries instance without any sub-Geometries (flattens the list)
     * @return
     */
    private Geometries flatten() {
        Geometries g = new Geometries();
        for (Intersectable i : geometries) {
            if (i instanceof Geometries) {
                ((Geometries)i).flatten().geometries.forEach(x -> g.add(x));
            }
            else {
                g.add(i);
            }
        }
        return g;
    }

    /**
     * Helper function that finds longest axis of a BoundingBox
     * 0 = x-axis, 1 = y-axis, 2 = z-axis
     * @param b
     * @return
     */
    protected int calcLongestAxis(BoundingBox b) {
        int longestAxis = 0;
        double axisLength = b.getMaxPoint().getX() - b.getMinPoint().getX();
        for (int i = 1; i < 3; ++i) {
            if (b.getMaxPoint().getCoord(i) - b.getMinPoint().getCoord(i) > axisLength) longestAxis = i;
        }
        return longestAxis;
    }

    /**
     * Helper function that finds all Geometry's on a given half of a BoundingBox
     * @param b
     * @param axis the axis of the BoundingBox to split
     * @param middle the coordinate of the axis
     * @param greater
     * @return
     */
    protected List<Intersectable> splitByAxis(BoundingBox b, int axis, double middle, boolean greater) {
        List<Intersectable> list = new LinkedList<>();
        for (Intersectable i : b.geometries) {
            if ((greater && i.getMiddle().getCoord(axis) >= middle) || (!greater && i.getMiddle().getCoord(axis) < middle)) list.add(i);
        }
        return list;
    }

}
