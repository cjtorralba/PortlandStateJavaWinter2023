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
 */
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void testReadMe() {
        MainMethodResult result = invokeMain(Project5.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The purpose"));
    }


    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project5.class);
        assertThat(result.getTextWrittenToStandardError(), containsString("** Missing"));
    }

    @Test
    void test2HostAndPortOnly() {
        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT);

        assertThat(result.getTextWrittenToStandardError(), containsString("** Missing"));
    }


    @Test
    void testSearchAirline() {

        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "airline name", "123", "PDX", "10/20/2000", "5:15", "PM", "LAX", "10/21/2000", "6:45", "AM");
        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "-search", "airline name");

        assertThat(result.getTextWrittenToStandardError(), containsString("ll"));


    }


    @Disabled
    void test3() {
        String word = "WORD";
        try {
            MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, word);
            assertThat(result.getTextWrittenToStandardError(), containsString("** "));

        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }
/*
    @Test
    void test4AddDefinition() {
        String word = "WORD";
        String definition = "DEFINITION";

        MainMethodResult result = invokeMain( Project5.class, HOSTNAME, PORT, word, definition );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.definedWordAs(word, definition)));

        result = invokeMain( Project5.class, HOSTNAME, PORT, word );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(PrettyPrinter.formatDictionaryEntry(word, definition)));

        result = invokeMain( Project5.class, HOSTNAME, PORT );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(PrettyPrinter.formatDictionaryEntry(word, definition)));
    }

     */
}