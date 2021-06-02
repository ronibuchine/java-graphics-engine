package renderer;

import java.util.List;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static primitives.Util.alignZero;

public class BasicRayTracer extends RayTraceBase {

    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double INITIAL_K = 1;
    private static final double MIN_CALC_COLOR_K = 0.001;


    @Override
    public Color traceRay(Ray r) {
        GeoPoint closest = findClosestIntersection(r);
        return closest == null ? scene.background : calcColor(closest, r.getDir());
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
    private Color calcColor(GeoPoint point, Vector incident, int rLevel, double k) {
        Color color = point.geometry.getEmission().add(calcLocalEffects(point, incident, k));
        return rLevel == 1 ? color : color.add(calcGlobalEffects(point, incident, rLevel, k));
    }
    /**
     * Base case of recursion. Adds ambient light to the point
     * @param gp
     * @param r
     * @return
     */
    private Color calcColor(GeoPoint gp, Vector incident) {
        return calcColor(gp, incident, MAX_CALC_COLOR_LEVEL, INITIAL_K)
            .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates effects based on light sources and material
     * 
     * @param intersection
     * @param incident
     * @param k Scale of lighting
     * @return {@link Color}
     */
    private Color calcLocalEffects(GeoPoint intersection, Vector incident, double k) {
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(incident));
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
                double ktr = transparency(light, l, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color intensity = light.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusion(kd, l, n, intensity),
                            calcSpecular(ks, l, n, incident, nShininess, intensity));
                }
            }
        }
        return color;
    }

    /**
     * Method which determines an intersection points level of shade
     * 
     * @param l            Vector from light source
     * @param intersection the intersection point to be checked
     */
    private double transparency(LightSource source, Vector l, GeoPoint gp) {
        Vector toLight = l.scale(-1);
        Ray newToLight = Ray.constructRefractionRay(gp, toLight);

        double lightDistance = source.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(newToLight, lightDistance);
        if (intersections == null) return 1;
        
        double ktr = 1; //default scaling of shadow
        for (GeoPoint p : intersections) {
            ktr *= p.geometry.getMaterial().kT;
            if (ktr < MIN_CALC_COLOR_K) return 0;
        }
        return ktr;
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

    /**
     * Calculates reflection and refraction lighting of intersection point
     * @param gp
     * @param incident
     * @param rLevel Maximum levels of recursion left
     * @param k Scale effect of current calculations on the pixel color
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector incident, int rLevel, double k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();
        double kkR = k * material.kR;
        if (kkR > MIN_CALC_COLOR_K) {
            color = calcGlobalEffect(Ray.constructReflectionRays(gp, incident, material.glossiness, distribution), rLevel, material.kR, kkR);
        }
        double kkT = k * material.kT;
        if (kkT > MIN_CALC_COLOR_K) {
            color = color.add(calcGlobalEffect(Ray.constructRefractionRays(gp, incident, material.glossiness, distribution), rLevel, material.kT, kkT));
        }
        return color;
    }

    /**
     * Calculates Color of reflection/refraction ray
     * @param r {@link Ray}
     * @param rLevel Maximum levels of recursion left
     * @param kX Scale effect of calculation
     * @param kkX Total scale effect on final pixel color
     * @return
     */
    private Color calcGlobalEffect(Ray r, int rLevel, double kX, double kkX) {
        GeoPoint gp = findClosestIntersection(r);
        return gp == null ? scene.background : calcColor(gp, r.getDir(), rLevel - 1, kkX).scale(kX);
    }
}
