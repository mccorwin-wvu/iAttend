package com.WVU.iAttend;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.LocationListener;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class CreateAClassActivity extends AppCompatActivity {

    private double log = 0;
    private double lat = 0;
    CodeGenerator regCode = new CodeGenerator();
    private String joinCodeCode = regCode.nextCode().toString();

    private double updatedLog = 0;
    private double updatedLat = 0;


    private boolean permissionGranted = false;

    private Criteria criteria;
    private String bestProvider;

    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_aclass);


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

        TextView radioButtonMonv = (TextView) findViewById(R.id.radioButtonMon);
        TextView radioButtonTuesv = (TextView) findViewById(R.id.radioButtonTues);
        TextView radioButtonWedv = (TextView) findViewById(R.id.radioButtonWed);
        TextView radioButtonThursv = (TextView) findViewById(R.id.radioButtonThurs);
        TextView radioButtonFriv = (TextView) findViewById(R.id.radioButtonFri);
        TextView radioButtonSatv = (TextView) findViewById(R.id.radioButtonSat);
        TextView radioButtonSunv = (TextView) findViewById(R.id.radioButtonSun);
        radioButtonMonv.setTypeface(mytypeface);
        radioButtonTuesv.setTypeface(mytypeface);
        radioButtonWedv.setTypeface(mytypeface);
        radioButtonThursv.setTypeface(mytypeface);
        radioButtonFriv.setTypeface(mytypeface);
        radioButtonSatv.setTypeface(mytypeface);
        radioButtonSunv.setTypeface(mytypeface);


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


        final EditText className = (EditText) findViewById(R.id.ClassName);
        final TimePicker startTimePicker = (TimePicker) findViewById(R.id.StarttimePicker);
        final TimePicker endTimePicker = (TimePicker) findViewById(R.id.endtimePicker);
        final RadioButton mon = (RadioButton) findViewById(R.id.radioButtonMon);
        final RadioButton tues = (RadioButton) findViewById(R.id.radioButtonTues);
        final RadioButton wed = (RadioButton) findViewById(R.id.radioButtonWed);
        final RadioButton thurs = (RadioButton) findViewById(R.id.radioButtonThurs);
        final RadioButton fri = (RadioButton) findViewById(R.id.radioButtonFri);
        final RadioButton sat = (RadioButton) findViewById(R.id.radioButtonSat);
        final RadioButton sun = (RadioButton) findViewById(R.id.radioButtonSun);
        final DatePicker startDate = (DatePicker) findViewById(R.id.StartDayPicker);
        final DatePicker endDate = (DatePicker) findViewById(R.id.EndDatePicker);
        final TextView joinCode = (TextView) findViewById(R.id.JoinCode);
        final Button setLocationButton = (Button) findViewById(R.id.LocationButton);
        final Switch locationSwitch = (Switch) findViewById(R.id.LocationSwitch);
        final Switch codeSwitch = (Switch) findViewById(R.id.CodeSwitch);
        final Button createButton = (Button) findViewById(R.id.createButton);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                updatedLog= location.getLongitude();
                updatedLat = location.getLatitude();


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


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        else{
            locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

        }










        startDate.setMinDate(System.currentTimeMillis() - 1000);

        joinCode.setText(joinCodeCode);

        Intent intent = getIntent();

        final int user_id = intent.getIntExtra("user_id", 0);

        setLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                log = updatedLog;
                lat = updatedLat;


                Toast toast = Toast.makeText(getApplicationContext(), "Longitude = " + log + "\n Latitude = " + lat,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();


            }


        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String classNameFinal = className.getText().toString();
                final String startTimeFinal = Integer.toString(startTimePicker.getCurrentHour()) +"$"+ Integer.toString(startTimePicker.getCurrentMinute());
                final String endTimeFinal = Integer.toString(endTimePicker.getCurrentHour()) +"$"+ Integer.toString(endTimePicker.getCurrentMinute());
                String daysFinal ="";
                final String startDayFinal=Integer.toString(startDate.getDayOfMonth())+"$"+Integer.toString(startDate.getMonth())+"$"+Integer.toString(startDate.getYear());
                final String endDayFinal=Integer.toString(endDate.getDayOfMonth())+"$"+Integer.toString(endDate.getMonth())+"$"+Integer.toString(endDate.getYear());
                final String joinCodeFinal = joinCodeCode;
                final String logFinal = Double.toString(log);
                final String latFinal = Double.toString(lat);
                final String locationEnabled;
                final String codeEnabled;
                final String admin_id = Integer.toString(user_id);
                final String classRosterFinal = "$";
                boolean noError = true;
                if(mon.isChecked()){daysFinal = daysFinal.concat("mon$");}if(tues.isChecked()){daysFinal = daysFinal.concat("tues$");}if(wed.isChecked()){daysFinal = daysFinal.concat("wed$");}if(thurs.isChecked()){daysFinal = daysFinal.concat("thurs$");}if(fri.isChecked()){daysFinal = daysFinal.concat("fri$");}if(sat.isChecked()){daysFinal = daysFinal.concat("sat$");}if(sun.isChecked()){daysFinal = daysFinal.concat("sun$");}
                Calendar startDateCal = Calendar.getInstance();
                Calendar endDateCal = Calendar.getInstance();
                startDateCal.set(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
                endDateCal.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth());

                if(endDateCal.getTimeInMillis() - startDateCal.getTimeInMillis() < 604800000){

                    Toast toast = Toast.makeText(getApplicationContext(), "Start Date and End Date must be at least 1 week apart.",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    noError = true;
                    return;

                }


                if(locationSwitch.isChecked()){
                    locationEnabled = "1";
                }
                else{
                    locationEnabled = "0";
                }

                if(codeSwitch.isChecked()){
                    codeEnabled = "1";
                }
                else{
                    codeEnabled = "0";
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(CreateAClassActivity.this, UserHomePageActivity.class);




                                Toast toast = Toast.makeText(getApplicationContext(), "Class created.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                intent.putExtra("user_id", user_id);

                                CreateAClassActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateAClassActivity.this);
                                builder.setMessage("Error, Class not Created").setNegativeButton("Retry",null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };




                if(noError == true){
                    CreateAClassRequest createAClassRequest = new CreateAClassRequest(classNameFinal,startTimeFinal,endTimeFinal,daysFinal, startDayFinal, endDayFinal, joinCodeFinal, logFinal, latFinal, locationEnabled, codeEnabled, admin_id, classRosterFinal, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(CreateAClassActivity.this);
                    queue.add(createAClassRequest);}



            }


        });






    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:

                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

                break;
            default:
                break;
        }
    }







}



