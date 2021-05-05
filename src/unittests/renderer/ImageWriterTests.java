package unittests.renderer;

import static org.junit.Assert.fail;

import org.junit.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * Unit testing class for {@link ImageWriter} methods.
 * 
 * @author Roni Buchine
 * @author Eliezer Jacobs
 */
public class ImageWriterTests {

    /**
     * Tests creating plain image with grid
     */
    @Test
    public void testWriteToImage() {
        try {
            ImageWriter im = new ImageWriter("test", 800, 500);
            Color green = new Color(52, 165, 111);

            for (int x = 0; x < 800; ++x) {
                for (int y = 0; y < 500; ++y) {
                    im.writePixel(x, y, green);
                }
            }
            im.printGrid(50, Color.BLACK);
            im.writeToImage();
        } catch (Exception e) {
            fail("Image was not created");
        }
    }
}
