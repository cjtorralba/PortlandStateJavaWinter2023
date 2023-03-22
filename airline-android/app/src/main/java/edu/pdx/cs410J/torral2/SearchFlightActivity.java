package edu.pdx.cs410J.torral2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SearchFlightActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_flights_activity);
    }


    public void searchFlights(View view) {


        Intent intent = new Intent(SearchFlightActivity.this, ListSearchedFlightsActivity.class);


        LinearLayout linearLayout = findViewById(R.id.searchFlightLayout);

        EditText airlineName = linearLayout.findViewById(R.id.editSearchFlightAirlineName);
        String airlineNameText = airlineName.getText().toString();

        EditText source = linearLayout.findViewById(R.id.editSearchFlightAirportSource);
        String sourceText = source.getText().toString();

        EditText destination = linearLayout.findViewById(R.id.editSearchFlightAirportDestination);
        String destinationText = destination.getText().toString();

        intent.putExtra("airlineName", airlineNameText);
        intent.putExtra("source", sourceText);
        intent.putExtra("destination", destinationText);

        startActivity(intent);


    }
}
