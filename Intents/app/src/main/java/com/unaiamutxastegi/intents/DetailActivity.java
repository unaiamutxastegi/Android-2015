package com.unaiamutxastegi.intents;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DetailActivity extends ActionBarActivity {

    private Button btnClose;
    private Button btnSendDetail;
    private TextView txtMessageDetail;
    private TextView lblMessageDetail;
    private Intent detailIntent;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        obtainViews();
        addEventListener();

        populateView();
    }

    private void obtainViews() {
        btnClose = (Button) findViewById(R.id.btnPhoto);
        btnSendDetail = (Button) findViewById(R.id.btnSendDetail);
        lblMessageDetail = (TextView) findViewById(R.id.lblMessage);
        txtMessageDetail = (TextView) findViewById(R.id.txtMessageDetail);
    }

    private void populateView() {
        detailIntent = getIntent();
        message = detailIntent.getStringExtra(MainActivity.MESSAGE);
        lblMessageDetail.setText(message);
    }

    private void addEventListener() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnSendDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                message = txtMessageDetail.getText().toString();

                if (message.length() > 0) {
                    Intent data = new Intent();
                    data.putExtra(MainActivity.MESSAGE, message);

                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    Toast toast = Toast.makeText(DetailActivity.this, getResources().getString(R.string.no_text), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
                    toast.show();
                }
            }
        });
    }

}
