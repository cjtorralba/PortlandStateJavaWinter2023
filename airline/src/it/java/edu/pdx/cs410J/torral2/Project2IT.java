package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * An integration test for the {@link Project2} main class.
 * This test will be used to test the functionality of the program as a whole
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("usage: java -jar"));
  }

  /**
   * Test main with an invalid flight number
   */
  @Test
    void testNotValidFlightNumber() {
        MainMethodResult result = invokeMain("American",  "fourtwenty", "PDX", "10/23/2023", "1:32",  "LAX", "11/12/2022", "10:06");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not a valid flight number, must be a digit - ex: 458"));
  }

  /**
   * Tests the -print as a command line argument
   */
  @Test
  void testPrintAsArgument() {
    String flightNumber = "10";
    String airlineName = "MyAirline";
    String src = "LAX";
    String departureDate = "10/23/2022";
    String departureTime = "10:32";
    String destination = "PDX";
    String arrivalDate = "1/9/2022";
    String arrivalTime = "1:09";

    MainMethodResult result;

    result = invokeMain("-print", airlineName, flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);

    assertThat(result.getTextWrittenToStandardOut(), containsString("Airline " + airlineName + " has flights:"));
  }

  /**
   * Tests too many arguments
   */
  @Test
  void testTooManyArguments() {
    MainMethodResult result = invokeMain("American", "Airlines",  "fourtwenty", "PDX", "10/23/2023", "1:32", "LAX", "11/12/2022", "10:06");
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid number of command line arguments"));
  }


  /**
   * Tests -README as a command line argument
   */
  @Test
  void testReadAsArgument(){

    MainMethodResult result = invokeMain("-README", "American Airlines", "123", "PDX", "10/23/2022", "12:40", "LAX", "10/23/1963", "12:42");
    assertThat(result.getTextWrittenToStandardOut(), containsString("The purpose of this program"));
  }


  /**
   * Tests incorrect arrival date format
   */
  @Test
  void testIncorrectArrivalDateFormat() {
    String arrivalDate = "123/23/2023";
    MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", "12:40", "LAX", arrivalDate, "12:42");
    assertThat(result.getTextWrittenToStandardError(), containsString("Format for arrival date is invalid, you entered " + arrivalDate + ". Please use correct format, example: 12/31/2022 or 1/2/2022"));
  }


  /**
   * Tests incorrect arrival time format
   */
  @Test
  void testIncorrectArrivalTimeFormat() {
    String arrivalTime = "123:32";
    MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", "12:40", "LAX", "11/23/2022", arrivalTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Format for arrival time is invalid, you entered " + arrivalTime + ". Please use correct format, example: 10:32 or 1:06"));
  }

  /**
   * Tests incorrect departure date format
   */
  @Test
  void testIncorrectDepartureDateFormat() {
    String departureDate = "123/23/2023";
    MainMethodResult result = invokeMain("American Airlines", "123", "PDX", departureDate, "12:40", "LAX", "10/23/2022", "12:42");
    assertThat(result.getTextWrittenToStandardError(), containsString("Format for departure date is invalid, you entered " + departureDate + ". Please use correct format, example: 12/31/2022 or 1/2/2022"));
  }

  /**
   * Tests incorrect departure time format
   */
  @Test
  void testIncorrectDepartureTimeFormat() {
    String departureTime = "12:111";
    MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", departureTime, "LAX", "12/23/2022", "12:45");
    assertThat(result.getTextWrittenToStandardError(), containsString("Format for departure time is invalid, you entered " + departureTime + ". Please use correct format, example: 10:32 or 1:06"));

  }

  /**
   * Tests invalid airport code
   */
  @Test
  void testInvalidAirportCode() {
    String source = "ABDC";

    MainMethodResult result = invokeMain("American Airlines", "123", source, "10/23/2022", "12:34", "LAX", "12/23/2022", "12:45");
    assertThat(result.getTextWrittenToStandardError(), containsString("Airport code must be three letters only, please run again with a valid airport code"));
  }

  /**
   * Tests dumping to a file that does not exist
   */
  @Test
  void testWriteToFileWIthNonExistentFile() {
    String file = "testfile-" + UUID.randomUUID() + ".txt";
    assertThat(new File(file).exists(), equalTo(false));
    MainMethodResult result = invokeMain("-textFile", file, "AirlineName","737", "PDX", "10/23/2923", "12:42", "LAX", "12/23/2003", "3:03");
    MainMethodResult result2 = invokeMain("-textFile", file, "AirlineName","737", "PDX", "10/23/2923", "12:42", "LAX", "12/23/2003", "3:03");
    assertThat(new File(file).exists(), equalTo(true));
  }


  /**
   * Tests dumping to a file that does exist
   * @throws IOException Unable to open file
   */
  @Test
  void testWriteToFileWithExistentFile() throws IOException {
  String fileName = "testfile-" + UUID.randomUUID() + ".txt";
  assertThat(new File(fileName).createNewFile(), equalTo(true));
  MainMethodResult result = invokeMain("-textFile", fileName, "737", "PDX", "10/23/2923", "12:42", "LAX", "12/23/2003", "3:03");
  assertThat(new File(fileName).exists(), equalTo(true));
  }
}