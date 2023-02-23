package edu.pdx.cs410J.torral2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 *  This class is used to convert a properly formatted text file containing airline objects to a xml file.
 *
 * @author Christian Torrlba
 * @version 1.0
 * @since 1.0
 */
public class Converter {


    static public void main(String[] args) {

        Airline airline = null;

        TextParser textParser = null;
        FileReader fileReader = null;

        XmlDumper xmlDumper = null;
        FileWriter fileWriter = null;

        // Processing command line arguments
        ArrayList<String> list = new ArrayList<>(List.of(args));

        try {

            // Incorrect number of command line arguements triggers usage statement
            if (list.size() != 2) {
                System.err.println("usage: java edu.pdx.cs410J.torral2.Converter textFile xmlFile");
                return;
            }

            String textFileName = list.get(0);
            String xmlFileName = list.get(1);

            File textFile = new File(textFileName);

            if (!textFile.exists()) {
                textFile.createNewFile();
            }

            fileReader = new FileReader(textFile);
            textParser = new TextParser(fileReader);
            airline = textParser.parse();

            fileWriter = new FileWriter(new File(xmlFileName));
            xmlDumper = new XmlDumper(fileWriter);
            xmlDumper.dump(airline);

        }catch(Exception e) {
            System.err.println("Could not convert file.");
            return;
        }

    }
}
