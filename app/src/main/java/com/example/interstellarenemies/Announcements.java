package com.example.interstellarenemies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class AnnouncementObject {
    public String header;
    public String content;
    public String date;
    public String id;

    public AnnouncementObject(String id, String header, String content, String date) {
        this.id = id;
        this.header = header;
        this.content = content;
        this.date = date;
    }
}


class AnnouncementAdapter extends ArrayAdapter<AnnouncementObject> {

    public AnnouncementAdapter(@NonNull Context context, int resource, ArrayList<AnnouncementObject> aoList) {
        super(context, resource, aoList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }
        TextView textView = (TextView) view;
        textView.setText(getItem(position).header);

        return view;
    }
}