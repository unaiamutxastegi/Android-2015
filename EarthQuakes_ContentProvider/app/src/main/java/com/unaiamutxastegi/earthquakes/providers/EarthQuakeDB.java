package com.unaiamutxastegi.earthquakes.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.unaiamutxastegi.earthquakes.model.Coordinate;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakeDB {

    private Context context;

    public EarthQuakeDB(Context context) {
        this.context = context;
    }

    public static final String[] result_columns = new String[]{
            EarthQuakeProvider.Columns.COLUMN_ID,
            EarthQuakeProvider.Columns.COLUMN_PLACE,
            EarthQuakeProvider.Columns.COLUMN_LAT,
            EarthQuakeProvider.Columns.COLUMN_LONG,
            EarthQuakeProvider.Columns.COLUMN_MAGNITUDE,
            EarthQuakeProvider.Columns.COLUMN_URL,
            EarthQuakeProvider.Columns.COLUMN_TIME};


    public void addEarthQuake(EarthQuake earthquake) {
        ContentValues newEarthQuake = new ContentValues();

        newEarthQuake.put(EarthQuakeProvider.Columns.COLUMN_ID, earthquake.get_id());
        newEarthQuake.put(EarthQuakeProvider.Columns.COLUMN_PLACE, earthquake.getPlace());
        newEarthQuake.put(EarthQuakeProvider.Columns.COLUMN_LAT, earthquake.getCoords().getLat());
        newEarthQuake.put(EarthQuakeProvider.Columns.COLUMN_LONG, earthquake.getCoords().getLng());
        newEarthQuake.put(EarthQuakeProvider.Columns.COLUMN_MAGNITUDE, earthquake.getMagnitude());
        newEarthQuake.put(EarthQuakeProvider.Columns.COLUMN_URL, earthquake.getUrl());
        newEarthQuake.put(EarthQuakeProvider.Columns.COLUMN_TIME, earthquake.getTime().getTime());

        ContentResolver cr = context.getContentResolver();
        cr.insert(EarthQuakeProvider.CONTENT_URI, newEarthQuake);
    }

    public List<EarthQuake> getAll() {
        String where = null;
        String whereArgs[] = null;


        return query(where, whereArgs);
    }

    private List<EarthQuake> query(String where, String whereArgs[]) {
        String groupBy = null;
        String having = null;
        String order = null;

        ContentResolver cr = context.getContentResolver();

        String orderBy = EarthQuakeProvider.Columns.COLUMN_TIME + " DESC";

        Cursor cursor = cr.query(EarthQuakeProvider.CONTENT_URI,result_columns,where,whereArgs,orderBy);
        List<EarthQuake> earthQuakes = new ArrayList<>();

        HashMap<String, Integer> indexes = new HashMap<>();

        for (int i = 0; i < result_columns.length; i++) {
            indexes.put(result_columns[i], getCursorIndex(cursor, result_columns[i]));
        }

        while (cursor.moveToNext()) {

            EarthQuake earthQuake = new EarthQuake();
            Coordinate coords = new Coordinate(cursor.getDouble(indexes.get(EarthQuakeProvider.Columns.COLUMN_LAT)), cursor.getDouble(indexes.get(EarthQuakeProvider.Columns.COLUMN_LONG)), 0);
            earthQuake.setCoords(coords);
            earthQuake.set_id(cursor.getString(indexes.get(EarthQuakeProvider.Columns.COLUMN_ID)));
            earthQuake.setPlace(cursor.getString(indexes.get(EarthQuakeProvider.Columns.COLUMN_PLACE)));
            earthQuake.setUrl(cursor.getString(indexes.get(EarthQuakeProvider.Columns.COLUMN_URL)));
            earthQuake.setMagnitude(cursor.getDouble(indexes.get(EarthQuakeProvider.Columns.COLUMN_MAGNITUDE)));
            earthQuake.setTime(cursor.getLong(indexes.get(EarthQuakeProvider.Columns.COLUMN_TIME)));

            earthQuakes.add(0, earthQuake);
        }

        cursor.close();

        return earthQuakes;

    }

    public List<EarthQuake> getAllByMagnitude(int magnitude) {

        String where = EarthQuakeProvider.Columns.COLUMN_MAGNITUDE + " >= ? ";
        String whereArgs[] = {
                String.valueOf(magnitude)
        };


        return query(where, whereArgs);
    }

    public EarthQuake getAllByID(String id) {

        String where = EarthQuakeProvider.Columns.COLUMN_ID + " = ? ";
        String whereArgs[] = {
                id
        };

        return query(where, whereArgs).get(0);
    }

    private int getCursorIndex(Cursor cursor, String Key) {
        return cursor.getColumnIndexOrThrow(Key);
    }
}
