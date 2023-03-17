package edu.pdx.cs410J.torral2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewFlightActivity extends AppCompatActivity {


    Activity mainMenu;
    Intent mainMenuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_flight_activity);
    }

    public void submitFlight() {




    }

}
