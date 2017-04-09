package com.WVU.iAttend;

//package com.simpleware.dale.listviewexample;
          import android.content.Context;
          import android.content.Intent;
          import android.support.v7.app.ActionBarActivity;
          import android.os.Bundle;
          import android.support.v7.app.AppCompatActivity;
          import android.view.LayoutInflater;
          import android.view.Menu;
          import android.view.MenuItem;
          import android.view.View;
          import android.view.ViewGroup;
          import android.widget.AdapterView;
          import android.widget.ArrayAdapter;
          import android.widget.Button;
          import android.widget.ImageView;
          import android.widget.ListView;
          import android.widget.TextView;
          import android.widget.Toast;
          import java.util.ArrayList;
          import java.util.List;



/**
 * Created by dale on 4/9/2017.
 */

 class MyListAdapter extends ArrayAdapter<String> {
            private int layout;
            private List<String> mObjects;
            private MyListAdapter(Context context, int resource, List<String> objects) {
                super(context, resource, objects);
                mObjects = objects;
                layout = resource;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder mainViewholder = null;
                String class_id[][] ;
                int ethan = 69;

                if(convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(layout, parent, false);
                    ViewHolder viewHolder = new ViewHolder();
                    viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                    viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                    convertView.setTag(viewHolder);
                }
                mainViewholder = (ViewHolder) convertView.getTag();
                mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                        // send the id to the next activity
                        Intent intent = new Intent(getContext().getApplicationContext(), UserHomePageActivity.class);
                        intent.putExtra("class_id",class_id);
                        getContext().getApplicationContext().startActivity(intent);
                    }
                });
                mainViewholder.title.setText(getItem(position));

                return convertView;
            }
        }
     class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
    }

