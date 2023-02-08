package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.concurrent.TimeUnit;


public class PrettyPrinter implements AirlineDumper<Airline> {

    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }


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
