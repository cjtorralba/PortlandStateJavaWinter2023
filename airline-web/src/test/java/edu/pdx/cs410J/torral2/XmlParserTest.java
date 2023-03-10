package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


/**
 * This class is used to test the functionality of the XmlParser class.
 *
 * @author Christian Torralba
 * @version 1.0
 * @since 1.0
 */
public class XmlParserTest {

   /**
    * Tests to make sure that the airline parsed from an XML file matches the correct airline name
    * @throws ParserException If there is an airline mismatch error.
    */
   @Test
   void testXmlParseValidAirline() throws ParserException, IOException {

      String xmlTestString = "<?xml version=\"1.0\" encoding=\"us-ascii\" standalone=\"no\"?>\n" +
              "<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">\n" +
              "<airline>\n" +
              "    <name>Airlines</name>\n" +
              "    <flight>\n" +
              "        <number>737</number>\n" +
              "        <src>PDX</src>\n" +
              "        <depart>\n" +
              "            <date day=\"23\" month=\"10\" year=\"1923\"/>\n" +
              "            <time hour=\"0\" minute=\"42\"/>\n" +
              "        </depart>\n" +
              "        <dest>LAX</dest>\n" +
              "        <arrive>\n" +
              "            <date day=\"23\" month=\"12\" year=\"2003\"/>\n" +
              "            <time hour=\"3\" minute=\"3\"/>\n" +
              "        </arrive>\n" +
              "    </flight>\n" +
              "</airline>";
      XmlParser xmlParser = new XmlParser(xmlTestString);
      Airline airline = xmlParser.parse();

      assertThat(airline.getName(), equalTo("Airlines"));



   }
}
