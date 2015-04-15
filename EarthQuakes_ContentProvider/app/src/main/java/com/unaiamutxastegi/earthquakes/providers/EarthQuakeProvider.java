package com.unaiamutxastegi.earthquakes.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

public class EarthQuakeProvider extends ContentProvider {

    public static final Uri CONTENT_URI = Uri.parse("content://com.unaiamutxastegi.provider.earthquakes/earthquakes");

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final UriMatcher uriMatcher;
    private EarthQuakeOpenHelper earthQuakeOpenHelper;

    public static class Columns implements BaseColumns {
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_MAGNITUDE = "magnitude";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LONG = "long";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_TIME = "time";
    }

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.unaiamutxastegi.provider.earthquakes", "earthquakes", ALLROWS);
        uriMatcher.addURI("com.unaiamutxastegi.provider.earthquakes", "earthquakes/#", SINGLE_ROW);
    }



    public EarthQuakeProvider() {
    }


    @Override
    public String getType(Uri uri) {
        // Return a	string	that identifies	the	MIME type
        // for a Content Provider URI
        switch (uriMatcher.match(uri)) {

            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.unaiamutxastegi.provider.earquakes";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.unaiamutxastegi.provider.earquakes";
            default:
                throw new IllegalArgumentException("Unsupported	URI:	" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = earthQuakeOpenHelper.getWritableDatabase();

/*
        switch (uriMatcher.match(uri)) {
            case ALLROWS:
                String table EarthQuakeOpenHelper.DATABASE_TABLE;
            default:
                break;
        }
*/
        String nullColumnHack = null;

        long id = db.insert(earthQuakeOpenHelper.DATABASE_TABLE, nullColumnHack, values);

        // Construc and return	the	URI of the newly inserted row.
        if (id > -1) {

            // Construct and return	the	URI	of the newly inserted row.
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);

            // Notify any observers	of the change in the data set.
            getContext().getContentResolver().notifyChange(insertedId, null);

            return insertedId;

        } else

            return null;

    }

    @Override
    public boolean onCreate() {
        earthQuakeOpenHelper = new EarthQuakeOpenHelper(getContext(), EarthQuakeOpenHelper.DATABASE_NAME,
                null, EarthQuakeOpenHelper.DATABASE_VERSION);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db;
        try {
            db = earthQuakeOpenHelper.getWritableDatabase();

        } catch (SQLiteException ex) {

            db = earthQuakeOpenHelper.getReadableDatabase();
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {

            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(Columns.COLUMN_ID + " = ?");
                selectionArgs = new String[]{rowID};

            default:
                break;

        }

        queryBuilder.setTables(EarthQuakeOpenHelper.DATABASE_TABLE);

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;

    }
    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthquakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;

        //	SQL	Statement	to	create	a	new	database.
        private static final String DATABASE_CREATE = "CREATE TABLE	" + DATABASE_TABLE
                + "(" + Columns.COLUMN_ID + " TEXT PRIMARY KEY, "
                + Columns.COLUMN_PLACE + " TEXT, "
                + Columns.COLUMN_MAGNITUDE + " REAL, "
                + Columns.COLUMN_LAT + " REAL, "
                + Columns.COLUMN_LONG + " REAL, "
                + Columns.COLUMN_URL + " TEXT,"
                + Columns.COLUMN_TIME + " INTEGER)";

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
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
