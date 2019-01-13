package com.example.grover_flashlight;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.camera2.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.provider.BaseColumns._ID;

public class MainActivity extends AppCompatActivity {

    public CameraManager cameraManager;
    private static final String TAG = "MainActivity.java";
    private boolean flashLightOn;
    private static final int CAMERA_REQUEST = 50;
    private ImageView imageFlashlight;
    private TextView flashStatus;
    private Button buttonEnable;

    // Database name
    static final String DATABASE_NAME = "Grover.db";
    // Table name
    static final String TABLE_NAME = "Flashlight";
    // Column names
    static final String COLUMN_NAME_MODE = "mode";
    static final String COLUMN_NAME_DATE = "date";
    static final String COLUMN_NAME_ID = "ID";

    /*static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_MODE + " VARCHAR, " + COLUMN_NAME_DATE + " VARCHAR)";*/

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    SQLiteDatabase db = null;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve the button widget to activate and deactivate the flash
        buttonEnable = (Button) findViewById(R.id.buttonEnable);

        final boolean hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager
                .FEATURE_CAMERA_FLASH);

        // Check whether the flash permission has been enabled on the device
        boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        buttonEnable.setEnabled(!isEnabled);

        toolbar.setNavigationIcon(R.drawable.ic_flash_white);

        // Set onClick listener for the button
        // If pressed, the program will request permission to access the camera
        buttonEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest
                        .permission.CAMERA}, CAMERA_REQUEST);
            }
        });

        /* If the first onClick listener is called and the user grants the program's request
           to access the camera, call the methods to turn the flashlight on or off
           depending on the current on or off state of the flash.
           If there is no flash available on the device, display a Toast message.
        */
        buttonEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasCameraFlash) {
                    if (flashLightOn)
                        flashLightOff();
                    else
                        flashLightOn();
                } else {
                    Toast.makeText(MainActivity.this, "No flash available on your device", Toast
                            .LENGTH_SHORT).show();
                }
            }
        });

/*        try {
            // MODE_PRIVATE = only this application
            db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            // Remove the table and start fresh
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(SQL_CREATE_ENTRIES);*/
/*

        } catch (Exception e) {
            Log.d(TAG, e.toString());
            Log.e("Error:", e.toString());
            Toast.makeText(getApplicationContext(), "Database Issue: " + e.toString(), Toast
                    .LENGTH_LONG).show();
        }*/
        /*finally { // This will always execute
            Log.d("Data written:", " ");
            db.close();
        }*/

    }

    private void addEntry(String columnName, String entry) {
        String dateString = "";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.now();
            dateString = localDateTime.toString();
            System.out.println(dateString);
            Log.i(TAG, "If: " + dateString, null);
        } else {
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
            dateString = dateFormat.format(date);
            System.out.println(dateString);
            Log.i(TAG, "Else: " + dateString, null);
        }

        try {
            db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            //db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL("CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + " (" + COLUMN_NAME_ID + " " +
                    "INTEGER " +
                    "PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_MODE + " VARCHAR, " +
                    COLUMN_NAME_DATE + " VARCHAR)");

            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + columnName + ", " +
                    COLUMN_NAME_DATE + ")" +
                    " VALUES " + "('" + entry + "','" + dateString + "');");


        } catch (SQLException e) {
            Log.e(TAG, "SQLite error", e);
        }
    }

    public void showDbEntries(View v) {
        findViewById(R.id.flashControls).setVisibility(View.GONE);
        findViewById(R.id.listViewLayout).setVisibility(View.VISIBLE);

        try {
            db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            ArrayList<String> timeList = new ArrayList<String>();
            ArrayList<String> modeList = new ArrayList<String>();
            ArrayList<String> idList = new ArrayList<String>();
            ArrayList<String> concatenatedItem = new ArrayList<String>();



            Log.i(TAG, "===========DATABASE ENTRIES===========", null);

            // Select a record and set the cursor object
            Cursor cursor = db.rawQuery("Select * FROM " + TABLE_NAME, null);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    timeList.add(cursor.getString(2));
                    modeList.add(cursor.getString(1));
                    idList.add(cursor.getString(0));
                } while (cursor.moveToNext());
                cursor.close();

                for (int i = 0; i < modeList.size(); i++) {
                    String msg = "#" + idList.get(i) + ": " + modeList.get(i) + " - " + timeList
                            .get(i);
                    concatenatedItem.add(msg);
                    Log.i("DATABASE ITEM", msg);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, concatenatedItem);

                listView = findViewById(R.id.listViewWidget);
                listView.setAdapter(arrayAdapter);
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQLite error", e);
        }
    }

    public void hideDbEntries(View view) {
        findViewById(R.id.listViewLayout).setVisibility(View.GONE);
        findViewById(R.id.flashControls).setVisibility(View.VISIBLE);
    }

    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        flashStatus = findViewById(R.id.flashStatus);
        imageFlashlight = findViewById(R.id.imageFlashStatus);
        try {
            String cameraId = null;

            if (cameraManager != null) {
                cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, true);
                flashLightOn = true;
                // Update the button text according to the flash activation state
                buttonEnable.setText(R.string.disable_flash);
                buttonEnable.setBackgroundColor(getColor((R.color.red)));
                imageFlashlight.setImageResource(R.drawable.ic_flash);
                flashStatus.setText(R.string.flash_on);

                /*db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
                db.execSQL(SQL_DELETE_ENTRIES);
                db.execSQL(SQL_CREATE_ENTRIES);

                db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME_MODE + ") VALUES" +
                        "('ON');");
                */
                addEntry("mode", "ON");
            }

        } catch (CameraAccessException e) {
            Log.e(TAG, "Camera access failed", e);
        }
    }



    private void flashLightOff() {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            if (cameraManager != null) {
                String cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, false);
                buttonEnable.setText(getString(R.string.enable_flash));
                flashLightOn = false;
                buttonEnable.setBackgroundColor(getColor((R.color.darkGreen)));
                imageFlashlight.setImageResource(R.drawable.ic_flash_off);
                flashStatus.setText(R.string.flash_off);

                addEntry("mode", "OFF");

            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Camera access failed", e);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    buttonEnable.setEnabled(false);
                    buttonEnable.setText(getString(R.string.enable_flash));
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied for the Camera", Toast
                            .LENGTH_SHORT).show();
                }
                break;
        }
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
