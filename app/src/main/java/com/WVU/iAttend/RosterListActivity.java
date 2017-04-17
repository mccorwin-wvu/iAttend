package com.WVU.iAttend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class RosterListActivity extends AppCompatActivity {
    private int user_id;
    private String record_ids;
    private String first_names;
    private String last_names;
    private String emails;
    private boolean noPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_list);

        ListView lv = (ListView) findViewById(R.id.rosterListView);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id",0);
        record_ids = intent.getStringExtra("record_ids");
        first_names = intent.getStringExtra("first_names");
        last_names = intent.getStringExtra("last_names");
        emails = intent.getStringExtra("emails");
        noPeople = intent.getBooleanExtra("noPeople", noPeople);



        if(noPeople == true){


            final ArrayList<String> personNameArray = new ArrayList<String>();
            final ArrayList<String> recordIDArray = new ArrayList<String>();

            personNameArray.add("No Classes");


            MyListAdapter gg = new MyListAdapter(this, R.layout.list_item, personNameArray);
            lv.setAdapter(gg);




        }

        else {

            final ArrayList<String> firstNameArrayList = new ArrayList<>(Arrays.asList(first_names.split("\\$")));
            final ArrayList<String> lastNameArrayList = new ArrayList<>(Arrays.asList(last_names.split("\\$")));
            final ArrayList<String> emailArrayList = new ArrayList<>(Arrays.asList(emails.split("\\$")));

            final ArrayList<String> personNameArray = new ArrayList<>();
            final ArrayList<String> recordIDArray = new ArrayList<>(Arrays.asList(record_ids.split("\\$")));


            for(int i = 0; i<firstNameArrayList.size(); i++){

                personNameArray.add(lastNameArrayList.get(i)+", "+firstNameArrayList.get(i)+", "+emailArrayList.get(i));


            }









            MyListAdapter gg = new MyListAdapter(this, R.layout.list_item, personNameArray);
            lv.setAdapter(gg);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    if (firstNameArrayList.get(position) == null) {
                        Toast toast = Toast.makeText(getApplicationContext(), "This person was deleted.",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        return;
                    } else {




                        Intent intent = new Intent(RosterListActivity.this, RosterMenuActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("first_name", firstNameArrayList.get(position));
                        intent.putExtra("last_name", lastNameArrayList.get(position));
                        intent.putExtra("email", emailArrayList.get(position));
                        intent.putExtra("record_id", recordIDArray.get(position));


                        RosterListActivity.this.startActivity(intent);
                    }

                }
            });

        }


















    }
}
