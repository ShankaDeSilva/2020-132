package com.isprid.smartsafari.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isprid.smartsafari.R;
import com.isprid.smartsafari.util.Constant;
import com.isprid.smartsafari.util.MySingleton;
import com.isprid.smartsafari.util.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    final String TAG = FormActivity.class.getSimpleName();
    EditText loc1;
    EditText animalname;
    EditText animalact;
    Button button1;
    DatabaseReference databaseReference;
    String latitude;
    String longitude;
    private PrefUtils pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Report Activity");

        pref = new PrefUtils(FormActivity.this);

        loc1 = findViewById(R.id.loc1);
        animalname = findViewById(R.id.animalname);
        animalact = findViewById(R.id.animalact);
        button1 = findViewById(R.id.button1);

        if (getIntent().hasExtra(Constant.INTENT_EXTRA_LATITUDE) && getIntent().hasExtra(Constant.INTENT_EXTRA_LONGITUDE)) {
            latitude = getIntent().getExtras().getString(Constant.INTENT_EXTRA_LATITUDE);
            longitude = getIntent().getExtras().getString(Constant.INTENT_EXTRA_LONGITUDE);

            loc1.setText(latitude + "," + longitude);
            loc1.setEnabled(false);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Activities");

        //Save data to firebase database when button click
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation();
            }
        });
    }

    public void addLocation() {
        String AnimalName = animalname.getText().toString();
        String AnimalActivity = animalact.getText().toString();
        final String userId = pref.getPrefsValue(Constant.UUID);


        if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(AnimalName) && !TextUtils.isEmpty(AnimalActivity)) {
            String id = databaseReference.push().getKey();

            Data dt = new Data(id, userId, latitude, longitude, AnimalName, AnimalActivity);
            databaseReference.child(id).setValue(dt);

            sendNotification(dt);

            loc1.setText("");
            animalact.setText("");
            animalname.setText("");

        } else {
            Toast.makeText(FormActivity.this, "Please fill the details!", Toast.LENGTH_LONG).show();
        }

    }

    private void sendNotification(Data data) {
        JSONObject notif = new JSONObject();
        JSONObject jsonObjects = new JSONObject();
        try {
            notif.put("title", data.getAnimalName());
            notif.put("body", data.getAnimalActivity());
            jsonObjects.put("data", notif);
            jsonObjects.put("to", "/topics/all");

        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Constant.FCM_API, jsonObjects,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        Toast.makeText(FormActivity.this, "Notification Sent", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", Constant.serverKey);
                params.put("Content-Type", Constant.contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }
}