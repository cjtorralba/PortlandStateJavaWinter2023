package edu.pdx.cs410J.torral2;


import java.io.File;

public class AndroidAirlineStorage {


    /**
     *
      * @param directory
     * @param airlineName
     * @return
     */
    public static File parseFile(File directory, String airlineName) {

        File file = new File(directory, airlineName + ".xml");

        if(file.exists()) {
            return file;
        }
        return null;
    }
}
