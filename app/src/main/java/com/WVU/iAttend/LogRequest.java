package com.WVU.iAttend;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class LogRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://98.239.148.75/LogRequest.php";
    private Map<String,String> params;
    public LogRequest(String record_id, String date, Response.Listener<String> listener){

        super(Request.Method.POST, LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("record_id",record_id);
        params.put("date",date);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }




}