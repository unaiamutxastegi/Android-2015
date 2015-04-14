package com.unaiamutxastegi.earthquakes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.unaiamutxastegi.earthquakes.alarm.Alarm;
import com.unaiamutxastegi.earthquakes.fragments.EarthQuakeListFragment;
import com.unaiamutxastegi.earthquakes.fragments.EarthQuakeListMapFragment;
import com.unaiamutxastegi.earthquakes.listerners.TabListener;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;
import com.unaiamutxastegi.earthquakes.service.DownloadEarthQuakeService;
import com.unaiamutxastegi.earthquakes.tasks.DownloadEarthquakesTask;


public class MainActivity extends Activity implements DownloadEarthquakesTask.AddEarthQuakeInterface {

    private final static String EARTHQUAKE_PREFS = "earthquake_prefs";
    private final static String SELECTED_TAB ="SELECTED_TAB";
    public int PREFS_ACTIVITY = 1;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createTab();
        //downloadEarthQuake();
        checkToSetAlarm();
    }

    private void createTab() {

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tabList = actionBar.newTab();
        tabList.setText("List")
                .setTabListener(new TabListener<EarthQuakeListFragment>
                        (this, R.id.fragmentContainer, EarthQuakeListFragment.class));

        actionBar.addTab(tabList);

        ActionBar.Tab tabMap = actionBar.newTab();

        tabMap.setText("Map")
                .setTabListener(new TabListener<EarthQuakeListMapFragment>
                        (this, R.id.fragmentContainer, EarthQuakeListMapFragment.class));

        actionBar.addTab(tabMap);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        actionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TAB, 0));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_TAB, actionBar.getSelectedNavigationIndex());

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefsIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(prefsIntent, PREFS_ACTIVITY);

            return true;
        }

        return super.onOptionsItemSelected(item);
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
