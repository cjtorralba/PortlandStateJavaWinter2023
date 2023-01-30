package edu.pdx.cs410J.torral2;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 * This class contains unit tests for the Flight class.
 *
 * @author Christian Julio Torralba
 * @version 1.0
 * @since 1.0
 */
public class FlightTest {

  /**
   * Testing for invalid arrival time format in the Flight constructor.
   */
  @Test
 void testInvalidArrivalTimeFormatInConstructor(){
   IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Flight(123, "PDX", "10/23/2023", "12:42", "LAX", "12/23/2022", "3:063"));
   assertThat(exception.getMessage(), containsString("Invalid arrival time provided."));
 }


  /**
   * Testing for invalid arrival date format in Flight constructor.
   */
  @Test
  void testInvalidArrivalDateFormatInConstructor(){
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Flight(123, "PDX", "10/23/2023", "12:42", "LAX", "12/23/3", "3:03"));
    assertThat(exception.getMessage(), containsString("Invalid arrival date provided."));
  }

  /**
   * Testing for invalid departure date format in the Flight constructor.
   */
  @Test
  void testInvalidDepartureDateFormatInConstructor(){
  IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> new Flight(123, "PDX", "10/23/", "12:42", "LAX", "12/23/2003", "3:03"));
    assertThat(exception.getMessage(), containsString("Invalid departure date provided."));
  }

  /**
   *  Testing for invalid departure time format in the Flight constructor.
   */
  @Test
  void testInvalidDepartureTimeFormatInConstructor(){
    IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> new Flight(123, "PDX", "10/23/2923", "122:423", "LAX", "12/23/2003", "3:03"));
    assertThat(exception.getMessage(), containsString("Invalid departure time provided."));
  }


  /**
   * Testing to ensure the getNumber() function works properly for Flight class.
   */
  @Test
  void testGetFlightNumber() {
      int testFlightNumber = 123;
      Flight flight = new Flight(testFlightNumber, "PDX", "10/23/2923", "12:42", "LAX", "12/23/2003", "3:03");
      assertThat(flight.getNumber(), equalTo(testFlightNumber));
  }



}
