package com.example.smartsafari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView leopard, elephant, birds, bear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //defining cards
        leopard = (CardView) findViewById(R.id.cv_leopard);
        elephant = (CardView) findViewById(R.id.cv_elephant);
        birds = (CardView) findViewById(R.id.cv_bird);
        bear = (CardView) findViewById(R.id.cv_bear);

        //Add click listeners to the cards
        leopard.setOnClickListener(this);
        elephant.setOnClickListener(this);
        birds.setOnClickListener(this);
        bear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        
    }
}