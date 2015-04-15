package com.unaiamutxastegi.earthquakes.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.unaiamutxastegi.earthquakes.providers.EarthQuakeDB;
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

/**
 * Created by cursomovil on 25/03/15.
 */
public class DownloadEarthquakesTask extends AsyncTask<String, EarthQuake, Integer> {

    public interface AddEarthQuakeInterface{
        //public void addEarthQuake(EarthQuake earthQuake);
        public void notifyTotal(int total);
    }

    private final String EARTHQUAKE = "EARTHQUAKE";

    private EarthQuakeDB earthQuakeDB;


    private AddEarthQuakeInterface target;

    public DownloadEarthquakesTask(Context context, AddEarthQuakeInterface target){
        this.target = target;
        this.earthQuakeDB = new EarthQuakeDB(context);
    }

    @Override
    protected Integer doInBackground(String... urls) {
        int count = 0;

        if (urls.length > 0){
            count = updateEarthQuake(urls[0]);
        }
            return count;
    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);

        target.notifyTotal(count.intValue());
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);

        //target.addEarthQuake(earthQuakes[0]);
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


}
