package edu.pdx.cs410J.torral2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addFlight(View view) {
        //Flight flight = new Flight(123, "PDX", "10/23/2001", "12:54 AM", "LAX", "10/24/2001", "4:14 PM");
        //Toast.makeText(this, flight.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, NewFlightActivity.class);
        startActivity(intent);
    }

    public void searchFlight(View view) {

        Intent intent = new Intent(MainActivity.this, SearchFlightActivity.class);
        startActivity(intent);
    }

    public void searchAirline(View view) {

        Intent intent = new Intent(MainActivity.this, SearchAirlineActivity.class);
        startActivity(intent);

    }

    public void readMe(View view) {
        Intent intent = new Intent(MainActivity.this, ReadMeActivity.class);
        startActivity(intent);
    }
}