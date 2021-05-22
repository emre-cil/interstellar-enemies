package com.example.interstellarenemies.invite;

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
import com.example.interstellarenemies.friends.FriendsObject;

import java.util.ArrayList;


public class InvitesAdapter extends ArrayAdapter<InvitesObject> {
    public InvitesAdapter(@NonNull Context context, int resource, ArrayList<InvitesObject> InvList) {
        super(context, resource, InvList);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_invite_item, parent, false);
        } else {
            view = convertView;
        }
        ImageView accept = view.findViewById(R.id.accept);
        ImageView reject = view.findViewById(R.id.reject);
        TextView textView = view.findViewById(R.id.invitesTextView);

        textView.setText(getItem(position).username);

        return view;
    }
}
