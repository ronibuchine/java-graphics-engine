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
     * The width of the {@link Camera}'s view plane
     */
    private double width;
    /**
     * The height of the {@link Camera}'s view plane
     */
    private double height;
    /**
     * The distance of the view plane from the {@link Camera}
     */
    private double distance;

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



    /**
     * Setter method for the {@link Camera}'s view plane dimensions
     * @param width
     * @param height
     * @return The {@link Camera}
     */
    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Setter method for the distance of the view plane from the {@link Camera}
     * @param distance
     * @return The {@link Camera}
     */
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a {@link Ray} that goes from the {@link Camera} to a pixel on its view plane
     * @param nX Number of columns in view plane
     * @param nY Number of rows in the view plane
     * @param j Column index of pixel
     * @param i Row index of pixel
     * @return A {@link Ray} that goes through given pixel in the view plane
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D pIJ = location.add(vTO.scale(distance));    //initialize pixel at center of view plane
        double xJ;
        double yI;

        if (nX % 2 == 0) {
            xJ = (width/nX) * (j - nX/2 + .5);
        }
        else {
            xJ = (width/nX) * (j - (nX-1) / 2);         //number of pixels to move horizontally * the height of pixel
        } 

        if (nY % 2 == 0) {
            yI = (height/nY) * (i - nY/2 + .5);
        }
        else {
            yI = (height/nY) * (i - (nY-1) / 2);
        }

        if (!isZero(xJ)) pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI)) pIJ = pIJ.add(vUP.scale(-yI));     //when i is above center of view plane, yI will be negative
        return new Ray(location, pIJ.subtract(location));   //Ray's constructor will normalize the direction vector
    }

    public Camera move(Vector move) {   //physical location movement
        location.add(move);
        return this;
    }
    public Camera roll(double angle) {   //roll rotation
        //TODO 3D Vector Rotation
        return this;
    }
    public Camera pitch(double angle) { //up-down rotation
        //TODO 3D Vector Rotation
        return this;
    }
    public Camera yaw(double angle) {     //side-to-side rotation
        //TODO 3D Vector Rotation
        return this;
    }
}
