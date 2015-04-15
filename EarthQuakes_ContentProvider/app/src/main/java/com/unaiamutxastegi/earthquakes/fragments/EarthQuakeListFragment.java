package com.unaiamutxastegi.earthquakes.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unaiamutxastegi.earthquakes.R;

import com.unaiamutxastegi.earthquakes.adapters.EarthquakeAdapter;
import com.unaiamutxastegi.earthquakes.providers.EarthQuakeDB;
import com.unaiamutxastegi.earthquakes.earthquake_detail;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;
import com.unaiamutxastegi.earthquakes.service.DownloadEarthQuakeService;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_refresh,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                doRefresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doRefresh() {
        Intent download = new Intent(getActivity(), DownloadEarthQuakeService.class);
        getActivity().startService(download);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        setHasOptionsMenu(true);

        earthQuakes = new ArrayList<>();

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
