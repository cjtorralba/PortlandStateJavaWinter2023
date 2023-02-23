package edu.pdx.cs410J.torral2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * This class is used to test the XmlDumper class.
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */

public class XmlDumperTest {

    /**
     * Tests to make sure that the information dumped goes to a file that exists.
     * @throws IOException File could not be located or created.
     */
    @Test
    void testDumpInXmlFormat() throws IOException {
        Airline testAirline = new Airline("Test Airline");
        Flight flight = new Flight(123, "PDX", "10/23/2345", "10:45 PM", "LAX", "12/13/3234", "1:23 AM");

        testAirline.addFlight(flight);

        File xmlFile = new File("test-airline.xml");
        XmlDumper xmlDumper = new XmlDumper(new FileWriter(xmlFile));

        xmlDumper.dump(testAirline);
        assertThat(xmlFile.exists(), equalTo(true));
        xmlFile.delete();
    }
}
