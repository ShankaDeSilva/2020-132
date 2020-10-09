package com.example.routing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Trip_type extends AppCompatActivity {

    Button half, fullday, twoday, threeday, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_type);

        half = findViewById(R.id.btn_half);
        fullday = findViewById(R.id.btn_full);
        twoday = findViewById(R.id.btn_two);
        threeday = findViewById(R.id.btn_three);
        next = findViewById(R.id.btn_next);

        Spinner mySpinner = (Spinner) findViewById(R.id.spn_bangalow);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Trip_type.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bangalows));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


    }
}