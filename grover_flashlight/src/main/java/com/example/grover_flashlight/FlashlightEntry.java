/*

package com.example.grover_flashlight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class FlashlightEntry extends FlashlightReaderContractj implements BaseColumns {

    static final String TABLE_NAME = "FLASHLIGHT2";
    static final String COLUMN_NAME_MODE = "mode";
    static final String COLUMN_NAME_DATE = "date";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FlashlightEntry.TABLE_NAME + " (" +
                    FlashlightEntry._ID + " INTEGER PRIMARY KEY," +
                    FlashlightEntry.COLUMN_NAME_DATE + " VARCHAR," +
                    FlashlightEntry.COLUMN_NAME_MODE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FlashlightEntry.TABLE_NAME;


    public static class FlashlightReaderDbHelper extends SQLiteOpenHelper {
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "Grover.db";

        FlashlightReaderDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
*/
