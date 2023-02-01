package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AirlineDumper;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is used to write information from an airline into a specified text file
 *
 * @author Christian TOrralba
 * @version 1.0
 * @since 1.0
 */
public class TextDumper implements AirlineDumper<Airline> {

  /**
   * Abstract data type so developer can use which ever writer they desire
   */
  private final Writer writer;

  /**
   * Creates a new TextDumper object
   * @param writer The type of writer the developer wishes to use
   */
  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * Writes contents of airline, including flights, to a text file, sections are deliminated with '|'
   * @param airline Airline we will be reading information from to write ot text file
   */
  @Override
  public void dump(Airline airline) {
    try (
      PrintWriter pw = new PrintWriter(this.writer);
      ) {
      Collection<Flight> list = airline.getFlights();
      for(Flight f : list) {
        pw.println(airline.getName() + "|" + f.getFlightAsTextFileString());
      }
      pw.flush();
    }
  }
}
