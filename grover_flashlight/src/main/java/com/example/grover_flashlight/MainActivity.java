package com.example.grover_flashlight;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public CameraManager cameraManager;
    private static final String TAG = "MainActivity.java";
    private boolean flashLightOn;
    private static final int CAMERA_REQUEST = 50;
    private ImageView imageFlashlight;
    private Button buttonEnable;

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

    }

    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        try {
            String cameraId = null;

            if (cameraManager != null) {
                cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, true);
                flashLightOn = true;
                buttonEnable.setText(R.string.enable_flash);
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
                buttonEnable.setText(getString(R.string.disable_flash));
                flashLightOn = false;

                //imageFlashlight.setImageResource(R.drawable.btn_switch_off);
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
