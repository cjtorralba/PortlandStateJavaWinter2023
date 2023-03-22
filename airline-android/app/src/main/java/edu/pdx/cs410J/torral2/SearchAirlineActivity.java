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

public class SearchAirlineActivity extends AppCompatActivity implements Parcelable {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_airline_activity);
    }



    public void searchAirline(View view) {


        Intent intent = new Intent(SearchAirlineActivity.this, ListAirlineActivity.class);
        Bundle bundle = new Bundle();




        Airline airline = null;
        ArrayList<Airline> airlineList = new ArrayList<>();    //(ArrayList<Airline>) getIntent().getSerializableExtra("airlines");
        ArrayList<Airline> airlineListForActivity;

        LinearLayout linearLayout = findViewById(R.id.linearSearchAirline);


        EditText airlineName = linearLayout.findViewById(R.id.editSearchAirlineName);
        String airlineNameString = airlineName.getText().toString();

        if(airlineNameString.trim().isEmpty()) { // We will list all airlines
            for(File file : getFilesDir().listFiles()) {
               if(file != null) {
                  try {
                          airlineList.add((new XmlParser(file)).parse());

                  } catch (ParserException pe) {
                      Toast.makeText(this, "Could not list all files.", Toast.LENGTH_SHORT).show();
                      return;
                  }
               }
            }
        } else { // User DID enter an airline name
            File fileDirectory = getFilesDir();
            for(File file : fileDirectory.listFiles()) {
                if (file != null) {
                    try {
                        Airline airlineFromFile = new XmlParser(file).parse();
                        if(airlineFromFile != null && airlineFromFile.equals(airlineNameString)) { // Had a match
                            airlineList.add(airlineFromFile);
                        }
                    } catch (ParserException pe) {
                        Toast.makeText(this, "Could not locate airline/", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        }
        Toast.makeText(this, "Starting new activity.", Toast.LENGTH_SHORT).show();
        intent.putParcelableArrayListExtra("airlines", airlineList);
        startActivity(intent);
    }





}
