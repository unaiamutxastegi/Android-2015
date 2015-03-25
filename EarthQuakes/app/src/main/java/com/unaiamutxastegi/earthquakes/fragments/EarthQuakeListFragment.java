package com.unaiamutxastegi.earthquakes.fragments;

import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.unaiamutxastegi.earthquakes.R;

import com.unaiamutxastegi.earthquakes.adapters.EarthquakeAdapter;
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

public class EarthQuakeListFragment extends ListFragment implements DownloadEarthquakesTask.AddEarthQuakeInterface {

    private ArrayList<EarthQuake> earthQuakes;
    private EarthquakeAdapter aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakes = new ArrayList<>();

        DownloadEarthquakesTask task = new DownloadEarthquakesTask(this);
        task.execute(getString(R.string.earthquake_url));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthquakeAdapter(getActivity(), R.layout.earthquake_list_item, earthQuakes);
        setListAdapter(aa);

        return layout;
    }


    @Override
    public void addEarthQuake(EarthQuake earthQuake) {
        earthQuakes.add(0,earthQuake);
        aa.notifyDataSetChanged();
    }
}
