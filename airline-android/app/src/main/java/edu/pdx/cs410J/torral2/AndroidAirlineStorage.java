package edu.pdx.cs410J.torral2;


import android.widget.Toast;

import java.io.File;

public class AndroidAirlineStorage {


    /**
     *
      * @param directory
     * @param airlineName
     * @return
     */
    public static File parseFile(File directory, String airlineName) {

        directory.mkdir();
        File file = new File(directory, airlineName + ".xml");

        if(file.exists()) {
            return file;
        }
        return null;
    }

    public static void listFiles(File directory) {
        System.out.println(directory.getAbsoluteFile());

    }
}
