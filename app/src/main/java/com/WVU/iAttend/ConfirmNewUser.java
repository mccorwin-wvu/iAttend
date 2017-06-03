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

public class ConfirmNewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_new_user);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

        TextView confirm_user_Buttonv = (TextView) findViewById(R.id.confirm_user_Button);
        confirm_user_Buttonv.setTypeface(mytypeface);

        TextView confirm_user_codev = (TextView) findViewById(R.id.confirm_user_code);
        confirm_user_codev.setTypeface(mytypeface);

        TextView confirm_user_emailv = (TextView) findViewById(R.id.confirm_user_email);
        confirm_user_emailv.setTypeface(mytypeface);

        TextView text_confrim_userv = (TextView) findViewById(R.id.text_confrim_user);
        text_confrim_userv.setTypeface(mytypeface);


        final EditText userEmailText = (EditText) confirm_user_emailv;
        final EditText userCodeText = (EditText) confirm_user_codev;
        final Button buttonRegister = (Button) confirm_user_Buttonv;

        final ProgressDialog loadingDialog = new ProgressDialog(ConfirmNewUser.this);
        loadingDialog.setCancelable(false);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setTitle("Loading......");
        loadingDialog.setMessage("Please Wait");


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean noError = true;
                final String email = userEmailText.getText().toString();
                final String codeText = userCodeText.getText().toString();

                // error checking for the code

                if (codeText.length() < 1) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmNewUser.this);
                    builder.setMessage("Please Enter The Code you received via email").setNegativeButton("Retry", null).create().show();

                    // error checking for the email

                } else if (!email.matches("[a-z0-9]+([-+._][a-z0-9]+){0,2}@.*?(\\.(a(?:[cdefgilmnoqrstuwxz]|ero|(?:rp|si)a)|b(?:[abdefghijmnorstvwyz]iz)|c(?:[acdfghiklmnoruvxyz]|at|o(?:m|op))|d[ejkmoz]|e(?:[ceghrstu]|du)|f[ijkmor]|g(?:[abdefghilmnpqrstuwy]|ov)|h[kmnrtu]|i(?:[delmnoqrst]|n(?:fo|t))|j(?:[emop]|obs)|k[eghimnprwyz]|l[abcikrstuvy]|m(?:[acdeghklmnopqrstuvwxyz]|il|obi|useum)|n(?:[acefgilopruz]|ame|et)|o(?:m|rg)|p(?:[aefghklmnrstwy]|ro)|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|t(?:[cdfghjklmnoprtvwz]|(?:rav)?el)|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw])\\b){1,2}")) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmNewUser.this);
                    builder.setMessage("Please Enter A Valid Email Address").setNegativeButton("Retry", null).create().show();

                }


                // response from the server

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            loadingDialog.hide();

                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            // if the response from the server is successful and the user then becomes confirmed, then the user is notified then sent to the login activity

                            if (success) {
                                Intent intent = new Intent(ConfirmNewUser.this, LoginActivity.class);
                                ConfirmNewUser.this.startActivity(intent);


                                Toast toast = Toast.makeText(getApplicationContext(), "User Confirmed Successfully.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            } else {

                                // if the code or email is not found or entered incorrectly then the user is notified to try again

                                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmNewUser.this);
                                builder.setMessage("Email or Code is Incorrect").setNegativeButton("Retry", null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };


                // if the noError flag is not false then the data is sent to the server and the server will send a response to the responseListener above

                if (noError == true) {

                    loadingDialog.show();

                    ConfirmRequest confirmRequest = new ConfirmRequest(email, codeText, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(ConfirmNewUser.this);
                    queue.add(confirmRequest);
                }


            }
        });


    }
}
