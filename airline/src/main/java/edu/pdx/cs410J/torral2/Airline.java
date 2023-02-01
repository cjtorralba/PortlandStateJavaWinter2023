package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private final Collection<Flight> flightList;

  public Airline(String name, Collection<Flight> flightList) {
    this.flightList = flightList;
    this.name = name;
  }

  public Airline(String name) {
    this(name, new ArrayList<>());
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {
    this.flightList.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    return this.flightList;
  }
}
