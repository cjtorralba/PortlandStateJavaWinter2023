package edu.pdx.cs410J.whitlock;

import com.google.common.annotations.VisibleForTesting;

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

      Date abnd time format:  mm/dd/yyyy HH:mm
  */

  public static void main(String[] args) {
    int flightNumber;
//    Flight flight = new Flight(flightNumber);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }
  }

}