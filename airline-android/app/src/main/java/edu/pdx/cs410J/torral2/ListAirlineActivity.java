package edu.pdx.cs410J.torral2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;


public class ListAirlineActivity extends AppCompatActivity implements Serializable {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_airlines_activity);


        String airlineNameString = getIntent().getExtras().getString("airlineString");
        ArrayList<Airline> airlineList = new ArrayList<>();

        if (airlineNameString.equalsIgnoreCase("banana")) {
            LinearLayout linearLayout = findViewById(R.id.listAirlineLayout);
            ImageView image = new ImageView(linearLayout.getContext());
            Toast.makeText(this, "You found the golden banana!", Toast.LENGTH_SHORT).show();
            image.setBackgroundResource(R.drawable.gold_banana);
            linearLayout.addView(image);
        }


        // Getting file directory
        File[] directory = getFilesDir().listFiles();
        if (directory == null) {
            Toast.makeText(this, "Issue finding files.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (airlineNameString.trim().isEmpty()) { // We will list all airlines
            for (File file : directory) {
                if (file != null) {
                    try {
                        airlineList.add((new XmlParser(file)).parse());

                    } catch (ParserException pe) {
                        Toast.makeText(this, "Could not list all files.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        } else { // User DID enter an airline name
            for (File file : directory) {
                if (file != null) {
                    try {
                        Airline airlineFromFile = new XmlParser(file).parse();
                        if (airlineFromFile != null && airlineFromFile.getName().equals(airlineNameString)) { // Had a match
                            airlineList.add(airlineFromFile);
                        }
                    } catch (ParserException pe) {
                        Toast.makeText(this, "Could not locate airline.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        }

        LinearLayout linearLayout = findViewById(R.id.listAirlineLayout);
        TextView airlineNameView = new TextView(linearLayout.getContext());
        TextView flightNumberView = new TextView(linearLayout.getContext());
        TextView sourceView = new TextView(linearLayout.getContext());
        TextView departView = new TextView(linearLayout.getContext());
        TextView destinationView = new TextView(linearLayout.getContext());
        TextView arrivalView = new TextView(linearLayout.getContext());

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm aa");
        for (Airline airline : airlineList) {

            addTextView("\t" + airline.getName(), true, linearLayout, 19f);

            for (Flight flight : airline.getFlights()) {
                int flightDuration = (int) Math.abs(TimeUnit.MILLISECONDS.toMinutes(flight.getArrival().toInstant().toEpochMilli() - flight.getDeparture().toInstant().toEpochMilli()));
                addTextView("\t\tFlight Number: " + flight.getNumber(), true, linearLayout, 18f);
                addTextView("\t\tSource Airport: " + AirportNames.getName(flight.getSource()), false, linearLayout, 18f);
                addTextView("\t\tDeparture time: " + format.format(flight.getDeparture()), false, linearLayout, 18f);
                addTextView("\t\tDestination Airport: " + AirportNames.getName(flight.getDestination()), false, linearLayout, 18f);
                addTextView("\t\tArrival Time: " + format.format(flight.getArrival()), false, linearLayout, 18f);
                addTextView("\t\tFlight Duration (Minutes): " + flightDuration, false, linearLayout, 18f);
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
