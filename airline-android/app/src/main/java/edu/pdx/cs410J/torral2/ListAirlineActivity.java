package edu.pdx.cs410J.torral2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ListAirlineActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_airlines_activity);

        TextView textView = findViewById(R.id.textAirlineDisplay);

        textView.setText("THIS IS SETTING THE TEXT ON LOAD");

        ArrayList<Airline> airlines = (ArrayList<Airline>) getIntent().getExtras().getParcelable("airlines");
        Toast.makeText(this, airlines.size(), Toast.LENGTH_SHORT).show();


    }

    public void displayAllFlights(View view) {





    }
}
