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


    /**
     * For ease of use so we can use shortcut to specify missing command line arguments
     */
    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {

        if (args.length == 0) {
            usage(MISSING_ARGS);
            return;
        }

        String hostName = null;
        String portString = null;


        ArrayList<String> list = new ArrayList<>(List.of(args));

        // Checking for arguments
        boolean hasHostName = list.contains("-host");
        boolean hasPort = list.contains("-port");

        boolean hasSearch = list.contains("-search");
        boolean hasPrint = list.contains("-print");
        if (list.contains("-README")) {
            System.out.printf("The purpose of this program is made to be a simple way to store flights associated with certain airlines.\n" +
                    "For example, if you were to be traveling via plane soon, it would be a good idea to run this with information\n" +
                    "about the flight number, the departing and arrival airport code, the departure date and time, as well as\n" +
                    "the arrival date and time, and the name of the airline you will be flying with. Just so you can stay extra organized!\n" +
                    "\n" +
                    "This program was written by Christian Torralba for the Advanced Java class (CS410P) taught by David Whitlock\n" +
                    "Winter term of 2023.\n" +
                    "\n" +
                    "This program will take necessary information related to an Airline and its flights. Each flight will have a flight number,\n" +
                    "a three letter source code for the airport the flight is leaving from, a departure date and time,  an arrival date and time,\n" +
                    "and three letter arrival airport code.\n" +
                    "\n" +
                    "The date and time must be passed in with the following format: MM/dd/yyyy h/mm AM|PM\n" +
                    "An example of this could be: 07/26/2001 7:56 pm or 12/01/1996 12:03 AM\n" +
                    "Be careful to have the am/pm separated from the actual time, as if you do not then an error will occur.\n" +
                    "This program does NOT support 24-hour time format.\n" +
                    "\n" +
                    "If you chose to use the option to write to an external file, do keep in mind that if the file does not exist, one will\n" +
                    "be created for you. If a file already does exist, and it contains misconfigured information, the program will NOT write to\n" +
                    "the file, but exit instead.\n" +
                    "\n" +
                    "The pretty print option will work in a similar way. If the file does not exist, one will be created. If a file does exist,\n" +
                    "but with information in it already, it will be overwritten, please be aware of this before you run the program.\n" +
                    "\n" +
                    "The source and arrival airport codes must be EXACTLY three characters, no numbers allowed. If it is not, then the program\n" +
                    "will exit and prompt you to run it again.\n" +
                    "\n" +
                    "There is also the option to have our airline and flights stored in xml format. By using the command line option '-xmlFile'\n" +
                    "You may specify the location of where you wish your airline to be stored.\n" +
                    "\n" +
                    "The Rest adaptation of this program integrates a Java servlet. Using this servlet allows us to stores airlines on an external server." +
                    "Program five allows the user to communicate with the web server, whether it be adding, or searching.\n" +
                    "This program will allow you to search for an airline by either the airline name, or you may also find all flights associated with an airline with flights starting\n" +
                    "at a certain location and terminating at a certain destination." +
                    "To see command line usage for this program please run with no arguments." +
                    "");
            return;
        }

        if (!hasHostName || !hasPort) { // Missing both hostname and port
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

        switch (list.size()) {
            case 1:
                // Searching for airline by name only and pretty printing all its flights
                if (hasSearch) {
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
                if (hasSearch) {
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
                } catch (NumberFormatException ne) {
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

                if (hasPrint) {
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
        err.println("usage: java -jar target/airline-client.jar [options] <args>");
        err.println(" args are (int this order): ");
        err.println("   airline        The name of the airline");
        err.println("   flightNumber   The flight number");
        err.println("   src            Three-letter code of departure");
        err.println("   Depart         Departure date/time ");
        err.println("   dest           Three letter code of arrival");
        err.println("   arrive         Arrival date/time");
        err.println("options are (options may appear in any order):");
        err.println("  -host hostname   Host computer on which the server runs");
        err.println("  -port port       Port on which the server is listening");
        err.println("  -search search   Search for flights");
        err.println("  -print           Prints a descriptions of the new flight");
        err.println("  -README          Prints a README for this project and exits");
    }
}