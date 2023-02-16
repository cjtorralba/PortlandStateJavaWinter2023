package edu.pdx.cs410J.torral2;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


/**
 * Test class for the PrettyPrinter class, ensuring that format and location will be correct.
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */
public class PrettyPrinterTest {


    /**
     * Tests to make sure that the text dumped into a file is printed in pretty format
     * @throws IOException If it is not possible to write to file.
     */
    @Test
    void airlineIsDumpedInPrettyFormat() throws IOException {

        String airlineName = "Test Airline";
        Airline airline = new Airline(airlineName);
        Flight flight = new Flight(123, "PDX", "10/23/2023", "10:45 PM", "LAX", "10/23/2023", "11:45 PM");
        airline.addFlight(flight);

        String fileName = "test-" + UUID.randomUUID() + ".txt";
        File textFile = new File(fileName);
        PrettyPrinter printer = new PrettyPrinter(new FileWriter(textFile));


        printer.dump(airline);
        BufferedReader br = new BufferedReader(new FileReader(textFile));

        String line = br.readLine();

        assertThat(line, equalTo("Airline: " + airlineName));

        textFile.delete();
    }
}
