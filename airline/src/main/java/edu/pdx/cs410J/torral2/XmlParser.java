package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParser implements AirlineParser {

    private final File file;


    public XmlParser(File file) { this.file = file; }

    @Override
    public Airline parse() throws ParserException {

        Airline airline = null;

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;

        try {
            docBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        try {
            doc = docBuilder.parse(this.file);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        //getting the root node
        String airlineName = doc.getElementsByTagName("name").item(0).getTextContent();
        NodeList listOfFlights = doc.getElementsByTagName("flight");


        List<Flight> arrayOfFlights = new ArrayList<>();

        for(int i = 0; i < listOfFlights.getLength(); ++i) {
            arrayOfFlights.add(Flight.parseNodeXML(listOfFlights.item(i).getChildNodes()));
        }

        if(airlineName != null && !arrayOfFlights.contains(null)) {
            airline = new Airline(airlineName, arrayOfFlights);
        }

        return airline;
    }
}
