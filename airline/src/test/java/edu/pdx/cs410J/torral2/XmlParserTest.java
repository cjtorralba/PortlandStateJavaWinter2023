package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


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
   void testXmlParseValidAirline() throws ParserException {

      File is = new File(getClass().getResource("valid-airline.xml").getFile());
      XmlParser xmlParser = new XmlParser(is);
      Airline airline = xmlParser.parse();

      assertThat(airline.getName(), equalTo("Valid Airlines"));
   }
}
