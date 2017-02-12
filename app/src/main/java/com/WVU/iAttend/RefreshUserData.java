package com.WVU.iAttend;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matt on 1/26/2017.
 */


public class RefreshUserData {

    private int user_id = -1;
    private  String first_name = "";
    private String last_name= "";
    private String email= "";
    private String password= "";
    private int confirmed= -1;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public void setRegister_code(String register_code) {
        this.register_code = register_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public void setAdmin_class_list(String admin_class_list) {
        this.admin_class_list = admin_class_list;
    }

    public void setUser_class_list(String user_class_list) {
        this.user_class_list = user_class_list;
    }

    private String register_code;
    private String device_code;
    private String admin_class_list;
    private String user_class_list;

    public int getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public String getRegister_code() {
        return register_code;
    }

    public String getDevice_code() {
        return device_code;
    }

    public String getAdmin_class_list() {
        return admin_class_list;
    }

    public String getUser_class_list() {
        return user_class_list;
    }


    public RefreshUserData(String emailp, String passwordp,final Activity activity) {



        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override

            public void onResponse (String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){


                        Intent intent = new Intent();
                        intent.putExtra("first_name", "dfjvnjfnv");
                        intent.putExtra("last_name", last_name);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);


                        activity.startActivity(intent);









                    }

                    else{

                    }

                }
                catch (JSONException e){
                    e.printStackTrace();

                }


            }
        };





        LoginRequest loginRequest = new LoginRequest(emailp, passwordp, responseListener);
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(loginRequest);

        System.out.println(first_name);


    }









}



//        first_name = jsonResponse.getString("first_name");
//        last_name = jsonResponse.getString("last_name");
//        email = jsonResponse.getString("email");
//        password = jsonResponse.getString("password");
//        confirmed  = jsonResponse.getInt("confirmed");
//        register_code = jsonResponse.getString("register_code");
//        device_code = jsonResponse.getString("device_code");
//        admin_class_list = jsonResponse.getString("admin_class_list");
//        user_class_list = jsonResponse.getString("user_class_list");
