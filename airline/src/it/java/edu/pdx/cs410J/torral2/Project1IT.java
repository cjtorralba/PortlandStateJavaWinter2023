package edu.pdx.cs410J.torral2;

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
        MainMethodResult result = invokeMain("American",  "fourtwenty", "PDX", "departDate", "departTime",  "LAX", "arrivalDate", "arrivalTime");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not a valid flight number, must be a digit - ex: 458"));
  }

  @Test
    void testTooManyArguments() {
      MainMethodResult result = invokeMain("American", "Airlines",  "fourtwenty", "PDX", "departDate", "departeTime", "LAX", "arrivalDate", "arrivalTime");
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
}