package primitives;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

import geometries.Intersectable.GeoPoint;

/**
 * Class Ray is the basic ray class to represent a camera ray in a scene in
 * 3-dimensional space
 * 
 * @see Point3D
 * @see Vector
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class Ray {

    /**
     * DELTA is a constant to help with reflected rays. We want the origin point to
     * be slightly off the geometry to avoid bugs.
     */
    private static final double DELTA = 0.1;

    /**
     * Random number generator
     */
    private static Random GENERATOR = new Random();

    /**
     * Ray takes a direction {@link Vector} as a parameter
     */
    Vector dir;

    /**
     * the origin {@link Point3D} of the Ray
     */
    Point3D p0;

    /**
     * ctor normalizes the {@link Vector} and creates the object with that
     * {@link Vector} and {@link Point3D}
     * 
     * @param dir {@link Vector} representing {@link Ray}'s direction
     * @param p0  {@link Point3D} representing {@link Ray}'s start point
     */
    public Ray(Point3D p0, Vector dir) {
        this.dir = dir.normalized();
        this.p0 = p0;
    }

    /**
     * Getter for {@link Ray}'s direction vector
     * 
     * @return normal vector pointing in {@link Ray}'s direction
     */
    public Vector getDir() {
        return dir;
    }

    public Point3D getStartPoint() {
        return p0;
    }

    /**
     * Calculates {@link Point3D} a given length away from {@link Ray}'s starting
     * point
     * 
     * @param t length of point from beggining of {@link Ray}
     * @return {@link Point3D} that is given length from {@link Ray}'s start point
     */
    public Point3D getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    /**
     * Given a {@link List} of {@link Point3D}s, finds the closest point to the
     * ray's {@link Ray#p0 origin} point
     * 
     * @param list list of {@link Point3D}s
     * @return {@link Point3D} closest to {@link Ray}'s origin point or returns null
     *         if the list is empty
     */
    public Point3D findClosestPoint(List<Point3D> list) {
        if (!list.isEmpty()) {
            Comparator<Point3D> byDistance = (p1, p2) -> p1.distanceSquared(p0) > p2.distanceSquared(p0) ? 1 : -1;
            return Collections.min(list, byDistance);
        }
        return null; // is an empty list meant to return null?
    }

    /**
     * Given a {@link List} of {@link GeoPoint}s, finds the closest point to the
     * ray's {@link Ray#p0 origin} point
     * 
     * @param list list of {@link GeoPoint}s
     * @return {@link GeoPoint} closest to {@link Ray}'s origin point or returns
     *         null if the list is empty
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> list) {
        if (!list.isEmpty()) {
            Comparator<GeoPoint> byDistance = (p1,
                    p2) -> p1.point.distanceSquared(p0) > p2.point.distanceSquared(p0) ? 1 : -1;
            return Collections.min(list, byDistance);
        }
        return null; // is an empty list meant to return null?
    }

    /**
     * Constructs a new Ray that starts a bit off of the intersection point so it
     * doesn't re-intersect with itself
     * 
     * @param gp  intersection point
     * @param dir {@link Vector} pointing in direction of original {@link Ray}
     * @return
     */
    public static Ray constructRefractionRay(GeoPoint gp, Vector dir) {
        Vector normal = gp.geometry.getNormal(gp.point);
        // we must check the direction of the light
        // in order to make sure our ray direction is correct
        Vector delta = normal.scale(normal.dotProduct(dir) > 0 ? DELTA : -DELTA);
        return new Ray(gp.point.add(delta), dir);
    }

    /**
     * Constructs a ray that is reflected at the intersection point, based off the
     * incident angle
     * 
     * @param gp
     * @param incident
     * @return
     */
    public static Ray constructReflectionRay(GeoPoint gp, Vector incident) {
        Vector normal = gp.geometry.getNormal(gp.point);
        Vector reflection;
        try {
            reflection = incident.subtract(normal.scale(2 * normal.dotProduct(incident))).normalized();
        } catch (IllegalArgumentException e) {
            return null;
        }
        return constructRefractionRay(gp, reflection);
    }

    /**
     * Constructs a list of Rays that are refracted at an intersection point
     * 
     * @param gp     intersection point
     * @param dir    direction to center the spread
     * @param spread narrowness of spread
     * @param rayCount      number of constructed rays to create
     * @param loops  number of times to loop in a circle (0 for random distribution)
     */
    public static List<Ray> constructRefractionRays(GeoPoint gp, Vector dir, double spread, double loops,
            int rayCount) {
        if (rayCount < 1 || spread < 1)
            return null;
        List<Ray> rays = new LinkedList<>();
        Vector normal = gp.geometry.getNormal(gp.point);
        // we must check the direction of the light
        // in order to make sure our ray direction is correct
        Vector delta = normal.scale(normal.dotProduct(dir) > 0 ? DELTA : -DELTA);

        Point3D head = gp.point.add(delta);
        if (Double.isInfinite(spread))
            return List.of(new Ray(head, dir));
        Vector vRight = dir.calcVright();
        Vector original = dir.scale(spread);
        rays.add(new Ray(head, original));
        
        if (loops == 0) rays.addAll(randSpread(head, original, vRight, normal, rayCount));
        else rays.addAll(loopSpread(head, original, vRight, normal, loops, rayCount));
        return rays;
    }

    private static List<Ray> randSpread(Point3D head, Vector original, Vector vRight, Vector normal, int rayCount) {
        List<Ray> list = new LinkedList<>();
        
        Vector offset;
        for (int i = 1; i < rayCount; ++i) {
            try {
                offset = vRight.rotate(original.normalized(), GENERATOR.nextDouble() * 360).scale(GENERATOR.nextDouble());
                Ray ray = new Ray(head, original.add(offset));
                if (ray.dir.dotProduct(normal) > 0 == original.dotProduct(normal) > 0)
                    list.add(ray);
            } catch (IllegalArgumentException e) {
                --i;
            }
        }
        return list;
    }
    private static List<Ray> loopSpread(Point3D head, Vector original, Vector vRight, Vector normal, double loops, int rayCount) {
        List<Ray> list = new LinkedList<>();

        double scale = 1.0 / rayCount;
        double angle = 360.0 * loops / rayCount;

        Vector offset;
        for (int i = 1; i < rayCount; ++i) {
            offset = vRight.scale(scale * i).rotate(original.normalized(), angle * i);
            Ray ray = new Ray(head, original.add(offset));
            if (ray.dir.dotProduct(normal) > 0 == original.dotProduct(normal) > 0)// (!intersectsWithSelf(gp.geometry, ray))
                list.add(ray);
        }
        return list;
    }

    /**
     * Calculates main reflection ray and constructs a beam as if they were
     * refracted
     */
    public static List<Ray> constructReflectionRays(GeoPoint gp, Vector incident, double spread, double loops,
            int rayCount) {
        Vector normal = gp.geometry.getNormal(gp.point);
        Vector reflection;
        try {
            reflection = incident.subtract(normal.scale(2 * normal.dotProduct(incident))).normalized();
        } catch (IllegalArgumentException e) {
            return null;
        }

        return constructRefractionRays(gp, reflection, spread, loops, rayCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Ray))
            return false;
        Ray other = (Ray) obj;
        return dir.equals(other.dir) && p0.equals(other.p0);
    }

    @Override
    public String toString() {
        StringJoiner ray = new StringJoiner(": ", "{", "}");
        ray.add(p0.toString()).add(dir.toString());
        return ray.toString();
    }

}
