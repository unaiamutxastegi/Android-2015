package com.unaiamutxastegi.earthquakes.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unaiamutxastegi.earthquakes.R;
import com.unaiamutxastegi.earthquakes.fragments.abstracts.AbstractMapFragment;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;

import java.util.List;

/**
 * Created by cursomovil on 13/04/15.
 */
public class EarthQuakeListMapFragment extends AbstractMapFragment{
    private SharedPreferences prefs;

    private List<EarthQuake> earthQuakes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    protected void getData() {
        int minMag = Integer.parseInt(prefs.getString(getString(R.string.PREF_MAGNITUDE_MIN), "0"));

        earthQuakes = earthQuakeDB.getAllByMagnitude(minMag);
    }

    @Override
    protected void showMap() {

        getMap().clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (EarthQuake earthQuake: earthQuakes) {

            MarkerOptions marker = createMarker(earthQuake);

            getMap().addMarker(marker);
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);

        getMap().animateCamera(cu);
    }

    @Override
    public void onResume() {
        super.onResume();

        getMap().setOnMapLoadedCallback(this);
    }
}
