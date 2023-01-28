package edu.pdx.cs410J.torral2;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the CS410J airline Project
 */
public class Project2 {

  /**
   *
   * @return
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

 @VisibleForTesting
  static boolean validTimeFormat(String time) {
    return time.matches("\\d?\\d:\\d?\\d");

 }


  @VisibleForTesting
 static boolean validDateFormat(String date) {
    return date.matches("\\d?\\d/\\d?\\d/\\d{4}");
 }

  /**
   *
   * @param args
   */
  public static void main(String[] args) {

    Flight flight;
    Airline airline;


    //arraylist or command line arguments
    ArrayList<String> list = new ArrayList<>(List.of(args));

    if(list.size() == 0) {
      System.err.println("Missing command line arguments");
      return;
    }

    if(list.contains("-README")) {
      readTheREADME();
      return;
    }
    boolean print = list.contains("-print");
    list.remove("-print");

    //not enough arguments
    if(list.size() != 8) {
      System.err.println("usage: java -jar target/airline-2023.0.0.jar [options] <args>\n" +
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
              "Date and time should be in the format: mm/dd/yyyy hh:mm\n");
      return;
    }

    String airlineName = list.get(0);

    int flightNumber = 0;
    try {
      flightNumber = Integer.parseInt(list.get(1));
    } catch(NumberFormatException exception) {
      System.err.println("Not a valid flight number, must be a digit - ex: 458");
      return;
    }

    String src = list.get(2);
    String departureDate = list.get(3);
    String departureTime = list.get(4);
    String destination = list.get(5);
    String arrivalDate = list.get(6);
    String arrivalTime = list.get(7);


    //testing for valid format for arrival date
    if(!validDateFormat(arrivalDate)){
      System.err.println("Format for arrival date is invalid, you entered " + arrivalDate + ". Please use correct format, example: 12/31/2022 or 1/2/2022");
      return;
    }

    //checking for valid arrival time
    if(!validTimeFormat(arrivalTime)){
      System.err.println("Format for arrival time is invalid, you entered " + arrivalTime + ". Please use correct format, example: 10:32 or 1:06");
      return;
    }

    //testubg fir vakud departure date format
    if(!validDateFormat(departureDate)) {
      System.err.println("Format for departure date is invalid, you entered " + departureDate + ". Please use correct format, example: 12/31/2022 or 1/2/2022");
      return;
    }

    //testing for valid departure time format
    if(!validTimeFormat(departureTime)){
      System.err.println("Format for departure time is invalid, you entered " + departureTime + ". Please use correct format, example: 10:32 or 1:06");
      return;
    }


    flight = new Flight(flightNumber, src, departureDate, departureTime, destination, arrivalDate, arrivalTime);
    airline = new Airline(airlineName, new ArrayList<Flight>(List.of(flight)));

    if(print){
      System.out.println(flight);
    }

    airline.addFlight(flight);
  }
}