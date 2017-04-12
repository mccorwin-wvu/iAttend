package com.WVU.iAttend;

//package com.simpleware.dale.listviewexample;
          import android.content.Context;
          import android.content.Intent;
          import android.content.res.TypedArray;
          import android.graphics.Color;
          import android.graphics.Typeface;
          import android.support.v7.app.ActionBarActivity;
          import android.os.Bundle;
          import android.support.v7.app.AppCompatActivity;
          import android.util.TypedValue;
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

          import static java.security.AccessController.getContext;


/**
 * Created by dale on 4/9/2017.
 */

 class MyListAdapter extends ArrayAdapter<String> {
            private int layout;
            private List<String> mObjects;
            public MyListAdapter(Context context, int resource, List<String> objects) {
                super(context, resource, objects);
                mObjects = objects;
                layout = resource;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder mainViewholder = null;
                Typeface mytypeface = Typeface.createFromAsset(getContext().getAssets(),"Minecraftia-Regular.ttf");

                if(convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(layout, parent, false);
                    ViewHolder viewHolder = new ViewHolder();

                    viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);

                    convertView.setTag(viewHolder);
                }
                mainViewholder = (ViewHolder) convertView.getTag();
//
                mainViewholder.title.setText(getItem(position));
                mainViewholder.title.setTypeface(mytypeface);


                return convertView;
            }
        }
     class ViewHolder {


         TextView title;
     }




