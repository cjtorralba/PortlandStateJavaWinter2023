package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AirlineDumper;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

public class PrettyPrinter implements AirlineDumper<Airline> {

    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }


    @Override
    public void dump(Airline airline) {
        try (PrintWriter pw = new PrintWriter(this.writer)){

            Collection<Flight> list = airline.getFlights();
            for(Flight f : list) {

            }
            pw.flush();
        }
    }
}
