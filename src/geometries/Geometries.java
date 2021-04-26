package geometries;

import java.util.Arrays;
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
    
    public Geometries() {
        this.geometries = new LinkedList<>();   //efficient for adding elements. no need for random element access, only iteration
    }

    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<>(Arrays.asList(geometries));
    }

    public void add(Intersectable... geometry) {
        geometries.addAll(List.of(geometry));
    }

    /**
     * Finds all intersection points of a {@link Ray} and the list of {@link Geometries}
     * @param r the intersecting {@link Ray}
     * @return {@link List} of {@link Point3D}'s that {@link Ray} intersects
     */
    @Override
    public List<Point3D> findIntersections(Ray r) {
        List<Point3D> list = new LinkedList<>();
        for (Intersectable geom : geometries) {
            List<Point3D> temp = geom.findIntersections(r);
            if (temp != null) list.addAll(temp);
        }
        return list;
    } 


}
