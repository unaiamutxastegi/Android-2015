package com.unaiamutxastegi.earthquakes;

import android.app.AlarmManager;
import android.content.SharedPreferences;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.unaiamutxastegi.earthquakes.adapters.EarthquakeAdapter;
import com.unaiamutxastegi.earthquakes.alarm.Alarm;
import com.unaiamutxastegi.earthquakes.fragments.EarthQuakeListFragment;
import com.unaiamutxastegi.earthquakes.fragments.SettingFragment;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        //addPreferencesFromResource(R.xml.userpreferences);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        long  interval;
        interval = Integer.parseInt(sharedPreferences.getString(getString(R.string.PREF_UPDATE_INTERVAL), "1")) * 60 * 1000;

        if (key.equals(getString(R.string.PREF_AUTO_UPDATE))) {
            if (sharedPreferences.getBoolean(key, true)){
                Alarm.setAlarm(this, interval);
            }else{
                Alarm.stopAlarm(this);
            }
        } else if (key.equals(getString(R.string.PREF_UPDATE_INTERVAL))) {
            Alarm.updateAlarm(this,interval);
        }
    }
}
