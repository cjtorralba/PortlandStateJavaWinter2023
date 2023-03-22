package edu.pdx.cs410J.torral2;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import edu.pdx.cs410J.AirlineDumper;

public class XmlDumper implements AirlineDumper<Airline> {


    private final Writer writer;


    public XmlDumper(Writer writer) {
        this.writer = writer;
    }


    @Override
    public void dump(Airline airline) throws IOException {


        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            docBuilder.setEntityResolver(new AirlineXmlHelper());
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc = docBuilder.newDocument();

        // Creating Airline root tag
        Element rootElement = doc.createElement("airline");
        doc.appendChild(rootElement);

        // Creating Airline Name tag
        Element name = doc.createElement("name");
        name.setTextContent(airline.getName());
        rootElement.appendChild(name);


        for (Flight flight : airline.getFlights()) {

            // Creating flight element
            Element flightRoot = doc.createElement("flight");
            rootElement.appendChild(flightRoot);

            // Adding flight number to flightRoot
            Element flightNumber = doc.createElement("number");
            flightNumber.setTextContent(String.valueOf(flight.getNumber()));
            flightRoot.appendChild(flightNumber);

            // Adding source code to flightRoot
            Element src = doc.createElement("src");
            src.setTextContent(flight.getSource());
            flightRoot.appendChild(src);

            // Adding depart element for date and time
            Element depart = doc.createElement("depart");
            flightRoot.appendChild(depart);

            // Adding information regarding the DEPARTURE date and time
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(flight.getDeparture());

                Element date = doc.createElement("date");
                date.setAttribute("day", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                date.setAttribute("month", String.valueOf(calendar.get(Calendar.MONTH) + 1));
                date.setAttribute("year", String.valueOf(calendar.get(Calendar.YEAR)));

                Element time = doc.createElement("time");
                time.setAttribute("hour", String.valueOf(calendar.get(Calendar.HOUR)));
                time.setAttribute("minute", String.valueOf(calendar.get(Calendar.MINUTE)));

                depart.appendChild(date);
                depart.appendChild(time);
            }

            Element dest = doc.createElement("dest");
            dest.setTextContent(flight.getDestination());
            flightRoot.appendChild(dest);


            Element arrive = doc.createElement("arrive");
            flightRoot.appendChild(arrive);

            // Adding information regarding the ARRIVAL date and time
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(flight.getArrival());

                Element date = doc.createElement("date");
                date.setAttribute("day", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                date.setAttribute("month", String.valueOf(calendar.get(Calendar.MONTH) + 1));
                date.setAttribute("year", String.valueOf(calendar.get(Calendar.YEAR)));

                Element time = doc.createElement("time");
                time.setAttribute("hour", String.valueOf(calendar.get(Calendar.HOUR)));
                time.setAttribute("minute", String.valueOf(calendar.get(Calendar.MINUTE)));

                arrive.appendChild(date);
                arrive.appendChild(time);
            }
        }

        // Creating XML file


        DOMImplementation domImp = doc.getImplementation();
        DocumentType docType = domImp.createDocumentType("doctype", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());
            transformer.setOutputProperty(OutputKeys.ENCODING, "us-ascii");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }

        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(this.writer);

        try {
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
