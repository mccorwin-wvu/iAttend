package com.WVU.iAttend;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Process;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class UserHomePageActivity extends AppCompatActivity {
    private boolean toMannyClasses = false;
    private User user;
    private int user_id;
    private String androidID;


    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        // Setting the fount for all the Views in the activity


        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "Minecraftia-Regular.ttf");

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


        // making a separate variable of each view to use in the rest of the method


        Button refreshButton = (Button) user_home_refreshv;
        Button myClasses = (Button) UserHomeMyClassesv;
        Button joinedClasses = (Button) UserHomeJoinedClassesv;
        Button createAClass = (Button) UserHomeCreateAClassv;
        Button joinAClass = (Button) UserHomeJoinAClassv;
        Button registerDevice = (Button) UserHomeRegisterDevicev;
        Button changePassword = (Button) UserHomeChangePasswordv;
        Button logout = (Button) UserHomeLogoutv;

        final ProgressDialog loadingDialog = new ProgressDialog(UserHomePageActivity.this);
        loadingDialog.setCancelable(false);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setTitle("Loading......");
        loadingDialog.setMessage("Please Wait");



        Intent intent = getIntent();

        user = (User) intent.getSerializableExtra("user");
        user_id = intent.getIntExtra("user_id",0);

        // gets the android id of that current phone

        androidID = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        // device_string String array will have the android id linked for that account in [0] and the epoch time since that id was changed in [1]

        final String[] device_string;
        final String deviceID;
        int numberOfClasses = 0;

        // gets the number of classes that the user HAS MADE or is ADMIN OF i.e. 'my classes'

        if (user.getAdmin_class_list() != null) {
            numberOfClasses = user.getAdmin_class_list().split("\\$").length;
        }

        // limits the user to 20 or less 'my classes'

        if (numberOfClasses >= 20) {
            toMannyClasses = true;
        }


        // puts the the accounts androidID into the device_string along with the epoch time

        if (user.getDevice_code() != null) {

            device_string = user.getDevice_code().split("\\$");
            deviceID = device_string[0];

        } else {
            deviceID = "";
        }

        if(user == null){

            loadingDialog.show();


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {


                            User user = new User(jsonResponse.getInt("user_id"), jsonResponse.getString("first_name"), jsonResponse.getString("last_name"), jsonResponse.getString("email"), jsonResponse.getString("password")
                                    , jsonResponse.getInt("confirmed"), jsonResponse.getString("register_code"), jsonResponse.getString("device_code"), jsonResponse.getString("admin_class_list"), jsonResponse.getString("user_class_list"));


                            Intent intent = new Intent(UserHomePageActivity.this, UserHomePageActivity.class);

                            intent.putExtra("user", user);

                            loadingDialog.hide();

                            finish();

                            UserHomePageActivity.this.startActivity(intent);





                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserHomePageActivity.this);
                            builder.setMessage("Refresh Fail").setNegativeButton("Retry", null).create().show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }


                }
            };


            DataRequest dataRequest = new DataRequest(Integer.toString(user_id), responseListener);
            RequestQueue queue = Volley.newRequestQueue(UserHomePageActivity.this);
            queue.add(dataRequest);



        }




        // gets updated data from the server and restarts the  user home page activity with the new data

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.show();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {


                                User user = new User(jsonResponse.getInt("user_id"), jsonResponse.getString("first_name"), jsonResponse.getString("last_name"), jsonResponse.getString("email"), jsonResponse.getString("password")
                                        , jsonResponse.getInt("confirmed"), jsonResponse.getString("register_code"), jsonResponse.getString("device_code"), jsonResponse.getString("admin_class_list"), jsonResponse.getString("user_class_list"));


                                Intent intent = new Intent(UserHomePageActivity.this, UserHomePageActivity.class);

                                intent.putExtra("user", user);

                                loadingDialog.hide();

                                finish();

                                UserHomePageActivity.this.startActivity(intent);





                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserHomePageActivity.this);
                                builder.setMessage("Refresh Fail").setNegativeButton("Retry", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                };


                DataRequest dataRequest = new DataRequest(Integer.toString(user.getUser_id()), responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserHomePageActivity.this);
                queue.add(dataRequest);

            }
        });


        // sends the user along with the user object to the change password activity


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHomePageActivity.this, ChangePasswordActivity.class);


                intent.putExtra("user", user);


                UserHomePageActivity.this.startActivity(intent);


            }


        });



        // sends the user along with the user object to the register device activity


        registerDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHomePageActivity.this, RegisterDeviceActivity.class);

                intent.putExtra("user", user);

                finish();


                UserHomePageActivity.this.startActivity(intent);


            }


        });


        // sends the user along with the user object to the join a class activity


        joinAClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (deviceID.compareTo(androidID) != 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Your Account Is Not Registered To This Device, Please Register the Device First",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {


                    Intent intent = new Intent(UserHomePageActivity.this, JoinClassActivity.class);

                    intent.putExtra("user", user);


                    UserHomePageActivity.this.startActivity(intent);
                }


            }


        });


        // sends the user to the admin class list

        myClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the user does not have their device register to their app then it will not let them go to the class list

                if (deviceID.compareTo(androidID) != 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Your Account Is Not Registered To This Device, Please Register the Device First",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                boolean success = jsonResponse.getBoolean("success");

                                // String of admin class names from the server separated with a '$'

                                String class_names = jsonResponse.getString("class_names");

                                // String of admin class ids from the server separated with a '$'

                                String class_ids = jsonResponse.getString("class_ids");

                                // boolean value that is true if the user has no admin classes

                                boolean noClasses = jsonResponse.getBoolean("noClasses");

                                if (success) {


                                    // sends that info to the my classes list

                                    Intent intent = new Intent(UserHomePageActivity.this, MyClassesListActivity.class);

                                    intent.putExtra("user_id", user.getUser_id());
                                    intent.putExtra("class_names", class_names);
                                    intent.putExtra("class_ids", class_ids);
                                    intent.getBooleanExtra("noClasses", noClasses);


                                    UserHomePageActivity.this.startActivity(intent);


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserHomePageActivity.this);
                                    builder.setMessage("List Not Found").setNegativeButton("Retry", null).create().show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    };


                    MyClassListRequest myClassListRequest = new MyClassListRequest(Integer.toString(user.getUser_id()), responseListener);

                    RequestQueue queue = Volley.newRequestQueue(UserHomePageActivity.this);
                    queue.add(myClassListRequest);

                }


            }


        });

        joinedClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (deviceID.compareTo(androidID) != 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Your Account Is Not Registered To This Device, Please Register the Device First",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                // String of joined class names from the server separated with a '$'

                                String class_names = jsonResponse.getString("class_names");

                                // String of joined record ids from the server separated with a '$'

                                String record_ids = jsonResponse.getString("record_ids");

                                // String of joined class ids from the server separated with a '$'

                                String class_ids = jsonResponse.getString("class_ids");

                                // boolean that is true if the current user has joined no classes

                                boolean noClasses = jsonResponse.getBoolean("noClasses");

                                if (success) {


                                    // sends the user with the above data to the joined class list activity

                                    Intent intent = new Intent(UserHomePageActivity.this, JoinedClassesListActivity.class);

                                    intent.putExtra("user_id", user.getUser_id());
                                    intent.putExtra("class_names", class_names);
                                    intent.putExtra("record_ids", record_ids);
                                    intent.putExtra("class_ids", class_ids);
                                    intent.getBooleanExtra("noClasses", noClasses);


                                    UserHomePageActivity.this.startActivity(intent);


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserHomePageActivity.this);
                                    builder.setMessage("List Not Found").setNegativeButton("Retry", null).create().show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    };


                    ClassListRequest classListRequest = new ClassListRequest(Integer.toString(user.getUser_id()), responseListener);

                    RequestQueue queue = Volley.newRequestQueue(UserHomePageActivity.this);
                    queue.add(classListRequest);


                }


            }


        });


        createAClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // checks to see if the device is registered

                if (deviceID.compareTo(androidID) != 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Your Account Is Not Registered To This Device, Please Register the Device First",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    // checks to see if the user has more than 20 classes created

                } else if (toMannyClasses == true) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You have too many classes, please delete some before adding another",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    // sends the user to the create a class activity along with user_id

                } else {
                    Intent intent = new Intent(UserHomePageActivity.this, CreateAClassActivity.class);

                    intent.putExtra("user", user);


                    UserHomePageActivity.this.startActivity(intent);
                }


            }


        });



        // ends the user home page activity and sends the user to the longing activity

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHomePageActivity.this, LoginActivity.class);

                finish();

                UserHomePageActivity.this.startActivity(intent);


            }


        });


    }
}
