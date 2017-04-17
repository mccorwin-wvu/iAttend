package com.WVU.iAttend;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class EditAttendanceActivity extends AppCompatActivity {

    private int record_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attendance);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

        TextView editAttendanceTitlev = (TextView) findViewById(R.id.editAttendanceTitle);
        editAttendanceTitlev.setTypeface(mytypeface);

        TextView presentv = (TextView) findViewById(R.id.present);
        presentv.setTypeface(mytypeface);

        TextView absentv = (TextView) findViewById(R.id.absent);
        absentv.setTypeface(mytypeface);

        TextView editButtonv = (TextView) findViewById(R.id.editButton);
        editButtonv.setTypeface(mytypeface);

        final RadioButton present = (RadioButton) findViewById(R.id.present);
        final RadioButton absent = (RadioButton) findViewById(R.id.absent);
        final DatePicker editDate = (DatePicker) findViewById(R.id.editDayPicker);
        final Button editButton = (Button) findViewById(R.id.editButton);

        Intent intent = getIntent();

        record_id = Integer.parseInt(intent.getStringExtra("record_id"));




        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = editDate.getYear();
                int month = editDate.getMonth();
                int day = editDate.getDayOfMonth();
                String type;
                month++;
                String date = Integer.toString(day)+"$"+Integer.toString(month)+"$"+Integer.toString(year)+"-";

                DateTime now = new DateTime();
                DateTime selected = new DateTime(year,month,day,0,0);

                if(now.isBefore(selected)){

                    Toast toast = Toast.makeText(getApplicationContext(), "The Day you Selected Has not passed yet.",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    //return;

                }

                if(!present.isChecked() && !absent.isChecked()){
                    Toast toast = Toast.makeText(getApplicationContext(), "You need to either select present or absent.",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    return;



                }

                if(present.isChecked()){
                    type = "1";

                }
                else {

                    type = "0";

                }










                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String result = jsonResponse.getString("success");

                            if (result.compareTo("success") == 0) {









                                Toast toast = Toast.makeText(getApplicationContext(), "Day Edited.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();






                            }
                            else if (result.compareTo("notFound") == 0) {








                                Toast toast = Toast.makeText(getApplicationContext(), "The Date you selected has not been found.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                                return;






                            }

                            else if (result.compareTo("alreadyPresent") == 0) {








                                Toast toast = Toast.makeText(getApplicationContext(), "The Date you are trying to edit is already listed as 'Present' .",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                                return;






                            }






                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditAttendanceActivity.this);
                                builder.setMessage("Class Does Not Exist").setNegativeButton("Retry", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                };


                EditRequest editRequest = new EditRequest(Integer.toString(record_id), type, date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditAttendanceActivity.this);
                queue.add(editRequest);


            }


        });


    }
}
