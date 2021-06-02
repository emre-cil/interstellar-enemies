package com.example.interstellarenemies.leaderboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.interstellarenemies.R;

import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<LeaderboardObject> {
    public LeaderboardAdapter(@NonNull Context context, int resource, @NonNull List<LeaderboardObject> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_item, parent, false);
        } else {
            view = convertView;
        }
        TextView name = view.findViewById(R.id.listItemText);
        TextView score = view.findViewById(R.id.listItemText2);
        name.setText(getItem(position).name);
        score.setText(getItem(position).score.toString() );
        return view;
    }
}
