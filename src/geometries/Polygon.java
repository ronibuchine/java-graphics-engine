package geometries;

import primitives.Vector;
import primitives.Point3D;
import primitives.Ray;

import static primitives.Util.*;

import java.util.List;
import java.util.StringJoiner;

/**
 * {@link Polygon} class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class Polygon extends Geometry {
	/**
	 * List of {@link Polygon}'s vertices
	 */
	protected List<Point3D> vertices;
	/**
	 * Associated {@link Plane} in which the polygon lays
	 */
	protected Plane plane;

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex)</li>
	 *                                  </ul>
	 */
	public Polygon(Point3D... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			return; // no need for more tests for a {@link Triangle}

		Vector n = plane.getNormal();

		// Subtracting any subsequent {@link Point3D}s will throw an IllegalArgumentException
		// because of Zero {@link Vector} if they are in the same {@link Point3D}
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
	}

	@Override
	/**
	 * Getter for the normal {@list Vector} of the Polygon
	 * @param p A {@link Point3D}
	 * @return The normal {@link Vector}
	 */
	public Vector getNormal(Point3D point) {
		return plane.getNormal();
	}

	@Override
	public String toString() {

		StringJoiner allVertices = new StringJoiner(", ", "{","}");
		for (Point3D a:vertices) {
			allVertices.add(a.toString());
		}
		return allVertices.toString();
	}

	/**
	 * Implementation of findGeoIntersections for {@link Polygon}
	 * @param r The {@link Ray}
	 * @return {@link List} of {@link GeoPoint}s
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray r, double limit) {
		List <GeoPoint> list = plane.findGeoIntersections(r, limit); 	//gets point where ray intersects the polygon of the plane
		if (list == null) return null;						//if ray doesn't intersect plane at all, then no intersection point
		GeoPoint p = list.get(0);
		try {	
			Vector n = plane.getNormal();
			Vector edge1 = vertices.get(vertices.size() - 2).subtract(p.point);
			Vector edge2 = vertices.get(vertices.size() - 1).subtract(p.point);
			for (int i = 0; i < vertices.size(); ++i) {
				if (edge1.crossProduct(edge2).dotProduct(n) <= 0) return null;
				edge1 = edge2;
				edge2 = vertices.get(i).subtract(p.point);
			}
			/*boolean sign = (plane.getNormal().dotProduct(edge1.crossProduct(edge2)) > 0);
			for (int i = 1; i < vertices.size(); ++i) {		//calculate normal vectors from point to pair of consecutive points on polygon and make sure they are facing same direction
				edge1 = edge2;
				edge2 = vertices.get(i).subtract(p.point);
				if (sign != plane.getNormal().dotProduct(edge1.crossProduct(edge2)) > 0) return null; //point is outside of polygon
			}*/
		}
		catch (IllegalArgumentException e) { return null; } //intersection point is on edge of polygon
		p.geometry = this; //change GeoPoint's geometry to Polygon (instead of Plane)
		return List.of(p); //point is inside polygon
	}

	@Override
	public Point3D getMinPoint() {
		return Point3D.min(vertices.toArray(new Point3D[0]));
	}

	@Override
	public Point3D getMaxPoint() {
		return Point3D.max(vertices.toArray(new Point3D[0]));
	}
	
}
