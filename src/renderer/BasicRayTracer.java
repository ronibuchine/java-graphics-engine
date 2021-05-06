package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

public class BasicRayTracer extends RayTraceBase {

    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray r) {
        List<Point3D> intersections = scene.geometries.findIntersections(r);
        if (intersections == null)
            return scene.background;
        Point3D closestPoint = r.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    private Color calcColor(Point3D point) {
        return scene.ambientLight.getIntensity();
    }

}
