package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


/**
 * This class is used to test the TextDumper class. The goal is to ensure the information being dumped is correct,
 * and formated properly
 *
 * @author Christian Toralba
 * @version 1.0
 * @since 1.0
 */


public class TextDumperTest {

  @Test
  void airlineNameIsDumpedInTextFormat() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(airline);

    String text = sw.toString();
    assertThat(text.isBlank(), equalTo(true));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);
    Flight flight = new Flight(123, "PDX", "10/23/2345", "10:45", "LAX", "12/13/1234", "1:23");
    airline.addFlight(flight);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));


    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
  }


}
