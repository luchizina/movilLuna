package com.example.applunacrowdfunding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class customAdapter extends BaseAdapter {
Context context;
List<String> props = new ArrayList<>();
    LayoutInflater inflter;


    public customAdapter(Context applicationContext, List<String> pro) {
        this.context = context;
        this.props = pro;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
            return props.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView tv = (TextView) view.findViewById(R.id.txtLista);
        tv.setText(props.get(i));
        return view;
    }



}
