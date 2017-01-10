package com.WVU.iAttend;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

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


        final EditText firstNameText = (EditText) findViewById(R.id.regFirstName);
        final EditText lastNameText = (EditText) findViewById(R.id.regLastName);
        final EditText emailText = (EditText) findViewById(R.id.regEmail);
        final EditText passText = (EditText) findViewById(R.id.regPassword);
        final EditText passConText = (EditText) findViewById(R.id.regPasswordCon);

        final Button buttonRegister = (Button) findViewById(R.id.regButton);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean noError = true;
                final String first_name = firstNameText.getText().toString();
                final String last_name = lastNameText.getText().toString();
                final String email = emailText.getText().toString();
                final String password = passText.getText().toString();
                final String password_con = passConText.getText().toString();

                if(first_name.length()<1){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Enter a First Name").setNegativeButton("Retry",null).create().show();

                }
                else if(last_name.length()<1){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Enter a Last Name").setNegativeButton("Retry",null).create().show();

                }

                else if(email.length()<1){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Enter an Email Address").setNegativeButton("Retry",null).create().show();

                }

                else if(password.length()<1){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Enter an Password").setNegativeButton("Retry",null).create().show();

                }

                else if(password_con.length()<1){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Confirm the Password").setNegativeButton("Retry",null).create().show();

                }

                else if(!password.equals(password_con)){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(password +"  "+ password_con).setNegativeButton("Retry",null).create().show();
                }
                else if(!first_name.matches("[a-zA-Z]+")){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("First Name Can Only Contain Letters").setNegativeButton("Retry",null).create().show();
                }
                else if(!last_name.matches("[a-zA-Z]+")) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Last Name Can Only Contain Letters").setNegativeButton("Retry", null).create().show();
                }

                else if(!email.matches("[a-z0-9]+([-+._][a-z0-9]+){0,2}@.*?(\\.(a(?:[cdefgilmnoqrstuwxz]|ero|(?:rp|si)a)|b(?:[abdefghijmnorstvwyz]iz)|c(?:[acdfghiklmnoruvxyz]|at|o(?:m|op))|d[ejkmoz]|e(?:[ceghrstu]|du)|f[ijkmor]|g(?:[abdefghilmnpqrstuwy]|ov)|h[kmnrtu]|i(?:[delmnoqrst]|n(?:fo|t))|j(?:[emop]|obs)|k[eghimnprwyz]|l[abcikrstuvy]|m(?:[acdeghklmnopqrstuvwxyz]|il|obi|useum)|n(?:[acefgilopruz]|ame|et)|o(?:m|rg)|p(?:[aefghklmnrstwy]|ro)|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|t(?:[cdfghjklmnoprtvwz]|(?:rav)?el)|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw])\\b){1,2}")){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Please Enter A Valid Email Address").setNegativeButton("Retry", null).create().show();

                }
                else if(password.length() <=5){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Password Must Contain at Least 6 Characters").setNegativeButton("Retry",null).create().show();
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Email is already Registered").setNegativeButton("Retry",null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };




                if(noError == true){
                RegisterRequest registerRequest = new RegisterRequest(first_name,last_name,email,password,responseListener);

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);}



            }
        });













    }
}
