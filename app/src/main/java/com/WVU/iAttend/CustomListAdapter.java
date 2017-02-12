package com.WVU.iAttend;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.WVU.iAttend.R;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int id;
    private List <String>items ;

    public CustomListAdapter(Context context, int textViewResourceId , List<String> list )
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.listTextViewUserHome);
        Typeface mytypeface = Typeface.createFromAsset(getContext().getAssets(),"Minecraftia-Regular.ttf");

        if(items.get(position) != null )
        {
            text.setTextColor(Color.RED);
            text.setText(items.get(position));

            text.setTypeface(mytypeface);



        }

        return mView;
    }

}