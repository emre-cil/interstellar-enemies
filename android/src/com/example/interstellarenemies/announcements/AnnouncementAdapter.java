package com.example.interstellarenemies.announcements;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;

import com.example.interstellarenemies.R;

import java.util.*;

public class AnnouncementAdapter extends ArrayAdapter<AnnouncementObject> {

    public AnnouncementAdapter(@NonNull Context context, int resource, ArrayList<AnnouncementObject> aoList) {
        super(context, resource, aoList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        } else {
            view = convertView;
        }
        TextView textView = view.findViewById(R.id.listItemText);
        textView.setText(getItem(position).header);

        return view;
    }
}