package com.example.interstellarenemies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

class JoinListObject {
    @NonNull
    private final Date createDate;
    @NonNull
    private final Integer maxPlayers;
    @NonNull
    private final UUID uuid;
    private String header;
    private String content;
    private Integer currPlayers;

    public JoinListObject(String header, String content, Date date, Integer maxPlayers) {
        this.header = header;
        this.content = content;
        this.maxPlayers = maxPlayers;
        this.currPlayers = 0;
        this.uuid = UUID.randomUUID();
        this.createDate = date;
    }

    public String getHeader() {
        return header;
    }

    public Integer getCurrPlayers() {
        return currPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateDate() {
        return createDate;
    }
}

class JoinListAdapter extends ArrayAdapter<JoinListObject> {

    public JoinListAdapter(@NonNull Context context, int resource, ArrayList<JoinListObject> jloList) {
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
        textView.setText(String.format("%d/%d: %s", jlo.getCurrPlayers(), jlo.getMaxPlayers(), jlo.getHeader()));

        return view;
    }
}

