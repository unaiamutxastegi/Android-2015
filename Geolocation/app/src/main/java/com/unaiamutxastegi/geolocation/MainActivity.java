package com.unaiamutxastegi.geolocation;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.unaiamutxastegi.geolocation.listerners.LocationListener;


public class MainActivity extends ActionBarActivity implements com.unaiamutxastegi.geolocation.listerners.LocationListener.AddLocationInterface{

    private TextView lblLatitude;
    private TextView lblLongitude;
    private TextView lblSpeed;
    private TextView lblAltitude;
    private String bestProvider;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblAltitude = (TextView) findViewById(R.id.lblAltitude);
        lblLatitude = (TextView) findViewById(R.id.lblLatitude);
        lblLongitude = (TextView) findViewById(R.id.lblLongitude);
        lblSpeed = (TextView) findViewById(R.id.lblSpeed);

        getLocationProvider();
        listenLocationChanges();
    }

    private void listenLocationChanges() {
        int t = 5000;
        int distance = 5;

        LocationListener locationListener = new LocationListener(this);

        locationManager.requestLocationUpdates(bestProvider, t, distance, locationListener);
    }


    public void getLocationProvider() {
        Criteria criteria = new Criteria();
        String serviceString = this.LOCATION_SERVICE;


        locationManager = (LocationManager) getSystemService(serviceString);

        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(true);
        criteria.setSpeedRequired(true);

        bestProvider = locationManager.getBestProvider(criteria, true);
    }

    @Override
    public void addLocation(Location location) {
        lblAltitude.setText(String.valueOf(location.getAltitude()));
        lblLatitude.setText(String.valueOf(location.getLatitude()));
        lblSpeed.setText(String.valueOf(location.getSpeed()));
        lblLongitude.setText(String.valueOf(location.getLongitude()));
    }
}
