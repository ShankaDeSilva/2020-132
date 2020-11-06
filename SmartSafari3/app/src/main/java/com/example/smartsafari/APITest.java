package com.example.smartsafari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APITest extends AppCompatActivity {

    private EditText startLat, startLon, endLat, endLon , mRequest;

    private CardView leopard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_test);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btnRequest = findViewById(R.id.btnRequest);
        startLat = findViewById(R.id.etSlat);
        startLon = findViewById(R.id.etSlon);
        endLat = findViewById(R.id.etDlat);
        endLon = findViewById(R.id.etDlon);
        mRequest = findViewById(R.id.etReq);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://10.0.2.2:5002/routes";

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("x1", startLat.getText().toString());
                    postparams.put("y1", startLon.getText().toString());
                    postparams.put("x2", endLat.getText().toString());
                    postparams.put("y2", endLon.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(jsonObjectRequest);
            }
        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String req_url = url + "/" + mRequest.getText();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, req_url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAGG", response.toString());
                        try {
                            JSONArray data = response.getJSONArray("data");
                            if (data.length() == 0) {
                                Toast.makeText(getApplicationContext(), "Id doesn't exist", Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject object = data.getJSONObject(0);
                                Toast.makeText(getApplicationContext(), "" + object.getString("FirstName"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                queue.add(jsonObjectRequest);
            }
        });

    }

    //GET method example
//    public void vollyGet(){
//        String url = "https://reqres.in/api/users?page=2"; //get request url
//        List<String> jsonResponses = new ArrayList<>();
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray jsonArray = response.getJSONArray("data");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String email = jsonObject.getString("email");
//
//                        jsonResponses.add(email);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);
//    }

    //POST request example
//    public void volleyPost() {
//        String postUrl = "https://reqres.in/api/users";
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        JSONObject postData = new JSONObject();
//        try {
//            postData.put("name", "Jonathan");
//            postData.put("name","Software Engineer");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                System.out.println(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);
//    }
}