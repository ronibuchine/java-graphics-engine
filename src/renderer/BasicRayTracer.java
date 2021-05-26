package renderer;

import java.util.List;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
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

    @Override
    public Color traceRay(Ray r) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(r);
        if (intersections == null)
            return scene.background;
        GeoPoint closestPoint = r.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, r);
    }

    /**
     * Calculates the color of a given point
     * 
     * @param point
     * @return The {@link Color} of the point
     */
    private Color calcColor(GeoPoint point, Ray r) {
        return scene.ambientLight.getIntensity().add(point.geometry.getEmission()).add(calcLocalEffects(point, r));
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
            if (nl * nv > 0) { // camera and light are on same side of geometry
                if (unshaded(l, intersection)) {
                    Color intensity = light.getIntensity(intersection.point);
                    color = color.add(calcDiffusion(kd, l, n, intensity),
                            calcSpecular(ks, l, n, r.getDir(), nShininess, intensity));
                }
            }
        }
        return color;
    }

    /**
     * Method which determines if an intersection point is covered by a shadow
     * 
     * @param l            Vector towards the light source
     * @param n            normal vector from object
     * @param intersection the intersection point to be checked
     */
    private boolean unshaded(Vector l, GeoPoint intersection) {
        Vector lightDirection = l.scale(-1);
        Vector n = intersection.geometry.getNormal(intersection.point);
        // we must check the direction of the light in order to make sure our ray
        // direction is correct
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = intersection.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        return intersections == null;
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

}
