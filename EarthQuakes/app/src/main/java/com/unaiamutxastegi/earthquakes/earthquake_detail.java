package com.unaiamutxastegi.earthquakes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.unaiamutxastegi.earthquakes.fragments.EarthQuakeListFragment;


public class earthquake_detail extends ActionBarActivity {

    private String earthQuakeDetail;
    private TextView txtInfoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_detail);

        txtInfoDetail = (TextView) findViewById(R.id.txtInfoDetail);

        Intent earthquakeIntent = getIntent();

        earthQuakeDetail = earthquakeIntent.getStringExtra(EarthQuakeListFragment.EARTHQUAKE);

        showLayout();

    }

    private void showLayout() {
        txtInfoDetail.setText(earthQuakeDetail);
    }


}
