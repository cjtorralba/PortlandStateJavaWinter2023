package edu.pdx.cs410J.whitlock;

import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    int flightNumber;
//    Flight flight = new Flight(flightNumber);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    //System.err.println("Missing command line arguments");

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

    for (int i = 0; i < list.size(); i++) {

    }
  }

}