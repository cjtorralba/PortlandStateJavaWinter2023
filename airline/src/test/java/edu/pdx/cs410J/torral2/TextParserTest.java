package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * This class is used to test the TextParser class and the range of possibilities that could happen to it
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */
public class TextParserTest {

  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());



    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Test Airline"));
  }

  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  @Test
  void extraInformationInTextFile() throws ParserException{
    InputStream resource = getClass().getResourceAsStream("invalid-num-args-airline.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, tp::parse);
  }

  @Test
  void invalidFlightNumber() {
    InputStream resource = getClass().getResourceAsStream("invalid-flightnumber-airline.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, tp::parse);
  }


  @Test
  void testMultiLineValidFlights() throws ParserException {
      InputStream resource = getClass().getResourceAsStream("valid-multiline-airline.txt");
      assertThat(resource, notNullValue());

      TextParser tp = new TextParser(new InputStreamReader(resource));
      Airline airline = tp.parse();

      assertThat(airline.getName(), containsString("valid Flight Airline"));
  }

  @Test
  void testMultiLineInvalidFlights() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(ParserException.class, tp::parse);
  }

  @Test
  void testMultiLineInvalidFlightNames() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-flight-names.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(ParserException.class, tp::parse);
  }

  @Test
  void testInvalidFlightNumberAfterValidFlightNumber() {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-flight-numbers.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(ParserException.class, tp::parse);
  }




  @Test
  void testInvalidArrivalTimeFormat() {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-arrivaltime-format.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(ParserException.class, tp::parse);
  }


  @Test
  void testInvalidArrivalDateFormat() {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-arrivaldate-format.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(ParserException.class, tp::parse);
  }


  @Test
  void testInvalidAirportCodeFormat() {
    InputStream resource = getClass().getResourceAsStream("multiline-invalid-airportcode-format.txt");
    assertThat(resource, notNullValue());

    TextParser tp = new TextParser(new InputStreamReader(resource));

    assertThrows(ParserException.class, tp::parse);
  }




}
