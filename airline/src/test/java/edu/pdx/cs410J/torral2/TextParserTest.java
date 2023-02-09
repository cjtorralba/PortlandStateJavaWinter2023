package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * This class is used to test the TextParser class and the range of possibilities that could happen to it
 *
 * @author Christian Torralba
 * @version 3.0
 * @since 1.0
 */
public class TextParserTest {

  /**
   * Tests that valid lines of text can be parsed
   * @throws ParserException If lines could not be parsed
   */
  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("valid Flight Airline"));
  }


  /**
   * Tests that text file with invalid flights throws an exception
   */
  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that extra information about a flight in the TextFile throws an exception
   * @throws ParserException If text could not be parsed
   */
  @Test
  void extraInformationInTextFile() throws ParserException{
    InputStream resource = getClass().getResourceAsStream("invalid-num-args-airline.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, tp::parse);
  }

  /**
   * Tests parsing a flight with an invalid flight number
   */
  @Test
  void invalidFlightNumber() {
    InputStream resource = getClass().getResourceAsStream("invalid-flightnumber-airline.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, tp::parse);
  }


  /**
   * Test to make sure airline will add all valid flights in the text file ot it
   * @throws ParserException If text could not be parsed
   */
  @Test
  void testMultiLineValidFlights() throws ParserException {
      InputStream resource = getClass().getResourceAsStream("valid-multiline-airline.txt");
      assertThat(resource, notNullValue());

      TextParser tp = new TextParser(new InputStreamReader(resource));
      Airline airline = tp.parse();

      assertThat(airline.getName(), containsString("valid Flight Airline"));
  }

  /**
   * Tests multiple invalid flights in a text file
   * @throws ParserException If text could not be parsed
   */
  @Test
  void testMultiLineInvalidFlights() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(IllegalArgumentException.class, tp::parse);
  }

  /**
   * Tests a valid flight followed by invalid flight names
   * @throws ParserException If text could not be parsed
   */
  @Test
  void testMultiLineInvalidFlightNames() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-flight-names.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(IllegalArgumentException.class, tp::parse);
  }

  /**
   * Tests invalid flight number after airlines with valid flight numbers
   */
  @Test
  void testInvalidFlightNumberAfterValidFlightNumber() {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-flight-numbers.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(IllegalArgumentException.class, tp::parse);
  }


  /**
   * Tests text with an invalid arrival time format
   */
  @Test
  void testInvalidArrivalTimeFormat() {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-arrivaltime-format.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(ParserException.class, tp::parse);
  }


  /**
   * Tests text with an invalid arrivla date format
   */
  @Test
  void testInvalidArrivalDateFormat() {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-arrivaldate-format.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(ParserException.class, tp::parse);
  }


  /**
   * Tests text with an invalid airport code
   */
  @Test
  void testInvalidAirportCodeFormat() {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-airportcode-format.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(ParserException.class, tp::parse);
  }
}
