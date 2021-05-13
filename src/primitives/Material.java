package primitives;

/**
 * Class to represent material of a {@link Geometry}
 */
public class Material {
    
    public double kD = 0;
    public double kS = 0;
    public int nShininess = 0;

    /**
     * Sets material's diffuse reflection
     * @param kD
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }
    /**
     * Sets material's specular reflection
     * @param kS 
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }
    /**
     * Sets material's shininess
     * @param shininess 
     */
    public Material setShininess(int shininess) {
        nShininess = shininess;
        return this;
    }
}
