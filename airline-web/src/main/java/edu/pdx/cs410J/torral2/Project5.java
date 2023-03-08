package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
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




        ArrayList<String> list = new ArrayList<>(List.of(args));

        // Checking for arguments
        boolean hasHostName = list.contains("-host");
        boolean hasPort = list.contains("-port");

        boolean hasSearch = list.contains("-search");
        boolean hasPrint = list.contains("-print");
        boolean hasReadme = list.contains("-README");

        if(!hasHostName || !hasPort) { // Missing both hostname and port
            System.err.println("Missing hostname or port.");
            return;
        }

        // Getting port
        portString = list.get(list.indexOf("-port") + 1);
        list.remove(portString);
        list.remove("-port");

        // Getting hostname
        hostName = list.get(list.indexOf("-host") + 1);
        list.remove(hostName);
        list.remove("-host");

        list.remove("-search");
        list.remove("-print");
        list.remove("-README");

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

        Airline tempAirline = null;
        Flight tempFlight = null;

        String airlineName = null;

        String flightNumberString = null;
        int parsedFlightNumber = -1;

        String source = null;
        String departDate = null;
        String departTime = null;
        String departAMPM = null;

        String destination = null;
        String arrivalDate = null;
        String arrivalTime = null;
        String arrivalAMPM = null;

        switch(list.size()) {
            case 1:
                // Searching for airline by name only and pretty printing all its flights
                if(hasSearch) {
                    airlineName = list.get(0);

                    try {
                        tempAirline = client.getAirlineByName(airlineName);
                    } catch (Exception e) {
                        error("Could not parse airline by name.");
                        return;
                    }

                    PrettyPrinter prettyPrinter = new PrettyPrinter(new OutputStreamWriter(System.out));
                    prettyPrinter.dump(tempAirline);
                }
                break;

            case 3:
                // Searching for all flights given a specific airline name source and destination code
                if(hasSearch) {
                    airlineName = list.get(0);
                    source = list.get(1);
                    destination = list.get(2);


                    try {
                        tempAirline = client.getAirlineByName(airlineName);
                    } catch (Exception e) {
                        error("Could not find specified flight.");
                    }
                }
                break;

            case 10:
                // Adding a given flight to the server.
                airlineName = list.get(0);

                flightNumberString = list.get(1);

                source = list.get(2);
                departDate = list.get(3);
                departTime = list.get(4);
                departAMPM = list.get(5);

                destination = list.get(6);
                arrivalDate = list.get(7);
                arrivalTime = list.get(8);
                arrivalAMPM = list.get(9);

                try {
                    parsedFlightNumber = Integer.parseInt(flightNumberString);
                } catch(NumberFormatException ne) {
                    error("Invalid flight number provided");
                    return;
                }


                tempAirline = new Airline(airlineName);
                tempFlight = new Flight(parsedFlightNumber, source, departDate, departTime + " " + departAMPM, destination, arrivalDate, arrivalTime + " " + arrivalAMPM);
                tempAirline.addFlight(tempFlight);

                try {
                    client.addFlightEntry(airlineName, flightNumberString, source, departDate, departTime + " " + departAMPM, destination, arrivalDate, arrivalTime + " " + arrivalAMPM);
                } catch (IOException e) {
                    error("Could not add flight to specified airline.");
                }

                if(hasPrint) {
                    PrettyPrinter prettyPrinter = new PrettyPrinter(new OutputStreamWriter(System.out));
                    prettyPrinter.dump(tempAirline);
                }
                break;

            default:
                error(MISSING_ARGS);
                return;
        }

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