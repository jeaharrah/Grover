package com.example.jeaha.grovermodule;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.database.Cursor;
import android.database.sqlite.*;

public class MainActivity extends AppCompatActivity {
    // Database name
    private static final String DATABASE_NAME = "GroverDB";

    // Table names
    private static final String TABLE_FLASHLIGHT = "Flashlight";
    private static final String TABLE_GPS = "GPS";
    private static final String TABLE_ACCELEROMETER = "Accelerometer";
    private static final String TABLE_TEMPERATURE = "Temperature";

    // Table Columns Names
    private static final String FLASHLIGHT_KEY_ID = "FlashlightID";
    private static final String FLASHLIGHT_STATE = "FlashlightState";

    private static final String GPS_KEY_ID = "GpsID";
    private static final String GPS_NAME = "GpsName";

    private static final String ACCELEROMETER_KEY_ID = "AccelerometerID";
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
