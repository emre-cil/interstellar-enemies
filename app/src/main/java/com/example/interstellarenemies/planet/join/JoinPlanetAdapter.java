package com.example.interstellarenemies.planet.join;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import java.util.ArrayList;

public class JoinPlanetAdapter extends ArrayAdapter<JoinListObject> {

    public JoinPlanetAdapter(@NonNull Context context, int resource, ArrayList<JoinListObject> jloList) {
        super(context, resource, jloList);
    }

    @SuppressLint("DefaultLocale")
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
        JoinListObject jlo = getItem(position);
        textView.setText(String.format("%d/%s: %s", jlo.users.size(), jlo.max_users, jlo.name));

        return view;
    }
}

