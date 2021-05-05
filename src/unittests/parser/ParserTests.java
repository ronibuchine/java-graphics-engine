package unittests.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import org.xml.sax.SAXException;
import parser.SceneDescriptor;

/**
 * Unit testing class for parsing XML documents
 */
public class ParserTests {

    @Test
    public void testInitializeFromXMLString() throws SAXException, IOException {
        File file = new File(System.getProperty("user.dir") + "/images/basicRenderTestTwoColors.xml");

        String xml = Files.readString(Path.of(file.getPath()));
        SceneDescriptor sd = SceneDescriptor.InitializeFromXMLstring(xml);
        assertEquals("SceneDescriptor parse from xlm", 4, sd.polygons.size());
    }
    
}
