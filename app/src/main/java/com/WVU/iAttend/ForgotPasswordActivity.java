package com.WVU.iAttend;

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

        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView iAttendTextForgotPasswordv = (TextView) findViewById(R.id.iAttendTextForgotPassword);
        iAttendTextForgotPasswordv.setTypeface(mytypeface);

        TextView forgotPasswordEmailBoxv = (TextView) findViewById(R.id.forgotPasswordEmailBox);
        forgotPasswordEmailBoxv.setTypeface(mytypeface);

        TextView forgotPasswordButtonv = (TextView) findViewById(R.id.forgotPasswordButton);
        forgotPasswordButtonv.setTypeface(mytypeface);

        TextView forgotPasswordTextv = (TextView) findViewById(R.id.forgotPasswordText);
        forgotPasswordTextv.setTypeface(mytypeface);



        final EditText forgotPasswordEmail = (EditText) findViewById(R.id.forgotPasswordEmailBox);
        final Button forgotPasswordButton = (Button) findViewById(R.id.forgotPasswordButton);

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String email = forgotPasswordEmail.getText().toString();







                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){



                                String email = jsonResponse.getString("email");
                                String password = jsonResponse.getString("password");
                                int confirmed  = jsonResponse.getInt("confirmed");

                                if(confirmed == 0){

                                    Intent intent = new Intent(ForgotPasswordActivity.this,ConfirmNewUser.class);

                                    ForgotPasswordActivity.this.startActivity(intent);

                                    Toast toast = Toast.makeText(getApplicationContext(), "Your account in unconfirmed, please make a new one OR register it on this page.",
                                            Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                }
                                else {
                                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);


                                    new SendMailTask(ForgotPasswordActivity.this).execute("iattend.no.respond@gmail.com",
                                            "iattend@wvu",email,"PASSWORD FOR iAttend", "Password: "+password);

                                    Toast toast = Toast.makeText(getApplicationContext(), "Please check your email for your password.",
                                            Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    ForgotPasswordActivity.this.startActivity(intent);


                                }

                            }

                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                                builder.setMessage("Invalid Email").setNegativeButton("Retry",null).create().show();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();

                        }


                    }
                };





                ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ForgotPasswordActivity.this);
                queue.add(forgotPasswordRequest);









            }
        });



















    }
}
