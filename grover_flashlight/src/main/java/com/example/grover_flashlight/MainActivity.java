package com.example.grover_flashlight;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.camera2.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public CameraManager cameraManager;
    private static final String TAG = "MainActivity.java";
    private boolean flashLightOn;
    private static final int CAMERA_REQUEST = 50;
    private ImageView imageFlashlight;
    private TextView flashStatus;
    private Button buttonEnable;

    // Database name
    private static final String DATABASE_NAME = "GroverDB";
    // Table name
    private static final String TABLE_FLASHLIGHT = "FLASHLIGHT";
    // Column names
    private static final String FLASHLIGHT_KEY_ID = "FlashlightID";
    private static final String FLASHLIGHT_NAME = "FlashlightName";

    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        System.out.println("Made it through the content setup");

        // Retrieve the button widget to activate and deactivate the flash
        buttonEnable = (Button) findViewById(R.id.buttonEnable);

        final boolean hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager
                .FEATURE_CAMERA_FLASH);

        // Check whether the flash permission has been enabled on the device
        boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        buttonEnable.setEnabled(!isEnabled);

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

        GridView gridF = (GridView) findViewById(R.id.gridViewFlashlight);

        try {
            // MODE_PRIVATE = only this application
            db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            // Remove the table and start fresh
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASHLIGHT);

            db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_FLASHLIGHT + "(" + FLASHLIGHT_KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FLASHLIGHT_NAME + " VARCHAR);");

            db.execSQL("INSERT INTO " + TABLE_FLASHLIGHT + "(" + FLASHLIGHT_NAME + ") VALUES " +
                    "('ON');");
            db.execSQL("INSERT INTO " + TABLE_FLASHLIGHT + "(" + FLASHLIGHT_NAME + ") VALUES " +
                    "('OFF');");

            ArrayList<String> list1 = new ArrayList<String>();

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, list1);

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

        } catch (Exception e) {
            Log.d(TAG, e.toString());
            Log.e("Error:", e.toString());
            Toast.makeText(getApplicationContext(), "Database Issue: " + e.toString(), Toast
                    .LENGTH_LONG).show();
        }
        finally { // This will always execute
            Log.d("Data written:", " ");
            db.close();
        }

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
