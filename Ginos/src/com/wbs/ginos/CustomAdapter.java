package com.wbs.ginos;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Image
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.custom_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView details = (TextView)vi.findViewById(R.id.details); // details
        TextView price = (TextView)vi.findViewById(R.id.price); // price
     //   ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

         /*

        // Setting all values in listview
       title.setText(data.get(position).get(FetchSelCatActivity.KEY_TITLE));
        details.setText(data.get(position).get(FetchSelCatActivity.KEY_DETAILS));
        price.setText(data.get(position).get(FetchSelCatActivity.KEY_PRICE));  */
        //Code for image
        return vi;
    }
}