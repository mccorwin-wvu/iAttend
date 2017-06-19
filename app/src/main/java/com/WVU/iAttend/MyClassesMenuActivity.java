package com.WVU.iAttend;

import android.content.Intent;
import android.graphics.Typeface;
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

import org.json.JSONException;
import org.json.JSONObject;

public class MyClassesMenuActivity extends AppCompatActivity {

    private String class_id;
    private int user_id;
    private String class_name;
    private String start_time;
    private String end_time;
    private String days_of_week;
    private String start_date;
    private String end_date;
    private String join_code;
    private String log;
    private String lat;
    private String loc_enabled;
    private String code_enabled;
    private String admin_id;
    private String class_roster;
    private String dates;
    private String numberOfDays;
    private String current_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes_menu);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

        TextView myClassesMenuTextv = (TextView) findViewById(R.id.myClassesMenuText);
        myClassesMenuTextv.setTypeface(mytypeface);

        final TextView myClassesMenuJoinCodev = (TextView) findViewById(R.id.myClassesMenuJoinCode);
        myClassesMenuJoinCodev.setTypeface(mytypeface);

        final TextView myClassesMenuClassCodev = (TextView) findViewById(R.id.myClassesMenuClassCode);
        myClassesMenuClassCodev.setTypeface(mytypeface);

        TextView classMenuRosterv = (TextView) findViewById(R.id.classMenuRoster);
        classMenuRosterv.setTypeface(mytypeface);

        TextView classMenuNewJoinCodeButtonv = (TextView) findViewById(R.id.classMenuNewJoinCodeButton);
        classMenuNewJoinCodeButtonv.setTypeface(mytypeface);

        TextView classMenuNewClassCodeButtonv = (TextView) findViewById(R.id.classMenuNewClassCodeButton);
        classMenuNewClassCodeButtonv.setTypeface(mytypeface);

        TextView classMenudelClassButtonv = (TextView) findViewById(R.id.classMenuDeleClass);
        classMenudelClassButtonv.setTypeface(mytypeface);

        Button classMenuRoster = (Button) findViewById(R.id.classMenuRoster);
        Button joinCodeButton = (Button) findViewById(R.id.classMenuNewJoinCodeButton);
        Button classCodeButton = (Button) findViewById(R.id.classMenuNewClassCodeButton);
        Button delButton = (Button) findViewById(R.id.classMenuDeleClass);

        Intent intent = getIntent();

        user_id = intent.getIntExtra("user_id",0);

        class_id = intent.getStringExtra("class_id");
        class_name = intent.getStringExtra("class_name");
        start_time = intent.getStringExtra("start_time");
        end_time = intent.getStringExtra("end_time");
        days_of_week = intent.getStringExtra("days_of_week");
        start_date = intent.getStringExtra("start_date");
        end_date = intent.getStringExtra("end_date");
        join_code = intent.getStringExtra("join_code");
        log = intent.getStringExtra("log");
        lat = intent.getStringExtra("lat");
        loc_enabled = intent.getStringExtra("loc_enabled");
        code_enabled = intent.getStringExtra("code_enabled");
        admin_id = intent.getStringExtra("admin_id");
        class_roster = intent.getStringExtra("class_roster");
        dates = intent.getStringExtra("dates");
        numberOfDays = intent.getStringExtra("numberOfDays");
        current_code = intent.getStringExtra("current_code");


        myClassesMenuJoinCodev.setText("Join Code: " + join_code);

        myClassesMenuClassCodev.setText("Class Code: " + current_code);

        myClassesMenuTextv.setText(class_name);



        classMenuRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");
                            String first_names = jsonResponse.getString("first_names");
                            String record_ids = jsonResponse.getString("record_ids");
                            String last_names = jsonResponse.getString("last_names");
                            String emails = jsonResponse.getString("emails");
                            boolean noPeople = jsonResponse.getBoolean("noPeople");

                            if (success) {

                                Intent intent = new Intent(MyClassesMenuActivity.this, RosterListActivity.class);

                                intent.putExtra("user_id",user_id);
                                intent.putExtra("first_names", first_names);
                                intent.putExtra("record_ids", record_ids);
                                intent.putExtra("last_names", last_names);
                                intent.putExtra("emails", emails);
                                intent.getBooleanExtra("noPeople",noPeople);





                                MyClassesMenuActivity.this.startActivity(intent);



                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyClassesMenuActivity.this);
                                builder.setMessage("List Not Found").setNegativeButton("Retry", null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };





                RosterListRequest rosterListRequest = new RosterListRequest(class_roster, responseListener);

                RequestQueue queue = Volley.newRequestQueue(MyClassesMenuActivity.this);
                queue.add(rosterListRequest);











            }


        });




























        joinCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Functions regCode = new Functions();
                final String newJoinCode = regCode.nextCode().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                myClassesMenuJoinCodev.setText("Join Code: " + newJoinCode);

                                Toast toast = Toast.makeText(getApplicationContext(), "Join Code Updated",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyClassesMenuActivity.this);
                                builder.setMessage("Class Not Found").setNegativeButton("Retry", null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };


                ChangeCodeRequest changeCodeRequest = new ChangeCodeRequest(class_id, newJoinCode, "0", responseListener);

                RequestQueue queue = Volley.newRequestQueue(MyClassesMenuActivity.this);
                queue.add(changeCodeRequest);


            }


        });


        classCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Functions regCode = new Functions();
                final String newClassCode = regCode.nextCode().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                myClassesMenuClassCodev.setText("Class Code: " + newClassCode);

                                Toast toast = Toast.makeText(getApplicationContext(), "Class Code Updated",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyClassesMenuActivity.this);
                                builder.setMessage("Class Not Found").setNegativeButton("Retry", null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };


                ChangeCodeRequest changeCodeRequest = new ChangeCodeRequest(class_id, newClassCode, "1", responseListener);

                RequestQueue queue = Volley.newRequestQueue(MyClassesMenuActivity.this);
                queue.add(changeCodeRequest);


            }


        });


        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MyClassesMenuActivity.this, UserHomePageActivity.class);

                intent.putExtra("user_id", user_id);

                Toast toast = Toast.makeText(getApplicationContext(), "Class Deleted",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();




                MyClassesMenuActivity.this.startActivity(intent);





            }


        });


    }
}
