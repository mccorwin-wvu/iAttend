package com.WVU.iAttend;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 1/15/2017.
 */

public class RegisterDeviceRequest extends StringRequest {

    private static final String CONFIRM_REQUEST_URL = "http://98.239.148.75/RegisterDev.php";
    private Map<String,String> params;
    public RegisterDeviceRequest(String user_id, String device_code, Response.Listener<String> listener){

        super(Method.POST,CONFIRM_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("device_code",device_code);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}