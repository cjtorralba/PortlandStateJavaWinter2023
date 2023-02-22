package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

public class XmlParser implements AirlineParser {

    private final File file;


    public XmlParser(File file) { this.file = file; }

    @Override
    public Airline parse() throws ParserException {

        /*
         * Basic logic and referenced from  https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
         */

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;

        try {
//            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//            documentBuilderFactory.setFeature(XMLConstants.ACCESS_EXTERNAL_DTD, true);
            docBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        try {
            doc = docBuilder.parse(this.file);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

//        doc.getDocumentElement().normalize();
        System.out.println("We got past all that shit");

        //getting the root node
        NodeList list = doc.getElementsByTagName("airline");
        NodeList children = list.item(0).getChildNodes();

        // Temp variables for parts of flight and airline
        String airlineName;
        String flightNumber;

        String src;
        String departTimeHour;
        String departTimeMinute;
        String departDateDay;
        String departDateMonth;
        String departDateDayYear;

        String dest;
        String arriveTimeHour;
        String arriveTimeMinute;
        String arriveDateDay;
        String arriveDateMonth;
        String arriveDateDayYear;




        for(int i = 0; i < children.getLength(); ++i) {

            Element element = (Element) list.item(i);
            System.out.println(element.getTagName());

            switch(element.getTagName()) {
                case "name" -> {
                    airlineName = element.getTextContent();
                    System.out.println(airlineName);
                }
                case "number" -> {
                    flightNumber = element.getTextContent();
                    System.out.println(flightNumber);
                }
                case "src" -> {
                    src = element.getTextContent();
                    System.out.println();
                }

                // Creating a node list for this since it has child date tags
                case "depart" -> {
                    NodeList departList = element.getChildNodes();
                }

                case "dest" -> {

                }

                case "arrive" -> {}


            }
        }





        return null;
    }
}
