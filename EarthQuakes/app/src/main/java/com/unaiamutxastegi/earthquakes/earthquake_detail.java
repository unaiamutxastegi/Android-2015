package com.unaiamutxastegi.earthquakes;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unaiamutxastegi.earthquakes.database.EarthQuakeDB;
import com.unaiamutxastegi.earthquakes.fragments.EarthQuakeListFragment;
import com.unaiamutxastegi.earthquakes.fragments.EarthQuakeMapFragment;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;


public class earthquake_detail extends Activity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private String earthQuakeDetail;
    private TextView txtInfoDetail;
    private EarthQuakeDB earthQuakeDB;
    //private EarthQuake earthQuake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_earthquake_detail);

        txtInfoDetail = (TextView) findViewById(R.id.txtInfoDetail);
        earthQuakeDB = new EarthQuakeDB(getApplicationContext());

        Intent earthquakeIntent = getIntent();

        earthQuakeDetail = earthquakeIntent.getStringExtra(EarthQuakeListFragment.EARTHQUAKE);
        EarthQuake earthQuake = earthQuakeDB.getAllByID(earthQuakeDetail);

        //setUpMapIfNeeded();
        showLayoutDetail(earthQuake);
        //showMap(earthQuake);

    }

    private void showLayoutDetail(EarthQuake earthQuake) {
        txtInfoDetail.setText("MAGNITUDE: " + earthQuake.getMagnitude() + " ID: " + earthQuake.get_id());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefsIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(prefsIntent,1);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }

}
