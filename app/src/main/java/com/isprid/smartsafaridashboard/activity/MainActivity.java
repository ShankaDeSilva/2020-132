package com.isprid.smartsafaridashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.isprid.smartsafaridashboard.R;
import com.isprid.smartsafaridashboard.util.Constant;
import com.isprid.smartsafaridashboard.util.PrefUtils;

public class MainActivity extends AppCompatActivity {

    private PrefUtils pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Dashboard");

        pref = new PrefUtils(this);

        TextView txtRole = findViewById(R.id.txtRole);
        txtRole.setText((pref.checkFromPrefs(Constant.ROLE)) ? pref.getPrefsValue(Constant.ROLE) : "Error");

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
}
