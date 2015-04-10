package com.unaiamutxastegi.earthquakes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.unaiamutxastegi.earthquakes.alarm.Alarm;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;
import com.unaiamutxastegi.earthquakes.service.DownloadEarthQuakeService;
import com.unaiamutxastegi.earthquakes.tasks.DownloadEarthquakesTask;


public class MainActivity extends ActionBarActivity implements DownloadEarthquakesTask.AddEarthQuakeInterface {

    private final static String EARTHQUAKE_PREFS = "earthquake_prefs";
    public int PREFS_ACTIVITY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);


        //downloadEarthQuake();
        checkToSetAlarm();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_list:
                openList();
                return true;
            case R.id.action_settings:
                Intent prefsIntent = new Intent(this, SettingsActivity.class);
                startActivityForResult(prefsIntent, PREFS_ACTIVITY);

                return true;
            case R.id.action_map:
                openMap();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openMap() {
        setContentView(R.layout.activity_map_layout);
    }

    private void openList() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
        t.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkToSetAlarm() {
        String KEY = "LAUNCHED_BEFORE";
        SharedPreferences prefs = getSharedPreferences(EARTHQUAKE_PREFS, Activity.MODE_PRIVATE);

        if (!prefs.getBoolean(KEY, false)) {
            long interval = getResources().getInteger(R.integer.default_interval) * 60 * 1000;
            Alarm.setAlarm(this, interval);

            prefs.edit().putBoolean(KEY, true).apply();
        }

    }

    private void downloadEarthQuake() {
        DownloadEarthquakesTask task = new DownloadEarthquakesTask(this, this);
        task.execute(getString(R.string.earthquake_url));

        //Intent download = new Intent(this, DownloadEarthQuakeService.class);
        //startService(download);
    }
}
