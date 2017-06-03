package com.WVU.iAttend;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 1/25/2017.
 */

public class ForgotPasswordRequest extends StringRequest {

    // old url = "http://98.239.148.75/ForgotPass.php"

    private static final String REGISTER_REQUEST_URL = "https://strawless-overload.000webhostapp.com/ForgotPass.php";
    private Map<String,String> params;
    public ForgotPasswordRequest(String email, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();

        params.put("email",email);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
