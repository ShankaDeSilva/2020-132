package com.isprid.smartsafari.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isprid.smartsafari.R;
import com.isprid.smartsafari.util.Constant;
import com.isprid.smartsafari.util.PrefUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String[] LOCATION_ = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private GoogleMap mMap;

    LocationManager locationManager;
    private double latitude;
    private double longitude;
    private DatabaseReference databaseReference;
    private String TAG = MapsActivity.class.getSimpleName();
    private PrefUtils pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        databaseReference = FirebaseDatabase.getInstance().getReference("Activities");
        pref = new PrefUtils(MapsActivity.this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!hasLocationPermissions()) {
            requiresLocationPermission();
        }

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
        //check the network provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    //get the latitude
                    latitude = location.getLatitude();

                    //get the longitude
                    longitude = location.getLongitude();

                    LatLng latlng = new LatLng(latitude, longitude);

                    Geocoder geocoder = new Geocoder(getApplicationContext());

                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latlng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    latitude = location.getLatitude();

                    //get the longitude
                    longitude = location.getLongitude();

                    LatLng latlng = new LatLng(latitude, longitude);

                    Geocoder geocoder = new Geocoder(getApplicationContext());

                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latlng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        fetchReportActivities();
    }

    private void fetchReportActivities() {
        final ArrayList<Data> activitiesList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous data list
                activitiesList.clear();
                // Clears the previously touched position
                mMap.clear();

                addCurrentLocationMarker();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting activities
                    Data data = postSnapshot.getValue(Data.class);

                    if (!pref.getPrefsValue(Constant.UUID).equals(data.getUuid())) {
                        //adding data to the list
                        activitiesList.add(data);

                        Log.e(TAG, "latitude > " + data.latitude + " | longitude > " + data.longitude);
                        LatLng latLng = new LatLng(Double.parseDouble(data.latitude), Double.parseDouble(data.longitude));

                        // Creating a marker
                        MarkerOptions markerOptions = new MarkerOptions();

                        // Setting the position for the marker
                        markerOptions.position(latLng);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                        // Setting the title for the marker.
                        // This will be displayed on taping the marker
                        markerOptions.title(data.animalName);
                        markerOptions.snippet(data.animalActivity);

                        // Placing a marker on the touched position
                        mMap.addMarker(markerOptions);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }

    private void addCurrentLocationMarker() {
        if (latitude != 0.0 && longitude != 0.0) {
            LatLng latlng = new LatLng(latitude, longitude);

            Geocoder geocoder = new Geocoder(getApplicationContext());

            try {
                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                String str = addressList.get(0).getLocality() + ",";
                str += addressList.get(0).getCountryName();
                mMap.addMarker(new MarkerOptions().position(latlng).title(str));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 5.2f));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    private void requiresLocationPermission() {
        if (!EasyPermissions.hasPermissions(this, LOCATION_)) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location),
                    RC_LOCATION_CONTACTS_PERM,
                    LOCATION_);
        }
    }

    private boolean hasLocationPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_);
    }

    public void reportactivity(View view) {
        System.out.println("latitude > " + latitude + " | longitude > " + longitude);
        Intent intent = new Intent(MapsActivity.this, FormActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_LATITUDE, String.valueOf(latitude));
        intent.putExtra(Constant.INTENT_EXTRA_LONGITUDE, String.valueOf(longitude));
        startActivity(intent);
    }
}