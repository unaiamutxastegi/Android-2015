package com.unaiamutxastegi.earthquakes.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.unaiamutxastegi.earthquakes.R;
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
import java.util.ArrayList;

import static android.provider.Settings.Global.getString;

/**
 * Created by cursomovil on 25/03/15.
 */
public class DownloadEarthquakesTask extends AsyncTask<String, EarthQuake, Integer> {

    public interface AddEarthQuakeInterface{
        public void addEarthQuake(EarthQuake earthQuake);
    }

    private final String EARTHQUAKE ="EARTHQUAKE";

    private JSONObject json;
    private AddEarthQuakeInterface target;

    public DownloadEarthquakesTask(AddEarthQuakeInterface target){
        this.target = target;
    }

    @Override
    protected Integer doInBackground(String... urls) {
        if (urls.length > 0){
            updateEarthQuake(urls[0]);
        }
            return null;
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);

        target.addEarthQuake(earthQuakes[0]);
    }

    private void updateEarthQuake(String earthquakeFeed) {
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

                for (int i = earthquakes.length() - 1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processEarthQuakeTask(JSONObject jsonObject) {
        try {
            EarthQuake earthQuake;
            String id = jsonObject.getString("id");
            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");

            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1),jsonCoords.getDouble(2));

            JSONObject properties = jsonObject.getJSONObject("properties");

            earthQuake = new EarthQuake();
            earthQuake.setCoords(coords);
            earthQuake.set_id(id);
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setTime(properties.getInt("time"));
            earthQuake.setUrl(properties.getString("url"));

            Log.d(EARTHQUAKE, earthQuake.toString());

            publishProgress(earthQuake);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
