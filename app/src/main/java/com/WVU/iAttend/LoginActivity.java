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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



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

        final EditText loginEmail = (EditText) findViewById(R.id.logingEmailBox);
        final EditText loginPass = (EditText) findViewById(R.id.logingPassBox);
        final Button loginButton = (Button) findViewById(R.id.logingButton);
        final TextView registerText = (TextView) findViewById(R.id.logingReg);
        final TextView forgotPasswordText = (TextView) findViewById(R.id.forgotPasswordHomePage);




        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(forgotPasswordIntent);
            }
        });


        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);



            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String email = loginEmail.getText().toString();
                final String password = loginPass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){

                            int user_id = jsonResponse.getInt("user_id");
                            String first_name = jsonResponse.getString("first_name");
                            String last_name = jsonResponse.getString("last_name");
                            String email = jsonResponse.getString("email");
                            String password = jsonResponse.getString("password");
                            int confirmed  = jsonResponse.getInt("confirmed");
                            String register_code = jsonResponse.getString("register_code");
                            String device_code = jsonResponse.getString("device_code");
                            String admin_class_list = jsonResponse.getString("admin_class_list");
                            String user_class_list = jsonResponse.getString("user_class_list");


                            if(confirmed == 0){

                                Intent intent = new Intent(LoginActivity.this,ConfirmNewUser.class);

                                LoginActivity.this.startActivity(intent);

                                Toast toast = Toast.makeText(getApplicationContext(), "Please confirm your account before logging in.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, UserHomePageActivity.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("first_name", first_name);
                                intent.putExtra("last_name", last_name);
                                intent.putExtra("email", email);
                                intent.putExtra("password", password);
                                intent.putExtra("confirmed", confirmed);
                                intent.putExtra("register_code", register_code);
                                intent.putExtra("device_code", device_code);
                                intent.putExtra("admin_class_list", admin_class_list);
                                intent.putExtra("user_class_list", user_class_list);


                                LoginActivity.this.startActivity(intent);
                            }

                        }

                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Invalid Email or Password").setNegativeButton("Retry",null).create().show();
                        }

                    }
                    catch (JSONException e){
                        e.printStackTrace();

                    }


                    }
                };


                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);



            }
        });


    }
}
