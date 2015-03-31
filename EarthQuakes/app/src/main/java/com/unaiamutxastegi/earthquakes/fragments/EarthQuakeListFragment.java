package com.unaiamutxastegi.earthquakes.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.unaiamutxastegi.earthquakes.R;

import com.unaiamutxastegi.earthquakes.adapters.EarthquakeAdapter;
import com.unaiamutxastegi.earthquakes.database.EarthQuakeDB;
import com.unaiamutxastegi.earthquakes.earthquake_detail;
import com.unaiamutxastegi.earthquakes.model.Coordinate;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;
import com.unaiamutxastegi.earthquakes.tasks.DownloadEarthquakesTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class EarthQuakeListFragment extends ListFragment {

    public static final String EARTHQUAKE = "an earthquake";
    private SharedPreferences prefs;
    private ArrayList<EarthQuake> earthQuakes;
    private EarthquakeAdapter aa;
    private EarthQuakeDB earthQuakeDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakes = new ArrayList<>();
        earthQuakeDB = new EarthQuakeDB(getActivity());

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //updateEarthQuakeList(0);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        EarthQuake earthQuake = earthQuakes.get(position);

        Intent earthquakeIntent = new Intent(getActivity(), earthquake_detail.class);
        String strDetail = earthQuake.get_id();
        earthquakeIntent.putExtra(EARTHQUAKE, strDetail);
        startActivity(earthquakeIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthquakeAdapter(getActivity(), R.layout.earthquake_list_item, earthQuakes);
        setListAdapter(aa);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateEarthQuakeList(Integer.parseInt(prefs.getString(getString(R.string.PREF_MAGNITUDE_MIN), "0")));
    }

    private void updateEarthQuakeList(int magnitude) {
        earthQuakes.clear();
        earthQuakes.addAll(earthQuakeDB.getAllByMagnitude(magnitude));

        aa.notifyDataSetChanged();
    }
}
