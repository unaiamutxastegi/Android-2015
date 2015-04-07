package com.unaiamutxastegi.geolocation;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private TextView lblLatitude;
    private TextView lblLongitude;
    private TextView lblSpeed;
    private TextView lblAltitude;

    private boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblAltitude = (TextView) findViewById(R.id.lblAltitude);
        lblLatitude = (TextView) findViewById(R.id.lblLatitude);
        lblLongitude = (TextView) findViewById(R.id.lblLongitude);
        lblSpeed = (TextView) findViewById(R.id.lblSpeed);

        configureClient();
        connectClient();
    }

    private void connectClient() {
        Log.d("GPS","Connecting...");
        mGoogleApiClient.connect();
        connected = true;
    }

    private void configureClient() {
        Log.d("GPS","Configuring..");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void addLocation(Location location) {
        lblAltitude.setText("Altitude: " + String.valueOf(location.getAltitude()));
        lblLatitude.setText("Latitude: " + String.valueOf(location.getLatitude()));
        lblSpeed.setText("Speed: " + String.valueOf(location.getSpeed()));
        lblLongitude.setText("Longitude: " + String.valueOf(location.getLongitude()));
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("GPS","Connected");
        getLocation();
    }

    private void getLocation() {
        if (connected) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                addLocation(mLastLocation);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
