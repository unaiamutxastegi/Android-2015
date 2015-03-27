package com.unaiamutxastegi.earthquakes;

import android.content.SharedPreferences;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.unaiamutxastegi.earthquakes.adapters.EarthquakeAdapter;
import com.unaiamutxastegi.earthquakes.fragments.EarthQuakeListFragment;
import com.unaiamutxastegi.earthquakes.fragments.SettingFragment;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();

    }


}
