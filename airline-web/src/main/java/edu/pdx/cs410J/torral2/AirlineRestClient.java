package edu.pdx.cs410J.torral2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
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

    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
      this.http = http;
    }

  /**
   * Returns all dictionary entries from the server
   */
  public Airline getAllAirlineEntries() throws IOException, ParserException {
    Response response = http.get(Map.of());
    throwExceptionIfNotOkayHttpStatus(response);

    TextParser parser = new TextParser(new StringReader(response.getContent()));
    return parser.parse();
  }

  /**
   * Returns the definition for the given word
   */
  public String getAirlineName(String airlineName) throws IOException, ParserException {
    Response response = http.get(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName));
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();

    TextParser parser = new TextParser(new StringReader(content));
    return airlineName;
  }


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
//        Response response = http.post(Map.of(AirlineServlet.WORD_PARAMETER, word, AirlineServlet.DEFINITION_PARAMETER, definition));
        throwExceptionIfNotOkayHttpStatus(response);
    }

    public void removeAllDictionaryEntries() throws IOException {
        Response response = http.delete(Map.of());
        throwExceptionIfNotOkayHttpStatus(response);
    }

    private void throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getHttpStatusCode();
        if (code != HTTP_OK) {
            String message = response.getContent();
            throw new RestException(code, message);
        }
    }

}
