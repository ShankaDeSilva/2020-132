package com.example.smartsafari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TestActivity0 extends AppCompatActivity{

    private CardView leopard, elephant, birds, bear;
    private TextView leopard1, elephant1, birds1, bear1;
    private RoutingRepository routingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test0);

        //defining cards
        leopard = (CardView) findViewById(R.id.cv_leopard);
//        leopard1 = (TextView) findViewById(R.id.txt_leopard);
//        elephant = (CardView) findViewById(R.id.cv_elephant);
//        elephant1 = (TextView) findViewById(R.id.txt_leopard);
//        birds = (CardView) findViewById(R.id.cv_bird);
//        birds1 = (TextView) findViewById(R.id.txt_leopard);
//        bear = (CardView) findViewById(R.id.cv_bear);
//        bear1 = (TextView) findViewById(R.id.txt_leopard);

        //Add click listeners to the cards
//        leopard.setOnClickListener(this);
//        leopard1.setOnClickListener(this);
//        elephant.setOnClickListener(this);
//        elephant1.setOnClickListener(this);
//        birds.setOnClickListener(this);
//        birds1.setOnClickListener(this);
//        bear.setOnClickListener(this);
//        bear1.setOnClickListener(this);

        leopard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestActivity0.this, TestActivity1.class));
            }
        });


    }


    }

