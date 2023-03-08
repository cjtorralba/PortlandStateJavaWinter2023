package edu.pdx.cs410J.torral2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client. Stores airlines and flights related to airlines.
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */
public class AirlineRestClient {

    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final HttpRequestHelper http;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

    /**
     * Constructor for AirlineRestClient
     * @param http HttpRequestHelper for specific servlet
     */
    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
        this.http = http;
    }


    /**
     * Returns a given airline and all its flights.
     * @param airlineName Name of the airline to be searched for
     */
    public Airline getAirlineByName(String airlineName) throws IOException, ParserException {
        Response response = http.get(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();

        XmlParser xmlParser = new XmlParser(content);
        return xmlParser.parse();
    }

    /**
     *  Adds a flight entry to a specified airline name
     * @param airlineName Specific airline to be adding the flight to
     * @param flightNumber Flight number for the flight to be added.
     * @param source Three letter source code for airport
     * @param departDate Date of departure for the flight
     * @param departTime Time of departure for the flight
     * @param destination Three letter airport code for arrival airport
     * @param arrivalDate Date of arrival for flight
     * @param arrivalTime Time of arrival for flight
     * @throws IOException If unable to add flight or could not find airline
     */
    public void addFlightEntry(String airlineName, String flightNumber, String source, String departDate, String departTime, String destination, String arrivalDate, String arrivalTime) throws IOException {
        Response response = http.post(Map.of(
                AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName,
                AirlineServlet.FLIGHT_NUMBER_PARAMETER, flightNumber,
                AirlineServlet.FLIGHT_SOURCE_PARAMETER, source,
                AirlineServlet.FLIGHT_DEPART_DATE_PARAMETER, departDate,
                AirlineServlet.FLIGHT_DEPART_TIME_PARAMETER, departTime,
                AirlineServlet.FLIGHT_DESTINATION_PARAMETER, destination,
                AirlineServlet.FLIGHT_ARRIVAL_DATE_PARAMETER, arrivalDate,
                AirlineServlet.FLIGHT_ARRIVAL_TIME_PARAMETER, arrivalTime
        ));
        throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     *  Throws an exception if the HTTP status is not okay and does not fit our desired needs.
     * @param response
     */
    private void throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getHttpStatusCode();
        if (code != HTTP_OK) {
            String message = response.getContent();
            throw new RestException(code, message);
        }
    }

}
