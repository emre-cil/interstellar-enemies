package com.example.interstellarenemies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

class AnnouncementObject {
    private String header;
    private String content;

    @NonNull
    private final Date date;
    @NonNull
    private final UUID uuid;

    public AnnouncementObject(String header, String content, Date date) {
        this.header = header;
        this.content = content;
        this.uuid = UUID.randomUUID();
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Date getDate() {
        return date;
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
        textView.setText(getItem(position).getHeader());

        return view;
    }
}

public class Announcements extends AppCompatActivity {
    private final LinkedList<AnnouncementObject> annList = new LinkedList<>();
    private final ArrayList<AnnouncementObject> listItems = new ArrayList<>();
    private AnnouncementAdapter adapter;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        ListView lv = findViewById(R.id.Announcements_ListView);
        adapter = new AnnouncementAdapter(this, android.R.layout.simple_list_item_1, listItems);

        lv.setAdapter(adapter);

        //TODO:
        // This is just for testing, but it works
        //  We will create AnnouncementObject(String: header, String: content)
        //  where AnnouncementAdapter only shows header in the list.
        //  but when we click on an item in the list,
        //  it should show content and header. (Not implemented yet.)
        for (int i = 0; i < 1000; i++) {
            annList.add(new AnnouncementObject(i + ": HEADER", i + ": CONTENT", new Date()));
        }

        adapter.addAll(annList);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            AnnouncementObject ao = listItems.get(position);
            //TODO:
            // Show popup information about announcement.
            Toast.makeText(this,
                String.format(
                    "Header: %s\n" +
                    "Content: %s\n" +
                    "UUID: %s\n" +
                    "Date: %s\n",
                    ao.getHeader(),
                    ao.getContent(),
                    ao.getUUID().toString(),
                    ao.getDate().toString()
                ), Toast.LENGTH_SHORT).show();
        });
    }


}