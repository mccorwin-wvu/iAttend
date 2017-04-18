package com.WVU.iAttend;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class RosterListRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://98.239.148.75/RosterListRequest.php";
    private Map<String,String> params;
    public RosterListRequest(String class_roster, Response.Listener<String> listener){

        super(Request.Method.POST, LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("class_roster",class_roster);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }




}