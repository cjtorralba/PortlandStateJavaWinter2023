package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLOutput;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * A unit test for the REST client that demonstrates using mocks and
 * dependency injection
 *
 * @author Christian Torralba
 * @version 1.0
 * @since  1.0
 */
public class AirlineRestClientTest {

  @Test
  void getAirlineEntryWithAirlineName() throws ParserException, IOException {

    String airlineName = "My Airline";
    Airline airline = new Airline(airlineName);
    airline.addFlight(new Flight(123, "PDX", "10/20/2000", "10:54 PM", "LAX", "10/21/2000", "5:30 AM"));

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName)))).thenReturn(airlineNameFromHTTP(airline));

    AirlineRestClient client = new AirlineRestClient(http);

    assertThat(client.getAirlineByName(airlineName).getName(), equalTo(airlineName));
  }

  private HttpRequestHelper.Response airlineNameFromHTTP(Airline airline) throws IOException {
    StringWriter sw = new StringWriter();
    new XmlDumper(sw).dump(airline);
    return new HttpRequestHelper.Response(sw.toString());
  }
}
