package com.unaiamutxastegi.earthquakes.fragments.abstracts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unaiamutxastegi.earthquakes.providers.EarthQuakeDB;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;

/**
 * Created by cursomovil on 13/04/15.
 */
public abstract class AbstractMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {

    protected EarthQuakeDB earthQuakeDB;

    protected GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakeDB = new EarthQuakeDB(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        getMap().setOnMapLoadedCallback(this);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        setupMapIfNeeded();
        getMap().setOnMapLoadedCallback(this);
    }

    private void setupMapIfNeeded() {
        if (map == null) {
            map = this.getMap();
        }
    }

    protected MarkerOptions createMarker(EarthQuake earthQuake) {
        LatLng point = new LatLng(
                earthQuake.getCoords().getLng(),
                earthQuake.getCoords().getLat()
        );

        MarkerOptions marker = new MarkerOptions()
                .position(point)
                .title(String.valueOf(earthQuake.getMagnitude()))
                .snippet(earthQuake.getCoords().toString());

        return marker;
    }

    @Override
    public void onMapLoaded() {
        this.getData();
        this.showMap();
    }


    abstract protected void getData();

    abstract protected void showMap();
}
