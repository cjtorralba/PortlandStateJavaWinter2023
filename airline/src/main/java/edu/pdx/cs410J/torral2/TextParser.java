package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.checkerframework.checker.regex.qual.Regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A skeletal implementation of the <code>TextParser</code> class for Project 2.
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  @Override
  public Airline parse() throws ParserException {
    try (
            BufferedReader br = new BufferedReader(this.reader)
    ) {


      // Aquiring very first airline from the text file to make sure all following airline names must be the same
      String textFileString = br.readLine();
      textFileString = textFileString.replaceAll("\\s", "");
      String[] listOfWords = textFileString.split("\\|");


      if(listOfWords.length != 8) {
        throw new ParserException("Error parsing file: Invalid number of arguments for a flight");
      }

      String airlineName = listOfWords[0];

     /*
      * Error checking for all formats of information from text file
      */

      int flightNumber;
      try{
        flightNumber = Integer.parseInt(listOfWords[1]);
      } catch (NumberFormatException NFE) {
          throw new ParserException("Unable to parse file: Issue parsing flight number from text file.");
      }

        // Parsing text into individual Strings
        String src = listOfWords[2];
        String departureDate = listOfWords[3];
        String departureTime = listOfWords[4];
        String destination = listOfWords[5];
        String arrivalDate = listOfWords[6];
        String arrivalTime = listOfWords[7];


        // Testing parsed text
        parseSegments(src, departureDate, departureTime, destination, arrivalDate, arrivalTime);

        Airline airline = new Airline(airlineName);
        Flight flight = new Flight(flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);

        airline.addFlight(flight);

        // Reading each individual line till end of file
        while((textFileString = br.readLine()) != null && textFileString.length() != 0) {

            // Removing all spaces from string
            textFileString = textFileString.replaceAll("\\s", "");

            // Parsing string and putting each individual segment into an array, string is deliminated by '|'
            listOfWords = textFileString.split("\\|");
            if(!airline.getName().equals(listOfWords[0])) {
                throw new ParserException("Unable to parse text file: cannot have multiple airlines with different names.");
            }

            // Check to make sure string is not null
            if (textFileString != null) {
                // Creating flight with current contents
                try{
                    flightNumber = Integer.parseInt(listOfWords[1]);
                } catch (NumberFormatException NFE) {
                    throw new ParserException("Issue parsing flight number from text file.");
                }
                src = listOfWords[2];
                departureDate = listOfWords[3];                // Testing parsed text
                departureTime = listOfWords[4];
                destination = listOfWords[5];
                arrivalDate = listOfWords[6];
                arrivalTime = listOfWords[7];

                parseSegments(src, departureDate, departureTime, destination, arrivalDate, arrivalTime);

                flight = new Flight(flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);
                airline.addFlight(flight);
            }
        }

        return airline;

    } catch (IOException e) {
        throw new ParserException("While parsing airline text", e);
    }
  }

    private boolean parseSegments(String src, String departureDate, String departureTime, String destination, String arrivalDate, String arrivalTime) throws ParserException {
        if(!(Project2.validAirportCode(src) || Project2.validAirportCode(destination))) {
            throw new ParserException("Error parsing text file: Invalid airport code supplied, must be exactly 3 letters, no numbers allowed");
        }
        if(!(Project2.validDateFormat(departureDate) || Project2.validDateFormat(arrivalDate))) {
            throw new ParserException("Error parsing text file: Invalid arrival/departure date format");
        }
        if(!(Project2.validTimeFormat(departureTime) || Project2.validTimeFormat(arrivalTime))) {
            throw new ParserException("Error parsing text file: Invalid arrival/departure time format");
        }
        return true;
    }
}
