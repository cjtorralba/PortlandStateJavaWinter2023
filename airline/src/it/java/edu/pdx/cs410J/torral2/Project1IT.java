package edu.pdx.cs410J.torral2;

import com.sun.tools.javac.Main;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  @Test
    void testNotValidFlightNumber() {
        MainMethodResult result = invokeMain("American",  "fourtwenty", "PDX", "10/23/2023", "1:32",  "LAX", "11/12/2022", "10:06");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not a valid flight number, must be a digit - ex: 458"));
  }

  @Test
  void testPrintAsArgument() {
    String flightNumber = "10";
    String airline = "MyAirline";
    String src = "LAX";
    String departureDate = "10/23/2022";
    String departureTime = "10:32";
    String destination = "PDX";
    String arrivalDate = "1/9/2022";
    String arrivalTime = "1:09";

    MainMethodResult result = invokeMain("-print",airline, flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);

    assertThat(result.getTextWrittenToStandardOut(), containsString("Flight " + flightNumber + " departs " + src + " at " + departureDate + " " + departureTime + " arrives " + destination + " at " + arrivalDate + " " + arrivalTime));
  }

  @Test
    void testTooManyArguments() {
      MainMethodResult result = invokeMain("American", "Airlines",  "fourtwenty", "PDX", "10/23/2023", "1:32", "LAX", "11/12/2022", "10:06");
      assertThat(result.getTextWrittenToStandardError(), containsString("usage: java -jar target/airline-2023.0.0.jar [options] <args>\n" +
      "args are (in this order):\n" +
              "airline The name of the airline\n" +
              "flightNumber The flight number\n" +
              "src Three-letter code of departure airport\n" +
              "depart Departure date and time (24-hour time)\n" +
              "dest Three-letter code of arrival airport\n" +
              "arrive Arrival date and time (24-hour time)\n" +
              "options are (options may appear in any order):\n" +
              "-print Prints a description of the new flight\n" +
              "-README Prints a README for this project and exits\n" +
              "Date and time should be in the format: mm/dd/yyyy hh:mm\n"));
  }


  @Test
  void testReadAsArgument(){

    MainMethodResult result = invokeMain("-README", "American Airlines", "123", "PDX", "10/23/2022", "12:40", "LAX", "10/23/1963", "12:42");
    assertThat(result.getTextWrittenToStandardOut(), containsString("usage: java -jar target/airline-2023.0.0.jar [options] <args>"));
  }


    @Test
    void testIncorrectArrivalDateFormat() {
      String arrivalDate = "123/23/2023";
      MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", "12:40", "LAX", arrivalDate, "12:42");
        assertThat(result.getTextWrittenToStandardError(), containsString("Format for arrival date is invalid, you entered " + arrivalDate + ". Please use correct format, example: 12/31/2022 or 1/2/2022"));
    }
    @Test
    void testIncorrectArrivalTimeFormat() {
      String arrivalTime = "123:32";
      MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", "12:40", "LAX", "11/23/2022", arrivalTime);
      assertThat(result.getTextWrittenToStandardError(), containsString("Format for arrival time is invalid, you entered " + arrivalTime + ". Please use correct format, example: 10:32 or 1:06"));
    }

    @Test
    void testIncorrectDepartureDateFormat() {
      String departureDate = "123/23/2023";
      MainMethodResult result = invokeMain("American Airlines", "123", "PDX", departureDate, "12:40", "LAX", "10/23/2022", "12:42");
      assertThat(result.getTextWrittenToStandardError(), containsString("Format for departure date is invalid, you entered " + departureDate + ". Please use correct format, example: 12/31/2022 or 1/2/2022"));
    }

    @Test
    void testIncorrectDepartureTimeFormat() {
      String departureTime = "12:111";
      MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", departureTime, "LAX", "12/23/2022", "12:45");
      assertThat(result.getTextWrittenToStandardError(), containsString("Format for departure time is invalid, you entered " + departureTime + ". Please use correct format, example: 10:32 or 1:06"));

    }

}