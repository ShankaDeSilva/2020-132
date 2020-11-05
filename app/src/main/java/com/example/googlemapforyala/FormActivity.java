package com.example.googlemapforyala;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity
{
    Toolbar toolbar;
    EditText loc1;
    EditText animalname;
    EditText animalact;
    Button button1;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        databaseReference = FirebaseDatabase.getInstance().getReference("FormData");


        loc1 = findViewById(R.id.loc1);
        animalname = findViewById(R.id.animalname);
        animalact = findViewById(R.id.animalact);
        button1 = findViewById(R.id.button1);


    //Save data to firebase database when button click
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Addlocation();
            }
        });

    }
    public  void Addlocation()
    {
        String location = loc1.getText().toString();
        String AnimalName = animalname.getText().toString();
        String AnimalActivity = animalact.getText().toString();

        if(!TextUtils.isEmpty(location) && !TextUtils.isEmpty(AnimalName) && !TextUtils.isEmpty(AnimalActivity))
        {
            String id = databaseReference.push().getKey();

            Data dt = new Data(location,AnimalName,AnimalActivity);
            databaseReference.child(id).setValue(dt);

            loc1.setText("");
            animalact.setText("");
            animalact.setText("");

        }
        else
        {
            Toast.makeText(FormActivity.this,"Please fill the details!",Toast.LENGTH_LONG).show();
        }

    }

}

