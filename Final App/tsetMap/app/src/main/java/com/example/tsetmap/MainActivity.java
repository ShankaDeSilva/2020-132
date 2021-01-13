package com.example.tsetmap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsetmap.animalsJSON.ElephantLatLng;
import com.example.tsetmap.animalsJSON.bLatLng;
import com.example.tsetmap.animalsJSON.eLatLng;
import com.example.tsetmap.animalsJSON.leLatLng;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnMapClickListener, LocationListener, GoogleMap.OnMarkerClickListener {

    //Get current location
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 11.5f;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    LatLng curLoc;

    Double latitude, longitude;
    SupportMapFragment mapFragment;
    GoogleMap mMap;
//    TextView textView;
    //    GoogleApiClient mGoogleApiClient;
//    Button getRoute;
    private Spinner animalSpinner, destinationSpinner, startSpinner;
    LocationManager locationManager;
    FusedLocationProviderClient client;
    Double curLatitude, curLongitude;
    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation;

    Button btnMapChange;
    TextView txtDistance;

    //bungalow LatLng
    Double bLatitude, bLongitude;

    //Elephants LatLng
    ElephantLatLng elephantLatLng;
    Double eLatitude, eLongitude;
    //Leopard LatLng
    Double leoLatitude, leoLongitude;
    //Birds LatLng
    Double birdLatitude, birdLongitude;
    //Bears LatLng
    Double bearLatitude, bearLongitude;

    private static final String TAG = "MainActivity";
    ApiInterface apiInterface;

    Response<LocDir> dResponse;
    Double dLongitude, dLatitude, dLongitude1, dLatitude1;
    Double sLatitude, sLongitude;
    Double distance = 0.0;
    ArrayList arrayList1;
    public Integer size;
    Type[] types;
    PolylineOptions polylineOptions;
    ArrayList<Double> lat = new ArrayList<>();
    Double travelTime;

    Marker marker;
    private int height = 70;
    private int width = 70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        textView = findViewById(R.id.editTextTextPersonName);
//        getRoute = findViewById(R.id.btn_generate);
        animalSpinner = findViewById(R.id.spnAnimal);
        destinationSpinner = findViewById(R.id.spnDestination);
        startSpinner = findViewById(R.id.spnStart);
        btnMapChange = findViewById(R.id.btnMapChange);
        txtDistance = findViewById(R.id.txtDistance);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);

//        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
//        btnGetLocation = findViewById(R.id.btnGetLocation);
//        btnGetLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    OnGPS();
//                } else {
//                    getLocation();
//                }
//            }
//        });

                //Initialize fused location
//        client = LocationServices.getFusedLocationProviderClient(this);

        //Check permission
//        if (ActivityCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When permission granted
            //Call method
//            getCurrentLocation();
//            Task<Location> task = client.getLastLocation();
//            task.addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    //When success
//                    if (location != null){
//                        curLatitude = location.getLatitude();
//                        curLongitude = location.getLongitude();
//                        textView.setText(curLatitude.toString()+curLongitude.toString());
//                    }else {
//                        //When permission denied
//                        //Request Permission
//                        ActivityCompat.requestPermissions(MainActivity.this,
//                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
//                    }
//                }
//            });
//        }

        latitude = -33.852;
        longitude = Double.valueOf("151.211");

        startSpinnerSet();
        animalSpinnerSet();
        destinationSpinnerSet();
        getLocationPermission();

    }

