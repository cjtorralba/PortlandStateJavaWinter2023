package edu.pdx.cs410J.torral2;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project2</code> class.  This is different
 * from <code>Project2IT</code> which is an integration test and can capture data
 * written to {@link System#out} and the like.
 */
class Project2Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project2.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("The purpose of this program"));
    }
  }


  @Test
  void testGetFlightNumber() {
    int testFlightNumber = 123;
    Flight testFlight = new Flight(testFlightNumber, "PDX", "10/23/2022", "12:45", "LAX", "10/24/2022", "1:15");
    assertThat(testFlightNumber, equalTo(testFlight.getNumber()));
  }


  @Test
  void testGetDepartureString() {
    String departeTime = "10:53";
    String departureDate = "10/24/2023";
    String departureDateAndTime = departureDate + " " + departeTime;

    Flight testFlight = new Flight(722, "PDX",departureDate, departeTime, "LAX", "10/24/2022", "1:15");

    assertThat(departureDateAndTime, containsString(testFlight.getDepartureString()));
  }

  @Test
  void testGetArrivalString() {
    String arriveTime = "10:53";
    String arriveDate = "10/24/2023";
    String arriveDateAndTime = arriveDate + " " + arriveTime;

    Flight testFlight = new Flight(722, "PDX", "10/23/2021", "3:53", "LAX", arriveDate, arriveTime);

    assertThat(arriveDateAndTime, containsString(testFlight.getArrivalString()));
  }


  @Test
  void testGetSource() {
    String src = "SOURCE";
    Flight testFlight = new Flight(123, src, "10/12/1234", "10:23", "PDX", "11/23/2009", "11:20");
    assertThat(src, containsString(testFlight.getSource()));
  }


  @Test
  void testGetDestination() {
    String dest = "DESTINATION";
    Flight testFlight = new Flight(123, "PDX", "10/12/1234", "10:23", dest, "1/23/2009", "11:20");
    assertThat(dest, containsString(testFlight.getDestination()));
  }


}

