package edu.pdx.cs410J.torral2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.pdx.cs410J.ParserException;

public class SearchFlightActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_flights_activity);
    }



    public void searchFlights(View view) {

        Airline airline;
        ArrayList<Flight> listOfFlights = new ArrayList<>();

        LinearLayout linearLayout = findViewById(R.id.searchFlightLayout);

        EditText airlineName = linearLayout.findViewById(R.id.editSearchFlightAirlineName);
        String airlineNameText = airlineName.getText().toString();

        EditText source = linearLayout.findViewById(R.id.editSearchFlightAirportSource);
        String sourceText = source.getText().toString();

        EditText destination = linearLayout.findViewById(R.id.editSearchFlightAirportDestination);
        String destinationText = destination.getText().toString();

        if(airlineNameText.trim().isEmpty()) { // User did not supply airline name, searching all airlines
            File fileDirectory = getFilesDir();
            for(File file : fileDirectory.listFiles()) {
                if(file != null) {
                    try {
                         listOfFlights.addAll(new XmlParser(file).parse()
                                .getFlights()
                                .stream()
                                .filter( f -> f.getSource().equals(sourceText) && f.getDestination().equals(destinationText))
                                .collect(Collectors.toList()));
                    } catch (ParserException pe) {
                        Toast.makeText(this, "Could not parse file: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    }
                }

            }
            for (Flight f : listOfFlights) {
                Toast.makeText(this, f.getFlightAsTextFileString(), Toast.LENGTH_SHORT).show();
            }

        } else { // User DID supply airline name, so we will only be searching flights connected to specified airline


        }


    }



}
