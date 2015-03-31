package com.unaiamutxastegi.earthquakes.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.unaiamutxastegi.earthquakes.model.EarthQuake;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakeDB {
    private EarthQuakeOpenHelper helper;
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_MAGNITUDE = "magnitude";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LONG = "long";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_TIME = "time";
    public static final String[] result_columns = new String[]{
            COLUMN_ID, COLUMN_PLACE, COLUMN_LAT, COLUMN_LONG, COLUMN_LONG, COLUMN_MAGNITUDE, COLUMN_URL, COLUMN_TIME};

    public EarthQuakeDB(Context context) {
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthquakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;

        //	SQL	Statement	to	create	a	new	database.
        private static final String DATABASE_CREATE = "CREATE TABLE	" + DATABASE_TABLE
                + "(_id TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL, long REAL, url TEXT, time INTEGER)";

        public EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //	Simplest	case	is	to	drop	the	old	table	and	create	a	new	one.
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            //	Create	a	new	one.
            onCreate(db);
        }

    }

    public void addEarthQuake(EarthQuake earthquake) {
        if (db != null) {
            ContentValues newEarthQuake = new ContentValues();

            newEarthQuake.put(COLUMN_ID, earthquake.get_id());
            newEarthQuake.put(COLUMN_PLACE, earthquake.getPlace());
            newEarthQuake.put(COLUMN_LAT, earthquake.getCoords().getLat());
            newEarthQuake.put(COLUMN_LONG, earthquake.getCoords().getLng());
            newEarthQuake.put(COLUMN_MAGNITUDE, earthquake.getMagnitude());
            newEarthQuake.put(COLUMN_URL, earthquake.getUrl());
            newEarthQuake.put(COLUMN_TIME,earthquake.getTime().getTime());
            try {
                db.insertOrThrow(helper.DATABASE_TABLE, null, newEarthQuake);
            }catch (android.database.SQLException ex){
            }
        }
    }

    public List<EarthQuake> getAll() {
        String where = null;
        String whereArgs[]=null;


        return query(where,whereArgs);
    }

    private List<EarthQuake> query (String where, String whereArgs[]){
        String groupBy = null;
        String having = null;
        String order = null;

        Cursor cursor = db.query(helper.DATABASE_TABLE,result_columns,where,whereArgs,groupBy,having,order);
        List<EarthQuake> earthQuakes = new ArrayList<>();

        HashMap<String, Integer> indexes = new HashMap<>();

        for(int i = 0; i < result_columns.length; i++)
        {
            indexes.put(result_columns[i],getCursorIndex(cursor,result_columns[i]));
        }

        while (cursor.moveToNext())	{

            EarthQuake earthQuake = new EarthQuake();
            earthQuake.set_id(cursor.getString(indexes.get(COLUMN_ID)));
            earthQuake.setPlace(cursor.getString(indexes.get(COLUMN_PLACE)));
            earthQuake.setUrl(cursor.getString(indexes.get(COLUMN_URL)));
            earthQuake.setMagnitude(cursor.getDouble(indexes.get(COLUMN_MAGNITUDE)));
            earthQuake.setTime(cursor.getLong(indexes.get(COLUMN_TIME)));
            //earthQuake.setCoords();

            earthQuakes.add(0,earthQuake);
        }

        cursor.close();

        return earthQuakes;

    }

    public List<EarthQuake> getAllByMagnitude(int magnitude) {

        String where = COLUMN_MAGNITUDE + " >= ? ";
        String whereArgs[]= {
                String.valueOf(magnitude)
        };


        return query(where,whereArgs);
    }

    public EarthQuake getAllByID(String id) {

        String where = COLUMN_ID + " = ? ";
        String whereArgs[]= {
                id
        };

        return query(where, whereArgs).get(0);
    }

    private int getCursorIndex(Cursor cursor, String Key){
        return cursor.getColumnIndexOrThrow(Key);
    }
}
