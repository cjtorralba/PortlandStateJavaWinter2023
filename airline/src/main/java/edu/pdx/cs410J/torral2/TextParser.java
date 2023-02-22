package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * This class is used to parse information from a text file and add it all into a flight.
 * The text in the file should be deliminated with the '|' character
 *
 * @author Christian Torralba
 * @version 3.0
 * @since 1.0
 */
public class TextParser implements AirlineParser<Airline> {


    /**
     * Abstract type reader to be able to implement whichever for of reader you desire.
     */
    private final Reader reader;

    /**
     * Creates a new TextParser object.
     * @param reader Any reader which will be read from.
     */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

    /**
     * Parses a given file for a collection of Flights to assign to an Airline, returns that new airline with given flight list.
     * @return Airline filled with Flights from text file
     * @throws ParserException If unable to read file, or if there is malformed data in the file.
     */
  @Override
  public Airline parse() throws ParserException {
    try (
            BufferedReader br = new BufferedReader(this.reader)
    ) {


      // Aquiring very first airline from the text file to make sure all following airline names must be the same
      String textFileString = br.readLine();
      if(textFileString == null || textFileString.isBlank()) {
          throw new ParserException("Error: File to be parsed appears to be blank");
      }
//      textFileString = textFileString.replaceAll("\\s", "");
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

      } catch (NumberFormatException nfe) {
          throw new ParserException("Unable to parse file: Issue parsing flight number from text file.");
      }

        // Parsing text into individual Strings
        String src = listOfWords[2].trim();
        String departureDate = listOfWords[3].trim();
        String departureTime = listOfWords[4].trim();
        String destination = listOfWords[5].trim();
        String arrivalDate = listOfWords[6].trim();
        String arrivalTime = listOfWords[7].trim();


        // Testing parsed text
        parseSegments(src, departureDate, departureTime, destination, arrivalDate, arrivalTime);

        Airline airline = new Airline(airlineName);
        System.out.println(airlineName);
        Flight flight = new Flight(flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);

        airline.addFlight(flight);

        // Reading each individual line till end of file
        while((textFileString = br.readLine()) != null && textFileString.length() != 0) {

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

                // Trimming padded spaces
                src = listOfWords[2].trim();
                departureDate = listOfWords[3].trim();
                departureTime = listOfWords[4].trim();
                destination = listOfWords[5].trim();
                arrivalDate = listOfWords[6].trim();
                arrivalTime = listOfWords[7].trim();

                // Ensuring all variables are valid
                parseSegments(src, departureDate, departureTime, destination, arrivalDate, arrivalTime);

                try {
                    flight = new Flight(flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                    return null;
                }
                airline.addFlight(flight);
            }
        }

        return airline;

    } catch (IOException e) {
        throw new ParserException("While parsing airline text");
    }
  }


    /**
     * @param src Three letter airport code
     * @param departureDate Date of departure for flight
     * @param departureTime Time of departure for flight
     * @param destination   Three letter airport code for arriving airport
     * @param arrivalDate   Date of arrival for flight
     * @param arrivalTime   Time of arrival for flight
     * @return Returns true if passes all checks, throws ParserException if there is an issue with any formats.
     * @throws ParserException If unable to parse file.
     */
    private boolean parseSegments(String src, String departureDate, String departureTime, String destination, String arrivalDate, String arrivalTime) throws ParserException {

       if(!Project4.validAirportCode(src) || !Project4.validAirportCode(destination)) {
           throw new ParserException("Error parsing text file: Invalid airport code provided, must be exactly three characters");
       }
        if(!Project4.validDateFormat(departureDate) || !Project4.validDateFormat(arrivalDate)) {
            throw new ParserException("Error parsing text file: Invalid arrival/departure date format recieved " + departureDate + " and " + arrivalDate);
        }
        if(!Project4.validTimeFormat(departureTime) || !Project4.validTimeFormat(arrivalTime)) {
            throw new ParserException("Error parsing text file: Invalid arrival/departure time format");
        }
        return true;
    }
}
