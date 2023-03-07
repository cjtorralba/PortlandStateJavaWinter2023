package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        String hostName = null;
        String portString = null;

        String airlineName = null;

        int flightNumber = -1;

        String source = null;
        String departDate = null;
        String departTime = null;

        String destination = null;
        String arrivalDate = null;
        String arrivalTime = null;


        for (String arg : args) {
            if (hostName == null) {
                hostName = arg;

            } else if (portString == null) {
                portString = arg;

            } else if (airlineName == null) {
                airlineName = arg;

            } else if (source == null) {
                source = arg;

            } else {
                usage("Extraneous command line argument: " + arg);
            }
        }

        if (hostName == null) {
            usage(MISSING_ARGS);
            return;

        } else if (portString == null) {
            usage("Missing port");
            return;
        }

        int port;
        try {
            port = Integer.parseInt(portString);
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        String message;
        try {
            if (airlineName == null) {
                // Print all word/definition pairs
                ArrayList<Airline> airlines = client.getAllAirlineEntries();
                StringWriter sw = new StringWriter();
                PrettyPrinter pretty = new PrettyPrinter(sw);
                for (Airline airline : airlines) {
                    if (airline != null) {
                        pretty.dump(airline);
                    }
                }
                message = sw.toString();

            } else if (source == null) {
                // Print all dictionary entries
                //  message = PrettyPrinter.formatDictionaryEntry(word, client.getDefinition(word));

            } else {
                // Post the word/definition pair
                // client.addDictionaryEntry(word, definition);
                //message = Messages.definedWordAs(word, definition);
            }

        } catch (IOException | ParserException ex) {
            error("While contacting server: " + ex.getMessage());
            return;
        }

        // System.out.println(message);
    }

    private static void error(String message) {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    /**
     * Prints usage information for this program and exits
     *
     * @param message An error message to print
     */
    private static void usage(String message) {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project5 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();
    }
}