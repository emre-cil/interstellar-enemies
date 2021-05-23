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
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        } else {
            view = convertView;
        }
        TextView textView = view.findViewById(R.id.listItemText);
        textView.setText(getItem(position).gamesWon.toString() + ": " + getItem(position).name);

        return view;
    }
}
