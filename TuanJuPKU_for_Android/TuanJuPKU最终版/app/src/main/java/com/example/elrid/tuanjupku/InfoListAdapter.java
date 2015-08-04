package com.example.elrid.tuanjupku;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class InfoListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Object> movieList;
    File cache;

    public InfoListAdapter(Activity activity, List<Object> movieList, File f) {
        this.activity = activity;
        this.movieList = movieList;
        cache = f;
    }



    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int location) {
        return movieList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) instanceof Movie ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position) instanceof Movie;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Object item = getItem(position);
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (item instanceof Category) {
            if (v == null) {
                v =  inflater.inflate(R.layout.catelog, null);
            }
            TextView cat = (TextView) v.findViewById(R.id.cate);
            cat.setText(((Category) item).mTitle);

        } else if(item  instanceof Movie){
            if (v == null) {
                v = inflater.inflate(R.layout.info_row, null);
            }


            TextView title = (TextView) v.findViewById(R.id.ItemTitle);
            TextView content = (TextView) v.findViewById(R.id.ItemContent);

            title.setText(String.valueOf(((Movie)(movieList.get(position))).title));
            content.setText(((Movie)(movieList.get(position))).content);
        }
        //imgView.setImageResource(R.drawable.image_1);


        return v;
    }



}