package edu.pdx.cs410J.torral2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the CS410J airline Project
 * This class contains the main logic of the program
 *
 * @author Christian Torralba
 * @Version 1.0
 * @Since 1.0
 */
public class Project2 {

  /**
   * Reads the readme to the user when main is run with -README as argument
   */
  @VisibleForTesting
  static boolean readTheREADME(){
    try (
            InputStream readme = Project2.class.getResourceAsStream("README.txt")
    ) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      while(line != null){
        System.out.println(line);
        line = reader.readLine();
      }
    } catch (Exception e) {
      System.err.println("Could not parse README file");
      return false;
    }
    return true;
  }

  /**
   * @param time String to test for against proper format, for example: 10:23 or 1:04
   * @return True if time is in correct format
   */
 @VisibleForTesting
  static boolean validTimeFormat(String time) {
    return time.matches("\\d?\\d:\\d?\\d");

 }

  /**
   * @param date - String to test for against proper date format, example: 12/23/2013 or 1/1/2023.
   * @return True if string is in valid date format
   */
  @VisibleForTesting
 static boolean validDateFormat(String date) {
    return date.matches("\\d?\\d/\\d?\\d/\\d{4}");
 }

  /**
   * @param code Three letter airport code
   * @return True if and only if code is exactly 3 letters
   */
 @VisibleForTesting
 static boolean validAirportCode(String code) {
    return code.matches("[a-zA-Z]{3}");
 }



  public static void main(String[] args) {

    Flight flight = null;
    Airline airline = null;
    Airline textFileAirline = null;
    TextParser textParser = null;
    TextDumper textdumper = null;


    //arraylist or command line arguments
    ArrayList<String> list = new ArrayList<>(List.of(args));


    if (list.size() == 0) {
      System.err.println("Missing command line arguments");
      return;
    }

    if (list.contains("-README")) {
      readTheREADME();
      return;
    }
    boolean print = list.contains("-print");
    list.remove("-print");

    boolean writeToFile = list.contains("-textFile");
    String fileName = null;

    if (writeToFile) {
      fileName = list.get(list.indexOf("-textFile") + 1);
      list.remove("-textFile");
      list.remove(fileName);
    }


    // Not enough arguments
    if (list.size() != 8) {
      System.err.println("Error: Invalid number of command line arguments, please run with [-usage] for a full list of options");
      return;
    }

    // Getting name of airline from first index in the list
    String airlineName = list.get(0);

    int flightNumber = 0;
    try {
      flightNumber = Integer.parseInt(list.get(1));
    } catch (NumberFormatException exception) {
      System.err.println("Not a valid flight number, must be a digit - ex: 458");
      return;
    }


    // Parsing information from command line
    String src = list.get(2);
    String departureDate = list.get(3);
    String departureTime = list.get(4);
    String destination = list.get(5);
    String arrivalDate = list.get(6);
    String arrivalTime = list.get(7);

    // Testing for valid airport codes
    if (!validAirportCode(src) || !validAirportCode(destination)) {
      System.err.println("Airport code must be three letters only, please run again with a valid airport code.");
      return;
    }

    // Testing for valid format for arrival date
    if (!validDateFormat(arrivalDate)) {
      System.err.println("Format for arrival date is invalid, you entered " + arrivalDate + ". Please use correct format, example: 12/31/2022 or 1/2/2022");
      return;
    }

    // Checking for valid arrival time
    if (!validTimeFormat(arrivalTime)) {
      System.err.println("Format for arrival time is invalid, you entered " + arrivalTime + ". Please use correct format, example: 10:32 or 1:06");
      return;
    }

    // Testing for valid departure date format
    if (!validDateFormat(departureDate)) {
      System.err.println("Format for departure date is invalid, you entered " + departureDate + ". Please use correct format, example: 12/31/2022 or 1/2/2022");
      return;
    }

    // Testing for valid departure time format
    if (!validTimeFormat(departureTime)) {
      System.err.println("Format for departure time is invalid, you entered " + departureTime + ". Please use correct format, example: 10:32 or 1:06");
      return;
    }

    // Adding current flight to airline
    if (!writeToFile) {
      airline = new Airline(airlineName);
    }
    flight = new Flight(flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);


    // If our read/write flag was set, we will first read all information from text file and put it into the airline
    // object, then we will add out current flight passed in via command line, then we will write back to the file all
    // the flights added to the airline.
    if (writeToFile) {

      // Reading/writing airline to file.
      File file = new File(fileName);




      // Checking to see if the file exists, if not we create it.
      boolean exists = file.exists();
      if (!exists) {
        try {
          file.createNewFile();
          System.out.println("Created file");
        } catch (IOException e) {
          System.err.println("There was an issue creating the file in location: " + fileName);
        }
      }


      // Attempting to create FileReader and parse all information from
      // text file to store it into airline.
      if (exists) {
        try {
          FileReader fileReader = new FileReader(file);
          textParser = new TextParser(fileReader);
          try {
            textFileAirline = textParser.parse();
          } catch (ParserException parse) {
            System.err.println(parse.getMessage());
            return;
          }

        } catch (FileNotFoundException fnf) {
          System.err.println("File could not be located.");
          return;
        }

        // Checking to make sure airline name from textfile matches airline name given via command line
        if (!textFileAirline.getName().equals(airlineName)) {
          System.err.println("Airline name from text file does not match airline name given on command line. Please check text file or change command line arguments.");
          return;
        }
        // Reaching this point means the airline names do match, so we can assign airline to airlineFromTextFile and add current flight
        airline = textFileAirline;
        airline.addFlight(flight);
      }

        // Writing all information in airline to text file
        try {
          airline = new Airline(airlineName);
          airline.addFlight(flight);

          FileWriter fileWriter = new FileWriter(file);
          textdumper = new TextDumper(fileWriter);
          textdumper.dump(airline);
        } catch (IOException fnf) {
          System.err.println("File could not be created, please try re-running the program with a different path.");
          return;
        }
    } else {  // Not working with external file, so only information adding to airline will be via command line
      airline = new Airline(airlineName);
      airline.addFlight(flight);
    }
  // If print has been added as argument, we will print out airline name and its flights.
    if(print) {
      System.out.println("Airline " + airline.getName() + " has flights: ");
      for(Flight f : airline.getFlights())
        System.out.println("\t" + f);
    }

  }
}