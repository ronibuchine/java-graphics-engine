package primitives;

/**
 * Class to represent material of a {@link Geometry}
 */
public class Material {
    
    public double kD = 0;
    public double kS = 0;
    public int nShininess = 0;

    //transparency
    public double kT = 0;
    //reflection
    public double kR = 0;
    //glossiness
    public double glossiness = Double.POSITIVE_INFINITY;

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
     * Sets material's shininess (lower is shinier)
     * @param shininess 
     */
    public Material setShininess(int shininess) {
        nShininess = shininess;
        return this;
    }

    /**
     * Sets material's transparency
     * @param kT
     */
    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }
    /**
     * Sets material's reflection
     * @param kR 
     */
    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }
    /**
     * Sets material's glossiness
     */
    public Material setGloss(double gloss) {
        this.glossiness = gloss;
        return this;
    }
}
