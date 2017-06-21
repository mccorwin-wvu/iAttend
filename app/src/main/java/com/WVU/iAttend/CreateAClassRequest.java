package com.WVU.iAttend;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 1/4/2017.
 */

public class CreateAClassRequest extends StringRequest{

    // old url = "http://98.239.148.75/CreateAClass.php"

    private static final String REGISTER_REQUEST_URL = "https://strawless-overload.000webhostapp.com/CreateAClass.php";
    private Map<String,String> params;
    public CreateAClassRequest(String class_name, String start_time, String end_time, String days_of_week, String start_date, String end_date,
                               String join_code, String log, String lat, String loc_enabled, String code_enabled, String admin_id, String class_roster, String dates, String numberOfDays, String current_code, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("class_name",class_name);
        params.put("start_time",start_time);
        params.put("end_time",end_time);
        params.put("days_of_week",days_of_week);
        params.put("start_date",start_date);
        params.put("end_date",end_date);
        params.put("join_code",join_code);
        params.put("log",log);
        params.put("lat",lat);
        params.put("loc_enabled",loc_enabled);
        params.put("code_enabled",code_enabled);
        params.put("admin_id",admin_id);
        params.put("class_roster",class_roster);
        params.put("dates",dates);
        params.put("numberOfDays",numberOfDays);
        params.put("current_code",current_code);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
