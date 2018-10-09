package com.example.grover_datetime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
// Imports for Calendar and Date
import java.util.Calendar;
import java.util.Date;

/**
 * Penn State Abington Grover ("Ground-based Rover") Project - Date/Time App.
 * This Grover app displays the date and time and updates it when the button is pressed.
 *
 * @author Jennifer A'Harrah (jka5240)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the TextView widget text with the date and time instance from the Calendar class
        Date currentTime = Calendar.getInstance().getTime();
        TextView timeTxtView = (TextView) findViewById(R.id.timeTxtView);
        timeTxtView.setText(currentTime.toString());

    }

    /**
     * Update the date/time when the XML button is pressed
     * @param view - so that the XML button can use this method in the onClick attribute
     * @author Jennifer A'Harrah (jka5240)
     */
    public void getDate(View view) {

        Date currentTime = Calendar.getInstance().getTime();
        TextView timeTxtView = (TextView) findViewById(R.id.timeTxtView);
        timeTxtView.setText(currentTime.toString());

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
