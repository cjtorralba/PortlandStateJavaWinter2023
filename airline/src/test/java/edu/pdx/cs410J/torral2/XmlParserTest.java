package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class XmlParserTest {


   @Test
   void testXmlParseValidAirline() throws ParserException {
      XmlParser xmlParser = new XmlParser(new File("C:\\Users\\cjtorralba\\IdeaProjects\\PortlandStateJavaWinter2023\\airline\\src\\test\\resources\\edu\\pdx\\cs410J\\torral2\\valid-airline.xml"));
      Airline airline = xmlParser.parse();

      assertThat(airline.getName(), equalTo("Valid Airlines"));
   }

}
