package com.isprid.smartsafaridashboard.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.isprid.smartsafaridashboard.R;
import com.isprid.smartsafaridashboard.animalsJSON.ElephantLatLng;
import com.isprid.smartsafaridashboard.animalsJSON.eLatLng;
import com.isprid.smartsafaridashboard.animalsJSON.leLatLng;
import com.isprid.smartsafaridashboard.interfaces.ApiClient;
import com.isprid.smartsafaridashboard.interfaces.ApiInterface;
import com.isprid.smartsafaridashboard.interfaces.LatLong;
import com.isprid.smartsafaridashboard.interfaces.LocDir;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteFinder extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnMapClickListener, LocationListener {

    Double latitude, longitude;
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    TextView textView;
    //    GoogleApiClient mGoogleApiClient;
//    Button getRoute;
    private Spinner animalSpinner, destinationSpinner;
    LocationManager locationManager;
    FusedLocationProviderClient client;
    Double curLatitude, curLongitude;
    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation;


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

    private static final String TAG = "RouteFinder";
    ApiInterface apiInterface;

    Response<LocDir> dResponse;
    Double dLongitude, dLatitude;
    Double sLatitude, sLongitude;
    ArrayList arrayList1;
    public Integer size;
    Type[] types;
    PolylineOptions polylineOptions;
    ArrayList<Double> lat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_finder);

        textView = findViewById(R.id.editTextTextPersonName);
//        getRoute = findViewById(R.id.btn_generate);
        animalSpinner = findViewById(R.id.spnAnimal);
        destinationSpinner = findViewById(R.id.spnDestination);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation();
                }
            }
        });

        latitude = -33.852;
        longitude = Double.valueOf("151.211");

        animalSpinnerSet();
        destinationSpinnerSet();
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                curLatitude = locationGPS.getLatitude();
                curLongitude = locationGPS.getLongitude();
                Toast.makeText(this,""+curLatitude.toString() + "," + curLongitude.toString(),Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
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
                    mMap.clear();
                    LatLng point = new LatLng(bLatitude,bLongitude);
                    mMap.addMarker(new MarkerOptions().position(point).title("Old Buthawa Wild Life Department Bungalow"));
                } else if (destinationSpinner.getItemAtPosition(i).equals("New Buthawa Wild Life Department Bungalow")) {
                    bLatitude = 6.31482;
                    bLongitude = 81.48285;
                    LatLng point = new LatLng(bLatitude,bLongitude);
                    mMap.addMarker(new MarkerOptions().position(point).title("New Buthawa Wild Life Department Bungalow"));
                    mMap.clear();
                } else if (destinationSpinner.getItemAtPosition(i).equals("Thalgasmankada Bungalow")) {
                    bLatitude = 6.39833;
                    bLongitude = 81.48183;
                    mMap.clear();
                } else if (destinationSpinner.getItemAtPosition(i).equals("Warahana Wildlife Circuit Bungalow")) {
                    bLatitude = 6.41309;
                    bLongitude = 81.45874;
                    mMap.clear();
                } else if (destinationSpinner.getItemAtPosition(i).equals("Ondaatje Bungalow")) {
                    bLatitude = 6.3222;
                    bLongitude = 81.38414;
                    mMap.clear();
                } else if (destinationSpinner.getItemAtPosition(i).equals("Heenwewa Bungalow")) {
                    bLatitude = 6.3449;
                    bLongitude = 81.43095;
                    mMap.clear();
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
        animals.add("Bears");

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

                    int height = 75;
                    int width = 50;
                    Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.tiger);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    for (leLatLng eLatLng:leopards){
                        eLatitude = eLatLng.getLatitude();
                        eLongitude = eLatLng.getLongitude();
                        LatLng points = new LatLng(eLatitude,eLongitude);
                        mMap.addMarker(new MarkerOptions().position(points)).setIcon(smallMarkerIcon);
                    }



                }
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

        LatLng zoom = new LatLng(6.4987172, 81.4530718);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom, 9.5f));
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
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(markerOptions);
        Toast.makeText(this, "" + latLng.latitude + ":" + latLng.longitude, Toast.LENGTH_LONG).show();
        sLatitude = latLng.latitude;
        sLongitude = latLng.longitude;
        textView.setText(sLatitude.toString() + sLongitude.toString());


    }

    public void getRoutes(View view) {
        LocDir locDir = new LocDir(sLatitude,sLongitude,bLatitude,bLongitude);

        Call<LocDir> locDirCall1 = apiInterface.postLocDir(locDir);
        locDirCall1.enqueue(new Callback<LocDir>() {
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
                for (int i = 0; i < size - 1; i++) {

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
                    Toast.makeText(RouteFinder.this, "" + x, Toast.LENGTH_LONG).show();
                    dLatitude = x.getLatitude();
                    dLongitude = x.getLongitude();
                    response.body().getLatLong().listIterator();
                    Toast.makeText(RouteFinder.this, "dLongitude: " + dLongitude,Toast.LENGTH_LONG).show();
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
                Toast.makeText(RouteFinder.this, "dLongitude: " + dLongitude.toString(),Toast.LENGTH_LONG).show();

                PolylineOptions rectOptions = new PolylineOptions().addAll(latLngs);
                mMap.addPolyline(rectOptions);
                Log.e("","" + lat);
            }

            @Override
            public void onFailure(Call<LocDir> call, Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getLocalizedMessage());
            }
        });
    }

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
}