//    private void OnGPS() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//            }
//        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//
//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
//        }else {
//            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (locationGPS != null) {
//                curLatitude = locationGPS.getLatitude();
//                curLongitude = locationGPS.getLongitude();
//                Toast.makeText(this,""+curLatitude.toString() + "," + curLongitude.toString(),Toast.LENGTH_LONG).show();
//            }else {
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void getCurrentLocation() {
//        if (ActivityCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            //Initialize task location
//            Task<Location> task = client.getLastLocation();
//            task.addOnSuccessListener(new OnSuccessListener<Location>() {
//                @SuppressLint("SetTextI18n")
//                @Override
//                public void onSuccess(Location location) {
//                    //When success
//                    if (location != null) {
//                        curLatitude = location.getLatitude();
//                        curLongitude = location.getLongitude();
//                        textView.setText(curLatitude.toString());
//                    }
//                }
//            });
//        }
//    }

    private void startSpinnerSet() {
        List<String> gates = new ArrayList<>();
        gates.add(0, "Choose Start Point");
        gates.add("Palatupana Entrance");
        gates.add("Katagamuwa Entrance");

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gates);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(arrayAdapter);

        startSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (startSpinner.getItemAtPosition(i).equals("Choose Start Point")) {
                    //do nothing
                } else if (startSpinner.getItemAtPosition(i).equals("Palatupana Entrance")) {
                    sLatitude = 6.28146;
                    sLongitude = 81.41263;
                    LatLng point = new LatLng(sLatitude, sLongitude);
                    mMap.addMarker(new MarkerOptions().position(point));
                } else if (startSpinner.getItemAtPosition(i).equals("Katagamuwa Entrance")) {
                    sLatitude = 6.38832;
                    sLongitude = 81.41317;
                    LatLng point = new LatLng(sLatitude, sLongitude);
                    mMap.addMarker(new MarkerOptions().position(point));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void destinationSpinnerSet() {
        List<String> bangalows = new ArrayList<>();
        bangalows.add(0, "Choose Destination");
        bangalows.add("Old Buthawa Wild Life Department Bungalow");
        bangalows.add("New Buthawa Wild Life Department Bungalow");
        bangalows.add("Thalgasmankada Bungalow");
        bangalows.add("Warahana Wildlife Circuit Bungalow");
        bangalows.add("Ondaatje Bungalow");
        bangalows.add("Heenwewa Bungalow");
        bangalows.add("Mahaseelawa Bungalow");
        bangalows.add("Yala Bungalow");
        bangalows.add("Sithulpawwa Buddhist Monastery");
        bangalows.add("Patanangala Beach");
        bangalows.add("Akasa Chaithya");

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bangalows);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationSpinner.setAdapter(arrayAdapter);

        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (destinationSpinner.getItemAtPosition(i).equals("Choose Bungalow")) {
                    //do nothing
                }else if (destinationSpinner.getItemAtPosition(i).equals("Old Buthawa Wild Life Department Bungalow")) {
                    bLatitude = 6.31598;
                    bLongitude = 81.48276;
                    LatLng point = new LatLng(bLatitude,bLongitude);

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Old Buthawa Wild Life Department Bungalow").icon(bungalowIcon));
                } else if (destinationSpinner.getItemAtPosition(i).equals("New Buthawa Wild Life Department Bungalow")) {
                    bLatitude = 6.31482;
                    bLongitude = 81.48285;
                    LatLng point = new LatLng(bLatitude,bLongitude);

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("New Buthawa Wild Life Department Bungalow").icon(bungalowIcon));
                } else if (destinationSpinner.getItemAtPosition(i).equals("Thalgasmankada Bungalow")) {
                    bLatitude = 6.39833;
                    bLongitude = 81.48183;
                    LatLng point = new LatLng(bLatitude,bLongitude);

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Thalgasmankada Bungalow").icon(bungalowIcon));
                } else if (destinationSpinner.getItemAtPosition(i).equals("Warahana Wildlife Circuit Bungalow")) {
                    bLatitude = 6.41309;
                    bLongitude = 81.45874;
                    LatLng point = new LatLng(bLatitude,bLongitude);

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Warahana Wildlife Circuit Bungalow").icon(bungalowIcon));
                } else if (destinationSpinner.getItemAtPosition(i).equals("Ondaatje Bungalow")) {
                    bLatitude = 6.3222;
                    bLongitude = 81.38414;
                    LatLng point = new LatLng(bLatitude,bLongitude);


                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Ondaatje Bungalow").icon(bungalowIcon));
                } else if (destinationSpinner.getItemAtPosition(i).equals("Heenwewa Bungalow")) {
                    bLatitude = 6.3449;
                    bLongitude = 81.43095;
                    LatLng point = new LatLng(bLatitude,bLongitude);


                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Heenwewa Bungalow").icon(bungalowIcon));
                } else if (destinationSpinner.getItemAtPosition(i).equals("Yala Bungalow")) {
                    bLatitude = 6.36387;
                    bLongitude = 81.51932;
                    LatLng point = new LatLng(bLatitude, bLongitude);


                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Yala Bungalow").icon(bungalowIcon));
                } else if (destinationSpinner.getItemAtPosition(i).equals("Sithulpawwa Buddhist Monastery")) {
                    bLatitude = 6.38749;
                    bLongitude = 81.44896;
                    LatLng point = new LatLng(bLatitude, bLongitude);


                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Sithulpawwa Buddhist Monastery").icon(bungalowIcon));
                } else if (destinationSpinner.getItemAtPosition(i).equals("Mahaseelawa Bungalow")) {
                    bLatitude = 6.29125;
                    bLongitude = 81.43479;
                    LatLng point = new LatLng(bLatitude, bLongitude);


                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Mahaseelawa Bungalow").icon(bungalowIcon));
                }else if (destinationSpinner.getItemAtPosition(i).equals("Patanangala Beach")) {
                    bLatitude = 6.34422;
                    bLongitude = 81.49741;
                    LatLng point = new LatLng(bLatitude, bLongitude);


                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Patanangala Beach").icon(bungalowIcon));
                } else if (destinationSpinner.getItemAtPosition(i).equals("Akasa Chaithya")) {
                    bLatitude = 6.36259;
                    bLongitude = 81.47661;
                    LatLng point = new LatLng(bLatitude, bLongitude);

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bungalow);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor bungalowIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    mMap.addMarker(new MarkerOptions().position(point).title("Akasa Chaithya").icon(bungalowIcon));
                }
