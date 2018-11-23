package com.example.grovermodule;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.database.Cursor;
import android.database.sqlite.*;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Database name
    private static final String DATABASE_NAME = "GroverDB";

    // Table names
    private static final String TABLE_FLASHLIGHT = "FLASHLIGHT";
    private static final String TABLE_GPS = "GPS";
    private static final String TABLE_ACCELEROMETER = "ACCEL";
    private static final String TABLE_TEMPERATURE = "TEMPERATURE";

    // Table Columns Names
    private static final String FLASHLIGHT_KEY_ID = "FlashlightID";
    private static final String FLASHLIGHT_NAME = "FlashlightName";
    private static final String FLASHLIGHT_STATE = "FlashlightState";

    private static final String GPS_KEY_ID = "GpsID";
    private static final String GPS_NAME = "GpsName";

    private static final String ACCELEROMETER_KEY_ID = "AccelerometerID";
    private static final String ACCELEROMETER_NAME = "AccelerometerName";
    private static final String ACCELEROMETER_X_NAME = "AccelerometerXName";
    private static final String ACCELEROMETER_Y_NAME = "AccelerometerYName";
    private static final String ACCELEROMETER_Z_NAME = "AccelerometerZName";

    private static final String TEMPERATURE_KEY_ID = "TemperatureID";
    private static final String TEMPERATURE_NAME = "TemperatureName";
    private static final String TEMPERATURE_UNIT = "TemperatureUnit";


    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve the GridView resources
        GridView gridF = (GridView) findViewById(R.id.gridViewFlashlight);
        GridView gridG = (GridView) findViewById(R.id.gridViewGps);
        GridView gridA = (GridView) findViewById(R.id.gridViewAccel);
        GridView gridT = (GridView) findViewById(R.id.gridViewTemp);

        try {
            // MODE_PRIVATE = only this application
            db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            // Remove the table and start fresh
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASHLIGHT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GPS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCELEROMETER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPERATURE);

            db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_FLASHLIGHT + "(" + FLASHLIGHT_KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FLASHLIGHT_NAME + " VARCHAR);");

            db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_GPS + "(" + GPS_KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GPS_NAME + " VARCHAR);");

            db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_ACCELEROMETER + "(" +
                    ACCELEROMETER_KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACCELEROMETER_NAME + " VARCHAR);");
            /*db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_ACCELEROMETER + "(" +
                    ACCELEROMETER_KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACCELEROMETER_X_NAME + " VARCHAR);");
            db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_ACCELEROMETER + "(" +
                    ACCELEROMETER_KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACCELEROMETER_Y_NAME + " VARCHAR);");
            db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_ACCELEROMETER + "(" +
                    ACCELEROMETER_KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACCELEROMETER_Z_NAME + " VARCHAR);");
*/
            db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_TEMPERATURE + "(" + TEMPERATURE_KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEMPERATURE_NAME + " VARCHAR);");

            /*db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_TEMPERATURE + "(" + TEMPERATURE_KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEMPERATURE_UNIT + " VARCHAR);");
*/
            db.execSQL("INSERT INTO " + TABLE_FLASHLIGHT + "(" + FLASHLIGHT_NAME + ") VALUES " +
                    "('ON');");
            db.execSQL("INSERT INTO " + TABLE_FLASHLIGHT + "(" + FLASHLIGHT_NAME + ") VALUES " +
                    "('OFF');");
            db.execSQL("INSERT INTO " + TABLE_FLASHLIGHT + "(" + FLASHLIGHT_NAME + ") VALUES " +
                    "('567');");
            db.execSQL("INSERT INTO " + TABLE_GPS + "(" + GPS_NAME + ") VALUES " +
                    "('-36.5, 125.7');");
            db.execSQL("INSERT INTO " + TABLE_GPS + "(" + GPS_NAME + ") VALUES " +
                    "('-36.5');");
            db.execSQL("INSERT INTO " + TABLE_GPS + "(" + GPS_NAME + ") VALUES " +
                    "('GPS ON');");
            db.execSQL("INSERT INTO " + TABLE_GPS + "(" + GPS_NAME + ") VALUES " +
                    "('GPS OFFLINE');");
            db.execSQL("INSERT INTO " + TABLE_ACCELEROMETER + "(" + ACCELEROMETER_NAME + ") " +
                    "VALUES " +
                    "('(X) -5, (Y) 7, (Z) -9');");
            db.execSQL("INSERT INTO " + TABLE_ACCELEROMETER + "(" + ACCELEROMETER_NAME + ") " +
                    "VALUES " +
                    "('-5, 7, -9');");
            db.execSQL("INSERT INTO " + TABLE_ACCELEROMETER + "(" + ACCELEROMETER_NAME + ") " +
                    "VALUES " +
                    "('-11, 13, -17');");
            db.execSQL("INSERT INTO " + TABLE_ACCELEROMETER + "(" + ACCELEROMETER_NAME + ") " +
                    "VALUES " +
                    "('-19');");
            /*db.execSQL("INSERT INTO " + TABLE_ACCELEROMETER + "(" + ACCELEROMETER_X_NAME + ") " +
                    "VALUES " +
                    "('1');");
            db.execSQL("INSERT INTO " + TABLE_ACCELEROMETER + "(" + ACCELEROMETER_Y_NAME + ") " +
                    "VALUES " +
                    "('2');");
            db.execSQL("INSERT INTO " + TABLE_ACCELEROMETER + "(" + ACCELEROMETER_Z_NAME + ") " +
                    "VALUES " +
                    "('3');");
            db.execSQL("INSERT INTO " + TABLE_TEMPERATURE + "(" + TEMPERATURE_UNIT + ") " +
                    "VALUES " +
                    "('Fahrenheit');");
            db.execSQL("INSERT INTO " + TABLE_TEMPERATURE + "(" + TEMPERATURE_UNIT + ") " +
                    "VALUES " +
                    "('Celsius');");*/
            db.execSQL("INSERT INTO " + TABLE_TEMPERATURE + "(" + TEMPERATURE_NAME + ") " +
                    "VALUES " +
                    "('32.0F');");
            db.execSQL("INSERT INTO " + TABLE_TEMPERATURE + "(" + TEMPERATURE_NAME + ") " +
                    "VALUES " +
                    "('32.1F');");
            db.execSQL("INSERT INTO " + TABLE_TEMPERATURE + "(" + TEMPERATURE_NAME + ") " +
                    "VALUES " +
                    "('32.2F');");
            db.execSQL("INSERT INTO " + TABLE_TEMPERATURE + "(" + TEMPERATURE_NAME + ") " +
                    "VALUES " +
                    "('32.3F');");

            /*// Insert a record another way
            ContentValues cv = new ContentValues();
            //cv.put(key, value);
            cv.put(FLASHLIGHT_NAME, "On state");
            //db.insert(table, nullColumnHack, value)
            db.insert(TABLE_FLASHLIGHT, null, cv);
*/
            ArrayList<String> list1 = new ArrayList<String>();
            ArrayList<String> list2 = new ArrayList<String>();
            ArrayList<String> list3 = new ArrayList<String>();
            ArrayList<String> list4 = new ArrayList<String>();
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, list1);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, list2);
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, list3);
            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, list4);

            // Select a record and set the cursor object
            Cursor cursor = db.rawQuery("Select * FROM " + TABLE_FLASHLIGHT, null);
            if (cursor != null && cursor.moveToPosition(0)) {
                cursor.moveToFirst();
                do {
                    list1.add(cursor.getString(1));
                    gridF.setAdapter(adapter1);
                    gridF.setVisibility(View.VISIBLE);

                } while (cursor.moveToNext());
            }

            // Select a record and set the cursor object
            Cursor cursor2 = db.rawQuery("Select * FROM " + TABLE_GPS, null);
            if (cursor2 != null) {
                cursor2.moveToFirst();
                do {
                    list2.add(cursor2.getString(1));
                    gridG.setAdapter(adapter2);
                    gridG.setVisibility(View.VISIBLE);
                } while (cursor2.moveToNext());
            }

            // Select a record and set the cursor object
            Cursor cursor3 = db.rawQuery("Select * FROM " + TABLE_FLASHLIGHT, null);
            if (cursor3 != null) {
                cursor3.moveToFirst();
                do {
                    list3.add(cursor.getString(1));
                    gridA.setAdapter(adapter3);
                    gridA.setVisibility(View.VISIBLE);
                } while (cursor3.moveToNext());
            }

            // Select a record and set the cursor object
            Cursor cursor4 = db.rawQuery("Select * FROM " + TABLE_GPS, null);
            if (cursor4 != null) {
                cursor4.moveToFirst();
                do {
                    list4.add(cursor.getString(1));
                    gridT.setAdapter(adapter4);
                    gridT.setVisibility(View.VISIBLE);
                } while (cursor4.moveToNext());
            }

            gridF.setVisibility(View.INVISIBLE);
            gridG.setVisibility(View.INVISIBLE);
            gridA.setVisibility(View.VISIBLE);
            gridT.setVisibility(View.VISIBLE);

        }
        catch (Exception e) {
            Log.d("Error:", e.toString());
            Toast.makeText(getApplicationContext(), "Database Issue: " + e.toString(), Toast
                    .LENGTH_LONG).show();
        }
        finally { // This will always execute
            Log.d("Data written:", " ");
            db.close();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
