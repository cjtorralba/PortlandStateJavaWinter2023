package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  private final int flightNumber;
  private final String src;
  private final String depart;

  private final String dest;
  private final String arrive;


  /**
   * @param flightNumber
   * @param src
   * @param depart
   * @param dest
   * @param arrive
   */
  public Flight(int flightNumber, String src, String depart, String dest, String arrive) {

    this.flightNumber = flightNumber;

    this.src = src;

    this.depart = depart;

    this.dest = dest;

    this.arrive = arrive;
  }


  @Override
  public int getNumber() {
    return this.flightNumber;
  }

  @Override
  public String getSource() {
    return this.src;
  }

  @Override
  public String getDepartureString() {
    return this.depart;
  }

  @Override
  public String getDestination() {
    return this.dest;
  }

  @Override
  public String getArrivalString() {
    return this.arrive;
  }
}
