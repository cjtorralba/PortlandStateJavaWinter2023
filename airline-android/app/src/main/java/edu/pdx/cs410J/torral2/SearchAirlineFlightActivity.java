package edu.pdx.cs410J.torral2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SearchAirlineFlightActivity extends AppCompatActivity{


    Activity mainMenu;
    Intent mainMenuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_flight_activity);
    }




}


