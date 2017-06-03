package com.WVU.iAttend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

        TextView iAttendTextForgotPasswordv = (TextView) findViewById(R.id.iAttendTextForgotPassword);
        iAttendTextForgotPasswordv.setTypeface(mytypeface);

        TextView forgotPasswordEmailBoxv = (TextView) findViewById(R.id.forgotPasswordEmailBox);
        forgotPasswordEmailBoxv.setTypeface(mytypeface);

        TextView forgotPasswordButtonv = (TextView) findViewById(R.id.forgotPasswordButton);
        forgotPasswordButtonv.setTypeface(mytypeface);

        TextView forgotPasswordTextv = (TextView) findViewById(R.id.forgotPasswordText);
        forgotPasswordTextv.setTypeface(mytypeface);


        final EditText forgotPasswordEmail = (EditText) forgotPasswordEmailBoxv;
        final Button forgotPasswordButton = (Button) forgotPasswordButtonv;

        final ProgressDialog loadingDialog = new ProgressDialog(ForgotPasswordActivity.this);
        loadingDialog.setCancelable(false);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setTitle("Loading......");
        loadingDialog.setMessage("Please Wait");

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // the email of the account with the forgotten password

                final String email = forgotPasswordEmail.getText().toString();


                // response from the server that contains the email, password and that it is a confirmed user

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            loadingDialog.hide();

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            // if the email is found in the database

                            if (success) {

                                String email = jsonResponse.getString("email");
                                String password = jsonResponse.getString("password");
                                int confirmed = jsonResponse.getInt("confirmed");

                                // if the email entered is not confirmed it sends the user to the confirm user page

                                if (confirmed == 0) {

                                    Intent intent = new Intent(ForgotPasswordActivity.this, ConfirmNewUser.class);

                                    ForgotPasswordActivity.this.startActivity(intent);

                                    Toast toast = Toast.makeText(getApplicationContext(), "Your account in unconfirmed, please make a new one OR register it on this page.",
                                            Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                }

                                // if the user is confirmed the password is sent to that users email

                                else {
                                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);


                                    loadingDialog.show();

                                    new SendMailTask(ForgotPasswordActivity.this).execute("iattend.no.respond@gmail.com",
                                            "iattend@wvu", email, "PASSWORD FOR iAttend", "Password: " + password);

                                    loadingDialog.hide();

                                    Toast toast = Toast.makeText(getApplicationContext(), "Please check your email for your password.",
                                            Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    ForgotPasswordActivity.this.startActivity(intent);


                                }

                            }


                            // if the email is not found in the database

                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                                builder.setMessage("Invalid Email").setNegativeButton("Retry", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                };


                // the email is sent to the server and the email, password, and confirmed flag is returned in the responseListener

                loadingDialog.show();
                ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ForgotPasswordActivity.this);
                queue.add(forgotPasswordRequest);


            }
        });


    }
}
