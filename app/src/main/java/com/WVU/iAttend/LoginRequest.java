package com.WVU.iAttend;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 1/5/2017.
 */



//old server URL == "http://98.239.148.75/Login.php"

//subclass that sends a string of data to the PHP file, their is a separate subclass for each type of communication with the server


public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://strawless-overload.000webhostapp.com/Login.php";
    private Map<String,String> params;
    public LoginRequest(String email, String password, Response.Listener<String> listener){

        super(Request.Method.POST, LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }




}
