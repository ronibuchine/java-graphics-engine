package unittests;

import org.junit.Test;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;


/**
 * Unit testing class made to test the functionality {@link Vector} methods
 * @author Roni Buchine
 * @author Eliezer Jacobs * 
 */
public class VectorTests {
    
    final double ACCURACY = .0000001;

    @Test  
    /**
     * Unit test for the crossProduct method for {@link Vector}s
     */ 
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), ACCURACY);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    @Test  
    /**
     * Unit test for the dotProduct method for {@link Vector}s
     */  
    public void testDotProduct() {
        
        Vector v1 = new Vector(0, 0, 1);
        Vector v2 = new Vector(1, 0, 0);
        Vector v3 = new Vector(0, 1, 5);
        Vector v4 = new Vector(0, 0, -1);

        // ============ Equivalence Partitions Tests ==============    
        // acute angle vector - returns poitive
        assertEquals(5, v1.dotProduct(v3), ACCURACY);

        // vectors that form an obtuse angle - return negative
        assertEquals(-1, v1.dotProduct(v4), ACCURACY);

        // ============== Boundary Tests =================
        // perpendicular vectors - returns 0         
        assertEquals(0, v1.dotProduct(v2), ACCURACY);        
    }

    @Test 
    /**
     * Unit test for the length method for {@link Vector}s
     */
    public void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // length of a vector in each octant - should all be equal to each other
        assertEquals(Math.sqrt(3), new Vector(1, 1, 1).length(), ACCURACY);
        assertEquals(Math.sqrt(3), new Vector(1, 1, -1).length(), ACCURACY);
        assertEquals(Math.sqrt(3), new Vector(1, -1, 1).length(), ACCURACY);
        assertEquals(Math.sqrt(3), new Vector(-1, 1, 1).length(), ACCURACY);
        assertEquals(Math.sqrt(3), new Vector(-1, -1, 1).length(), ACCURACY);
        assertEquals(Math.sqrt(3), new Vector(1, -1, -1).length(), ACCURACY);
        assertEquals(Math.sqrt(3), new Vector(-1, 1, -1).length(), ACCURACY);
        assertEquals(Math.sqrt(3), new Vector(-1, -1, -1).length(), ACCURACY);
        
    }

    @Test
    /**
     * Unit test for the Subtract method for {@link Vector}s
     */
    public void testSubtract() {
        // =============Equivalence Partitions Tests ==============
        // vector and itself - return error
        Vector v1 = new Vector (1, 2, 3);
        try {
            v1.subtract(v1);
            fail("subtract() for a vector and itself does not throw an exception");
        } catch (Exception e) {}
        // vector and its complement         
        assertEquals("subtract() didn't return the correct Vector when subtracting its complement", v1.subtract(v1.scale(-1)), new Vector(2, 4, 6));        
        // two regular vectors
        Vector v2 = new Vector(7, 3, 4);
        assertEquals("subtract() didn't return the correct Vector for regular vector subtraction", v1.subtract(v2), new Vector(-6, -1, -1));
    }

    @Test
    /**
     * Unit test for the normalize method for {@link Vector}s
     */
    public void testNormalize() {
        // ============Equivalence Partitions Tests ==============
        Vector v1 = new Vector(0, 3, 4);
        // a positive vector // return normalized version
        assertEquals("normalize() did not return the correct positive vector", v1.normalize() , new Vector(0, 0.6, 0.8));
        // a negative vector - returns a negative normalized vector
        v1 = v1.scale(-5);
        assertEquals("normalize() did not return the correct negative vector", v1.normalize(), new Vector(0, -.6, -.8));
        // ============== Boundary Value Tests ==============
        // unit vector - return itself
        Vector v2 = new Vector(0, 0, 1);
        assertEquals("normalize() did not correcty compute a unit vector", v2.normalize(), new Vector(0, 0, 1));
    
    }

}