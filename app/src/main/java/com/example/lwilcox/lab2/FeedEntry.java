package com.example.lwilcox.lab2;

/**
 * Created by lwilcox on 9/24/2015.
 */
import android.provider.BaseColumns;

public class FeedEntry implements BaseColumns{
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String TABLE_NAME = "IMAGES";
    public static final String COLUMN_NAME_ENTRY_ID = "LIST_ITEM";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE +  " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
