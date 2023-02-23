package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class XmlDumperTest {

    @Test
    void testDumpInXmlFormat(@TempDir File tempDir) throws IOException, ParserException {

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
