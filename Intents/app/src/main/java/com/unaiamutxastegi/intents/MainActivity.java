package com.unaiamutxastegi.intents;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;


public class MainActivity extends ActionBarActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 2;
    public static final String MESSAGE = "message";
    private static final int SHOW_MESSAGE = 1;

    private Button btnSend;
    private Button btnPhoto;
    private TextView txtMessage;
    private TextView lblMessage;
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtainViews();

        addEventListener();
    }

    private void obtainViews() {
        btnSend = (Button) findViewById(R.id.btnSendDetail);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        lblMessage = (TextView) findViewById(R.id.lblMessage);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
    }

    private void addEventListener() {

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                message = txtMessage.getText().toString();
                if (message.length() > 0) {
                    Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                    detailIntent.putExtra(MESSAGE, message);
                    startActivityForResult(detailIntent, SHOW_MESSAGE);
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.no_text), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
                    toast.show();
                }
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }else{
            Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.no_camera), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
            toast.show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case SHOW_MESSAGE:
                    String message = data.getStringExtra(MESSAGE);
                    lblMessage.setText(message);
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imgBitmap = (Bitmap) extras.get("data");
                    imgPhoto.setImageBitmap(imgBitmap);
                    break;
            }

        }
    }
}
