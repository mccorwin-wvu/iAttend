package com.WVU.iAttend;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.LocationListener;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static com.WVU.iAttend.Functions.hideSoftKeyboard;


public class CreateAClassActivity extends AppCompatActivity {


    // user object
    private User user;

    // latitude and longitude variables
    private double log = 0;
    private double lat = 0;


    // code generator for the Join code
    private String joinCodeCode = Functions.nextCode();


    // location object for the gps
    private Location locationGPS;


    // location object for the network
    private Location locationNET;



    // location manager object
    private LocationManager locationManager;


    // two location listeners in case a gps signal is not avaialbe it will use the network

    // location listener for the gps
    private LocationListener locationListenerGPS;

    // location listener for the network
    private LocationListener locationListenerNET;

    // send the user back to the user home page
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreateAClassActivity.this, UserHomePageActivity.class);
        intent.putExtra("user", user);
        finish();
        CreateAClassActivity.this.startActivity(intent);

        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_aclass);

        setupUI(findViewById(R.id.createAClassParr));

        Intent intent = getIntent();


        // gets the user object from the home page

        user = (User) intent.getSerializableExtra("user");

        // setting the font for all the text

        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

        TextView createAClassTextv = (TextView) findViewById(R.id.createAClassText);
        createAClassTextv.setTypeface(mytypeface);

        TextView ClassNamev = (TextView) findViewById(R.id.ClassName);
        ClassNamev.setTypeface(mytypeface);

        TextView StartTimeTextv = (TextView) findViewById(R.id.StartTimeText);
        StartTimeTextv.setTypeface(mytypeface);

        TextView endTImeTextv = (TextView) findViewById(R.id.endTImeText);
        endTImeTextv.setTypeface(mytypeface);

        TextView DaysOfTheWeekTextv = (TextView) findViewById(R.id.DaysOfTheWeekText);
        DaysOfTheWeekTextv.setTypeface(mytypeface);



        TextView buttonMonv = (TextView) findViewById(R.id.radioButtonMon);
        TextView buttonTuesv = (TextView) findViewById(R.id.radioButtonTues);
        TextView buttonWedv = (TextView) findViewById(R.id.radioButtonWed);
        TextView buttonThursv = (TextView) findViewById(R.id.radioButtonThurs);
        TextView buttonFriv = (TextView) findViewById(R.id.radioButtonFri);
        TextView buttonSatv = (TextView) findViewById(R.id.radioButtonSat);
        TextView buttonSunv = (TextView) findViewById(R.id.radioButtonSun);
        buttonMonv.setTypeface(mytypeface);
        buttonTuesv.setTypeface(mytypeface);
        buttonWedv.setTypeface(mytypeface);
        buttonThursv.setTypeface(mytypeface);
        buttonFriv.setTypeface(mytypeface);
        buttonSatv.setTypeface(mytypeface);
        buttonSunv.setTypeface(mytypeface);


        TextView StartDayTextv = (TextView) findViewById(R.id.StartDayText);
        StartDayTextv.setTypeface(mytypeface);

        TextView EndDayTextv = (TextView) findViewById(R.id.EndDayText);
        EndDayTextv.setTypeface(mytypeface);

        TextView JoinCodeTextv = (TextView) findViewById(R.id.JoinCodeText);
        JoinCodeTextv.setTypeface(mytypeface);

        TextView JoinCodev = (TextView) findViewById(R.id.JoinCode);
        JoinCodev.setTypeface(mytypeface);

        TextView LocationButtonv = (TextView) findViewById(R.id.LocationButton);
        LocationButtonv.setTypeface(mytypeface);

        TextView LocationSwitchv = (TextView) findViewById(R.id.LocationSwitch);
        LocationSwitchv.setTypeface(mytypeface);

        TextView CodeSwitchv = (TextView) findViewById(R.id.CodeSwitch);
        CodeSwitchv.setTypeface(mytypeface);

        TextView createButtonv = (TextView) findViewById(R.id.createButton);
        createButtonv.setTypeface(mytypeface);


        // setting variables for all the buttons/edittext/switches

        final EditText className = (EditText) ClassNamev;
        final TimePicker startTimePicker = (TimePicker) findViewById(R.id.StarttimePicker);
        final TimePicker endTimePicker = (TimePicker) findViewById(R.id.endtimePicker);
        final CheckBox mon = (CheckBox) buttonMonv;
        final CheckBox tues = (CheckBox) buttonTuesv;
        final CheckBox wed = (CheckBox) buttonWedv;
        final CheckBox thurs = (CheckBox) buttonThursv;
        final CheckBox fri = (CheckBox) buttonFriv;
        final CheckBox sat = (CheckBox) buttonSatv;
        final CheckBox sun = (CheckBox) buttonSunv;
        final DatePicker startDate = (DatePicker) findViewById(R.id.StartDayPicker);
        final DatePicker endDate = (DatePicker) findViewById(R.id.EndDatePicker);
        final TextView joinCode = JoinCodev;
        final Button setLocationButton = (Button) LocationButtonv;
        final Switch locationSwitch = (Switch) LocationSwitchv;
        final Switch codeSwitch = (Switch) CodeSwitchv;
        final Button createButton = (Button) createButtonv;


        // setting a location manager

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // overriding the locationListener methods to customise them for this acvtivity

        locationListenerGPS = new LocationListener() {

            // when the locationListener senses a change in coordinates it sets the 'locationGPS' to the new coordinates

            @Override
            public void onLocationChanged(Location location) {

                locationGPS = location;


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            // if the locationListener senses that the gps is disabled on the device then it will take the use directly to the setting menu to enable the gps

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);

            }
        };

        // this method is exactly the same as locationListenerGPS but it will be used when requesting for the coordinates using the phones network

        locationListenerNET = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                locationNET = location;


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);

            }
        };


        // this if else sequence is used to detect if the correct permissions are granted and if the devices SDK is 24 or higher it will run the request permissions method

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // checks to see if the fine location permission is granted and if the coarse location permission is granted

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                // if one of those permissions is not granted then it will call the requestPermissions method with a string array filled with the permissions and a code for the switch statement

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);

            }

            // if both of the permissions are granted then it will call the getLocation method

            else{
                getLocation();
            }
        }

        // if the devices SDK is 23 or lower it will directly call the getLocation method

        else{

            getLocation();

        }

        // sets the start date on the date date picker to the current time

        startDate.setMinDate(System.currentTimeMillis() - 1000);

        // sets the text of the join code text view

        joinCode.setText(joinCodeCode);


        // listener for the get location button, this sets the log and lat variables to the GPS or NETWORK location depending on which is available

        setLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the lat and log of the locationGPS variable is not '0' then it will set lat and log to the gps lat and log

                if(locationGPS.getLongitude() != 0 && locationGPS.getLatitude() !=0 ){
                    lat = locationGPS.getLatitude();
                    log = locationGPS.getLongitude();
                }

                // else it will use the NETWORK lat and log

                else{
                    lat = locationNET.getLatitude();
                    log = locationNET.getLongitude();
                }


                // displays the coordinates in a toast

                Toast toast = Toast.makeText(getApplicationContext(), "Longitude = " + log + "\n Latitude = " + lat,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();


            }


        });


        // create button listener that sends all the data to the server and creates the class

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setting the class name
                final String classNameFinal = className.getText().toString();
                // storing the start time and end time in a variable as 'HOUR$MIN'
                final String startTimeFinal = Integer.toString(startTimePicker.getCurrentHour()) +"$"+ Integer.toString(startTimePicker.getCurrentMinute());
                final String endTimeFinal = Integer.toString(endTimePicker.getCurrentHour()) +"$"+ Integer.toString(endTimePicker.getCurrentMinute());
                // a variable for the days that the class is active
                String daysFinal = "";
                // storing the start date and end date in a variable as 'MONTH$DAY$YEAR'
                final String startDayFinal=Integer.toString(startDate.getDayOfMonth())+"$"+Integer.toString(startDate.getMonth()+1)+"$"+Integer.toString(startDate.getYear());
                final String endDayFinal=Integer.toString(endDate.getDayOfMonth())+"$"+Integer.toString(endDate.getMonth()+1)+"$"+Integer.toString(endDate.getYear());
                // join code variable
                final String joinCodeFinal = joinCodeCode;
                // variable for log and lat
                final String logFinal = Double.toString(log);
                final String latFinal = Double.toString(lat);
                // location and code variables stored as '1 = enabled, 0 = disabled'
                final String locationEnabled;
                final String codeEnabled;
                // variable for the id of the use that created the class
                final String admin_id = Integer.toString(user.getUser_id());
                // empty string for the class roster
                final String classRosterFinal = "";
                // current code to log attendance
                final String current_code = Functions.nextCode();
                // boolean to for checking the user input
                boolean noError = true;
                // sets the string for the days that the class is in session, if the days are mon tues wed then the string would read mon$tues$wed
                if(mon.isChecked()){daysFinal = daysFinal.concat("mon$");}if(tues.isChecked()){daysFinal = daysFinal.concat("tues$");}if(wed.isChecked()){daysFinal = daysFinal.concat("wed$");}if(thurs.isChecked()){daysFinal = daysFinal.concat("thurs$");}if(fri.isChecked()){daysFinal = daysFinal.concat("fri$");}if(sat.isChecked()){daysFinal = daysFinal.concat("sat$");}if(sun.isChecked()){daysFinal = daysFinal.concat("sun$");}
                // two calendar objects to store the start date and end date
                Calendar startDateCal = Calendar.getInstance();
                Calendar endDateCal = Calendar.getInstance();
                // set the start date and end date
                startDateCal.set(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
                endDateCal.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth());

                // makes sure that the start date is at least 7 days apart from the end date, 604800000 is the number of milliseconds in a week


                if(endDateCal.getTimeInMillis() - startDateCal.getTimeInMillis() < 604800000){

                    Toast toast = Toast.makeText(getApplicationContext(), "Start Date and End Date must be at least 1 week apart.",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    noError = false;
                    return;

                }

                // makes sure that at least 1 char is entered for the name

                if(classNameFinal.length()<=0){

                    Toast toast = Toast.makeText(getApplicationContext(), "Must Enter a Name for the Class.",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    noError = false;
                    return;

                }

                // makes sure that 1 day is picked for when the class is in session

                if(daysFinal.compareTo("") == 0){

                    Toast toast = Toast.makeText(getApplicationContext(), "Must pick at least 1 day.",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    noError = false;
                    return;

                }


                // setting the location switch, 1 = enabled 0 = disabled

                if(locationSwitch.isChecked()){
                    locationEnabled = "1";
                }
                else{
                    locationEnabled = "0";
                }

                // setting code switch, 1 = enabled 0 = disabled

                if(codeSwitch.isChecked()){
                    codeEnabled = "1";
                }
                else{
                    codeEnabled = "0";
                }



                // uses the function getDays to return a string days that the class is in session, they are formatted as so "day$month$year-day$month$year-"

                String dates = Functions.getDays(startDayFinal,endDayFinal,daysFinal);

                // counts the number of in the session by splitting the dates string and getting the length of the array

                String numberOfDays = Integer.toString(dates.split("-").length);


                // response from the database determining if the class was successfully created

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            // if success then the user is sent back to the user home page

                            if(success){
                                Intent intent = new Intent(CreateAClassActivity.this, UserHomePageActivity.class);

                                Toast toast = Toast.makeText(getApplicationContext(), "Class created.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                intent.putExtra("user", user.getUser_id());

                                CreateAClassActivity.this.startActivity(intent);




                            }
                            // else and error message is printed
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateAClassActivity.this);
                                builder.setMessage("Error, Class not Created").setNegativeButton("Retry",null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };



                // if there is no error on input then they data is sent to the server

                if(noError == true){
                    CreateAClassRequest createAClassRequest = new CreateAClassRequest(classNameFinal,startTimeFinal,endTimeFinal,daysFinal, startDayFinal, endDayFinal, joinCodeFinal,
                            logFinal, latFinal, locationEnabled, codeEnabled, admin_id, classRosterFinal, dates, numberOfDays, current_code, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(CreateAClassActivity.this);
                    queue.add(createAClassRequest);}



            }


        });






    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getLocation();
                }



        }
    }

    // calls request location updates for both network and gps

    private void getLocation(){


        // request location updates from gps every second and calls the locationListenerGPS

        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1000, 0, locationListenerGPS);

        // request location updates from network every second and calls the locationListenerNET

        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 1000, 0, locationListenerNET);



    }

    // hides the soft keyboard when tapping anywhere outside the class name box

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(CreateAClassActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }




}



