package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Converter {


    /*
     * Usage: java edu.pdx.cs410J.<login-id>.Converter textFile xmlFile
     */


    static public void main(String[] args) {

        Airline airline = null;

        TextParser textParser = null;
        FileReader fileReader = null;

        XmlDumper xmlDumper = null;
        FileWriter fileWriter = null;

        ArrayList<String> list = new ArrayList<>(List.of(args));
        try {
            if (list.size() != 2) {
                System.err.println("usage: java edu.pdx.cd410J.torral2.Converter textFile xmlFile");
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

            System.out.println(airline.getName());

            fileWriter = new FileWriter(new File(xmlFileName));
            System.err.println("Could not open file to write to.");

            xmlDumper = new XmlDumper(fileWriter);

            xmlDumper.dump(airline);
        }catch(Exception e) {
            System.err.println("Could not convert file.");
            return;
        }

    }
}
