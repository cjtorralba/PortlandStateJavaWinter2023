package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * An integration test for the {@link Project4} main class.
 * This test will be used to test the functionality of the program as a whole
 *
 * @author Christian Torralba
 * @version 4.0
 * @since 1.0
 */
class Project4IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project4.class, args );
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
        MainMethodResult result = invokeMain("American",  "fourtwenty", "PDX", "10/23/2023", "1:32", "pm",  "LAX", "11/12/2022", "10:06", "pm");
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
    String departureDate = "10/23/1022";
    String departureTime = "10:32";
    String destination = "PDX";
    String arrivalDate = "1/9/2022";
    String arrivalTime = "1:09";
    String amOrPm = "pm";

    MainMethodResult result;

    result = invokeMain("-print", airlineName, flightNumber, src, departureDate, departureTime, amOrPm,  destination, arrivalDate, arrivalTime, amOrPm);

    assertThat(result.getTextWrittenToStandardOut(), containsString("Airline " + airlineName + " has flights:"));
  }

  /**
   * Tests too many arguments
   */
  @Test
  void testTooManyArguments() {
    MainMethodResult result = invokeMain("American", "Airlines",  "fourtwenty", "PDX", "10/23/2023", "1:32", "pm", "LAX", "11/12/2022", "10:06", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid number of command line arguments"));
  }


  /**
   * Tests -README as a command line argument
   */
  @Test
  void testReadAsArgument(){

    MainMethodResult result = invokeMain("-README", "American Airlines", "123", "PDX", "10/23/2022", "12:40", "pm",  "LAX", "10/23/1963", "12:42", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString("The purpose of this program"));
  }


  /**
   * Tests that user cannot put a flight in that arrives before it departs.
   */
  @Test
  void testArrivalTimeBeforeDepartureTime() {
    MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", "12:40", "pm","LAX", "10/23/1963", "12:42", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Cannot have arrival date before departure date"));
  }


  /**
   * Tests incorrect arrival date format
   */
  @Test
  void testIncorrectArrivalDateFormat() {
    String arrivalDate = "123/23/2023";
    MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", "12:40","pm", "LAX", arrivalDate, "12:42", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Format for arrival date is invalid"));
  }


  /**
   * Tests incorrect arrival time format
   */
  @Test
  void testIncorrectArrivalTimeFormat() {
    String arrivalTime = "123:32";
    MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", "12:40", "pm", "LAX", "11/23/2022", arrivalTime, "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Format for arrival time is invalid"));
  }

  /**
   * Tests incorrect departure date format
   */
  @Test
  void testIncorrectDepartureDateFormat() {
    String departureDate = "123/23/2023";
    MainMethodResult result = invokeMain("American Airlines", "123", "PDX", departureDate, "12:40", "PM", "LAX", "10/23/2022", "12:42", "PM");
    assertThat(result.getTextWrittenToStandardError(), containsString("Format for departure date is invalid, you entered " + departureDate + ". Please use correct format, example: 12/31/2022 or 1/2/2022"));
  }

  /**
   * Tests incorrect departure time format
   */
  @Test
  void testIncorrectDepartureTimeFormat() {
    String departureTime = "12:111";
    MainMethodResult result = invokeMain("American Airlines", "123", "PDX", "10/23/2022", departureTime,"pm", "LAX", "12/23/2022", "12:45", "PM");
    assertThat(result.getTextWrittenToStandardError(), containsString("Format for departure time is invalid,"));

  }

  /**
   * Tests invalid airport code
   */
  @Test
  void testInvalidAirportCode() {
    String source = "ABDC";

    MainMethodResult result = invokeMain("American Airlines", "123", source, "10/23/2022", "12:34", "pm", "LAX", "12/23/2022", "12:45", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Airport code must be three letters only, please run again with a valid airport code"));
  }

  /**
   * Tests dumping to a file that does not exist
   */
  @Test
  void testWriteToFileWIthNonExistentFile() {
    String file = "testfile-" + UUID.randomUUID() + ".txt";
    File newFile = new File(file);
    assertThat(newFile.exists(), equalTo(false));
    MainMethodResult result = invokeMain("-textFile", file, "AirlineName","737", "PDX", "10/23/1923", "12:42","pm", "LAX", "12/23/2003", "3:03","pm");
    MainMethodResult result2 = invokeMain("-textFile", file, "AirlineName","737", "PDX", "10/23/1923", "12:42","pm", "LAX", "12/23/2003", "3:03","pm");
    assertThat(newFile.exists(), equalTo(true));
    newFile.delete();
  }


  /**
   * Tests dumping to a file that does exist
   * @throws IOException Unable to open file
   */
  @Test
  void testWriteToFileWithExistentFile() throws IOException {
  String fileName = "testfile-" + UUID.randomUUID() + ".txt";
  File file = new File(fileName);
  assertThat(file.createNewFile(), equalTo(true));
  MainMethodResult result = invokeMain("-textFile", fileName, "737", "PDX", "10/23/2923", "12:42", "pm", "LAX", "12/23/2003", "3:03","pm");
  assertThat(file.exists(), equalTo(true));
  file.delete();
  }


  /**
   * Tests '-' as a command line argument after -pretty is specified, hopefully printing to stdout.
   * @throws IOException If unable to open file.
   */
  @Test
  void testDashWorksForPrettyPrint() throws IOException {
    MainMethodResult result = invokeMain("-pretty", "-", "MyAirline", "737", "PDX", "10/23/1923", "12:42", "pm", "LAX", "12/23/2003", "3:03", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Airline: MyAirline"));
  }


  /**
   * Tests that PrettyPrinter can write to a valid file that already exists.
   * @throws IOException If file could not be created.
   */
  @Test
  void prettyPrintWritesToFileThatExists() throws IOException {
    String fileName = "testFile-" + UUID.randomUUID() + ".txt";
    File file = new File(fileName);

    assertThat(file.createNewFile(), equalTo(true));
    MainMethodResult result = invokeMain("-pretty", fileName, "MyAirline", "737", "PDX", "10/23/1923", "12:42", "pm", "LAX", "12/23/2003", "3:03","pm");

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = br.readLine();

    assertThat(line, containsString("Airline: MyAirline"));
    file.delete();
  }


  /**
   * Tests that the xmlFile option will create a file if it does not exist and write to it.
   * @throws IOException If unable to create or write to file.
   */
  @Test
  void testXmlFileWriteToFileThatDoesNotExist() throws IOException {
    String xmlFilename = "testXmlFile-" + UUID.randomUUID() + ".xml";
    File xmlFile = new File(xmlFilename);
    MainMethodResult result = invokeMain("-xmlFile", xmlFilename, "MyAirline", "737", "PDX", "10/23/1923", "12:42", "pm", "LAX", "12/23/2003", "3:03","pm");
    BufferedReader br = new BufferedReader(new FileReader(xmlFile));
    String line = br.readLine();
    assertThat(line, containsString("<?xml version="));
  }

  /**
   * Tests to make sure that the xmlFile option can write to a file that does exist, ensuring that the airline names match.
   * @throws IOException If airline names do not match
   */
  @Test
  void testXmlFileWriteToFileThatDoesExist() throws IOException {
    String xmlFileName = "valid-airline.xml";
    File xmlFile = new File(getClass().getResource(xmlFileName).getFile());
    assertThat(xmlFile.exists(), equalTo(true));
    MainMethodResult result = invokeMain("-xmlFile", xmlFileName, "Valid Airlines", "737", "PDX", "10/23/1923", "12:42", "pm", "LAX", "12/23/2003", "3:03","pm");
    BufferedReader br = new BufferedReader(new FileReader(new File(xmlFileName)));
    String line = br.readLine();
    assertThat(line, containsString("<?xml version="));
  }


}