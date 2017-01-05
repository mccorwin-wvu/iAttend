package com.WVU.iAttend;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView userHomeTitlev = (TextView) findViewById(R.id.userHomeTitle);
        userHomeTitlev.setTypeface(mytypeface);

        TextView userHomeFirstNamev = (TextView) findViewById(R.id.userHomeFirstName);
        userHomeFirstNamev.setTypeface(mytypeface);

        TextView userHomeLastNamev = (TextView) findViewById(R.id.userHomeLastName);
        userHomeLastNamev.setTypeface(mytypeface);

        TextView userHomeEmailv = (TextView) findViewById(R.id.userHomeEmail);
        userHomeEmailv.setTypeface(mytypeface);


        final EditText userHomeFirstName = (EditText) findViewById(R.id.userHomeFirstName);
        final EditText userHomeLastName = (EditText) findViewById(R.id.userHomeLastName);
        final EditText userHomeEmail = (EditText) findViewById(R.id.userHomeEmail);


        Intent intent = getIntent();
        String first_name = intent.getStringExtra("first_name");
        String last_name = intent.getStringExtra("last_name");
        String email = intent.getStringExtra("email");


        userHomeFirstName.setText(first_name);
        userHomeLastName.setText(last_name);
        userHomeEmail.setText(email);


    }
}
