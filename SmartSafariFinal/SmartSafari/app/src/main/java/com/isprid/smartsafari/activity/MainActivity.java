package com.isprid.smartsafari.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.isprid.smartsafari.R;
import com.isprid.smartsafari.util.Constant;
import com.isprid.smartsafari.util.PrefUtils;

import org.tensorflow.lite.examples.classification.ClassifierActivity;


public class MainActivity extends AppCompatActivity {

    private PrefUtils pref;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Dashboard");

        pref = new PrefUtils(this);

        Log.e(TAG, "REG ID >>> " + pref.getPrefsValue(Constant.REG_ID));
        FirebaseMessaging.getInstance().subscribeToTopic(Constant.SUBSCRIBE_TO);

        TextView txtRole = findViewById(R.id.txtRole);
        txtRole.setText((pref.checkFromPrefs(Constant.ROLE)) ? pref.getPrefsValue(Constant.ROLE) : "Error");

        CardView cvCommentAnalyzer = findViewById(R.id.cvCommentAnalyzer);
        CardView cvMammalsIdentifier = findViewById(R.id.cvMammalsIdentifier);

        cvCommentAnalyzer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
                startActivity(intent);
            }
        });

        cvMammalsIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClassifierActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            pref.clearAllPrefsData();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void RouteFinder(View view) {
        Intent routeFinder = new Intent(MainActivity.this, RouteFinderActivity.class);
        startActivity(routeFinder);
    }

    public void LocationSender(View view) {
        Intent locationSender = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(locationSender);
    }
}
