package edu.pdx.cs410J.torral2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

public class ListSearchedFlightsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_searched_flights_activity);


        ArrayList<Airline> airlineList = new ArrayList<>();
        ArrayList<Flight> listOfFlights = new ArrayList<>();


        String airlineNameText = getIntent().getExtras().getString("airlineName");
        String source = getIntent().getExtras().getString("source").toUpperCase();
        String destination = getIntent().getExtras().getString("destination").toUpperCase();


        File[] directory = getFilesDir().listFiles();
        if (directory == null) {
            Toast.makeText(this, "Could not find any airlines files.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (airlineNameText.trim().isEmpty()) { // User did not supply airline name, searching all airlines
            for (File file : directory) {
                if (file != null) {
                    try {
                        Airline airlineFromFile = new XmlParser(file).parse();

                        listOfFlights.addAll(airlineFromFile
                                .getFlights()
                                .stream()
                                .filter(f -> f.getSource().equals(source) && f.getDestination().equals(destination))
                                .collect(Collectors.toList()));

                        if (!listOfFlights.isEmpty()) {
                            airlineList.add(new Airline(airlineFromFile.getName()));
                            airlineList
                                    .stream()
                                    .filter(a -> a.getName().equals(airlineFromFile.getName()))
                                    .findFirst()
                                    .get()
                                    .getFlights()
                                    .addAll(
                                            listOfFlights
                                                    .stream()
                                                    .filter(f -> f.getSource().equals(source) && f.getDestination().equals(destination))
                                                    .collect(Collectors.toList()));


                            listOfFlights.clear();
                        }

                    } catch (ParserException pe) {
                        Toast.makeText(this, "Could not parse file: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    }
                }

            }

        } else { // User DID supply airline name, so we will only be searching flights connected to specified airline
            for (File file : directory) {
                if (file != null) {
                    try {
                        Airline airlineFromFile = new XmlParser(file).parse();
                        if (airlineFromFile.getName().equals(airlineNameText)) {

                            listOfFlights.addAll(airlineFromFile
                                    .getFlights()
                                    .stream()
                                    .filter(f -> f.getSource().equals(source) && f.getDestination().equals(destination))
                                    .collect(Collectors.toList()));

                            if (!listOfFlights.isEmpty()) {
                                airlineList.add(new Airline(airlineFromFile.getName()));
                                airlineList
                                        .stream()
                                        .filter(a -> a.getName().equals(airlineFromFile.getName()))
                                        .findFirst()
                                        .get()
                                        .getFlights()
                                        .addAll(
                                                listOfFlights
                                                        .stream()
                                                        .filter(f -> f.getSource().equals(source) && f.getDestination().equals(destination))
                                                        .collect(Collectors.toList()));


                                listOfFlights.clear();
                            }
                        }
                    } catch (ParserException pe) {
                        Toast.makeText(this, "Could not parse file.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

            }

        }

        LinearLayout linearLayout = findViewById(R.id.listSearchedFlightsLayout);
        ;
        TextView airlineNameView = new TextView(linearLayout.getContext());
        TextView flightNumberView = new TextView(linearLayout.getContext());
        TextView sourceView = new TextView(linearLayout.getContext());
        TextView departView = new TextView(linearLayout.getContext());
        TextView destinationView = new TextView(linearLayout.getContext());
        TextView arrivalView = new TextView(linearLayout.getContext());

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        for (Airline airline : airlineList) {

            addTextView("\t" + airline.getName(), true, linearLayout, 19f);

            for (Flight flight : airline.getFlights()) {
                addTextView("\t\tFlight Number: " + flight.getNumber(), true, linearLayout, 18f);
                addTextView("\t\tSource Airport: " + AirportNames.getName(flight.getSource()), false, linearLayout, 18f);
                addTextView("\t\tDeparture time: " + format.format(flight.getDeparture()), false, linearLayout, 18f);
                addTextView("\t\tDestination Airport: " + AirportNames.getName(flight.getDestination()), false, linearLayout, 18f);
                addTextView("\t\tArrival Time: " + format.format(flight.getArrival()), false, linearLayout, 18f);
                addTextView("\n", false, linearLayout, 18f);
            }
        }

    }

    public void addTextView(int text, boolean isBold, LinearLayout layout, float fontSize) {
        addTextView(text + "", isBold, layout, fontSize);
    }

    public void addTextView(String text, boolean isBold, LinearLayout layout, float fontSize) {


        TextView textView = new TextView(layout.getContext());
        textView.setTextSize(fontSize);

        textView.setText(text);
        if (isBold) {
            textView.setTypeface(null, Typeface.BOLD);
        }

        if (textView.getParent() != null) {
            ((ViewGroup) textView.getParent()).removeView(textView);
        }
        layout.addView(textView);
    }


}


