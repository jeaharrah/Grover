package com.example.grover_accel;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Penn State Abington Grover ("Ground-based Rover") Project - Accelerometer App.
 * Retrieves accelerometer data from the phone attached to the Grover
 * Code taken from tutorial at:
 * https://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
 * <p>
 * Accelerometers use the standard sensor coordinate system. In practice, this means that the
 * following conditions apply when a device is laying flat on a table in its natural orientation:
 * <p>
 * If you push the device on the left side (so it moves to the right), the x acceleration value is
 * positive. If you push the device on the bottom (so it moves away from you), the y acceleration
 * value is positive. If you push the device toward the sky with an acceleration of A m/s2, the z
 * acceleration value is equal to A + 9.81, which corresponds to the acceleration of the device (+A
 * m/s2) minus the force of gravity (-9.81 m/s2). The stationary device will have an acceleration
 * value of +9.81, which corresponds to the acceleration of the device (0 m/s2 minus the force of
 * gravity, which is -9.81 m/s2)
 */

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private static final String TAG = "Accelerometer";

    /**
     * @author Jennifer A'Harrah (jka5240)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_speedometer);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager
                .SENSOR_DELAY_NORMAL);

        System.out.println(senAccelerometer.getName());

    }

    // Float fields that will store accelerometer values for each axis
    float x, y, z;

    /**
     * @param sensorEvent
     * @author Jennifer A'Harrah (jka5240)
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];

        // Output the X accelerometer value to the output debug log
        Log.d(TAG, "onSensorChanged: X: " + sensorEvent.values[0]);
        // Output the Y accelerometer value to the output debug log
        Log.d(TAG, "onSensorChanged: Y: " + sensorEvent.values[1]);
        // Output the Z accelerometer value to the output debug log
        Log.d(TAG, "onSensorChanged: Z: " + sensorEvent.values[2]);

        TextView xTxtView = findViewById(R.id.xOutput);
        TextView yTxtView = findViewById(R.id.yOutput);
        TextView zTxtView = findViewById(R.id.zOutput);

        String formattedXString = getString(R.string.x);
        String formattedYString = getString(R.string.y);
        String formattedZString = getString(R.string.z);

        xTxtView.setText(getString(R.string.x, x));
        yTxtView.setText(getString(R.string.y, y));
        zTxtView.setText(getString(R.string.z, z));

        /*TextView xPlus = findViewById(R.id.xPlus);
        TextView xMinus = findViewById(R.id.xMinus);
        TextView yPlus = findViewById(R.id.yPlus);
        TextView yMinus = findViewById(R.id.yMinus);

        if (x > 0) {
            xPlus.setText(getString(R.string.right, x));
            xMinus.setText(getString(R.string.inactiveHalf));
            xPlus.setBackgroundColor(getColor(R.color.yellow));
            xMinus.setBackgroundColor(getColor(R.color.white));

        }

        if (x < 0) {
            xMinus.setText(getString(R.string.left, x));
            xPlus.setText(getString(R.string.inactiveHalf));
            xMinus.setBackgroundColor(getColor(R.color.colorAccent));
            xPlus.setBackgroundColor(getColor(R.color.white));

        }

        if (y > 0) {
            yPlus.setText(getString(R.string.up, y));
            yMinus.setText(getString(R.string.inactiveHalf));
            yPlus.setBackgroundColor(getColor(R.color.messenger_blue));
            yMinus.setBackgroundColor(getColor(R.color.white));

        }

        if (y < 0) {
            yMinus.setText(getString(R.string.down, y));
            yPlus.setText(getString(R.string.inactiveHalf));
            yMinus.setBackgroundColor(getColor(R.color.mediumGreen));
            yPlus.setBackgroundColor(getColor(R.color.white));

        }*/


    }

    /**
     * @param sensor   - the device sensor
     * @param accuracy - the integer accuracy of the sensor
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager
                .SENSOR_DELAY_NORMAL);
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
