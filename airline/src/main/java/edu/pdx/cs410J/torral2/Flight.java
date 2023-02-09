package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AbstractFlight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 *  The Flight class is used to describe a typical airline flight.
 *  The Flight includes the 3-letter name of the departing airport, as well as the flight number, departure time and date.
 *  It also includes information about the arriving airport, being the 3-letter code of the airport,
 *  and the arrival time and date.
 *
 * @author Christian Torralba
 * @version 3.0
 * @since 1.0
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {
  /**
   * Flight number of the flight.
   */
  private final int flightNumber;

  /**
   * 3-letter Airport code for departing flight.
   */
  private final String src;


  /**
   * Departure date, format MM/DD/YYYY
   * Month and day can be one or two digit, year will always be four digits.
   */
  private final String departDate;

  /**
   * Departure time, format HH:MM
   * Hour and minute may be one or two digits.
   */
  private final String departTime;


  /**
   * Proper format for Arrival date and time, using MM/dd/yy h:mm
   */
 private final Date arrivalDateAndTime;


  /**
   * Proper format for Departure date and time, using MM/dd/yy h:mm
   */
  private final Date departureDateAndTime;

  /**
   *  3-letter code for arrival airport.
   */

  private final String dest;


  /**
   * Date of arrival, format MM/DD/YYYY
   * Month and day can be one or two digit, year will always be four digits.
   */
  private final String arriveDate;

  /**
   * Arrival time, format HH:MM
   * Hour and minute may be one or two digits.
   */
  private final String arriveTime;


  /**
   * Creates a new Flight with the specified information
   *
   * @param flightNumber Flight number of flight, integer value
   * @param src 3-letter String for departing airport
   * @param departDate Date of departure, as a String
   * @param departTime Time of departure, as a String
   * @param dest 3-letter String for arrival airport
   * @param arriveDate Date or arrival, as a String
   * @param arriveTime Time or arrival, as a String
   */
  public Flight(int flightNumber, String src, String departDate, String departTime, String dest, String arriveDate, String arriveTime) {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mm a");

    this.flightNumber = flightNumber;

    this.src = src;
    this.dest = dest;

    if (!Project3.validDateFormat(departDate))
      throw new IllegalArgumentException("Invalid departure date provided.");

    if (!Project3.validDateFormat(arriveDate))
      throw new IllegalArgumentException("Invalid arrival date provided.");

    if (!Project3.validTimeFormat(departTime))
      throw new IllegalArgumentException("Invalid departure time provided.");

    if (!Project3.validTimeFormat(arriveTime))
      throw new IllegalArgumentException("Invalid arrival time provided.");

    try {
      arrivalDateAndTime = df.parse(arriveDate + " " + arriveTime);
    } catch (ParseException pe) {
      throw new IllegalArgumentException("Incorrect arrival date or time format.");
    }

    try {
      departureDateAndTime = df.parse(departDate + " " + departTime);
    } catch (ParseException PE) {
      throw new IllegalArgumentException("Incorrect departure date or time format.");
    }

    if(arrivalDateAndTime.before(departureDateAndTime)) {
        throw new IllegalArgumentException("Cannot have arrival date before departure date.");
    }

    this.departDate = departDate;
    this.departTime = departTime;

    this.arriveDate = arriveDate;
    this.arriveTime = arriveTime;
  }



  public String getFlightAsTextFileString() {
    return this.flightNumber + "|" + this.src + "|" + this.departDate+ "|" + this.departTime+ "|" + this.dest + "|" + this.arriveDate + "|" + this.arriveTime;
  }

  /**
   * @return The flight number for current flight
   */
  @Override
  public int getNumber() {
    return this.flightNumber;
  }

  /**
   * @return The airport code for the source airport
   */
  @Override
  public String getSource() {
    return this.src;
  }

  @Override
  public Date getArrival() {
    return this.arrivalDateAndTime;
  }


  @Override
  public Date getDeparture() {
    return this.departureDateAndTime;
  }
  /**
   * @return The departure date and time
   */
  @Override
  public String getDepartureString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(this.departureDateAndTime);
  }

  /**
   * @return Returns the airport code for the destination airport
   */
  @Override
  public String getDestination() {
    return this.dest;
  }


  /**
   * Returns the arrival date and time in String form with MM/dd/yy h:mm:ss format
   * @return String The arrival date and time formatted together
   */
  @Override
  public String getArrivalString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(this.arrivalDateAndTime);
  }


  /**
   * This function compares two flights, flights with source codes that come first in the alphabet
   * have a higher value, if both flights have the same source, the flights with a earlier flight time
   * have higher priority.
   * @param flight The flight we are comparing to.
   * @return int Value 0 if flights are equal
   */
  @Override
  public int compareTo(Flight flight) {
    int result = this.src.compareTo(flight.src);
    if(result == 0) { // The sources are equal
      return this.departureDateAndTime.compareTo(flight.departureDateAndTime);
    }
    return result;
  }
}
