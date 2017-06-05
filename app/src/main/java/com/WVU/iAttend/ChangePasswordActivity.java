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


public class ChangePasswordActivity extends AppCompatActivity {

    private User user;

    // sends the user to the user home page when the back button in pressed

    public void onBackPressed() {
        Intent intent = new Intent(ChangePasswordActivity.this, UserHomePageActivity.class);
        intent.putExtra("user", user);
        finish();
        ChangePasswordActivity.this.startActivity(intent);

        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

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


        final EditText oldPass = (EditText) UserHomeChangePasswordOldPasswordv;
        final EditText newPass = (EditText) UserHomeChangePasswordNewPasswordv;
        final EditText confirmNewPass = (EditText) UserHomeChangePasswordConfirmv;
        final Button buttonPass = (Button) UserHomeChangePasswordButtonv;

        final ProgressDialog loadingDialog = new ProgressDialog(ChangePasswordActivity.this);
        loadingDialog.setCancelable(false);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setTitle("Loading......");
        loadingDialog.setMessage("Please Wait");

        Intent intent = getIntent();


        // gets the user object that was sent from the user home page activity
        user = (User) intent.getSerializableExtra("user");


        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // boolean flag that makes sure that the password that the user types in is valid

                boolean noError = true;


                // current password that the user enters
                final String oldP = oldPass.getText().toString();
                // new password that the user enters
                final String newP = newPass.getText().toString();
                // the new password again
                final String confrimP = confirmNewPass.getText().toString();

                // if the user does not enter the correct current password
                if (oldP.compareTo(user.getPassword()) != 0) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                    builder.setMessage("Wrong Current Password").setNegativeButton("Retry", null).create().show();

                }


                // if the user does not enter a new password
                else if (newP.length() < 1) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                    builder.setMessage("Please Enter a New Password").setNegativeButton("Retry", null).create().show();

                }

                // if the new password is not at lease 6 chars
                else if (newP.length() <= 5) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                    builder.setMessage("Password Must Contain at Least 6 Characters").setNegativeButton("Retry", null).create().show();

                }


                // if  the new password and the confirm password does not match
                else if (newP.compareTo(confrimP) != 0) {
                    noError = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                    builder.setMessage("Passwords Do Not Match").setNegativeButton("Retry", null).create().show();

                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            loadingDialog.hide();

                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(ChangePasswordActivity.this, UserHomePageActivity.class);
                                // sets the new user password in the user object
                                user.setPassword(newP);
                                // sends the user object with the updated password to the user home page activity
                                intent.putExtra("user", user);

                                finish();

                                ChangePasswordActivity.this.startActivity(intent);


                                Toast toast = Toast.makeText(getApplicationContext(), "Password Has Been Changed.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                                builder.setMessage("User not Found").setNegativeButton("Retry", null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };


                if (noError == true) {

                    loadingDialog.show();


                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(Integer.toString(user.getUser_id()), newP, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);
                    queue.add(changePasswordRequest);
                }


            }


        });


    }
}
