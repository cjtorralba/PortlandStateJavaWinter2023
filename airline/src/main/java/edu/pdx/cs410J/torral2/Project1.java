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
public class Project1 {


  /**
   *
   * @param dateAndTime
   * @return
   */
  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

  /**
   *
   * @return
   */
  static boolean readTheREADME(){
    try (
            InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
    } catch (Exception e) {
      System.err.println("Could not parse README file");
      return false;
    }
    return true;
  }





 /*
  command line arguments go in this order:
    airline          name of airline
    flightnumber    the flight number
    src               three letter code of departure
    departDate            departure date and time (24 hour time)
    departTime
    dest              three letter code of arrival airport
    arriveDate             arrival date and time (24 hour format)
    arriveTime

    optiona are
      -print      prints a description of the new flight
      -README     Prints a README for this project and exits

      Date and time format:  mm/dd/yyyy HH:mm
  */

  /**
   *
   * @param args
   */
  public static void main(String[] args) {

    Flight flight;
    Airline airline;


    //arraylist or command line arguments
    ArrayList<String> list = new ArrayList<>(List.of(args));

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

      /*
      date and time format (mm/dd/yyyy hh:mm)
      1 - name of airline
      2 - flight number
      3 - three letter departure code (src)
      4 - departure date and time
      5 - destination three letter code of arrival
      6 - arrival date and time (24 hour time)
       */

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




    flight = new Flight(flightNumber, src, departureDate + " " + departureTime, destination, arrivalDate + " " + arrivalTime);
    airline = new Airline(airlineName, new ArrayList<Flight>(List.of(flight)));

    if(print){
      System.out.println(flight.toString());
    }

    airline.addFlight(flight);
  }
}