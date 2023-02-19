package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.Reader;

public class XmlParser implements AirlineParser {

    private final File file;


    public XmlParser(File file) { this.file = file; }

    @Override
    public AbstractAirline parse() throws ParserException {

        /*
         * Basic logic and referenced from  https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
         */

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;

        try {
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            docBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        doc = docBuilder.parse(this.file);

        doc.getDocumentElement().normalize();





        return null;
    }
}
