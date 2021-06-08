package com.example.interstellarenemies.friends;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.interstellarenemies.R;

import java.util.ArrayList;

public class FriendsAdapter  extends ArrayAdapter<FriendsObject> {
    public FriendsAdapter(@NonNull Context context, int resource, ArrayList<FriendsObject> frList) {
        super(context, resource, frList);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_image_item, parent, false);
        } else {
            view = convertView;
        }
        ImageView imageView = view.findViewById(R.id.ofllineImage);
        TextView textView = view.findViewById(R.id.friendsTextView);
        FriendsObject fo = getItem(position);
        System.out.println(fo.status+"asdfffffffffffffffffff");
        if (fo.status.equals("online"))
            imageView.setImageResource(R.drawable.online);
        else
        imageView.setImageResource(R.drawable.offline);
        textView.setText(getItem(position).username);

        return view;
    }
}
