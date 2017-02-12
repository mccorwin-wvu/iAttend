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

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView UserHomeChangePasswordTextv = (TextView) findViewById(R.id.UserHomeChangePasswordText);
        UserHomeChangePasswordTextv.setTypeface(mytypeface);

        TextView UserHomeChangePasswordOldPasswordv = (TextView) findViewById(R.id.UserHomeChangePasswordOldPassword);
        UserHomeChangePasswordOldPasswordv.setTypeface(mytypeface);

        TextView UserHomeChangePasswordNewPasswordv = (TextView) findViewById(R.id.UserHomeChangePasswordNewPassword);
        UserHomeChangePasswordNewPasswordv.setTypeface(mytypeface);

        TextView UserHomeChangePasswordConfirmv = (TextView) findViewById(R.id.UserHomeChangePasswordConfirm);
        UserHomeChangePasswordConfirmv.setTypeface(mytypeface);

        TextView UserHomeChangePasswordButtonv = (TextView) findViewById(R.id.UserHomeChangePasswordButton);
        UserHomeChangePasswordButtonv.setTypeface(mytypeface);


        final EditText oldPass = (EditText) findViewById(R.id.UserHomeChangePasswordOldPassword);
        final EditText newPass = (EditText) findViewById(R.id.UserHomeChangePasswordNewPassword);
        final EditText confirmNewPass = (EditText) findViewById(R.id.UserHomeChangePasswordConfirm);
        final Button buttonPass = (Button) findViewById(R.id.UserHomeChangePasswordButton);

        Intent intent = getIntent();

        final String oldPassword = intent.getStringExtra("password");
        final int user_id = intent.getIntExtra("user_id",0);



        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean noError = true;



                final String oldP = oldPass.getText().toString();
                final String newP = newPass.getText().toString();
                final String confrimP = confirmNewPass.getText().toString();

                 if(oldP.compareTo(oldPassword)!=0){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                    builder.setMessage("Wrong Current Password").setNegativeButton("Retry",null).create().show();

                }




                else if(newP.length()<1){
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                    builder.setMessage("Please Enter a New Password").setNegativeButton("Retry",null).create().show();

                }


                 else if(newP.length()<=5){
                     noError = false;
                     AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                     builder.setMessage("Password Must Contain at Least 6 Characters").setNegativeButton("Retry",null).create().show();

                 }


                 else if(newP.compareTo(confrimP)!=0){
                     noError = false;
                     AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                     builder.setMessage("Passwords Do Not Match").setNegativeButton("Retry",null).create().show();

                 }






                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(ChangePasswordActivity.this, UserHomePageActivity.class);
                                intent.putExtra("user_id", user_id);
                                ChangePasswordActivity.this.startActivity(intent);



                                Toast toast = Toast.makeText(getApplicationContext(), "Password Has Been Changed.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                                builder.setMessage("User not Found").setNegativeButton("Retry",null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };




                if(noError == true){
                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(Integer.toString(user_id), newP, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);
                    queue.add(changePasswordRequest);}













            }


        });



















    }
}
