package edu.pdx.cs410J.torral2;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */
public class AirlineServlet extends HttpServlet {
    static final String AIRLINE_NAME_PARAMETER = "airlineName";
    static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";
    static final String FLIGHT_SOURCE_PARAMETER = "flightSource";
    static final String FLIGHT_DEPART_DATE_PARAMETER = "flightDepartDate";
    static final String FLIGHT_DEPART_TIME_PARAMETER = "flightDepartTime";
    static final String FLIGHT_ARRIVAL_DATE_PARAMETER = "flightArrivalDate";
    static final String FLIGHT_ARRIVAL_TIME_PARAMETER = "flightArrivalTime";
    static final String FLIGHT_DESTINATION_PARAMETER = "flightDestination";

    private final List<Airline> airlines = new ArrayList<>();

    /**
     * Handles an HTTP GET request from a client by writing the airline given by the client. If the airline name is null
     * then we error out. If the airline name is provided then we search for the airline and return one if we can find it.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request);
        if (airlineName != null) {
            writeAirline(airlineName, response);
        } else {
            System.err.println("Airline name not provided.");
        }
    }

    /**
     * Handles an HTTP POST request by storing the airline for the
     * request parameters.  It writes the airline
     * entry to the HTTP response.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/xml");

        String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request);
        String flightNumber = getParameter(FLIGHT_NUMBER_PARAMETER, request);
        String source = getParameter(FLIGHT_SOURCE_PARAMETER, request);
        String departDate = getParameter(FLIGHT_DEPART_DATE_PARAMETER, request);
        String departTime = getParameter(FLIGHT_DEPART_TIME_PARAMETER, request);
        String destination = getParameter(FLIGHT_DESTINATION_PARAMETER, request);
        String arriveDate = getParameter(FLIGHT_ARRIVAL_DATE_PARAMETER, request);
        String arriveTime = getParameter(FLIGHT_ARRIVAL_TIME_PARAMETER, request);


        if (flightNumber == null) {
            missingRequiredParameter(response, FLIGHT_NUMBER_PARAMETER);
            return;
        }
        if (source == null) {
            missingRequiredParameter(response, FLIGHT_SOURCE_PARAMETER);
            return;
        }
        if (destination == null) {
            missingRequiredParameter(response, FLIGHT_DESTINATION_PARAMETER);
            return;
        }

        int parsedFlightNumber = -1;
        try {
            parsedFlightNumber = Integer.parseInt(flightNumber);
        } catch (NumberFormatException ne) {
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "The flight number entered is not valid.");
        }


        Optional<Airline> optionalAirline = this.airlines.stream().filter(airline -> airline.getName().equals(airlineName)).findFirst();

        if (optionalAirline.isEmpty()) {
            airlines.add(new Airline(airlineName, new ArrayList<>(List.of(new Flight(parsedFlightNumber, source, departDate, departTime, destination, arriveDate, arriveTime)))));
        } else {
            optionalAirline.get().addFlight(new Flight(parsedFlightNumber, source, departDate, departTime, destination, arriveDate, arriveTime));
        }


        response.setStatus(HttpServletResponse.SC_OK);
    }


    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * <p>
     * The text of the error message is created by
     */
    private void missingRequiredParameter(HttpServletResponse response, String parameterName)
            throws IOException {
        String message = String.format("The required parameter \"%s\" is missing", parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }


    /**
     * Writes the contents and name of a given airline based off the airline name passed in
     * <p>
     * The text of the message is formatted with {@link XmlDumper}
     */
    private void writeAirline(String airlineName, HttpServletResponse response) throws IOException {

        if (airlineName == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {

            PrintWriter pw = response.getWriter();
            XmlDumper xmlDumper = new XmlDumper(pw);

            Optional<Airline> optionalAirline = airlines.stream().filter(airline -> airline.getName().equals(airlineName)).findFirst();

            if (optionalAirline.isPresent()) {
                xmlDumper.dump(optionalAirline.get());
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        }
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
