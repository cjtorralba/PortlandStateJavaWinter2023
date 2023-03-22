package edu.pdx.cs410J.torral2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;

import edu.pdx.cs410J.ParserException;

public class SearchAirlineActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_airline_activity);
    }



    public void searchAirline(View view) {


        Intent intent = new Intent(SearchAirlineActivity.this, ListAirlineActivity.class);

        LinearLayout linearLayout = findViewById(R.id.linearSearchAirline);

        EditText airlineName = linearLayout.findViewById(R.id.editSearchAirlineName);
        String airlineNameString = airlineName.getText().toString();

        intent.putExtra("airlineString", airlineNameString);
        startActivity(intent);
    }





}
