package edu.pdx.cs410J.torral2;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 * This class contains unit tests for the Flight class.
 *
 * @author Christian Torralba
 * @version 3.0
 * @since 1.0
 */
public class FlightTest {

  /**
   * Testing for invalid arrival time format in the Flight constructor.
   */
  @Test
 void testInvalidArrivalTimeFormatInConstructor(){
   IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Flight(123, "PDX", "10/23/2023", "12:42 pm", "LAX", "12/23/2022", "3:63"));
   assertThat(exception.getMessage(), containsString("Invalid arrival time provided."));
 }


  /**
   * Testing for invalid arrival date format in Flight constructor.
   */
  @Test
  void testInvalidArrivalDateFormatInConstructor(){
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Flight(123, "PDX", "10/23/2023", "12:42 pm", "LAX", "12/23/3", "3:03 pm"));
    assertThat(exception.getMessage(), containsString("Invalid arrival date provided."));
  }

  /**
   * Testing for invalid departure date format in the Flight constructor.
   */
  @Test
  void testInvalidDepartureDateFormatInConstructor(){
  IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> new Flight(123, "PDX", "10/23/", "12:42 pm", "LAX", "12/23/2003", "3:03 pm"));
    assertThat(exception.getMessage(), containsString("Invalid departure date provided."));
  }

  /**
   *  Testing for invalid departure time format in the Flight constructor.
   */
  @Test
  void testInvalidDepartureTimeFormatInConstructor(){
    IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> new Flight(123, "PDX", "10/23/2003", "122:423", "LAX", "12/23/2003", "3:03 pm"));
    assertThat(exception.getMessage(), containsString("Invalid departure time provided."));
  }


  /**
   * Testing to ensure the getNumber() function works properly for Flight class.
   */
  @Test
  void testGetFlightNumber() {
      int testFlightNumber = 123;
      Flight flight = new Flight(testFlightNumber, "PDX", "10/23/1002", "12:42 pm", "LAX", "12/23/2003", "3:03 pm");
      assertThat(flight.getNumber(), equalTo(testFlightNumber));
  }


    /**
     * Tests the function getDepartureString() for a given Flight
     */
    @Test
    void testGetDepartureString() {
        String departeTime = "10:53 PM";
        String departureDate = "10/24/2023";

        Flight testFlight = new Flight(722, "PDX",departureDate, departeTime, "LAX", "10/24/2024", "1:15 pm");
       departureDate = "10/24/23";
        String departureDateAndTime = departureDate + ", " + departeTime;

        assertThat(departureDateAndTime, CoreMatchers.containsString(testFlight.getDepartureString()));
    }


    /**
     * Tests the getArrivalString function for a flight
     */
    @Test
    void testGetArrivalString() throws ParseException {
        String arriveTime = "10:53 PM";
        String arriveDate = "10/24/2023";

        Flight testFlight = new Flight(722, "PDX", "10/23/2021", "3:53 am", "LAX", arriveDate, arriveTime);

        arriveDate = "10/24/23";
        String departureDateAndTime = arriveDate + ", " + arriveTime;

        assertThat(departureDateAndTime, containsString(testFlight.getArrivalString()));
    }


    /**
     * Tests getSource() function
     */
    @Test
    void testGetSource() {
        String src = "LAX";
        Flight testFlight = new Flight(123, src, "10/12/1234", "10:23 am", "PDX", "11/23/2009", "11:20 pm");
        assertThat(src, CoreMatchers.containsString(testFlight.getSource()));
    }

    /**
     * Tests getDestination() function
     */
    @Test
    void testGetDestination() {
        String dest = "PDX";
        Flight testFlight = new Flight(123, "PDX", "10/12/1234", "10:23 pm", dest, "1/23/2009", "11:20 am");
        assertThat(dest, CoreMatchers.containsString(testFlight.getDestination()));
    }
}
