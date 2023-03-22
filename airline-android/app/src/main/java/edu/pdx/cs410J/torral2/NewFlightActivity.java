package edu.pdx.cs410J.torral2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.pdx.cs410J.ParserException;

public class NewFlightActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_flight_activity);
    }

    public void submitFlight(View view) {


        Airline airline;
        Flight flight;

        LinearLayout linearLayout = findViewById(R.id.flightInformationLayout);


        EditText airlineName = linearLayout.findViewById(R.id.editAirlineName);
        String airlineNameText = airlineName.getText().toString();

        // Flight number
        EditText flightNumber = linearLayout.findViewById(R.id.editFlightNumber);
        int flightNumberInt = -1;
        try {
            flightNumberInt = Integer.parseInt(flightNumber.getText().toString());
        } catch (NumberFormatException nfe) {
            // DO SOMETHING HERE
        }

        // Source Airport Code
        EditText source = linearLayout.findViewById(R.id.editSource);
        String sourceText = source.getText().toString().toUpperCase();

        // Departure Date
        EditText departDate = linearLayout.findViewById(R.id.editDepartDate);
        String departDateText = departDate.getText().toString();

        // Departure Time
        EditText departTime = linearLayout.findViewById(R.id.editDepartTime);
        String departTimeText = departTime.getText().toString();

        // Adding space between AM/PM for time format
        departTimeText = departTimeText.trim();
        if(departTimeText.length() < 6 || departTimeText.length() > 7) {
            Toast.makeText(this, "Invalid departure time.", Toast.LENGTH_LONG).show();
            return;
        }
        departTimeText = departTimeText.substring(0, departTimeText.length() - 2) + " " + departTimeText.substring(departTimeText.length() - 2);

        // Destination Airport Code
        EditText destination = linearLayout.findViewById(R.id.editDestination);
        String destinationText = destination.getText().toString().toUpperCase();

        // Arrival Date
        EditText arrivalDate = linearLayout.findViewById(R.id.editArriveDate);
        String arrivalDateText = arrivalDate.getText().toString();

        // Arrival Time
        EditText arrivalTime = linearLayout.findViewById(R.id.editArriveTime);
        String arrivalTimeText = arrivalTime.getText().toString();

        // Adding space between AM/PM time format
        arrivalTimeText = arrivalTimeText.trim();
        if(arrivalTimeText.length() < 6 || arrivalTimeText.length() > 7) {
            Toast.makeText(this, "Invalid arrival time.", Toast.LENGTH_LONG).show();
            return;
        }
        arrivalTimeText = arrivalTimeText.substring(0, arrivalTimeText.length() - 2) + " " + arrivalTimeText.substring(arrivalTimeText.length() - 2);


        // Ensuring airline name is not an empty string
        if ( airlineNameText.isEmpty()) {
            Toast.makeText(this, "Cannot have empty airline name.", Toast.LENGTH_SHORT).show();
            return;
        }


        // Creating a flight object, catching any malformed user input.
        try {
            flight = new Flight(flightNumberInt, sourceText, departDateText, departTimeText, destinationText, arrivalDateText, arrivalTimeText);
        } catch (IllegalArgumentException iae) {
            Toast.makeText(this, iae.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }


        File airlineFile = AndroidAirlineStorage.parseFile(getFilesDir(), airlineNameText);
        Airline tempAirline;

        if(airlineFile != null) { // File DID exist so we can add flight to airline acquired from file then write back to file
            try {
                tempAirline = new XmlParser(airlineFile).parse();
                tempAirline.addFlight(flight);
                new XmlDumper(new FileWriter(airlineFile)).dump(tempAirline);

                // Clearing text from edit fields
                airlineName.getText().clear();
                flightNumber.getText().clear();
                source.getText().clear();
                departDate.getText().clear();
                departTime.getText().clear();
                destination.getText().clear();
                arrivalDate.getText().clear();
                arrivalTime.getText().clear();

                Toast.makeText(this, "Added your Flight!", Toast.LENGTH_SHORT).show();

            } catch (IOException | ParserException e) {
                Toast.makeText(this, "Could not add Airline to file.", Toast.LENGTH_SHORT).show();
            }

        } else { // File does NOT exist, so we are creating a new XML file which is the name of the airline, ex: "My_Airline.xml"
            try {
                airline = new Airline(airlineNameText, List.of(flight));
                new XmlDumper(new FileWriter(new File(getFilesDir(), airlineNameText + ".xml"))).dump(airline);

                // Clearing text from edit fields
                airlineName.getText().clear();
                flightNumber.getText().clear();
                source.getText().clear();
                departDate.getText().clear();
                departTime.getText().clear();
                destination.getText().clear();
                arrivalDate.getText().clear();
                arrivalTime.getText().clear();

                Toast.makeText(this, "Added your Flight!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Could not add Airline to file.", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

}
