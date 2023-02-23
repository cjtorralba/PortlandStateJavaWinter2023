package edu.pdx.cs410J.torral2;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class is used to test some functionality of the xml parsing classes.
 * @author David Whitlock
 * @version 1.0
 * @since 1.0
 */

class AirlineXmlHelperTest {

  @Test
  void canParseValidXmlFile() throws ParserConfigurationException, IOException, SAXException {
    AirlineXmlHelper helper = new AirlineXmlHelper();

    DocumentBuilderFactory factory =
      DocumentBuilderFactory.newInstance();
    factory.setValidating(true);

    DocumentBuilder builder =
      factory.newDocumentBuilder();
    builder.setErrorHandler(helper);
    builder.setEntityResolver(helper);

    builder.parse(this.getClass().getResourceAsStream("valid-airline.xml"));
  }

  @Test
  void cantParseInvalidXmlFile() throws ParserConfigurationException {
    AirlineXmlHelper helper = new AirlineXmlHelper();


    DocumentBuilderFactory factory =
      DocumentBuilderFactory.newInstance();
    factory.setValidating(true);

    DocumentBuilder builder =
      factory.newDocumentBuilder();
    builder.setErrorHandler(helper);
    builder.setEntityResolver(helper);

    assertThrows(SAXParseException.class, () ->
      builder.parse(this.getClass().getResourceAsStream("invalid-airline.xml"))
    );
  }

}
