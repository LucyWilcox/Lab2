package com.example.lwilcox.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FeedReaderDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedEntry.SQL_CREATE_ENTRIES);
    }

    public void addItem(String item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_ENTRY_ID, item);

        long newRowId;
        newRowId = db.insert(
                FeedEntry.TABLE_NAME,
                null,
                values);
    }

//    public void removeItem(String item) {
//        // Define 'where' part of query.
//        String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
//// Specify arguments in placeholder order.
//        String[] selectionArgs = { String.valueOf(rowId) };
//// Issue SQL statement.
//        db.delete(table_name, selection, selectionArgs);
//    }

    public ArrayList<String> readList(){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<String> results = new ArrayList<String>();
// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_ENTRY_ID
        };

// How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                FeedEntry.COLUMN_NAME_UPDATED + " DESC";

        Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        c.moveToFirst();
        while(!c.isAfterLast())  {
            results.add(c.getString(1));
            c.moveToNext();
        }
        return results;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(FeedEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}