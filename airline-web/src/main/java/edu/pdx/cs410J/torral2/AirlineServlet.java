package edu.pdx.cs410J.torral2;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
    //  static final String WORD_PARAMETER = "word";
    static final String AIRLINE_NAME_PARAMETER = "airlineName";
    //    static final String DEFINITION_PARAMETER = "definition";
    static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";
    static final String FLIGHT_SOURCE_PARAMETER = "flightSource";
    static final String FLIGHT_DEPART_DATE_PARAMETER = "flightDepartDate";
    static final String FLIGHT_DEPART_TIME_PARAMETER = "flightDepartTime";
    static final String FLIGHT_ARRIVAL_DATE_PARAMETER = "flightArrivalDate";
    static final String FLIGHT_ARRIVAL_TIME_PARAMETER = "flightArrivalTime";
    static final String FLIGHT_DESTINATION_PARAMETER = "flightDestination";

    private final List<Airline> airlines = new ArrayList<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request);

        if(airlineName != null) {
            writeAirline(airlineName, response);
        } else {
            writeAllAirlineEntries(response);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request);
        String flightNumber = getParameter(FLIGHT_NUMBER_PARAMETER, request);
        String source = getParameter(FLIGHT_SOURCE_PARAMETER, request);
        String departDate = getParameter(FLIGHT_DEPART_DATE_PARAMETER, request);
        String departTime = getParameter(FLIGHT_DEPART_TIME_PARAMETER, request);
        String destination = getParameter(FLIGHT_DESTINATION_PARAMETER, request);
        String arriveDate = getParameter(FLIGHT_ARRIVAL_DATE_PARAMETER, request);
        String arriveTime = getParameter(FLIGHT_ARRIVAL_TIME_PARAMETER, request);


        if (airlineName == null) {
            missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
            return;
        }
        if (flightNumber == null) {
            missingRequiredParameter(response, FLIGHT_NUMBER_PARAMETER);
            return;
        }
        if (source == null) {
            missingRequiredParameter(response, FLIGHT_SOURCE_PARAMETER);
            return;
        }
        if (departDate == null) {
            missingRequiredParameter(response, FLIGHT_DEPART_DATE_PARAMETER);
            return;
        }
        if (departTime == null) {
            missingRequiredParameter(response, FLIGHT_DEPART_TIME_PARAMETER);
            return;
        }
        if (destination == null) {
            missingRequiredParameter(response, FLIGHT_DESTINATION_PARAMETER);
            return;
        }
        if (arriveDate == null) {
            missingRequiredParameter(response, FLIGHT_ARRIVAL_DATE_PARAMETER);
            return;
        }
        if (arriveTime == null) {
            missingRequiredParameter(response, FLIGHT_ARRIVAL_TIME_PARAMETER);
            return;
        }

        int parsedFlightNumber = -1;
        try {
            parsedFlightNumber = Integer.parseInt(flightNumber);
        } catch(NumberFormatException ne) {
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "The flight number entered is not valid.");
        }


        Optional<Airline> optionalAirline = this.airlines.stream().filter(airline -> airline.getName().equals(airlineName)).findFirst();

        if(optionalAirline.isEmpty()) {
            airlines.add(new Airline(airlineName, new ArrayList<>(List.of(new Flight(parsedFlightNumber, source, departDate, departTime, destination, arriveDate, arriveTime)))));
        } else {
            optionalAirline.get().addFlight(new Flight(parsedFlightNumber, source, departDate, departTime, destination, arriveDate, arriveTime));
        }

        PrintWriter pw = response.getWriter();
        pw.println(optionalAirline.isEmpty() ? "airline is empty" : optionalAirline.get().getName());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        //this.dictionary.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * <p>
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter(HttpServletResponse response, String parameterName)
            throws IOException {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the definition of the given word to the HTTP response.
     * <p>
     * The text of the message is formatted with {@link OldTextDumper}
     */
    private void writeAirline(String airlineName, HttpServletResponse response) throws IOException {

        if (airlineName == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();
            TextDumper dumper = new TextDumper(pw);

            Optional<Airline> optionalAirline = airlines.stream().filter(airline -> airline.getName().equals(airlineName)).findFirst();

            if(optionalAirline.isPresent()) {
                dumper.dump(optionalAirline.get());
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
            }


        }
    }

    /**
     * Writes all of the dictionary entries to the HTTP response.
     * <p>
     * The text of the message is formatted with {@link OldTextDumper}
     */
    private void writeAllAirlineEntries(HttpServletResponse response) throws IOException {
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        for(Airline airline : airlines) {
            dumper.dump(airline);
        }
        Arrays.stream(sw.toString().split("\n")).forEach(line -> {
            try {
                response.getWriter().println(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });



        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     * <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }
}
