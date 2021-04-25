package geometries;

import java.util.ArrayList;
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
        this.geometries = new ArrayList<>();        // we need to have a reasoning for choosing an arraylist over a linked list
    }

    public void add(Intersectable geometry) {
        geometries.add(geometry);
    }

    @Override
    public List<Point3D> findIntersections(Ray r) {
        // TODO Auto-generated method stub
        return null;
    } 


}
