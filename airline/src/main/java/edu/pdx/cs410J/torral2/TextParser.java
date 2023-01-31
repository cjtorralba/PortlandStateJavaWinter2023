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
      String[] listOfWords = textFileString.split("\n");

      String airlineNmae = listOfWords[0];

      int flightNumber;
      try{
        flightNumber = Integer.parseInt(listOfWords[1]);
      } catch (NumberFormatException NFE) {
        throw new ParserException("Issue parsing flight number from text file.");
      }

      String src = listOfWords[2];
      String departureDate = listOfWords[3];
      String departureTime = listOfWords[4];
      String destination = listOfWords[5];
      String arrivalDate = listOfWords[6];
      String arrivalTime = listOfWords[7];

      Airline airline = new Airline(airlineNmae);
      Flight flight = new Flight(flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);

      airline.addFlight(flight);

      // Reading each individual line till end of file
      while( (textFileString = br.readLine()) != null && textFileString.length() != 0) {
        br.readLine();

        // Removing all spaces from string
        textFileString = textFileString.replaceAll("\\s", "");

        // Parsing string and putting each individual segment into an array, string is deliminated by '|"
         listOfWords = textFileString.split("\\|");

        // Check to make sure string is not null
        if (textFileString != null) {
          // Creating flight with current contents
          for (String s : listOfWords)
            System.out.println(s);
        }
      }

      return new Airline("YUP");
    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
