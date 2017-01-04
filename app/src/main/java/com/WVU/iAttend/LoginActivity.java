package com.WVU.iAttend;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView iAttendTextv = (TextView) findViewById(R.id.iAttendText);
        iAttendTextv.setTypeface(mytypeface);

        TextView logingEmailBoxv = (TextView) findViewById(R.id.logingEmailBox);
        logingEmailBoxv.setTypeface(mytypeface);

        TextView logingPassBoxv = (TextView) findViewById(R.id.logingPassBox);
        logingPassBoxv.setTypeface(mytypeface);

        TextView logingButtonv = (TextView) findViewById(R.id.logingButton);
        logingButtonv.setTypeface(mytypeface);

        TextView logingRegv = (TextView) findViewById(R.id.logingReg);
        logingRegv.setTypeface(mytypeface);

        final EditText loginEmail = (EditText) findViewById(R.id.logingEmailBox);
        final EditText loginPass = (EditText) findViewById(R.id.logingPassBox);
        final Button loginButton = (Button) findViewById(R.id.logingButton);
        final TextView registerText = (TextView) findViewById(R.id.logingReg);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);



            }
        });

    }
}
