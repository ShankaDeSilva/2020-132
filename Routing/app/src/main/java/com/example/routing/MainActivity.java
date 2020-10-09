package com.example.routing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {

    private static clsConnectionPG con = new clsConnectionPG();
    CheckBox leopard, elephant, bird, bear;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    leopard = findViewById(R.id.cb_leopard);
    elephant = findViewById(R.id.cb_elephant);
    bird = findViewById(R.id.cb_bird);
    bear = findViewById(R.id.cb_bear);
    next = findViewById(R.id.btn_next);

    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });

    }

    public void onCheckBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case R.id.cb_leopard:
                if(checked)
                    Toast.makeText(getApplicationContext(),"Leopard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb_elephant:
                if (checked)
                    Toast.makeText(getApplicationContext(),"Elephant",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb_bird:
                if (checked)
                    Toast.makeText(getApplicationContext(),"Bird",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb_bear:
                if (checked)
                    Toast.makeText(getApplicationContext(),"Bear",Toast.LENGTH_SHORT).show();
        }
    }
}