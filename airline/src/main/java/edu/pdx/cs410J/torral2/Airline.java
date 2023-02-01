package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Airline class is used to contain many flights.
 * These flights will all be associated with the one Airline.
 *
 * @author  Christian Torralba
 * @version 1.0
 * @since 1.0
 */

public class Airline extends AbstractAirline<Flight> {

  /**
   * Name of Airline
   */
  private final String name;


  /**
   * Collection of flights
   */
  private final Collection<Flight> flightList;


  /**
   * Creates a new Airline with list of flights
   * @param name Name of airline
   * @param flightList Collection of flights
   */
  public Airline(String name, Collection<Flight> flightList) {
    this.flightList = flightList;
    this.name = name;
  }


  /**
   * Creates a new Airline with string passed in, but airline will have an empty list
   * @param name The name of the airline
   */
  public Airline(String name) {
    this(name, new ArrayList<>());
  }


  /**
   * Returns the name of the airline
   * @return Name of airline
   */
  @Override
  public String getName() {
    return this.name;
  }


  /**
   * Adds a flight to an airlines collection of flights
   * @param flight Flight to be added to collection
   */
  @Override
  public void addFlight(Flight flight) {
    this.flightList.add(flight);
  }

  /**
   * Returns an airlines collection of flights
   * @return A collection of flights
   */
  @Override
  public Collection<Flight> getFlights() {
    return this.flightList;
  }
}
