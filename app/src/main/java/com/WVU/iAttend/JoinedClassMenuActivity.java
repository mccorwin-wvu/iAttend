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

public class JoinedClassMenuActivity extends AppCompatActivity {


    private String class_name;
    private int user_id;
    private String record_id;
    private String class_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_class_menu);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

        TextView joinedListMenuTextv = (TextView) findViewById(R.id.joinedListMenuText);
        joinedListMenuTextv.setTypeface(mytypeface);

        TextView joinedMenuLogv = (TextView) findViewById(R.id.joinedMenuLog);
        joinedMenuLogv.setTypeface(mytypeface);

        TextView joinedMenuRecv = (TextView) findViewById(R.id.joinedMenuRec);
        joinedMenuRecv.setTypeface(mytypeface);

        TextView joinedMenuLeavev = (TextView) findViewById(R.id.joinedMenuLeave);
        joinedMenuLeavev.setTypeface(mytypeface);



        final Button joinedMenuLog = (Button) findViewById(R.id.joinedMenuLog);
        final Button joinedMenuRec = (Button) findViewById(R.id.joinedMenuRec);
        final Button joinedMenuLeave = (Button) findViewById(R.id.joinedMenuLeave);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);
        class_name = intent.getStringExtra("class_name");
        record_id = intent.getStringExtra("record_id");
        class_id = intent.getStringExtra("class_id");



        joinedListMenuTextv.setText(class_name);



        joinedMenuLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(JoinedClassMenuActivity.this, LogAttendanceActivity.class);

                intent.putExtra("user_id", user_id);
                intent.putExtra("class_name", class_name);
                intent.putExtra("record_id", record_id);
                intent.putExtra("class_id", class_id);




                JoinedClassMenuActivity.this.startActivity(intent);






            }


        });


        joinedMenuRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");
                            String dates = jsonResponse.getString("dates");
                            String dates_present = jsonResponse.getString("dates_present");




                            if(success){



                                Intent intent = new Intent(JoinedClassMenuActivity.this, AttendanceRecordActivity.class);

                                intent.putExtra("user_id", user_id);
                                intent.putExtra("dates", dates);
                                intent.putExtra("dates_present", dates_present);




                                JoinedClassMenuActivity.this.startActivity(intent);




                            }



                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinedClassMenuActivity.this);
                                builder.setMessage("Class Not Found").setNegativeButton("Retry",null).create().show();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();

                        }


                    }
                };





                RecordRequest recordRequest  = new RecordRequest(record_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinedClassMenuActivity.this);
                queue.add(recordRequest);





            }


        });


        joinedMenuLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(JoinedClassMenuActivity.this, UserHomePageActivity.class);

                intent.putExtra("user_id", user_id);





                JoinedClassMenuActivity.this.startActivity(intent);






            }


        });











    }
}
