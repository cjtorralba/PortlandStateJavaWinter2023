package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.concurrent.TimeUnit;


/**
 * The PrettyPrinter class is made to ensure a pleasant output is produced to the user.
 * PrettyPrinter will print out an Airline, along with its Flights, in a human-readable way, including
 * the duration of the Flight in minutes.
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */
public class PrettyPrinter implements AirlineDumper<Airline> {

    private final Writer writer;


    /**
     * Creates a new PrettyPrinter object with a specified writer of your choice.
     * @param writer The writer that we will be writing to.
     */
    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Writes the Airline name and its Flights to a specified writer output
     * @param airline The Airline object passed in to have information written
     */
    @Override
    public void dump(Airline airline) {
        try (PrintWriter pw = new PrintWriter(this.writer)){

            Collection<Flight> list = airline.getFlights();

            pw.println("The " + airline.getName() + " has the following flights: ");
            for(Flight f : list) {
                long minutes = Math.abs(TimeUnit.MILLISECONDS.toMinutes(f.getArrival().toInstant().toEpochMilli() - f.getDeparture().toInstant().toEpochMilli()));
                pw.println("\tFlight " + f.getNumber() + " leaving from " + AirportNames.getName(f.getSource()) + "(" +
                        f.getSource() + ") " + f.getDepartureString() + " arrives in " +
                        AirportNames.getName(f.getDestination()) + "(" + f.getDestination() + ")" +
                        " at " + f.getArrivalString() + ", the length of this flight is " + minutes + " minutes.");
            }
            pw.flush();
        }
    }
}
