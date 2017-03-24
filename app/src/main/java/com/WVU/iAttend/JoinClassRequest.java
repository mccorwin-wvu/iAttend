package com.WVU.iAttend;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 1/31/2017.
 */

public class JoinClassRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://98.239.148.75/JoinClassRequest.php";
    private Map<String,String> params;
    public JoinClassRequest(String user_id, String first_name, String last_name, String email,  String join_code, String user_class_list, Response.Listener<String> listener){

        super(Request.Method.POST, LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("first_name",first_name);
        params.put("last_name",last_name);
        params.put("email",email);
        params.put("join_code",join_code);
        params.put("user_class_list",user_class_list);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }




}