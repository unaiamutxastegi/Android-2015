package com.unaiamutxastegi.configchange;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityA extends ActionBarActivity {

    private final String DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Log.d("CHANGE", "ActivityA onCreate()");

        if (savedInstanceState != null) {
            String data = savedInstanceState.getString(DATA);
            Log.d("CHANGE", "ActivityA OnCreate saved data" + data);
        }

        Button btnOpenA = (Button) findViewById(R.id.btnOpenA);

        btnOpenA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANGE", "ActivityA onClik()");

                Intent OpenB = new Intent(ActivityA.this, ActivityB.class);
                startActivity(OpenB);
            }
        });

        Button btnClose = (Button) findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANGE", "ActivityA Close");
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d("CHANGE", "ActivityA onSaveInstanceState()");

        outState.putString(DATA, "datos");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        Log.d("CHANGE", "ActivityA onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState, persistentState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CHANGE", "ActivityA OnStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("CHANGE", "ActivityA onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CHANGE", "ActivityA onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CHANGE", "ActivityA onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CHANGE", "ActivityA onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CHANGE", "ActivityA onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
