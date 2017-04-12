package com.WVU.iAttend;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class RecordRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://98.239.148.75/RecordRequest.php";
    private Map<String,String> params;
    public RecordRequest(String record_id, Response.Listener<String> listener){

        super(Request.Method.POST, LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("record_id",record_id);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }




}