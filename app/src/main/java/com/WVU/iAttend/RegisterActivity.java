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

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

        TextView regFirstNamev = (TextView) findViewById(R.id.regFirstName);
        regFirstNamev.setTypeface(mytypeface);

        TextView regLastNamev = (TextView) findViewById(R.id.regLastName);
        regLastNamev.setTypeface(mytypeface);

        TextView regEmailv = (TextView) findViewById(R.id.regEmail);
        regEmailv.setTypeface(mytypeface);

        TextView regPasswordv = (TextView) findViewById(R.id.regPassword);
        regPasswordv.setTypeface(mytypeface);

        TextView regPasswordConv = (TextView) findViewById(R.id.regPasswordCon);
        regPasswordConv.setTypeface(mytypeface);

        TextView regTextv = (TextView) findViewById(R.id.regText);
        regTextv.setTypeface(mytypeface);

        TextView regButtonv = (TextView) findViewById(R.id.regButton);
        regButtonv.setTypeface(mytypeface);


        final EditText firstNameText = (EditText) regFirstNamev;
        final EditText lastNameText = (EditText) regLastNamev;
        final EditText emailText = (EditText) regEmailv;
        final EditText passText = (EditText) regPasswordv;
        final EditText passConText = (EditText) regPasswordConv;
        final Button buttonRegister = (Button) regButtonv;


        // when the register button is clicked

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // flag for checking the correctness of the field
                boolean noError = true;
                // object to generate the code to be sent to the users email
                CodeGenerator regCode = new CodeGenerator();
                // fields that the user has filled out except the register code, that is filled by the CodeGenerator object
                final String first_name = firstNameText.getText().toString();
                final String last_name = lastNameText.getText().toString();
                final String email = emailText.getText().toString();
                final String password = passText.getText().toString();
                final String password_con = passConText.getText().toString();
                final String register_code = regCode.nextCode().toString();


                // if any of these next if statements returns true then the noError flag is set to false

                // the first name has to be greater than 1 char

                if (first_name.length() < 1) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Enter a First Name").setNegativeButton("Retry", null).create().show();

                }


                // the last name has to be greater than 1 char

                else if (last_name.length() < 1) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Enter a Last Name").setNegativeButton("Retry", null).create().show();

                }

                // the email has to be greater than 1 char

                else if (email.length() < 1) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Enter an Email Address").setNegativeButton("Retry", null).create().show();

                }

                // making sure they enter a password and its length is at least 6 chars

                else if (password.length() <= 5) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Password Must Contain at Least 6 Characters").setNegativeButton("Retry", null).create().show();
                }

                // making sure that the two passwords match

                else if (!password.equals(password_con)) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Passwords Do Not Match").setNegativeButton("Retry", null).create().show();
                }

                // first name regex

                else if (!first_name.matches("^[a-z ,.'-]+$")) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("First Name Can Only Contain Letters").setNegativeButton("Retry", null).create().show();
                }

                // last name regex

                else if (!last_name.matches("^[a-z ,.'-]+$")) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Last Name Can Only Contain Letters").setNegativeButton("Retry", null).create().show();
                }

                // email regex

                else if (!email.matches("[a-z0-9]+([-+._][a-z0-9]+){0,2}@.*?(\\.(a(?:[cdefgilmnoqrstuwxz]|ero|(?:rp|si)a)|b(?:[abdefghijmnorstvwyz]iz)|c(?:[acdfghiklmnoruvxyz]|at|o(?:m|op))|d[ejkmoz]|e(?:[ceghrstu]|du)|f[ijkmor]|g(?:[abdefghilmnpqrstuwy]|ov)|h[kmnrtu]|i(?:[delmnoqrst]|n(?:fo|t))|j(?:[emop]|obs)|k[eghimnprwyz]|l[abcikrstuvy]|m(?:[acdeghklmnopqrstuvwxyz]|il|obi|useum)|n(?:[acefgilopruz]|ame|et)|o(?:m|rg)|p(?:[aefghklmnrstwy]|ro)|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|t(?:[cdfghjklmnoprtvwz]|(?:rav)?el)|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw])\\b){1,2}")) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Enter A Valid Email Address").setNegativeButton("Retry", null).create().show();

                }


                // response from the server confirming that the unconfirmed user has been inserted into the table

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                // sends the user to the confirm user activity

                                Intent intent = new Intent(RegisterActivity.this, ConfirmNewUser.class);
                                RegisterActivity.this.startActivity(intent);

                                // SendMailTask sends an email containing the register code to the email that the user entered in the registration page

                                new SendMailTask(RegisterActivity.this).execute("iattend.no.respond@gmail.com",
                                        "iattend@wvu", email, "REGISTER CODE FOR iAttend", "Register code: " + register_code);

                                // alerts the user that an email has been sent

                                Toast toast = Toast.makeText(getApplicationContext(), "Please check your email for a confirmation code.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            } else {

                                // if the email that the user entered in already CONFIRMED it cannot be used again

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Email is already Registered").setNegativeButton("Retry", null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };



                /* if the flag does not return as false then the data is sent to the server and a new user is added to the table, the server will then send
                   a response that the email the user entered is not already in use by a confirmed user and then sends an email.
                */

                if (noError == true) {
                    RegisterRequest registerRequest = new RegisterRequest(first_name, last_name, email, password, register_code, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }


            }
        });


    }
}
