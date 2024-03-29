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
    private double width = 1; // default value
    /**
     * The height of the {@link Camera}'s view plane
     */
    private double height = 1; // default value
    /**
     * The distance of the view plane from the {@link Camera}
     */
    private double distance = 1; // default value

    /**
     * Getter for the location of the {@link Camera}
     * 
     * @return {@link Point3D}
     */
    public Point3D getLocation() {
        return location;
    }

    /**
     * Getter for the vector representing which direction the {@link Camera} is
     * facing
     * 
     * @return {@link Vector}
     */
    public Vector getTO() {
        return vTO;
    }

    /**
     * Getter for the vector representing the {@link Camera}'s orientation
     * 
     * @return {@link Vector}
     */
    public Vector getUP() {
        return vUP;
    }

    /**
     * Getter for the {@link Vector} vRight
     * 
     * @return {@link Vector}
     */
    public Vector getRight() {
        return vRight;
    }

    /**
     * Getter for View Plane width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Getter for view plane height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Simple constructor initializes all instance variables. First it makes sure
     * that up and to are orthogonal. The {@link Vector} vRight is calculated as the
     * {@link Vector#crossProduct} of {@link Vector}'s up and to
     * 
     * @param location
     * @param to       The direction the {@link Camera} is facing
     * @param up
     */
    public Camera(Point3D location, Vector to, Vector up) {
        if (!isZero(up.dotProduct(to))) {
            throw new IllegalArgumentException("Vectors must be orthogonal");
        }
        this.location = location;
        this.vTO = to.normalized();
        this.vUP = up.normalized();
        this.vRight = to.crossProduct(up); // cross product of orthogonal unit vectors is already normal
    }

    /**
     * Setter method for the {@link Camera}'s view plane dimensions
     * 
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
     * 
     * @param distance
     * @return The {@link Camera}
     */
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a {@link Ray} that goes from the {@link Camera} to a pixel on its
     * view plane
     * 
     * @param nX Number of columns in view plane
     * @param nY Number of rows in the view plane
     * @param j  Column index of pixel
     * @param i  Row index of pixel
     * @return A {@link Ray} that goes through given pixel in the view plane
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D pIJ = location.add(vTO.scale(distance)); // initialize pixel at center of view plane

        if (nX % 2 == 0) {
            pIJ = pIJ.add(vRight.scale((width / nX) * (j - nX / 2 + .5))); // number of pixels to move horizontally *
                                                                           // height of pixel
        } else {
            double xJ = (width / nX) * (j - (nX - 1) / 2);
            if (!isZero(xJ))
                pIJ = pIJ.add(vRight.scale(xJ));
        }

        if (nY % 2 == 0) {
            pIJ = pIJ.add(vUP.scale(-(height / nY) * (i - nY / 2 + .5))); // number of pixels to move vertically *
                                                                          // height of pixel
        } else {
            double yI = (height / nY) * (i - (nY - 1) / 2);
            if (!isZero(yI))
                pIJ = pIJ.add(vUP.scale(-yI)); // when i is above center of view plane, yI will be negative
        }

        return new Ray(location, pIJ.subtract(location)); // Ray's constructor will normalize the direction vector
    }

    /**
     * Moves camera location
     * @param right number of pixels to move rightward
     * @param forward number of pixels to move forward
     * @param up number of pixels to move upward
     * @return
     */
    public Camera move(double right, double forward, double up) { // physical location movement
        if (right != 0) {
            location = location.add(vRight.scale(right));
        }
        if (forward != 0) {
            location = location.add(vTO.scale(forward));
        }
        if (up != 0) {
            location = location.add(vUP.scale(up));
        }
        return this;
    }

    /**
     * Roll camera
     * 
     * @param angle Degrees to rotate (to the right)
     * @return
     */
    public Camera roll(double angle) { // roll rotation (to the right)
        vRight = vRight.rotate(vTO, angle);
        vUP = vRight.crossProduct(vTO);
        return this;
    }

    /**
     * Rotate camera upwards
     * 
     * @param angle Degrees to rotate (upwards)
     * @return
     */
    public Camera pitch(double angle) { // up-down rotation (upwards)
        vUP = vUP.rotate(vRight, angle);
        vTO = vUP.crossProduct(vRight);
        return this;
    }

    /**
     * Turn camera (to the left)
     * 
     * @param angle Degrees to turn (to the left)
     * @return
     */
    public Camera yaw(double angle) { // side-to-side rotation (to the left)
        vTO = vTO.rotate(vUP, angle);
        vRight = vTO.crossProduct(vUP);
        return this;
    }
}
