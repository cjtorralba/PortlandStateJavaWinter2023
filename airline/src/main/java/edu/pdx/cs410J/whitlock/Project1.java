package edu.pdx.cs410J.whitlock;

import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

 /*
  command line arguments go in this order:
    airline          name of airline
    flightnumber    the flight number
    src               three letter code of departure
    depart            departure date and time (24 hour time)
    dest              three letter code of arrival airport
    arrive             arrival date and time (24 hour format)

    optiona are
      -print      prints a description of the new flight
      -README     Prints a README for this project and exits

      Date and time format:  mm/dd/yyyy HH:mm
  */

  public static void main(String[] args) {

    Flight flight;
    Airline airline;


    //arraylist or command line arguments
    ArrayList<String> list = new ArrayList<>(List.of(args));

    if(list.contains("-README")) {
      System.out.println("usage: java -jar target/airline-2023.0.0.jar [options] <args>" +
              "args are (in this order):" +
              "airline The name of the airline" +
              "flightNumber The flight number" +
              "src Three-letter code of departure airport" +
              "depart Departure date and time (24-hour time)" +
              "dest Three-letter code of arrival airport" +
              "arrive Arrival date and time (24-hour time)" +
              "options are (options may appear in any order):" +
              "-print Prints a description of the new flight" +
              "-README Prints a README for this project and exits" +
              "Date and time should be in the format: mm/dd/yyyy hh:mm");
    }
    boolean print = list.contains("-print");
    list.remove("-print");

    //not enough arguments
    if(list.size() != 6) {
      System.err.println("Not enough arguments, args are (in this order):" +
              "airline The name of the airline" +
              "flightNumber The flight number" +
              "src Three-letter code of departure airport" +
              "depart Departure date and time (24-hour time)" +
              "dest Three-letter code of arrival airport" +
              "arrive Arrival date and time (24-hour time)" +
              "options are (options may appear in any order):" +
              "-print Prints a description of the new flight" +
              "-README Prints a README for this project and exits" +
              "Date and time should be in the format: mm/dd/yyyy hh:mm" +
              "\nCommand line usage: java -jar target/airline-2023.0.0.jar [options] <args>");
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
    String departure = list.get(3);
    String destination = list.get(4);
    String arrival = list.get(5);




    flight = new Flight(flightNumber, src, departure, destination, arrival);
    airline = new Airline(airlineName, new ArrayList<Flight>(List.of(flight)));

    if(print){
      System.out.println(flight.toString());
    }

  }
}