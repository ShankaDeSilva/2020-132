package com.example.smartsafari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestActivity2 extends AppCompatActivity implements OnMapReadyCallback{
    //Initialize variables
    public static GoogleMap mapAPI;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    ArrayList<LatLng> markerPoints = new ArrayList();
    public static Button btnDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        btnDraw = (Button) findViewById(R.id.btnDraw);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mapAPI.setMyLocationEnabled(true);

        mapAPI.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                if (markerPoints.size() >= 10) {
                    return;
                }

                markerPoints.add(point);
                MarkerOptions options = new MarkerOptions();
                options.position(point);

                if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }else if (markerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }else {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                }
                mapAPI.addMarker(options);
            }
        });

        mapAPI.setOnMapLongClickListener(new OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                mapAPI.clear();
                markerPoints.clear();

            }
        });

        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markerPoints.size() >= 2) {
                    LatLng origin = markerPoints.get(0);
                    LatLng dest = markerPoints.get(1);
                    String url = getDirectionsUrl(origin, dest);
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(url);
                }
            }
        });
    }

    public String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor-false";
        String waypoints = "";
        for (int i = 2; i < markerPoints.size(); i++) {
            LatLng point = (LatLng) markerPoints.get(i);
            if (i == 2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }

        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + waypoints;

        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters; //json output url is here...........
        return url;
     }

//     @SuppressLint("LongLogTag")
//     public String downloadUrl(String strUrl) throws IOException {
//        String data = "";
//         InputStream iStream = null;
//         HttpsURLConnection urlConnection = null;
//         try {
//             URL url = new URL(strUrl);
//             urlConnection = (HttpsURLConnection) url.openConnection();
//             urlConnection.connect();
//             iStream = urlConnection.getInputStream();
//             BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//             StringBuffer sb = new StringBuffer();
//
//             String line = "";
//             while ((line = br.readLine()) != null) {
//                 sb.append(line);
//             }
//             data = sb.toString();
//             br.close();
//         } catch (Exception e) {
//             Log.d("Exception While downloading url", e.toString());
//         } finally {
//             iStream.close();
//             urlConnection.disconnect();
//         }
//         return data;
//     }

//     private class DownloadTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... url) {
//            String data = "";
//            try {
//                data = downloadUrl(url[0]);
//            }catch (Exception e) {
//                Log.d("Background Task", e.toString());
//            }
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            ParserTask parserTask = new ParserTask();
//
//            parserTask.execute(result);
//        }
//     }



     public static class DirectionsJSONParser {
        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;
            JSONObject jDistance = null;
            JSONObject jDuration = null;

            try {

                jRoutes = jObject.getJSONArray("routes");

                //Traversing all routes
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");

                    List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                    //Traversing all legs
                    for (int j = 0; j < jLegs.length(); j++) {

                        //Getting distance fromm the json data
                        jDistance = ((JSONObject) jLegs.get(j))
                                .getJSONObject("distance");
                        HashMap<String, String> hmDistance = new HashMap<String, String>();
                        hmDistance.put("distance", jDistance.getString("text"));

                        //Getting duration from the json data
                        jDuration = ((JSONObject) jLegs.get(j))
                                .getJSONObject("duration");
                        HashMap<String, String> hmDuration = new HashMap<String, String>();
                        hmDuration.put("duration", jDuration.getString("text"));

                        //Adding distance object to the path
                        path.add(hmDistance);

                        //Adding duration object to the path
                        path.add(hmDuration);

                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                        //Traversing all steps
                        for (int k = 0; k < jSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps
                                    .get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            //Traversing all points
                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat", Double.toString(((LatLng) list.get(1)).latitude));
                                hm.put("lon", Double.toString(((LatLng) list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                    }
                    routes.add(path);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {

            }

            return routes;
        }

        //Method to decode polyline points Courtesy :
        //  jeffreysambells.com/2010/05/27
        //	 decoding-polylines-from-google-maps-direction-api-with-java
        private List<LatLng> decodePoly(String encoded) {
            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }


            return poly;

        }
     }

//        //Assign variable
//        mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapAPI);
//
//        //mapFragment.getMapAsync(this);
//
//        //Initialize fused location
//        client = LocationServices.getFusedLocationProviderClient(this);
//
//        //Check Permission
//        if (ActivityCompat.checkSelfPermission(MainActivity3.this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            //When Permission granted
//            // call method
//            getCurrentLocation();
//        }else {
//            //When Permission Denied Request Permission
//            ActivityCompat.requestPermissions(MainActivity3.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//        }
//    }
//
//    private void getCurrentLocation() {
//        //Initialize Task Location
//        Task<Location> task = client.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(final Location location) {
//                //When Success
//                if (location != null) {
//                    //Sync Map
//                    mapFragment.getMapAsync(new OnMapReadyCallback() {
//                        @Override
//                        public void onMapReady(GoogleMap googleMap) {
//                            //Initialize lat lon
//                            LatLng latLng = new LatLng(location.getLatitude()
//                            ,location.getLongitude());
//
//                            //Create Marker Options
//                            MarkerOptions options = new MarkerOptions().position(latLng)
//                                    .title("You are Here");
//
//                            //Zoom Map
//                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//
//                            //Add Marker On Map
//                            googleMap.addMarker(options);
//
//                            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 44){
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                //When Permission Granted
//                //Call Method
//                getCurrentLocation();
//            }
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;
//        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        LatLng AT = new LatLng(20.039413, 74.479977);  // Position LatLng should be insert
        mapAPI.addMarker(new MarkerOptions().position(AT).title("Marker in Yaala"));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(AT));
    }
}