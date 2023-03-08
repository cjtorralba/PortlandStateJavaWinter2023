package edu.pdx.cs410J.torral2;

import com.sun.tools.javac.Main;
import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");


    /**
     * Tests the -README Command line option
     */
    @Test
    void testReadMe() {
        MainMethodResult result = invokeMain(Project5.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The purpose"));
    }


    /**
     * Tests no command line options.
     */
    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project5.class);
        assertThat(result.getTextWrittenToStandardError(), containsString("** Missing"));
    }


    /**
     * Tests if only port and host are specified, should call the usage method in this case
     */
    @Test
    void test2HostAndPortOnly() {
        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT);

        assertThat(result.getTextWrittenToStandardError(), containsString("** Missing"));
    }


    /**
     * Tests the search for an airline by name
     */
    @Test
    void testSearchAirline() {

        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "airline name", "123", "PDX", "10/20/2000", "5:15", "PM", "LAX", "10/21/2000", "6:45", "AM");
        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "-search", "airline name");

        assertThat(result.getTextWrittenToStandardError(), containsString(""));


    }
}