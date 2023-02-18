package edu.pdx.cs410J.torral2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The main class for the CS410J airline Project
 * This class contains the main logic of the program
 *
 * @author Christian Torralba
 * @version 3.0
 * @since 1.0
 */
public class Project4 {

  /**
   * Reads the readme to the user when main is run with -README as argument
   */
  @VisibleForTesting
  static boolean readTheREADME(){
    try (
            InputStream readme = Project4.class.getResourceAsStream("README.txt")
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
    return time.matches("\\d?\\d:\\d?\\d\\s?[pP|aA][mM]");
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
    return code.matches("[a-zA-Z]{3}") && (AirportNames.getName(code) != null);
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
      System.err.println("" +
              "usage: java -jar target/airline-2023.0.0.jar [options] <args>\n" +
              "\targs are (in this order):\n" +
              "\t\tairline                The name of the airline\n" +
              "\t\tflightNumber           The flight number\n" +
              "\t\tsrc                    Three-letter code of departure airport\n" +
              "\t\tdepart                 Departure date and time (24-hour time)\n" +
              "\t\tdest                   Three-letter code of arrival airport\n" +
              "\tarrive                   Arrival date and time (24-hour time)\n" +
              "\toptions are (options may appear in any order):\n" +
              "\t\t-print                 Prints a description of the new flight\n" +
              "\t\t-README                Prints a README for this project and exits\n" +
              "\t\t-textFile fileName     Prints output of airline and its flights to file specified\n" +
              "\t\t-pretty prettyFileName Prints output of airline in a human-readable format to specified file, use '-' to print to console\n" +
              "Date and time should be in the format: mm/dd/yyyy hh:mm");
      return;
    }

    if (list.contains("-README")) {
      readTheREADME();
      return;
    }


  /*
   * Checking for command line arguments
   */

    boolean print = list.remove("-print");

    // Textfile
    boolean writeToFile = list.contains("-textFile");
    String fileName = null;

    // Pretty print
    boolean prettyPrint = list.contains("-pretty");
    String prettyFileName = null;

    // XMLFile
    boolean xmlFile = list.contains("-xmlFile");
    String xmlFileName = null;

    if (writeToFile) {
      fileName = list.get(list.indexOf("-textFile") + 1);
      list.remove("-textFile");
      list.remove(fileName);
    }

    if(prettyPrint) {
      prettyFileName = list.get(list.indexOf("-pretty") + 1);
      list.remove("-pretty");
      list.remove(prettyFileName);
    }

    if(xmlFile) {
      xmlFileName = list.get(list.indexOf("-xmlFile") + 1);
      list.remove("-xmlFile");
      list.remove(xmlFileName);
    }

    // Not enough arguments
    if (list.size() != 10) {
      System.err.println("Error: Invalid number of command line arguments, please run with no arguments for a full list of options");
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
    String departureTime = list.get(4) + " " + list.get(5).toUpperCase();
    String destination = list.get(6);
    String arrivalDate = list.get(7);
    String arrivalTime = list.get(8) + " " + list.get(9).toUpperCase();

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

    try {
      flight = new Flight(flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      return;
    }


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
            Pattern pattern = Pattern.compile(".*[/\\\\]");
            Matcher match = pattern.matcher(fileName);
            String filePath = null;
            if(match.find()) {
              filePath = match.group(0);
              Files.createDirectories(Paths.get(filePath));
            }
          file.createNewFile();
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

      // Sorting list
      List<Flight> sortedList = null;
      if(airline != null) {
       sortedList = airline.getFlights().stream().sorted(Flight::compareTo).collect(Collectors.toList());
       airline = new Airline(airlineName, sortedList);
      } else {
        airline = new Airline(airlineName, List.of(flight));
      }



      // Writing all information in airline to text file
      try {
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

   // Working with pretty print now
    if(prettyPrint) {
      try {
        // If filename provided was '-' we are print to stdout
        if(prettyFileName.equals("-")) {
            PrettyPrinter ppw = new PrettyPrinter(new OutputStreamWriter(System.out));
            ppw.dump(airline);
        } else { // Printing to file provided by the user
          FileWriter fileWriter = new FileWriter(new File(prettyFileName));
          PrettyPrinter ppw = new PrettyPrinter(fileWriter);
          ppw.dump(airline);
        }
      } catch (IOException fnf) {
        System.err.println("File could not be created, please try re-running the program with a different path.");
        return;
      }
    }


    // If print has been added as argument, we will print out airline name and its flights.
    if(print) {
      System.out.println("Airline " + airline.getName() + " has flights: ");
      for(Flight f : airline.getFlights())
        System.out.println("\t" + f);
    }

  }
}