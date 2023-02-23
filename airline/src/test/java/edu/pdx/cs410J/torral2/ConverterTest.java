package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import edu.pdx.cs410J.InvokeMainTestCase;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ConverterTest extends  InvokeMainTestCase{
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Converter.class, args );
    }


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
