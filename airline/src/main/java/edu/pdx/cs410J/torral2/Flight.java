package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AbstractFlight;


/**
 *  The Flight class is used to describe a typical airline flight.
 *  The Flight includes the 3-letter name of the departing airport, as well as the flight number, departure time and date.
 *  It also includes information about the arriving airport, being the 3-letter code of the airport,
 *  and the arrival time and date.
 *
 * @author Christian Julio Torralba
 * @version 1.0
 * @since 1.0
 */
public class Flight extends AbstractFlight {


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

    this.flightNumber = flightNumber;

    this.src = src;

    if(!Project2.validDateFormat(departDate))
      throw new IllegalArgumentException("Invalid departure date provided.");

    if(!Project2.validDateFormat(arriveDate))
      throw new IllegalArgumentException("Invalid arrival date provided.");

    if(!Project2.validTimeFormat(departTime))
      throw new IllegalArgumentException("Invalid departure time provided.");

    if(!Project2.validTimeFormat(arriveTime))
      throw new IllegalArgumentException("Invalid arrival time provided.");


    this.departDate = departDate;
    this.departTime = departTime;

    this.dest = dest;

    this.arriveDate = arriveDate;
    this.arriveTime = arriveTime;

  }

  public String getFlightAsTextFileString() {
    return this.getNumber() + "|" + this.src + "|" + this.getDepartureStringForTextFile() + "|" + this.getDestination() + "|" + this.getArrivalStringForTextFile();
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

  /**
   * @return The departure date and time
   */
  @Override
  public String getDepartureString() {
    return this.departDate + " " + this.departTime;
  }

  /**
   * @return The departure date and time formatted for the text file, using '|' to separate the two
   */
  public String getDepartureStringForTextFile() {
    return this.departDate + "|" + this.departTime;
  }

  /**
   * @return Returns the airport code for the destination airport
   */
  @Override
  public String getDestination() {
    return this.dest;
  }


  /**
   * @return The arrival date and time formatted together
   */
  @Override
  public String getArrivalString() {
    return this.arriveDate + " " + this.arriveTime;
  }

  /**
   * @return The arrival date and time formatted for a text file, using '|' as a delimiter
   */
  public String getArrivalStringForTextFile() {
    return this.arriveDate + "|" + this.arriveTime;
  }
}
