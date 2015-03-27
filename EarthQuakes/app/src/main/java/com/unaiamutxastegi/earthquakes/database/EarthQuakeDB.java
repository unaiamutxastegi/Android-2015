package com.unaiamutxastegi.earthquakes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.unaiamutxastegi.earthquakes.model.EarthQuake;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakeDB {
    private EarthQuakeOpenHelper helper;
    private SQLiteDatabase db;

    public EarthQuakeDB(Context context){
        this.helper = new EarthQuakeOpenHelper(context,EarthQuakeOpenHelper.DATABASE_NAME,null,EarthQuakeOpenHelper.DATABASE_VERSION);
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
}