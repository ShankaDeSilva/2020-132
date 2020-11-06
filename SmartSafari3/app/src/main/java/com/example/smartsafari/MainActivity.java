package com.example.smartsafari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Spinner animalSpinner, destinationSpinner;
    private Button generateRoute;
    private static final int REQUEST_LOCATION = 1001;

    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    ArrayList markerPoints = new ArrayList();
    private FusedLocationProviderClient mFusedLocationClient;
    public LatLng latLng;

    Double bLatitude, bLongitude;


    private String bungalow;

    LocationManager locationManager;
    Double latitude, longitude;
    Location mLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // GPS location can be null if GPS is switched off
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                Toast.makeText(MainActivity.this, "lat " + location.getLatitude() + "\nlong " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

        //Add permission
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        //Get current Location
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        longitude = location.getLongitude();
//        latitude = location.getLatitude();

        animalSpinner = findViewById(R.id.spinner);
        destinationSpinner = findViewById(R.id.spinner2);


        List<String> animals = new ArrayList<>();
        animals.add(0, "Choose Animal");
        animals.add("Elephants");
        animals.add("Leopards");
        animals.add("Birds");
        animals.add("Bears");

//        List<Bungalows> bungalowsList = new ArrayList<>();
//        Bungalows bungalow0 = new Bungalows("Choose Bungalow",null, null);
//        Bungalows bungalow1 = new Bungalows("Old Buthawa Wild Life Department Bungalow",6.315983, 81.48276);
//        Bungalows bungalow2 = new Bungalows("New Buthawa Wild Life Department Bungalow", 6.314826, 81.48285);
//        Bungalows bungalow3 = new Bungalows("Thalgasmankada Bungalow", 6.398334, 81.481839);
//        Bungalows bungalow4 = new Bungalows("Warahana Wildlife Circuit Bungalow", 6.413099, 81.458744);
//        Bungalows bungalow5 = new Bungalows("Ondaatje Bungalow", 6.322203, 81.384147);
//        Bungalows bungalow6 = new Bungalows("Heenwewa Bungalow", 6.344901, 81.430956);

        List<String> bangalows = new ArrayList<>();
        bangalows.add(0, "Choose Bungalow");
        bangalows.add("Old Buthawa Wild Life Department Bungalow");
        bangalows.add("New Buthawa Wild Life Department Bungalow");
        bangalows.add("Thalgasmankada Bungalow");
        bangalows.add("Warahana Wildlife Circuit Bungalow");
        bangalows.add("Ondaatje Bungalow");
        bangalows.add("Heenwewa Bungalow");

        ArrayAdapter<String> arrayAdapter;
        ArrayAdapter<String> arrayAdapter1;
//        ArrayAdapter<Bungalows> adapter = new ArrayAdapter<Bungalows>(this, android.R.layout.simple_spinner_item, bungalowsList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, animals);
        arrayAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,bangalows);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        animalSpinner.setAdapter(arrayAdapter);
        destinationSpinner.setAdapter(arrayAdapter1);
//        destinationSpinner.setAdapter(adapter);

        animalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (animalSpinner.getItemAtPosition(i).equals("Choose Animal")){
                    //do nothing
                }
                else{
                    //on selecting a spinner item
                    String animal = animalSpinner.getItemAtPosition(i).toString();
                    
                    //show selected spinner item
                    Toast.makeText(animalSpinner.getContext(), "Selected: " +animal, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (destinationSpinner.getItemAtPosition(i).equals("Choose Bungalow")) {
                    //do nothing
                }else {
                    //on selecting a spinner item
                    bungalow = destinationSpinner.getItemAtPosition(i).toString();

                    //show selected spinner item
                    Toast.makeText(destinationSpinner.getContext(), "Selected: " +bungalow, Toast.LENGTH_SHORT).show();

                    if (bungalow == "Old Buthawa Wild Life Department Bungalow") {
                        bLatitude = 6.315983;
                        bLongitude = 81.48276;
                    }else if (bungalow == "New Buthawa Wild Life Department Bungalow") {
                        bLatitude = 6.314826;
                        bLongitude = 81.48285;
                        //return;
                    }else if (bungalow == "Thalgasmankada Bungalow") {
                        bLatitude = 6.398334;
                        bLongitude = 81.481839;
                       // return;
                    }else if (bungalow == "Warahana Wildlife Circuit Bungalow") {
                        bLatitude = 6.413099;
                        bLongitude = 81.458744;
                       // return;
                    }else if (bungalow == "Ondaatje Bungalow") {
                        bLatitude = 6.322203;
                        bLongitude = 81.384147;
                       //return;
                    }else if (bungalow == "Heenwewa Bungalow") {
                        bLatitude = 6.344901;
                        bLongitude = 81.430956;
                       // return;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://10.0.2.2:5002/route";

        generateRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("start latitude", latitude.getClass());
                    postparams.put("start longitude", longitude.toString());
                    postparams.put("destination latitude", bungalow);
                    postparams.put("destination longitude", bungalow);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postparams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng start = new LatLng(5.9668455, 80.6127441);
        mMap.addMarker(new MarkerOptions().position(latLng).title("You are Here").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));



    }
}