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

        if(list.size() != 2) {
            System.err.println("usage: java edu.pdx.cd410J.torral2.Converter textFile xmlFile");
            return;
        }

        String textFileName = list.get(0);
        String xmlFileName = list.get(1);

        File textFile = new File(textFileName);

        if(!textFile.exists()) {
            try {
                textFile.createNewFile();
            } catch(IOException e) {
                System.err.println("File does not exist and could not be created.");
                return;
            }
        }


        try {
            fileReader = new FileReader(textFile);
        } catch (FileNotFoundException e) {
            System.err.println("File could not be located.");
            return;
        }

        textParser = new TextParser(fileReader);

        try {
            airline = textParser.parse();
        } catch (ParserException e) {
            System.err.println("Could not parse the text file.");
            return;
        }

        System.out.println(airline.getName());

        try {
            fileWriter = new FileWriter(new File(xmlFileName));
        } catch (IOException e) {
            System.err.println("Could not open file to write to.");
            return;
        }

        xmlDumper = new XmlDumper(fileWriter);

        try {
            xmlDumper.dump(airline);
        } catch (IOException e) {
            System.err.println("Could not write contents to xml file.");
            return;
        }
    }



}
