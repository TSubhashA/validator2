package com.nextgentele.busvalidatorv2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nextgentele.busvalidatorv2.R;

import java.util.List;

public class MySpinnerAdapter extends ArrayAdapter<String> {
    List<String> objects;
    Context context;

    public MySpinnerAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.objects=objects;
        this.context=context;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);

    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.custom_spinner, parent, false);

        TextView label = (TextView) row.findViewById(R.id.text_spinner);

        label.setText(objects.get(position));

        return row;

    }

}