//                } else {
//                    //on selecting a spinner item
//                    String bungalow = destinationSpinner.getItemAtPosition(i).toString();
//
//                    //show selected spinner item
//                    Toast.makeText(destinationSpinner.getContext(), "Selected: " + bungalow, Toast.LENGTH_SHORT).show();
//
//                }
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

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, animals);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalSpinner.setAdapter(arrayAdapter);

        animalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (animalSpinner.getItemAtPosition(i).equals("Choose Animal")) {
                    //do nothing
                } else if (animalSpinner.getItemAtPosition(i).equals("Elephants")) {
                    ArrayList<eLatLng> elephants = new ArrayList<eLatLng>();
                    elephants.add(new eLatLng(6.29858,81.4411));
                    elephants.add(new eLatLng(6.30956,81.45978));
                    elephants.add(new eLatLng(6.31333,81.46335));
                    elephants.add(new eLatLng(6.31764,81.46021));
                    elephants.add(new eLatLng(6.3169,81.46054));
                    elephants.add(new eLatLng(6.3359,81.47552));
                    elephants.add(new eLatLng(6.32135, 81.42655));
                    elephants.add(new eLatLng(6.35402, 81.44771));
                    elephants.add(new eLatLng(6.38998, 81.46882));
                    elephants.add(new eLatLng(6.41372, 81.46058));
                    elephants.add(new eLatLng(6.37149, 81.51917));
                    elephants.add(new eLatLng(6.36251, 81.45613));

                    int height = 75;
                    int width = 50;
                    Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.elephant);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    for (eLatLng eLatLng:elephants){
                        eLatitude = eLatLng.getLatitude();
                        eLongitude = eLatLng.getLongitude();
                        LatLng points = new LatLng(eLatitude,eLongitude);
                        mMap.addMarker(new MarkerOptions().position(points)).setIcon(smallMarkerIcon);
                    }
//                    for ( i = 0; i < elephantLatLng.geteLatLng().size(); i++){
//                        eLatLng eLatLng = elephantLatLng.geteLatLng().get(i);
//                        eLatitude = eLatLng.getLatitude();
//                        eLongitude = eLatLng.getLongitude();
//                    }
                } else if (animalSpinner.getItemAtPosition(i).equals("Leopards")) {
                    ArrayList<leLatLng> leopards = new ArrayList<leLatLng>();
                    leopards.add(new leLatLng(6.30764, 81.45509));
                    leopards.add(new leLatLng(6.31911,81.43244));
                    leopards.add(new leLatLng(6.32877,81.44017));
                    leopards.add(new leLatLng(6.31226,81.46738));
                    leopards.add(new leLatLng(6.37687, 81.42542));
                    leopards.add(new leLatLng(6.3784, 81.45525));
                    leopards.add(new leLatLng(6.37876, 81.48727));
                    leopards.add(new leLatLng(6.33778, 81.44845));

                    int height = 75;
                    int width = 60;
                    Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.tiger);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    for (leLatLng leLatLng:leopards){
                        leoLatitude = leLatLng.getLatitude();
                        leoLongitude = leLatLng.getLongitude();
                        LatLng points = new LatLng(leoLatitude,leoLongitude);
                        mMap.addMarker(new MarkerOptions().position(points)).setIcon(smallMarkerIcon);
                    }



                } else if (animalSpinner.getItemAtPosition(i).equals("Birds")) {
                    ArrayList<bLatLng> birds = new ArrayList<bLatLng>();
                    birds.add(new bLatLng(6.39011, 81.46854));
                    birds.add(new bLatLng(6.35217, 81.44261));
                    birds.add(new bLatLng(6.33783, 81.44842));
                    birds.add(new bLatLng(6.33555, 81.47537));
                    birds.add(new bLatLng(6.28395, 81.41683));
                    birds.add(new bLatLng(6.29925, 81.44077));
                    birds.add(new bLatLng(6.31547, 81.47053));
                    birds.add(new bLatLng(6.33231, 81.48344));
                    birds.add(new bLatLng(6.31072, 81.42872));
                    birds.add(new bLatLng(6.32141, 81.42661));
                    birds.add(new bLatLng(6.36367, 81.51756));

                    int height = 75;
                    int width = 50;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bird);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    for (bLatLng bLatLng:birds){
                        birdLatitude = bLatLng.getLatitude();
                        birdLongitude = bLatLng.getLongitude();
                        LatLng points = new LatLng(birdLatitude,birdLongitude);
                        mMap.addMarker(new MarkerOptions().position(points)).setIcon(smallMarkerIcon);
                    }
                }
