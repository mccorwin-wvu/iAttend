package com.WVU.iAttend;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView regFirstNamev = (TextView) findViewById(R.id.regFirstName);
        regFirstNamev.setTypeface(mytypeface);

        TextView regLastNamev = (TextView) findViewById(R.id.regLastName);
        regLastNamev.setTypeface(mytypeface);

        TextView regEmailv = (TextView) findViewById(R.id.regEmail);
        regEmailv.setTypeface(mytypeface);

        TextView regPasswordv = (TextView) findViewById(R.id.regPassword);
        regPasswordv.setTypeface(mytypeface);

        TextView regPasswordConv = (TextView) findViewById(R.id.regPasswordCon);
        regPasswordConv.setTypeface(mytypeface);

        TextView regTextv = (TextView) findViewById(R.id.regText);
        regTextv.setTypeface(mytypeface);

        TextView regButtonv = (TextView) findViewById(R.id.regButton);
        regButtonv.setTypeface(mytypeface);


        final EditText firstNameText = (EditText) findViewById(R.id.regFirstName);
        final EditText lastNameText = (EditText) findViewById(R.id.regLastName);
        final EditText emailText = (EditText) findViewById(R.id.regEmail);
        final EditText passText = (EditText) findViewById(R.id.regPassword);
        final EditText passConText = (EditText) findViewById(R.id.regPasswordCon);

        final Button buttonRegister = (Button) findViewById(R.id.regButton);

    }
}
