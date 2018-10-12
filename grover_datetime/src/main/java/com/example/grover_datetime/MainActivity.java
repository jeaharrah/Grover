package com.example.grover_datetime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
// Imports for Calendar and Date
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Penn State Abington Grover ("Ground-based Rover") Project - Date/Time App.
 * This app displays the date and time of the rover phone and updates it when the button is pressed.
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

        toolbar.setNavigationIcon(R.drawable.ic_clock);

        // Set the TextView widget text with the date and time instance from the Calendar class
        Date currentTime = Calendar.getInstance().getTime();
        TextView timeTxtView = (TextView) findViewById(R.id.timeTxtView);
        timeTxtView.setText(currentTime.toString());

        int julian = convertToJulian(currentTime);
        TextView julianTxtView = (TextView) findViewById(R.id.julianTxtView);
        julianTxtView.setText(String.valueOf(julian));

    }

    /**
     * Update the date/time when the XML button is pressed
     * @return the date object for use in the convertToJulian method
     * @author Jennifer A'Harrah (jka5240)
     */
    public Date getDate() {
        Date currentTime = Calendar.getInstance().getTime();
        return currentTime;
    }

    /**
     * This method converts a given calendar date into Julian
     * @param date
     * @return the integer representation of the Julian date
     * @author Jennifer A'Harrah (jka5240)
     */
    public int convertToJulian(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        String syear = String.format(Locale.US,"%04d",year).substring(2);
        int century = Integer.parseInt(String.valueOf(((year / 100)+1)).substring(1));
        int julian = Integer.parseInt(String.format(Locale.US, "%d%s%03d",century,syear,calendar.get
                (Calendar.DAY_OF_YEAR)));

        TextView julianTxtView = (TextView) findViewById(R.id.julianTxtView);
        julianTxtView.setText(String.valueOf(julian));

        return julian;
    }

    /**
     * This method updates the date/time and Julian values displayed in the TextViews
     * @param view - so that the XML button can use this method in the onClick attribute
     * @author Jennifer A'Harrah jka5240
     */
    public void displayDateInfo(View view) {
        Date date = getDate();
        int julian = convertToJulian(date);

        TextView timeTxtView = (TextView) findViewById(R.id.timeTxtView);
        timeTxtView.setText(date.toString());

        TextView julianTxtView = (TextView) findViewById(R.id.julianTxtView);
        julianTxtView.setText(String.valueOf(julian));

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
