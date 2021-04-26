package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.Point3D;
import primitives.Ray;

/**
 * @author Roni Buchine
 * @author Eliezer Jacobs
 * Compiosite class which contains all of our scenes geometries
 */
public class Geometries implements Intersectable {
    
    private List<Intersectable> geometries;

    public Geometries(List<Intersectable> geometries) {
        this.geometries = geometries;
    }

    public Geometries() {
        this.geometries = new LinkedList<>();   //efficient for adding elements. no need for random element access, only iteration
    }

    public void add(Intersectable geometry) {
        geometries.add(geometry);
    }

    @Override
    public List<Point3D> findIntersections(Ray r) {
        List<Point3D> list = new LinkedList<>();
        for (Intersectable geom : geometries) {
            list.addAll(geom.findIntersections(r));
        }
        return list;
    } 


}
