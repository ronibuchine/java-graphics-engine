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
    
    private List<Intersectable> geometries;
    
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
        if (list.size() == 0) {
            return null;
        }
        return list;
    } 


}
