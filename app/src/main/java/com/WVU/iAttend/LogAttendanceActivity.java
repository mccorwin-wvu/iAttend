package com.WVU.iAttend;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LogAttendanceActivity extends AppCompatActivity {

    int class_id;
    int record_id;
    int user_id;
    String className;
    String startTime;
    String endTime;
    String days_of_week;
    String startDate;
    String endDate;
    double log;
    double lat;
    String loc_enabled;
    String code_enabled;
    String admin_id;
    String days;
    String current_code;
    int numberOfDays;
    boolean notAClassDay;


    private LocationManager locationManager;
    private LocationListener locationListener;

    private double updatedLog = 0;
    private double updatedLat = 0;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LogAttendanceActivity.this, UserHomePageActivity.class);
        intent.putExtra("user_id", user_id);
        LogAttendanceActivity.this.startActivity(intent);

        //super.onBackPressed();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_attendance);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView logAttendanceTextv = (TextView) findViewById(R.id.logAttendanceText);
        logAttendanceTextv.setTypeface(mytypeface);

        TextView attendanceLogClassNamev = (TextView) findViewById(R.id.attendanceLogClassName);
        attendanceLogClassNamev.setTypeface(mytypeface);

        TextView attendanceLogTimev = (TextView) findViewById(R.id.attendanceLogTime);
        attendanceLogTimev.setTypeface(mytypeface);

        TextView attendanceLogLocationv = (TextView) findViewById(R.id.attendanceLogLocation);
        attendanceLogLocationv.setTypeface(mytypeface);

        TextView attendanceLogCodev = (TextView) findViewById(R.id.attendanceLogCode);
        attendanceLogCodev.setTypeface(mytypeface);

        final TextView attendanceLogBoxv = (TextView) findViewById(R.id.attendanceLogBox);
        attendanceLogBoxv.setTypeface(mytypeface);

        TextView attendanceLogButtonv = (TextView) findViewById(R.id.attendanceLogButton);
        attendanceLogButtonv.setTypeface(mytypeface);

        final Button attendanceLogButton = (Button) findViewById(R.id.attendanceLogButton);

        Intent intent = getIntent();

        user_id = intent.getIntExtra("user_id",0);

         class_id = intent.getIntExtra("class_id",0);
         record_id = intent.getIntExtra("record_id",0);
         className = intent.getStringExtra("className");
         startTime = intent.getStringExtra("startTime");
         endTime = intent.getStringExtra("endTime");
         days_of_week = intent.getStringExtra("days_of_week");
         startDate = intent.getStringExtra("startDate");
         endDate = intent.getStringExtra("endDate");
         log = intent.getDoubleExtra("log",0);
         lat = intent.getDoubleExtra("lat",0);
         loc_enabled = intent.getStringExtra("loc_enabled");
         code_enabled = intent.getStringExtra("code_enabled");
         admin_id = intent.getStringExtra("admin_id");
         days = intent.getStringExtra("dates");
         numberOfDays = intent.getIntExtra("numberOfDays",0);
        current_code = intent.getStringExtra("current_code");





        if(className == null){


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {



                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){




                            int class_id = jsonResponse.getInt("class_id");
                            String className = jsonResponse.getString("class_name");
                            String startTime = jsonResponse.getString("start_time");
                            String endTime = jsonResponse.getString("end_time");
                            String days_of_week = jsonResponse.getString("days_of_week");
                            String startDate = jsonResponse.getString("start_date");
                            String endDate = jsonResponse.getString("end_date");
                            double log = Double.parseDouble(jsonResponse.getString("log"));
                            double lat = Double.parseDouble(jsonResponse.getString("lat"));
                            String loc_enabled = jsonResponse.getString("loc_enabled");
                            String code_enabled = jsonResponse.getString("code_enabled");
                            String admin_id = jsonResponse.getString("admin_id");
                            String current_code = jsonResponse.getString("current_code");
                            String days = jsonResponse.getString("dates");
                            int numberOfDays = jsonResponse.getInt("numberOfDays");

                            Intent intent = new Intent(LogAttendanceActivity.this, LogAttendanceActivity.class);


                            intent.putExtra("user_id", user_id);

                            intent.putExtra("class_id", class_id);
                            intent.putExtra("record_id", record_id);
                            intent.putExtra("className", className);
                            intent.putExtra("startTime",startTime );
                            intent.putExtra("endTime", endTime);
                            intent.putExtra("days_of_week", days_of_week);
                            intent.putExtra("startDate", startDate);
                            intent.putExtra("endDate", endDate);
                            intent.putExtra("log", log);
                            intent.putExtra("lat", lat);
                            intent.putExtra("loc_enabled", loc_enabled);
                            intent.putExtra("code_enabled", code_enabled);
                            intent.putExtra("admin_id", admin_id);
                            intent.putExtra("current_code", current_code);
                            intent.putExtra("dates", days);
                            intent.putExtra("numberOfDays", numberOfDays);



                            LogAttendanceActivity.this.startActivity(intent);




                        }

                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LogAttendanceActivity.this);
                            builder.setMessage("Class Does Not Exist").setNegativeButton("Retry",null).create().show();
                        }

                    }
                    catch (JSONException e){
                        e.printStackTrace();

                    }


                }
            };


            ClassDataRequest classDataRequest = new ClassDataRequest(Integer.toString(class_id), responseListener);
            RequestQueue queue = Volley.newRequestQueue(LogAttendanceActivity.this);
            queue.add(classDataRequest);

        }

        else {

            attendanceLogClassNamev.setText("Class Name: " + className);

            String[] timeArr = startTime.split("\\$");


            DateTime classTime1 = new DateTime(2000, 1, 1, Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]));


            DateTimeFormatter fm12hr = DateTimeFormat.forPattern("hh:mm:a");

            attendanceLogTimev.setText("Class Time: " + fm12hr.print(classTime1));

            if (Integer.parseInt(loc_enabled) == 1) {
                attendanceLogLocationv.setText("Location Verification: Enabled");
            } else {
                attendanceLogLocationv.setText("Location Verification: Disabled");
            }

            if (Integer.parseInt(code_enabled) == 1) {
                attendanceLogCodev.setText("Code Verification: Enabled");

            } else {
                attendanceLogCodev.setText("Code Verification: Disabled");
                attendanceLogBoxv.setFocusable(false);
            }

            notAClassDay = false;

            DateTime now = new DateTime();

            String dateNow = now.dayOfMonth() + "$" + now.monthOfYear() + "$" + now.year();

            String[] dayArr = days.split("-");

            for (String day : dayArr) {
                if (day.compareTo(dateNow) == 0) {
                    notAClassDay = true;
                    break;
                }
            }

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    updatedLog = location.getLongitude();
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


            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                            , 10);
                }
                return;
            } else {
                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

            }


            attendanceLogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (notAClassDay == true) {

                        Toast toast = Toast.makeText(getApplicationContext(), "Today is not a Class Day.",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        return;

                    }

                    if (current_code.compareTo(attendanceLogBoxv.getText().toString()) != 0) {

                        Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Code.",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        return;

                    }

                    if (checkCords(updatedLat, updatedLog, lat, log) == false) {


                        Toast toast = Toast.makeText(getApplicationContext(), "Location is not Close enough, keep trying.",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                        return;


                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Attendance Recorded",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();


                }


            });


        }
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


    static public boolean checkCords(double lat, double log, double classLat, double classLog){


        if((Math.abs(lat - classLat) < 000.001) && (Math.abs(log - classLog) < 000.001) ){
            return true;
        }

        return false;


    }



}
