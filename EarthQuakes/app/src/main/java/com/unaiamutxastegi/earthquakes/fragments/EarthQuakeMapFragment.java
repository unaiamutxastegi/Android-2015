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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unaiamutxastegi.earthquakes.R;
import com.unaiamutxastegi.earthquakes.fragments.abstracts.AbstractMapFragment;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarthQuakeMapFragment extends AbstractMapFragment {

    private EarthQuake earthQuake;

    @Override
    protected void getData() {
        String id = getActivity().getIntent().getStringExtra(EarthQuakeListFragment.EARTHQUAKE);

        earthQuake = earthQuakeDB.getAllByID(id);
    }

    @Override
    protected void showMap() {
        MarkerOptions marker = createMarker(earthQuake);

        getMap().addMarker(marker);

        CameraPosition camPos = new CameraPosition.Builder().target(marker.getPosition())
                .zoom(5)
                .build();

        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);

        getMap().animateCamera(camUpd);
    }

}
