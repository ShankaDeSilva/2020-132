package com.example.smartsafari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private Spinner animalSpinner, destinationSpinner;
    private Button generateRoute;
    private TextView textView;

    private GoogleMap mMap;
    private Marker startMarker, destinationMarker;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Double latitude, longitude;
    private LatLng start;

    JsonPlaceHolderApi jsonPlaceHolderApi;

    private static final String TAG = "MainActivity";
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        animalSpinner = findViewById(R.id.spnAnimal);
        destinationSpinner = findViewById(R.id.spnDestination);

        //Runtime Permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        animalSpinnerSet();
        destinationSpinnerSet();
        getLocation();
//        postData();
        getData();
        createPost();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Gson gson = new Gson();
    }

    //Get Request Work - Retrofit(testapp.py)
//    public void getRoutes(View view) {
//        Call<List<routes>> call = apiInterface.getRoutes();
//        call.enqueue(new Callback<List<routes>>() {
//            @Override
//            public void onResponse(Call<List<routes>> call, Response<List<routes>> response) {
//                Log.e(TAG, "OnResponse: " + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<routes>> call, Throwable t) {
//                Log.e(TAG, "OnFailure: " + t.getLocalizedMessage());
//            }
//        });
//    }

    //Post Request Work - Retrofit(server2.py)
    public void getRoutes(View view) {
        Locations locations = new Locations(6.27829, 81.40232, 6.31331, 81.46917);

        Call<Locations> locationsPostCall = apiInterface.postLocations(locations);
        locationsPostCall.enqueue(new Callback<Locations>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable t) {
                Log.e(TAG, "OnFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void createPost() {

    }

    private void getData() {

    }

//    private void postData() {
//        final RequestQueue queue = Volley.newRequestQueue(this);
//
//        generateRoute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                JSONObject postParams = new JSONObject();
//                try {
//                    postParams.put("start latitude", latitude.getClass());
//                    postParams.put("start longitude", longitude.toString());
//                    postParams.put("destination latitude", latitude);
//                    postParams.put("destination longitude", longitude);
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, postParams,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//            }
//        });
//    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
//        mapFragment.getMapAsync(this);

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,6, MainActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void destinationSpinnerSet() {
        List<String> bangalows = new ArrayList<>();
        bangalows.add(0, "Choose Bungalow");
        bangalows.add("Old Buthawa Wild Life Department Bungalow");
        bangalows.add("New Buthawa Wild Life Department Bungalow");
        bangalows.add("Thalgasmankada Bungalow");
        bangalows.add("Warahana Wildlife Circuit Bungalow");
        bangalows.add("Ondaatje Bungalow");
        bangalows.add("Heenwewa Bungalow");

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,bangalows);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationSpinner.setAdapter(arrayAdapter);

        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (destinationSpinner.getItemAtPosition(i).equals("Choose Bungalow")){
                    //do nothing
                }
                else{
                    //on selecting a spinner item
                    String bungalow = destinationSpinner.getItemAtPosition(i).toString();

                    //show selected spinner item
                    Toast.makeText(destinationSpinner.getContext(), "Selected: " +bungalow, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void animalSpinnerSet() {
        List<String> animals = new ArrayList<>();
        animals.add(0, "Choose Animal");
        animals.add("Elephants");
        animals.add("Leopards");
        animals.add("Birds");
        animals.add("Bears");

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, animals);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalSpinner.setAdapter(arrayAdapter);

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
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        start = new LatLng(location.getLatitude(),location.getLongitude());
        Toast.makeText(this, "" +location.getLatitude()+" , " +location.getLongitude(),Toast.LENGTH_LONG).show();
        try {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-33.852, 151.211);
//        LatLng start = new LatLng(latitude,longitude);
//        LatLng start = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(start));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
    }
}