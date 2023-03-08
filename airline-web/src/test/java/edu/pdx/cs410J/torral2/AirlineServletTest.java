package edu.pdx.cs410J.torral2;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {
  @Test
  void initiallyServletContainsNoAirlines() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn("Test");

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
  }


  @Test
  void addOneFlightToAirline() throws IOException {

    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "TEST AIRLINE NAME";
    String flightNumber = "123";


    String flightSrc = "LAX";
    String departDate = "10/20/2000";
    String departTime = "5:45 pm";
    String flightDest = "PDX";
    String arriveDate = "10/21/2000";
    String arriveTime = "5:55 AM";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airlineName);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(flightNumber);

    when(request.getParameter(AirlineServlet.FLIGHT_SOURCE_PARAMETER)).thenReturn(flightSrc);
    when(request.getParameter(AirlineServlet.FLIGHT_DEPART_DATE_PARAMETER)).thenReturn(departDate);
    when(request.getParameter(AirlineServlet.FLIGHT_DEPART_TIME_PARAMETER)).thenReturn(departTime);

    when(request.getParameter(AirlineServlet.FLIGHT_DESTINATION_PARAMETER)).thenReturn(flightDest);
    when(request.getParameter(AirlineServlet.FLIGHT_ARRIVAL_DATE_PARAMETER)).thenReturn(arriveDate);
    when(request.getParameter(AirlineServlet.FLIGHT_ARRIVAL_TIME_PARAMETER)).thenReturn(arriveTime);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    assertThat(stringWriter.toString(), containsString(""));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

  }

}
