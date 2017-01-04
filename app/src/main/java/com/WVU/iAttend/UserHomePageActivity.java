package com.WVU.iAttend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class UserHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        final EditText emailTextHome = (EditText) findViewById(R.id.userHomeEmail);
        final EditText passTextHome = (EditText) findViewById(R.id.userHomePass);
    }
}
