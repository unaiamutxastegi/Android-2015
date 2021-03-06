package com.unaiamutxastegi.earthquakes.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import com.unaiamutxastegi.earthquakes.MainActivity;
import com.unaiamutxastegi.earthquakes.R;
import com.unaiamutxastegi.earthquakes.alarm.Alarm;
import com.unaiamutxastegi.earthquakes.database.EarthQuakeDB;
import com.unaiamutxastegi.earthquakes.model.Coordinate;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;

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

public class DownloadEarthQuakeService extends Service {

    private EarthQuakeDB earthQuakeDB;
    private final String EARTHQUAKE = "EARTHQUAKE";


    @Override
    public void onCreate() {
        super.onCreate();

        earthQuakeDB = new EarthQuakeDB(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                updateEarthQuake(getString(R.string.earthquake_url));
            }
        });

        t.start();

        return Service.START_STICKY;
    }

    private Integer updateEarthQuake(String earthquakeFeed) {
        JSONObject json;
        int count = 0;

        try {
            URL url = new URL(earthquakeFeed);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(
                        httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;

                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");
                count = earthquakes.length();

                for (int i = earthquakes.length() - 1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }

                showNotifications(count);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((Integer) count);
    }

    private void processEarthQuakeTask(JSONObject jsonObject) {
        try {
            EarthQuake earthQuake;
            String id = jsonObject.getString("id");
            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");

            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1), jsonCoords.getDouble(2));

            JSONObject properties = jsonObject.getJSONObject("properties");

            earthQuake = new EarthQuake();
            earthQuake.setCoords(coords);
            earthQuake.set_id(id);
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setTime(properties.getInt("time"));
            earthQuake.setUrl(properties.getString("url"));

            earthQuakeDB.addEarthQuake(earthQuake);

            Log.d(EARTHQUAKE, earthQuake.toString());

            //publishProgress(earthQuake);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotifications(int count) {
        Intent intentToFire = new Intent(this, MainActivity.class);

        PendingIntent activityIntent = PendingIntent.getActivity(this, 0, intentToFire, 0);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.count_earthquakes, count))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(activityIntent);

        Notification notification = builder.getNotification();

        String svc = Context.NOTIFICATION_SERVICE;

        NotificationManager notificationManager = (NotificationManager) getSystemService(svc);
        int NOTIFICATION_REF = 1;

        notificationManager.notify(NOTIFICATION_REF, notification);
    }
}
