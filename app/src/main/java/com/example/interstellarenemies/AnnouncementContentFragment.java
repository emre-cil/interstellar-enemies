package com.example.interstellarenemies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnnouncementContentFragment extends Fragment {
    DatabaseReference dbRef;

    public AnnouncementContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret_view = inflater.inflate(R.layout.fragment_announcement_content, container, false);

        String id = getActivity().getIntent().getStringExtra("fragment::announcement::content::id");
        dbRef = FirebaseDatabase.getInstance().getReference("announcements/" + id);

        TextView headerView = ret_view.findViewById(R.id.ann_content_header);
        TextView contentView = ret_view.findViewById(R.id.ann_content_content);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot table : snapshot.getChildren()) {
                    switch (table.getKey()) {
                        case "header":
                            headerView.setText((String) table.getValue());
                            break;
                        case "content":
                            contentView.setText((String) table.getValue());
                            break;
                    }
                    System.out.println("announcementTable.key => " + table.getKey() + "\nvalue: " + table.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        contentView.setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnnouncementFragment()).commit();
        });

        headerView.setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnnouncementFragment()).commit();
        });

        return ret_view;
    }
}