package com.WVU.iAttend;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RosterMenuActivity extends AppCompatActivity {
    private int user_id;
    private String record_id;
    private String first_name;
    private String last_name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_menu);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

        TextView rosterMenuTextv = (TextView) findViewById(R.id.rosterMenuText);
        rosterMenuTextv.setTypeface(mytypeface);

        TextView rosterMenuRecv = (TextView) findViewById(R.id.rosterMenuRec);
        rosterMenuRecv.setTypeface(mytypeface);

        TextView rosterMenuEditv = (TextView) findViewById(R.id.rosterMenuEdit);
        rosterMenuEditv.setTypeface(mytypeface);

        TextView rosterMenuRemovev = (TextView) findViewById(R.id.rosterMenuRemove);
        rosterMenuRemovev.setTypeface(mytypeface);

        Button record = (Button) findViewById(R.id.rosterMenuRec);
        Button edit = (Button) findViewById(R.id.rosterMenuEdit);
        Button remove = (Button) findViewById(R.id.rosterMenuRemove);


        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id",0);
        record_id = intent.getStringExtra("record_id");
        first_name = intent.getStringExtra("first_name");
        last_name = intent.getStringExtra("last_name");
        email = intent.getStringExtra("email");

        rosterMenuTextv.setText(last_name+", "+first_name+", "+email);


        record.setOnClickListener(new View.OnClickListener() {
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

                            if (success) {

                                Intent intent = new Intent(RosterMenuActivity.this, AttendanceRecordActivity.class);

                                intent.putExtra("dates", dates);
                                intent.putExtra("dates_present", dates_present);





                                RosterMenuActivity.this.startActivity(intent);



                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RosterMenuActivity.this);
                                builder.setMessage("Not Found").setNegativeButton("Retry", null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };





                RecordRequest recordRequest = new RecordRequest(record_id, responseListener);

                RequestQueue queue = Volley.newRequestQueue(RosterMenuActivity.this);
                queue.add(recordRequest);







            }


        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RosterMenuActivity.this, EditAttendanceActivity.class);

                intent.putExtra("record_id", record_id);



                RosterMenuActivity.this.startActivity(intent);






            }


        });





        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RosterMenuActivity.this, UserHomePageActivity.class);

                intent.putExtra("user_id", user_id);



                RosterMenuActivity.this.startActivity(intent);






            }


        });
















    }
}
