package com.WVU.iAttend;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 1/4/2017.
 */

public class RegisterRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "http://98.239.148.75/Register.php";
    private Map<String,String> params;
    public RegisterRequest(String first_name, String last_name, String email, String password, String register_code, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("first_name",first_name);
        params.put("last_name",last_name);
        params.put("email",email);
        params.put("password",password);
        params.put("register_code",register_code);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
