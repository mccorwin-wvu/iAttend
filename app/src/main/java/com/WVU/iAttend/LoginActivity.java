package com.WVU.iAttend;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    private static final int CONFIRMED = 1;
    private static final int NOTCONFIRMED = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Setting the fount for all the Views in the activity

        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView iAttendTextv = (TextView) findViewById(R.id.iAttendText);
        iAttendTextv.setTypeface(mytypeface);

        TextView logingEmailBoxv = (TextView) findViewById(R.id.logingEmailBox);
        logingEmailBoxv.setTypeface(mytypeface);

        TextView logingPassBoxv = (TextView) findViewById(R.id.logingPassBox);
        logingPassBoxv.setTypeface(mytypeface);

        TextView logingButtonv = (TextView) findViewById(R.id.logingButton);
        logingButtonv.setTypeface(mytypeface);

        TextView logingRegv = (TextView) findViewById(R.id.logingReg);
        logingRegv.setTypeface(mytypeface);

        TextView forgotPasswordHomePagev = (TextView) findViewById(R.id.forgotPasswordHomePage);
        forgotPasswordHomePagev.setTypeface(mytypeface);


        // making a separate variable of each view to use in the rest of the method

        final EditText loginEmail = (EditText) logingEmailBoxv;
        final EditText loginPass = (EditText) logingPassBoxv;
        final Button loginButton = (Button) logingButtonv;
        final TextView registerText = logingRegv;
        final TextView forgotPasswordText = forgotPasswordHomePagev;



        // sends the user to the forgot password activity

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(forgotPasswordIntent);
            }
        });

        // sends the user to the register activity

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });


        // when the login button is pressed the app gets the email and password entered and checks if they match with the email and password in the database

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // contains the the current contents of the email and password box at the time the login button is pressed

                final String email = loginEmail.getText().toString();
                final String password = loginPass.getText().toString();


                // the response from the server that returns a string of JSON responses

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    // try block to catch any exceptions with the JSON string

                    try {

                        // a JSON response object that contains all the data

                        JSONObject jsonResponse = new JSONObject(response);

                        // a boolean from the server to make sure the email entered is in the database

                        boolean success = jsonResponse.getBoolean("success");

                        // if the email is in the database

                        if(success){

                            // get data from the JSON string and stores it in the User object

                            User user = new User(jsonResponse.getInt("user_id"), jsonResponse.getString("first_name"), jsonResponse.getString("last_name"), jsonResponse.getString("email"), jsonResponse.getString("password")
                                    , jsonResponse.getInt("confirmed"), jsonResponse.getString("register_code"), jsonResponse.getString("device_code"), jsonResponse.getString("admin_class_list"), jsonResponse.getString("user_class_list"));



                            // checks to see if the user is confirmed

                            if(user.getConfirmed() == NOTCONFIRMED){


                                // if the user entered is not confirmed then it prints a Toast and sends the user to the Confirm user activity

                                Intent intent = new Intent(LoginActivity.this,ConfirmNewUser.class);

                                LoginActivity.this.startActivity(intent);

                                Toast toast = Toast.makeText(getApplicationContext(), "Please confirm your account before logging in.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            }

                            // else if the user is confirmed

                            else {

                                // puts all the needed data into an intent object and sends that object along with the user to the User Home Page activity

                                Intent intent = new Intent(LoginActivity.this, UserHomePageActivity.class);

                                intent.putExtra("user",user);

                                LoginActivity.this.startActivity(intent);
                            }

                        }


                        // if the email or password does not match anything in the database

                        else{

                            // creates an alert dialog telling the user that they entered an invalid email or password

                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Invalid Email or Password").setNegativeButton("Retry",null).create().show();
                        }

                    }

                    // catches JSON exceptions

                    catch (JSONException e){
                        e.printStackTrace();

                    }


                    }
                };


                // loginRequest sends the email and password to the server and will trigger responseLister when the server responds
                //

                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);



            }
        });


    }
}