//                } else {
//                    //on selecting a spinner item
//                    String animal = animalSpinner.getItemAtPosition(i).toString();
//
//                    //show selected spinner item
//                    Toast.makeText(animalSpinner.getContext(), "Selected: " + animal, Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        if (mMap != null) {

        }

        LatLng zoom = new LatLng(6.33865057, 81.44501857);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom, 11.5f));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                buildGoogleApiClient();
//                mMap.setMyLocationEnabled(true);
//            }
//        }else {
//            buildGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
//        }
        //----------------------------------------------------------------------------------
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng));
//
//        Polyline polyline = mMap.addPolyline(new PolylineOptions().clickable(true).add(new LatLng(-35.016, 143.321),
//                new LatLng(-34.747, 145.592),
//                new LatLng(-34.364, 147.891),
//                new LatLng(-33.501, 150.217),
//                new LatLng(-32.306, 149.248),
//                new LatLng(dLatitude, dLongitude)));

        mMap.setOnPolylineClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this).build();
//        mGoogleApiClient.connect();
//    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        int color = polyline.getColor();

        if (color == Color.RED) {
            polyline.setColor(Color.BLUE);
        } else {
            polyline.setColor(Color.RED);
        }

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onMapClick(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        marker = mMap.addMarker(markerOptions);
        Toast.makeText(this, "" + latLng.latitude + ":" + latLng.longitude, Toast.LENGTH_LONG).show();
        sLatitude = latLng.latitude;
        sLongitude = latLng.longitude;
//        textView.setText(sLatitude.toString() + sLongitude.toString());
    }

    public void getRoutes(View view) {
        LocDir locDir = new LocDir(sLatitude,sLongitude,bLatitude,bLongitude);

        Call<LocDir> locDirCall1 = apiInterface.postLocDir(locDir);
        locDirCall1.enqueue(new Callback<LocDir>() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onResponse(Call<LocDir> call, Response<LocDir> response) {
                Log.e(TAG, "onResponse: " + response.body());
//                Toast.makeText(MainActivity.this,""+ response.body(),Toast.LENGTH_LONG).show();
                dResponse = response;
                ArrayList<LatLong> list = response.body().getLatLong();

                List<LatLng> latLngs = new ArrayList<>();
                response.body().getLatLong().toArray();
                size = response.body().getLatLong().size();
//                PolylineOptions options = new PolylineOptions().width(5).color(Color.RED);
                for (int i = 0; i < size; i++) {

//                    LatLong src = response.body().getLatLong().get(i);
//                    LatLong dest = response.body().getLatLong().get(i + 1);

//                    Polyline polyline = mMap.addPolyline(
//                            new PolylineOptions().add(
//                                    new LatLng(src.getLatitude(), src.getLongitude()), new LatLng(dest.getLatitude(), dest.getLongitude())
//                            ).width(2).color(Color.BLUE).geodesic(true)
//                    );
                    LatLong x = response.body().getLatLong().get(i);
//                    latLngs.add(new LatLng(response.body().getdLatitude(), response.body().getdLongitude()));
                    x.getLongitude().doubleValue();
                    Toast.makeText(MainActivity.this, "" + x, Toast.LENGTH_LONG).show();
                    dLatitude = x.getLatitude();
                    dLongitude = x.getLongitude();
                    dLatitude1 = x.getLastLatitude();
                    dLongitude1 = x.getLastLongitude();

//                    List list1 = x.getRoad();

                    distance = distance + x.getDistance();


                    response.body().getLatLong().listIterator();
                    Toast.makeText(MainActivity.this, "dLongitude: " + dLongitude,Toast.LENGTH_LONG).show();
                    LatLong point = response.body().getLatLong().get(i);
                    LatLng points = new LatLng(dLatitude,dLongitude);
//                    mMap.addMarker(new MarkerOptions().position(points));
//                    mMap.addPolyline(new PolylineOptions().color(Color.RED).add(points));
                    polylineOptions = new PolylineOptions().add(points);
//                    Polyline polyline = mMap.addPolyline(new PolylineOptions().clickable(true).addAll(Collections.singleton(points)));
//                    Toast.makeText(MainActivity.this, "" + types.toString(),Toast.LENGTH_LONG).show();
//                    HashMap<String,String> hashMap = new HashMap<String, String>();
//                    hashMap.put("lat", Double.toString(((LatLong)list.get(i)).latitude));

                    latLngs.add(points);
                }
                Toast.makeText(MainActivity.this, "dLongitude: " + dLongitude1.toString(),Toast.LENGTH_LONG).show();
                LatLng point = new LatLng(dLatitude, dLongitude);
                LatLng point1 = new LatLng(dLatitude1, dLongitude1);

                PolylineOptions rectOptions = new PolylineOptions().addAll(latLngs).clickable(true).color(Color.RED);
                mMap.addPolyline(rectOptions);
                PolylineOptions polylineOptions = new PolylineOptions().add(point,point1).clickable(true).color(Color.RED);
                mMap.addPolyline(polylineOptions);

                travelTime = distance/1000;
                txtDistance.setText("Distance : " + String.format("%.2f", travelTime) + "km");

                Log.e("","" + lat);
            }

            @Override
            public void onFailure(Call<LocDir> call, Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getLocalizedMessage());
            }
        });
    }

