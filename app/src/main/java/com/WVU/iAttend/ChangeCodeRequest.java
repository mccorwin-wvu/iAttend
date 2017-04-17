package com.WVU.iAttend;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class ChangeCodeRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://98.239.148.75/ChangeCodeRequest.php";
    private Map<String,String> params;
    public ChangeCodeRequest(String class_id, String code, String boo, Response.Listener<String> listener){

        super(Request.Method.POST, LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("class_id",class_id);
        params.put("code",code);
        params.put("boo",boo);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }




}