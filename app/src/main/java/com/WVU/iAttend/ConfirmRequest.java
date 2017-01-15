package com.WVU.iAttend;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 1/15/2017.
 */

public class ConfirmRequest extends StringRequest {

    private static final String CONFIRM_REQUEST_URL = "http://98.239.148.75/Confirm.php";
    private Map<String,String> params;
    public ConfirmRequest(String email, String register_code, Response.Listener<String> listener){

        super(Method.POST,CONFIRM_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("register_code",register_code);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}