package elements;

import primitives.*;

import static primitives.Util.*;

public class Camera {
    /**
     * A {@link Point3D} represent where the camera is stationed
     */
    private Point3D location;

    /**
     * A {@link Vector} representing which direction the {@link Camera} is facing
     */
    private Vector vTO;

    /**
     * A {@link Vector} representing which way the {@link Camera} is oriented
     */
    private Vector vUP;

    /**
     * A {@link Vector} to make some operations simpler (= vTO x vUP)
     */
    private Vector vRight;

    /**
     * Getter for the location of the {@link Camera}
     * @return {@link Point3D}
     */

     

    public Point3D getLocation() {
        return location;
    }

    /**
     * Getter for the vector representing which direction the {@link Camera} is facing
     * @return {@link Vector}
     */
    public Vector getTO() {
        return vTO;
    }

    /**
     * Getter for the vector representing the {@link Camera}'s orientation
     * @return {@link Vector}
     */
    public Vector getUP() {
        return vUP;
    }

    /**
     * Getter for the {@link Vector} vRight
     * @return {@link Vector}
     */
    public Vector getRight() {
        return vRight;
    }

    /**
     * Simple constructor initializes all instance variables.
     * First it makes sure that up and to are orthogonal.
     * The {@link Vector} vRight is calculated as the {@link Vector#crossProduct()} of {@link Vector}'s up and to
     * @param location
     * @param up
     * @param to The direction the {@link Camera} is facing
     */
    public Camera(Point3D location, Vector up, Vector to) {
        if (isZero(up.dotProduct(to))) {
            throw new IllegalArgumentException("Vectors must be orthogonal");
        }
        this.location = location;
        this.vTO = to.normalized();
        this.vUP = up.normalized();
        this.vRight = to.crossProduct(up); //cross product of orthogonal unit vectors is already normal
    }
}
