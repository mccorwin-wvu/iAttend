package com.WVU.iAttend;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static java.security.AccessController.getContext;

public class RegisterDeviceActivity extends AppCompatActivity {
    int user_id_home;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterDeviceActivity.this, UserHomePageActivity.class);
        intent.putExtra("user_id", user_id_home);
        RegisterDeviceActivity.this.startActivity(intent);

        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_device);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

        TextView RegisterDeviceTextv = (TextView) findViewById(R.id.RegisterDeviceText);
        RegisterDeviceTextv.setTypeface(mytypeface);

        TextView RegisterDeviceButtonv = (TextView) findViewById(R.id.RegisterDeviceButton);
        RegisterDeviceButtonv.setTypeface(mytypeface);

        Intent intent = getIntent();


        ImageView image = (ImageView) findViewById(R.id.RegisterDeviceImage);
        final TextView registerText = (TextView) findViewById(R.id.RegisterDeviceText);
        final Button registerButton = (Button) findViewById(R.id.RegisterDeviceButton);


        final int user_id = intent.getIntExtra("user_id", 0);
        final String device_code = intent.getStringExtra("device_code");

        user_id_home = user_id;


        final DateTime jodaTime = new DateTime();

//        DateTimeFormatter builder = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss.SSa");
//
//        System.out.println(jodaTime.toString(builder));


        final long epochMin = (jodaTime.getMillis()/1000)/60;
        final long device_id ;
        String currentCode = "0";


        if (device_code.compareTo(Integer.toString(-1)) == 0) {

            currentCode = "-1";
            device_id = epochMin;



        } else {
            String[] device_string = device_code.split("\\$");
            currentCode = device_string[0];
            device_id = Long.parseLong(device_string[1]);


        }

        final String androidID = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

//        System.out.print("hour is " + hour + " min is " + min + " day is " + day + " year is " + year);


        if (currentCode.compareTo(androidID) != 0) {

            image.setImageResource(R.drawable.red_x);
            registerText.setText("Device Not Registered");


        } else {

            image.setImageResource(R.drawable.green_check);
            registerText.setText("Device Registered");

        }

        final boolean clearToReg;

        if(((device_id + 60) < epochMin)|| currentCode.compareTo("-1") == 0){
            clearToReg = true;
        }
        else{
            clearToReg = false;
        }


        final long timeToWait = (device_id + 60) - (epochMin);

        System.out.println("time to wait: "+timeToWait);
        System.out.println(androidID);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterDeviceActivity.this, RegisterDeviceActivity.class);

                if (clearToReg == false) {



                    intent.putExtra("user_id", user_id);
                    intent.putExtra("device_code", device_code);




                    Toast toast = Toast.makeText(getApplicationContext(), "you must wait " + timeToWait + " minutes",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();


                    RegisterDeviceActivity.this.startActivity(intent);
                } else {

                    String newDeviceCode = androidID + "$" + epochMin;



                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {

                                    int user_id = jsonResponse.getInt("user_id");
                                    String device_code = jsonResponse.getString("device_code");


                                    Intent intent = new Intent(RegisterDeviceActivity.this, RegisterDeviceActivity.class);

                                    Toast toast = Toast.makeText(getApplicationContext(), "Your Device Has Been Registered",
                                            Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                    intent.putExtra("user_id", user_id);
                                    intent.putExtra("device_code", device_code);


                                    RegisterDeviceActivity.this.startActivity(intent);


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterDeviceActivity.this);
                                    builder.setMessage("Fail").setNegativeButton("Retry", null).create().show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }
                    };


                    RegisterDeviceRequest registerDeviceRequest = new RegisterDeviceRequest(Integer.toString(user_id), newDeviceCode, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(RegisterDeviceActivity.this);

                    queue.add(registerDeviceRequest);


                }


            }


        });


    }


}
