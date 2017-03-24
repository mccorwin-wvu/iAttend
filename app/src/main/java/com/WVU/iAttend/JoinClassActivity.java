package com.WVU.iAttend;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinClassActivity extends AppCompatActivity {


    private int user_id_home;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(JoinClassActivity.this, UserHomePageActivity.class);
        intent.putExtra("user_id", user_id_home);
        JoinClassActivity.this.startActivity(intent);

        //super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);




        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView JoinClassBoxv = (TextView) findViewById(R.id.JoinClassBox);
        JoinClassBoxv.setTypeface(mytypeface);

        TextView JoinClassButtonv = (TextView) findViewById(R.id.JoinClassButton);
        JoinClassButtonv.setTypeface(mytypeface);

        TextView JoinClassTextv = (TextView) findViewById(R.id.JoinClassText);
        JoinClassTextv.setTypeface(mytypeface);

        Intent intent = getIntent();

        final int user_id = intent.getIntExtra("user_id", 0);
        final String first_name = intent.getStringExtra("first_name");
        final String last_name = intent.getStringExtra("last_name");
        final String email = intent.getStringExtra("email");
        final String user_class_list = intent.getStringExtra("user_class_list");

        user_id_home = user_id;

        Button joinClassButton = (Button) findViewById(R.id.JoinClassButton);

        final EditText joinClassBox = (EditText) findViewById(R.id.JoinClassBox);


        joinClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String joinCode = joinClassBox.getText().toString();

                if(joinCode.length()<=0){

                    Toast toast = Toast.makeText(getApplicationContext(), "Need to Enter a Join Code.",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;

                }









                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");
                            boolean alreadyJoined = jsonResponse.getBoolean("alreadyJoined");
                            boolean classNotFound = jsonResponse.getBoolean("classNotFound");


                            if(success){

                                Toast toast = Toast.makeText(getApplicationContext(), "Class Joined.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent intent = new Intent(JoinClassActivity.this, UserHomePageActivity.class);

                                intent.putExtra("user_id", user_id);




                                JoinClassActivity.this.startActivity(intent);




                            }

                            else if(alreadyJoined){


                                Toast toast = Toast.makeText(getApplicationContext(), "Class Already Joined.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();





                            }

                            else if(classNotFound){


                                Toast toast = Toast.makeText(getApplicationContext(), "Class Not Found.",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }



                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinClassActivity.this);
                                builder.setMessage("Something Went Wrong").setNegativeButton("Retry",null).create().show();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();

                        }


                    }
                };





                JoinClassRequest joinClassRequest  = new JoinClassRequest(Integer.toString(user_id), first_name, last_name, email, joinCode, user_class_list, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinClassActivity.this);
                queue.add(joinClassRequest);









            }
        });














    }
}
