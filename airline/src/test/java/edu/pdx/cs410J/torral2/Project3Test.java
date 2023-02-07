package edu.pdx.cs410J.torral2;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *  This class is used to test units of the Project2 class, such as things like getting information from
 *  certain flights after they have been created.
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */
class Project3Test {


  /**
   * This test is to ensure that the readme is being stored in an external file
   * @throws IOException When readme cannot be found
   */
  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project3.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("The purpose of this program"));
    }
  }


  /**
   * Tests the function getNumber() for the Flight class
   */
  @Test
  void testGetFlightNumber() {
    int testFlightNumber = 123;
    Flight testFlight = new Flight(testFlightNumber, "PDX", "10/23/2022", "12:45", "LAX", "10/24/2022", "1:15");
    assertThat(testFlightNumber, equalTo(testFlight.getNumber()));
  }


  /**
   * Tests the function getDepartureString() for a given Flight
   */
  @Test
  void testGetDepartureString() {
    String departeTime = "10:53";
    String departureDate = "10/24/2023";
    String departureDateAndTime = departureDate + " " + departeTime;

    Flight testFlight = new Flight(722, "PDX",departureDate, departeTime, "LAX", "10/24/2022", "1:15");

    assertThat(departureDateAndTime, containsString(testFlight.getDepartureString()));
  }


  /**
   * Tests the getArrivalString function for a flight
   */
  @Test
  void testGetArrivalString() {
    String arriveTime = "10:53";
    String arriveDate = "10/24/2023";
    String arriveDateAndTime = arriveDate + " " + arriveTime;

    Flight testFlight = new Flight(722, "PDX", "10/23/2021", "3:53", "LAX", arriveDate, arriveTime);

    assertThat(arriveDateAndTime, containsString(testFlight.getArrivalString()));
  }


  /**
   * Tests getSource() function
   */
  @Test
  void testGetSource() {
    String src = "SOURCE";
    Flight testFlight = new Flight(123, src, "10/12/1234", "10:23", "PDX", "11/23/2009", "11:20");
    assertThat(src, containsString(testFlight.getSource()));
  }


  /**
   * Tests getDestination() function
   */
  @Test
  void testGetDestination() {
    String dest = "DESTINATION";
    Flight testFlight = new Flight(123, "PDX", "10/12/1234", "10:23", dest, "1/23/2009", "11:20");
    assertThat(dest, containsString(testFlight.getDestination()));
  }


}

