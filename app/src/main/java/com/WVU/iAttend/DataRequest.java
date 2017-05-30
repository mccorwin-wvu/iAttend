package com.WVU.iAttend;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 1/31/2017.
 */

public class DataRequest extends StringRequest {

    // old url = "http://98.239.148.75/DataRequest.php"


    private static final String LOGIN_REQUEST_URL = "https://strawless-overload.000webhostapp.com/DataRequest.php";
    private Map<String,String> params;
    public DataRequest(String user_id, Response.Listener<String> listener){

        super(Request.Method.POST, LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("user_id",user_id);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }




}