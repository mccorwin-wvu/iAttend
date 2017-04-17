package com.WVU.iAttend;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MyClassesListActivity extends AppCompatActivity {


    private int user_id;
    private String class_names;
    private String record_ids;
    private String class_ids;
    private boolean noClasses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes_list);
        ListView lv = (ListView) findViewById(R.id.MyClassesListView);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);
        noClasses = intent.getBooleanExtra("noClasses",noClasses);
        class_names = intent.getStringExtra("class_names");
        class_ids = intent.getStringExtra("class_ids");


        if(noClasses == true){


            final ArrayList<String> classNameArray = new ArrayList<String>();
            final ArrayList<String> classIDArray = new ArrayList<String>();

            classNameArray.add("No Classes");


            MyListAdapter gg = new MyListAdapter(this, R.layout.list_item, classNameArray);
            lv.setAdapter(gg);




        }

        else {


            final ArrayList<String> classNameArray = new ArrayList<>(Arrays.asList(class_names.split("\\$")));
            final ArrayList<String> classIDArray = new ArrayList<>(Arrays.asList(class_ids.split("\\$")));


            MyListAdapter gg = new MyListAdapter(this, R.layout.list_item, classNameArray);
            lv.setAdapter(gg);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    if (classIDArray.get(position) == null) {
                        Toast toast = Toast.makeText(getApplicationContext(), "This Class was deleted.",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        return;
                    } else {




                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {

                                    JSONObject jsonResponse = new JSONObject(response);

                                    boolean success = jsonResponse.getBoolean("success");
                                    String class_id = jsonResponse.getString("class_id");
                                    String class_name = jsonResponse.getString("class_name");
                                    String start_time = jsonResponse.getString("start_time");
                                    String end_time = jsonResponse.getString("end_time");
                                    String days_of_week = jsonResponse.getString("days_of_week");
                                    String start_date = jsonResponse.getString("start_date");
                                    String end_date = jsonResponse.getString("end_date");
                                    String join_code = jsonResponse.getString("join_code");
                                    String log = jsonResponse.getString("log");
                                    String lat = jsonResponse.getString("lat");
                                    String loc_enabled = jsonResponse.getString("loc_enabled");
                                    String code_enabled = jsonResponse.getString("code_enabled");
                                    String admin_id = jsonResponse.getString("admin_id");
                                    String class_roster = jsonResponse.getString("class_roster");
                                    String dates = jsonResponse.getString("dates");
                                    String numberOfDays = jsonResponse.getString("numberOfDays");
                                    String current_code = jsonResponse.getString("current_code");


                                    if (success) {

                                        Intent intent = new Intent(MyClassesListActivity.this, MyClassesMenuActivity.class);

                                        intent.putExtra("class_id", class_id);
                                        intent.putExtra("user_id", user_id);
                                        intent.putExtra("class_name", class_name);
                                        intent.putExtra("start_time", start_time);
                                        intent.putExtra("days_of_week", days_of_week);
                                        intent.putExtra("start_date", start_date);
                                        intent.putExtra("end_date", end_date);
                                        intent.putExtra("join_code", join_code);
                                        intent.putExtra("log", log);
                                        intent.putExtra("lat", lat);
                                        intent.putExtra("loc_enabled", loc_enabled);
                                        intent.putExtra("code_enabled", code_enabled);
                                        intent.putExtra("admin_id", admin_id);
                                        intent.putExtra("class_roster", class_roster);
                                        intent.putExtra("dates", dates);
                                        intent.putExtra("numberOfDays", numberOfDays);
                                        intent.putExtra("current_code", current_code);







                                        MyClassesListActivity.this.startActivity(intent);



                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MyClassesListActivity.this);
                                        builder.setMessage("List Not Found").setNegativeButton("Retry", null).create().show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        };





                        ClassDataRequest classDataRequest = new ClassDataRequest(classIDArray.get(position), responseListener);

                        RequestQueue queue = Volley.newRequestQueue(MyClassesListActivity.this);
                        queue.add(classDataRequest);

                    }

                }
            });

        }

    }



}