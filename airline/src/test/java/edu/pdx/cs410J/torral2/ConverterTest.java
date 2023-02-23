package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * This class is used to test the functionality of the Converter class which converts a text file to a xml file
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */

public class ConverterTest extends  InvokeMainTestCase{

    /**
     * This function creates a call to main and stores all regarding information.
     * @param args List of command line arguments
     * @return A main method result, the end of a call to main.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Converter.class, args );
    }


    /**
     * Tests to makes sure that a valid text file is converted into a valid xml file.
     * @throws IOException If it was unable to convert file.
     */
    @Test
    void testConvertFile() throws IOException {

        Airline airline = new Airline("testAirline");
        Flight flight = new Flight(123, "PDX", "10/23/2345", "10:45 PM", "LAX", "12/13/3234", "1:23 AM");
        airline.addFlight(flight);

        String testTextFile = "converterTextTest-" + UUID.randomUUID() + ".txt";
        String testXmlFile = "converterXML-" + UUID.randomUUID() + ".xml";

        File textFile = new File(testTextFile);
        FileWriter fileWriter = new FileWriter(textFile);
        TextDumper td = new TextDumper(fileWriter);
        td.dump(airline);

        assertThat(textFile.exists(), equalTo(true));

        MainMethodResult result = invokeMain(testTextFile, testXmlFile);

        File xmlFile = new File(testXmlFile);
        assertThat(xmlFile.exists(), equalTo(true));

        xmlFile.getAbsoluteFile().delete();
        textFile.delete();

    }
}
