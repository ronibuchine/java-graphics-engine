package renderer;

import java.util.List;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static primitives.Util.alignZero;

public class BasicRayTracer extends RayTraceBase {

    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * DELTA is a constant to help with shadows. We want the intersection point to
     * be slightly off the geometry to avoid bugs.
     */
    private static final double DELTA = 0.1;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double INITIAL_K = 1;
    private static final double MIN_CALC_COLOR_K = 0.001;


    @Override
    public Color traceRay(Ray r) {
        GeoPoint closest = findClosestIntersection(r);
        return closest == null ? scene.background : calcColor(closest, r);
    }

    /**
     * Helper function to get list of intersections and return the closest one
     * @param r
     * @return
     */
    private GeoPoint findClosestIntersection(Ray r) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(r);
        if (intersections == null) return null;
        return r.findClosestGeoPoint(intersections);
    }

    /**
     * Calculates the color of a given point
     * 
     * @param point
     * @return The {@link Color} of the point
     */
    private Color calcColor(GeoPoint point, Ray r, int rLevel, double k) {
        Color color = point.geometry.getEmission().add(calcLocalEffects(point, r));
        return rLevel == 1 ? color : color.add(calcGlobalEffects(point, r, rLevel, k));
    }
    /**
     * Base case of recursion. Adds ambient light to the point
     * @param gp
     * @param r
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray r) {
        return calcColor(gp, r, MAX_CALC_COLOR_LEVEL, INITIAL_K)
            .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates effects based on light sources and material
     * 
     * @param intersection
     * @param r
     * @return {@link Color}
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray r) {
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(r.getDir()));
        if (nv == 0) { // ray is perpendicular to geometry (probably should never happen)
            return Color.BLACK;
        }

        // declares some variables to make code cleaner
        int nShininess = intersection.geometry.getMaterial().nShininess;
        double kd = intersection.geometry.getMaterial().kD;
        double ks = intersection.geometry.getMaterial().kS;

        Color color = Color.BLACK;
        for (LightSource light : scene.lights) {
            Vector l = light.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0 && unshaded(light, l, intersection)) { // camera and light are on same side of geometry and nothing is in between them
                Color intensity = light.getIntensity(intersection.point);
                color = color.add(calcDiffusion(kd, l, n, intensity),
                        calcSpecular(ks, l, n, r.getDir(), nShininess, intensity));
            }
        }
        return color;
    }

    /**
     * Method which determines if an intersection point is covered by a shadow
     * 
     * @param l            Vector from light source
     * @param intersection the intersection point to be checked
     */
    private boolean unshaded(LightSource source, Vector l, GeoPoint intersection) {
        Vector toLight = l.scale(-1);
        Vector n = intersection.geometry.getNormal(intersection.point);
        // we must check the direction of the light in order to make sure our ray
        // direction is correct
        Vector delta = n.scale(n.dotProduct(toLight) > 0 ? DELTA : -DELTA);
        Point3D point = intersection.point.add(delta);
        Ray newToLight = new Ray(point, toLight);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(newToLight, source.getDistance(intersection.point));
        if (intersections == null) return true;

        double lightDistance = source.getDistance(intersection.point);
        for (GeoPoint p : intersections) {
            if (alignZero(p.point.distance(intersection.point) - lightDistance) <= 0 && p.geometry.getMaterial().kT == 0) return false;
        }
        return true;
    }

    /**
     * Calculates amount of diffusive light
     * 
     * @param kD
     * @param fromLight
     * @param normal
     * @param lightIntensity
     * @return {@link Color} of diffusive light
     */
    private Color calcDiffusion(double kD, Vector fromLight, Vector normal, Color lightIntensity) {
        return lightIntensity.scale(Math.abs(normal.dotProduct(fromLight) * kD));
    }

    /**
     * Calculates amount of specular light
     * 
     * @param kS
     * @param fromLight
     * @param normal
     * @param fromCamera
     * @param shininess
     * @param lightIntensity
     * @return {@link Color} of specular light
     */
    private Color calcSpecular(double kS, Vector fromLight, Vector normal, Vector fromCamera, int shininess,
            Color lightIntensity) {
        Vector reflection;
        try {
            reflection = fromLight.subtract(normal.scale(2 * normal.dotProduct(fromLight))).normalized();
        } catch (IllegalArgumentException e) {
            return Color.BLACK;
        }
        double vr = alignZero(reflection.dotProduct(fromCamera.scale(-1)));
        if (vr <= 0) {
            return Color.BLACK;
        }
        return lightIntensity.scale(Math.pow(vr, shininess) * kS);
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray r, int rLevel, double k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();
        double kkR = k * material.kR;
        if (kkR > MIN_CALC_COLOR_K) {
            color = calcReflection(gp, r.getDir(), rLevel, material.kR, kkR);
        }
        double kkT = k * material.kT;
        if (kkT > MIN_CALC_COLOR_K) {
            color = color.add(calcRefraction(gp, r.getDir(), rLevel, material.kT, kkT));
        }
        return color;
    }

    private Color calcReflection(GeoPoint p, Vector incident, int rLevel, double kR, double k) {
        Vector normal = p.geometry.getNormal(p.point);
        Vector reflection;
        try {
            reflection = incident.subtract(normal.scale(2 * normal.dotProduct(incident))).normalized();
        } catch (IllegalArgumentException e) { return Color.BLACK; }
        // we must check the direction of the light in order to make sure our ray
        // direction is correct
        //Vector delta = n.scale(n.dotProduct(toLight) > 0 ? DELTA : -DELTA);
        Point3D newPoint = p.point.add(reflection.scale(DELTA));
        Ray newReflection = new Ray(newPoint, reflection);
        GeoPoint gp = findClosestIntersection(newReflection);
        return gp == null ? scene.background : calcColor(gp, newReflection, rLevel - 1, k).scale(kR);
    }
    private Color calcRefraction(GeoPoint p, Vector incident, int rLevel, double kT, double k) {
        Point3D newPoint = p.point.add(incident.scale(DELTA));
        Ray refraction = new Ray(newPoint, incident);
        GeoPoint gp = findClosestIntersection(refraction);
        return gp == null ? scene.background : calcColor(gp, refraction, rLevel - 1, k).scale(kT);
    }

}
