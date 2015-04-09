package com.unaiamutxastegi.earthquakes.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unaiamutxastegi.earthquakes.R;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarthQuakeMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {

    private GoogleMap map;
    private List<EarthQuake> earthQuakes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        map = getMap();
        map.setOnMapLoadedCallback(this);

        return layout;
    }

    public void setEarthQuakes(List<EarthQuake> earthQuakes) {
        this.earthQuakes = earthQuakes;
    }

    @Override
    public void onMapLoaded() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (EarthQuake earthQuake : earthQuakes) {
            LatLng point = new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat());

            MarkerOptions marker = new MarkerOptions()
                    .position(point)
                    .title(String.valueOf(earthQuake.getMagnitude()))
                    .snippet(earthQuake.getCoords().toString());

            map.addMarker(marker);
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);

        map.moveCamera(cu);
    }
}
