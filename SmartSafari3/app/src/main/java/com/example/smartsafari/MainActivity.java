package com.example.smartsafari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView leopard, elephant, birds, bear;
    private TextView leopard1, elephant1, birds1, bear1;
    private RoutingRepository routingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //defining cards
        leopard = (CardView) findViewById(R.id.cv_leopard);
        leopard1 = (TextView) findViewById(R.id.txt_leopard);
        elephant = (CardView) findViewById(R.id.cv_elephant);
        elephant1 = (TextView) findViewById(R.id.txt_leopard);
        birds = (CardView) findViewById(R.id.cv_bird);
        birds1 = (TextView) findViewById(R.id.txt_leopard);
        bear = (CardView) findViewById(R.id.cv_bear);
        bear1 = (TextView) findViewById(R.id.txt_leopard);

        //Add click listeners to the cards
        leopard.setOnClickListener(this);
        leopard1.setOnClickListener(this);
        elephant.setOnClickListener(this);
        elephant1.setOnClickListener(this);
        birds.setOnClickListener(this);
        birds1.setOnClickListener(this);
        bear.setOnClickListener(this);
        bear1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        Routing r = new Routing(
//                leopard1.getText().toString(),
//                elephant1.getText().toString(),
//                birds1.getText().toString(),
//                bear1.getText().toString(),
//                leopard1.getText().charAt(i)
//        );

//        switch(CardView.OnClickListener()) {
//            case leopard:
//                routingRepository.getRoutingService().createRoute().enqueue(new Callback<Routing>() {
//                    @Override
//                    public void onResponse(Call<Routing> call, Response<Routing> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Routing> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(), "Error Create Route: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//        }
    }
}