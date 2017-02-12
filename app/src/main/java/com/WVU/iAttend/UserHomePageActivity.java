package com.WVU.iAttend;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView user_home_textv = (TextView) findViewById(R.id.user_home_text);
        user_home_textv.setTypeface(mytypeface);

        TextView user_home_refreshv = (TextView) findViewById(R.id.user_home_refresh);
        user_home_refreshv.setTypeface(mytypeface);

        TextView UserHomeMyClassesv = (TextView) findViewById(R.id.UserHomeMyClasses);
        UserHomeMyClassesv.setTypeface(mytypeface);

        TextView UserHomeJoinedClassesv = (TextView) findViewById(R.id.UserHomeJoinedClasses);
        UserHomeJoinedClassesv.setTypeface(mytypeface);

        TextView UserHomeCreateAClassv = (TextView) findViewById(R.id.UserHomeCreateAClass);
        UserHomeCreateAClassv.setTypeface(mytypeface);

        TextView UserHomeJoinAClassv = (TextView) findViewById(R.id.UserHomeJoinAClass);
        UserHomeJoinAClassv.setTypeface(mytypeface);

        TextView UserHomeRegisterDevicev = (TextView) findViewById(R.id.UserHomeRegisterDevice);
        UserHomeRegisterDevicev.setTypeface(mytypeface);

        TextView UserHomeChangePasswordv = (TextView) findViewById(R.id.UserHomeChangePassword);
        UserHomeChangePasswordv.setTypeface(mytypeface);

        TextView UserHomeLogoutv = (TextView) findViewById(R.id.UserHomeLogout);
        UserHomeLogoutv.setTypeface(mytypeface);







        final Button refreshButton = (Button) findViewById(R.id.user_home_refresh);

        Button myClasses = (Button) findViewById(R.id.UserHomeMyClasses);
        Button joinedClasses = (Button) findViewById(R.id.UserHomeJoinedClasses);
        Button createAClass = (Button) findViewById(R.id.UserHomeCreateAClass);
        Button joinAClass = (Button) findViewById(R.id.UserHomeJoinAClass);
        Button registerDevice = (Button) findViewById(R.id.UserHomeRegisterDevice);
        Button changePassword = (Button) findViewById(R.id.UserHomeChangePassword);
        Button logout = (Button) findViewById(R.id.UserHomeLogout);



       // ListView lv = (ListView) findViewById(R.id.homeList);



        Intent intent = getIntent();
        final int user_id = intent.getIntExtra("user_id", 0);
        String first_name = intent.getStringExtra("first_name");
        String last_name = intent.getStringExtra("last_name");
        final String email = intent.getStringExtra("email");
        final String password = intent.getStringExtra("password");
        int confirmed  = intent.getIntExtra("confirmed", 0);
        String register_code = intent.getStringExtra("register_code");
        String device_code = intent.getStringExtra("device_code");
        String admin_class_list = intent.getStringExtra("admin_class_list");
        String user_class_list = intent.getStringExtra("user_class_list");



        User user = new User(user_id, first_name, last_name, email, password, confirmed, register_code, device_code, admin_class_list, user_class_list);

        if(first_name == null){


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {



                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){


                            int user_id = jsonResponse.getInt("user_id");
                            String first_name = jsonResponse.getString("first_name");
                            String last_name = jsonResponse.getString("last_name");
                            String email = jsonResponse.getString("email");
                            String password = jsonResponse.getString("password");
                            int confirmed  = jsonResponse.getInt("confirmed");
                            String register_code = jsonResponse.getString("register_code");
                            String device_code = jsonResponse.getString("device_code");
                            String admin_class_list = jsonResponse.getString("admin_class_list");
                            String user_class_list = jsonResponse.getString("user_class_list");

                            Intent intent = new Intent(UserHomePageActivity.this, UserHomePageActivity.class);

                            intent.putExtra("user_id", user_id);
                            intent.putExtra("first_name", first_name);
                            intent.putExtra("last_name", last_name);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            intent.putExtra("confirmed", confirmed);
                            intent.putExtra("register_code", register_code);
                            intent.putExtra("device_code", device_code);
                            intent.putExtra("admin_class_list", admin_class_list);
                            intent.putExtra("user_class_list", user_class_list);



                            UserHomePageActivity.this.startActivity(intent);




                        }

                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserHomePageActivity.this);
                            builder.setMessage("Refresh Fail").setNegativeButton("Retry",null).create().show();
                        }

                    }
                    catch (JSONException e){
                        e.printStackTrace();

                    }


                }
            };


            DataRequest dataRequest = new DataRequest(Integer.toString(user_id), responseListener);
            RequestQueue queue = Volley.newRequestQueue(UserHomePageActivity.this);
            queue.add(dataRequest);

        }

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){


                                int user_id = jsonResponse.getInt("user_id");
                                String first_name = jsonResponse.getString("first_name");
                                String last_name = jsonResponse.getString("last_name");
                                String email = jsonResponse.getString("email");
                                String password = jsonResponse.getString("password");
                                int confirmed  = jsonResponse.getInt("confirmed");
                                String register_code = jsonResponse.getString("register_code");
                                String device_code = jsonResponse.getString("device_code");
                                String admin_class_list = jsonResponse.getString("admin_class_list");
                                String user_class_list = jsonResponse.getString("user_class_list");

                                Intent intent = new Intent(UserHomePageActivity.this, UserHomePageActivity.class);

                                intent.putExtra("user_id", user_id);
                                intent.putExtra("first_name", first_name);
                                intent.putExtra("last_name", last_name);
                                intent.putExtra("email", email);
                                intent.putExtra("password", password);
                                intent.putExtra("confirmed", confirmed);
                                intent.putExtra("register_code", register_code);
                                intent.putExtra("device_code", device_code);
                                intent.putExtra("admin_class_list", admin_class_list);
                                intent.putExtra("user_class_list", user_class_list);



                                UserHomePageActivity.this.startActivity(intent);




                            }

                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserHomePageActivity.this);
                                builder.setMessage("Refresh Fail").setNegativeButton("Retry",null).create().show();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();

                        }


                    }
                };


                DataRequest dataRequest = new DataRequest(Integer.toString(user_id), responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserHomePageActivity.this);
                queue.add(dataRequest);



            }
        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHomePageActivity.this, ChangePasswordActivity.class);

                intent.putExtra("user_id", user_id);
                intent.putExtra("password", password);


                UserHomePageActivity.this.startActivity(intent);






            }


        });







        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.program_list, testList);
       // ArrayAdapter<String> arrayAdapter = new CustomListAdapter(this , R.layout.program_list , testList);

















    }
}
