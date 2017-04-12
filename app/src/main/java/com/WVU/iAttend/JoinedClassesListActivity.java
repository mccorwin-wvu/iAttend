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

public class JoinedClassesListActivity extends AppCompatActivity {


    private int user_id;
    private String class_names;
    private String record_ids;
    private String class_ids;
    private boolean noClasses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_classes_list);
        ListView lv = (ListView) findViewById(R.id.JoinedListView);
        Intent intent = getIntent();

        user_id = intent.getIntExtra("user_id", 0);
        noClasses = intent.getBooleanExtra("noClasses",noClasses);
        class_names = intent.getStringExtra("class_names");
        record_ids = intent.getStringExtra("record_ids");
        class_ids = intent.getStringExtra("class_ids");



        if (class_names == null || record_ids == null ) {

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        boolean success = jsonResponse.getBoolean("success");
                        class_names = jsonResponse.getString("class_names");
                        record_ids = jsonResponse.getString("record_ids");
                        class_ids = jsonResponse.getString("class_ids");
                        noClasses = jsonResponse.getBoolean("noClasses");

                        if (success) {

                            Intent intent = new Intent(JoinedClassesListActivity.this, JoinedClassesListActivity.class);

                            intent.putExtra("user_id", user_id);
                            intent.putExtra("class_names", class_names);
                            intent.putExtra("record_ids", record_ids);
                            intent.putExtra("class_ids", class_ids);
                            intent.getBooleanExtra("noClasses",noClasses);





                            JoinedClassesListActivity.this.startActivity(intent);



                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(JoinedClassesListActivity.this);
                            builder.setMessage("List Not Found").setNegativeButton("Retry", null).create().show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            };





            ClassListRequest classListRequest = new ClassListRequest(Integer.toString(user_id), responseListener);

            RequestQueue queue = Volley.newRequestQueue(JoinedClassesListActivity.this);
            queue.add(classListRequest);

        }


        if(noClasses == true){


            final ArrayList<String> classNameArray = new ArrayList<String>();
            final ArrayList<String> recordIDArray = new ArrayList<String>();
            final ArrayList<String> classIDArray = new ArrayList<String>();

            classNameArray.add("No Classes");


            MyListAdapter gg = new MyListAdapter(this, R.layout.list_item, classNameArray);
            lv.setAdapter(gg);




        }

        else {


            final ArrayList<String> classNameArray = new ArrayList<>(Arrays.asList(class_names.split("\\$")));
            final ArrayList<String> recordIDArray = new ArrayList<>(Arrays.asList(record_ids.split("\\$")));
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

                        Toast.makeText(JoinedClassesListActivity.this, "", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(JoinedClassesListActivity.this, JoinedClassMenuActivity.class);

                        intent.putExtra("record_id", recordIDArray.get(position));
                        intent.putExtra("class_name", classNameArray.get(position));
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("class_id", classIDArray.get(position));


                        JoinedClassesListActivity.this.startActivity(intent);
                    }

                }
            });

        }

    }



}