//    Location Listener
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Double latitude = marker.getPosition().latitude;
        Double longitude = marker.getPosition().longitude;

        Toast.makeText(MainActivity.this, "Marker : " + latitude.toString() + " , " + longitude.toString(), Toast.LENGTH_LONG).show();

        bLatitude = latitude;
        bLongitude = longitude;

        return false;
    }

    public void changeType(View view) {
        int mapType = mMap.getMapType();

        if (mapType != GoogleMap.MAP_TYPE_SATELLITE) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            btnMapChange.setBackground(getDrawable(R.drawable.normalmap));
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            btnMapChange.setBackground(getDrawable(R.drawable.satellite));
        }

    }

    @SuppressLint("SetTextI18n")
    public void clearMap(View view) {
        mMap.clear();
        distance = 0.0;
        txtDistance.setText("Distance : 0.0km");
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(Task task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);
                            Toast.makeText(MainActivity.this, "" + currentLocation.getLatitude() +
                                    "," + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                            curLoc = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(curLoc));
                            sLatitude = currentLocation.getLatitude();
                            sLongitude = currentLocation.getLongitude();
                        }else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MainActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MainActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void getLocation(View view) {
        if (mLocationPermissionGranted){
            getDeviceLocation();

        }
    }
//    -------------------------------------------------------

//    public void RequestGR(LatLng start, LatLng end, final Callback<JSONArray> arrayCallback)
//    {
//        LocDir locDir = new LocDir(6.27829,81.40232,6.29992,81.4322);
//
//        Call<LocDir> locDirCall = apiInterface.postLocDir(locDir);
//        locDirCall.enqueue(new Callback<LocDir>() {
//            @Override
//            public void onResponse(Call<LocDir> call, Response<LocDir> response) {
//                ArrayList<LocDir> jsonArray = response.body().getLatLong();
//                arrayCallback.onResponse(jsonArray);
//            }
//
//            @Override
//            public void onFailure(Call<LocDir> call, Throwable throwable) {
//
//            }
//        });
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 44) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                //When permission granted
//                //Call method
//                getCurrentLocation();
//            }
//        }
//    }



}