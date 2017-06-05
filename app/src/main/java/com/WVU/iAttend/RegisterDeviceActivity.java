package com.WVU.iAttend;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
    private User user;


    // makes sure that when the back button is pressed it goes back to the user home page activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterDeviceActivity.this, UserHomePageActivity.class);
        intent.putExtra("user", user);
        finish();
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
        final TextView registerText = RegisterDeviceTextv;
        final Button registerButton = (Button) RegisterDeviceButtonv;

        final ProgressDialog loadingDialog = new ProgressDialog(RegisterDeviceActivity.this);
        loadingDialog.setCancelable(false);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setTitle("Loading......");
        loadingDialog.setMessage("Please Wait");


        // gets the user object that was sent to this activity

        user = (User) intent.getSerializableExtra("user");


        // creates a new DateTime object

        final DateTime jodaTime = new DateTime();


        // epochMin gets the current epoch time in MIN
        // lastRegisteredEpochmin is the last time the registered device was pressed
        // the device_code is formatted as "androidID$epochMin"

        final long epochMin = (jodaTime.getMillis() / 1000) / 60;
        final long lastRegisteredEpochmin;
        String currentCode = "0";


        // if the account has never registed its device code would be '-1' and would be able to register
        if (user.getDevice_code().compareTo(Integer.toString(-1)) == 0) {

            currentCode = "-1";
            lastRegisteredEpochmin = epochMin;

        }


        // else, the device_code is split and stored in a string array
        else {
            String[] device_string = user.getDevice_code().split("\\$");

            // current code is the last registered androidID
            currentCode = device_string[0];

            // lastRegisteredEpochmin is the time that the account was last registered
            lastRegisteredEpochmin = Long.parseLong(device_string[1]);


        }


        // gets the current androidID of the current device
        @SuppressLint("HardwareIds") final String androidID = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);


        // if the androidID in the user object does not match the current device's androidID then the image is set to a red x, else it is set to a green check mark

        if (currentCode.compareTo(androidID) != 0) {

            image.setImageResource(R.drawable.red_x);
            registerText.setText("Device Not Registered");


        } else {

            image.setImageResource(R.drawable.green_check);
            registerText.setText("Device Registered");

        }


        // flag that determines if the user can register, there is a 60 min cool down when the register button is pressed

        final boolean clearToReg;

        // if the lastRegisteredEpochmin + 60min is less than the current epoch time in min then the clearToReg is set to true OR the current code is "-1" which means that this account has not been registered before
        if (((lastRegisteredEpochmin + 60) < epochMin) || currentCode.compareTo("-1") == 0) {
            clearToReg = true;
        }
        // if it has not been more than 60 min then the flag is set to false
        else {
            clearToReg = false;
        }

        // timeToWait is the time to wait in min until the device can be registered again
        final long timeToWait = (lastRegisteredEpochmin + 60) - (epochMin);

        System.out.println("time to wait: " + timeToWait);
        System.out.println(androidID);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loadingDialog.show();

                Intent intent = new Intent(RegisterDeviceActivity.this, RegisterDeviceActivity.class);

                // if the clearToReg flag is false then it displays a Toast that tells the user how long they need to wait to register again

                if (clearToReg == false) {


                    intent.putExtra("user", user);


                    Toast toast = Toast.makeText(getApplicationContext(), "you must wait " + timeToWait + " minutes",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    finish();

                    RegisterDeviceActivity.this.startActivity(intent);
                } else {

                    // creates a new device code

                    String newDeviceCode = androidID + "$" + epochMin;


                    // response listen that relaunches the activity with the new device code
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                loadingDialog.hide();

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    // sets the new device code in the user object
                                    user.setDevice_code(jsonResponse.getString("device_code"));

                                    Intent intent = new Intent(RegisterDeviceActivity.this, RegisterDeviceActivity.class);


                                    // toast that lets the user know that the device has been registered

                                    Toast toast = Toast.makeText(getApplicationContext(), "Your Device Has Been Registered",
                                            Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                    // sends the user object to the new activity
                                    intent.putExtra("user", user);

                                    finish();

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


                    // sends the new device code to the server

                    RegisterDeviceRequest registerDeviceRequest = new RegisterDeviceRequest(Integer.toString(user.getUser_id()), newDeviceCode, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(RegisterDeviceActivity.this);

                    queue.add(registerDeviceRequest);


                }


            }


        });


    }


}
