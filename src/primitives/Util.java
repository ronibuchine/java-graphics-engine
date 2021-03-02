package primitives;
/**
 * Class Util is a utility class used to help avoid floating point and double errors in calculations
 * @author Roni Buchine and Eliezer Jacobs
 */

//all copied from slides
public class Util { //is this class meant to be an interface?

    private static int getExp(double n) {
        return (int)((Double.doubleToRawLongBits(n) >> 52) & 0x7FFL) - 1023;
    }

    public static boolean isZero(double n) {
        // Eliezer how do we implement this method?
        return getExp(n) < -20;
    }

    public static double alignZero(double n) {
        return getExp(n) < -20 ? 0.0 : n;
    }

}
