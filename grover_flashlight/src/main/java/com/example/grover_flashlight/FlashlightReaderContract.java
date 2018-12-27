/*

package com.example.grover_flashlight;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class FlashlightReaderContract extends MainActivity {

    final String TAG = "FlashlightContract";

    // Create the package-private constructor
    public FlashlightReaderContract() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FlashlightEntry.FlashlightReaderDbHelper mDbHelper = new
                FlashlightEntry.FlashlightReaderDbHelper(getBaseContext());
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        SQLiteDatabase db = openOrCreateDatabase(FlashlightEntry.FlashlightReaderDbHelper
                        .DATABASE_NAME,
                MODE_PRIVATE, null);

        db.execSQL("INSERT INTO " + FlashlightEntry.TABLE_NAME + " (" + FlashlightEntry
                .COLUMN_NAME_MODE + ") VALUES" +
                "('ON');");

        db.execSQL("INSERT INTO " + FlashlightEntry.TABLE_NAME + " (" + FlashlightEntry
                .COLUMN_NAME_MODE + ", " + FlashlightEntry.COLUMN_NAME_DATE + ") VALUES" +
                "('ON', NOW());");


        db.execSQL("SELECT " + FlashlightEntry.COLUMN_NAME_DATE + ", " + FlashlightEntry
                .COLUMN_NAME_MODE + " FROM " + FlashlightEntry.TABLE_NAME + ";"
        );


        try {
            ArrayList<String> list = new ArrayList<String>();
            ArrayList<String> list2 = new ArrayList<String>();

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, list);

            // Select a record and set the cursor object
            Cursor cursor = db.rawQuery("Select * FROM " + FlashlightEntry.TABLE_NAME, null);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    list.add(cursor.getString(1));
                    list2.add(cursor.getString(0));
                    for (int i = 0; i < list2.size(); i++) {
                        String msg = list2.get(i) + ": " + list.get(i);
                        Log.i("DATABASE ITEM", msg);
                    }

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.d(TAG, e.toString());
            Log.e("Error:", e.toString());
            Toast.makeText(getApplicationContext(), "Database Issue: " + e.toString(), Toast
                    .LENGTH_LONG).show();
        } finally { // This will always execute
            Log.d("Data written:", databaseList().toString());
            db.close();

        }
    }
}*/
