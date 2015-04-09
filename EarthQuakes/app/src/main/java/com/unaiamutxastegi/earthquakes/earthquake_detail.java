package com.unaiamutxastegi.earthquakes;

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


public class earthquake_detail extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private String earthQuakeDetail;
    private TextView txtInfoDetail;
    private EarthQuakeDB earthQuakeDB;
    private EarthQuake earthQuake;
    private EarthQuakeMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_earthquake_detail);

        mapFragment = (EarthQuakeMapFragment) getFragmentManager().findFragmentById(R.id.map);

        txtInfoDetail = (TextView) findViewById(R.id.txtInfoDetail);
        earthQuakeDB = new EarthQuakeDB(getApplicationContext());
        earthQuake = new EarthQuake();

        Intent earthquakeIntent = getIntent();

        earthQuakeDetail = earthquakeIntent.getStringExtra(EarthQuakeListFragment.EARTHQUAKE);
        earthQuake = earthQuakeDB.getAllByID(earthQuakeDetail);

        //setUpMapIfNeeded();
        showLayout();
        showMap(earthQuake);

    }

    private void showMap(EarthQuake earthQuake) {
        List<EarthQuake> earthQuakes = new ArrayList<>();
        earthQuakes.add(earthQuake);

        mapFragment.setEarthQuakes(earthQuakes);
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

    private void showLayout() {
        txtInfoDetail.setText("MAGNITUDE: " + earthQuake.getMagnitude() + " ID: " + earthQuake.get_id());
    }


    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    /**private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(earthQuake.getCoords().getLng(),earthQuake.getCoords().getLat())));
        mMap.addMarker(new MarkerOptions().position(new LatLng(earthQuake.getCoords().getLng(),earthQuake.getCoords().getLat())).title("Marker"));
    }**/

}
