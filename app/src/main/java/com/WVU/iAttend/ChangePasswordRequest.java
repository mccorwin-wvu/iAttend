package com.WVU.iAttend;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 2/12/2017.
 */

public class ChangePasswordRequest extends StringRequest {

    // old url = "http://98.239.148.75/ChangePassword.php"

    private static final String REGISTER_REQUEST_URL = "https://strawless-overload.000webhostapp.com/ChangePassword.php";
    private Map<String,String> params;
    public ChangePasswordRequest(String user_id, String password, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}