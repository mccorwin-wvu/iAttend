package com.WVU.iAttend;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Created by Ethan on 3/10/17.
 */

public class MyClassesActivity extends AppCompatActivity {
    private int user_id_home;
    private ListView classListView;
    public void onBackPressed(){
        Intent intent = new Intent(MyClassesActivity.this, UserHomePageActivity.class);
        intent.putExtra("user_id", user_id_home);
        MyClassesActivity.this.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        String[] classes = {"Class 1", "Class 2", "Nonsense", "Worthless"};
        //CustomListAdapter <- check how this works in project to get getListActivity working
        }

}
