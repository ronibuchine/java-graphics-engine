package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class BasicRayTracer extends RayTraceBase {

    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray r) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(r);
        if (intersections == null)
            return scene.background;
        GeoPoint closestPoint = r.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color of a given point
     * @param point
     * @return The {@link Color} of the point
     */
    private Color calcColor(GeoPoint point) {
        return scene.ambientLight.getIntensity()
            .add(point.geometry.getEmission());
    }

}
