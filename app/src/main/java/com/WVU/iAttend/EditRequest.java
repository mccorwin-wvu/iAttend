package com.WVU.iAttend;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 1/15/2017.
 */

public class EditRequest extends StringRequest {

    private static final String CONFIRM_REQUEST_URL = "http://98.239.148.75/EditRequest.php";
    private Map<String,String> params;
    public EditRequest(String record_id, String type, String date, Response.Listener<String> listener){

        super(Method.POST,CONFIRM_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("record_id",record_id);
        params.put("type",type);
        params.put("date",date);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}