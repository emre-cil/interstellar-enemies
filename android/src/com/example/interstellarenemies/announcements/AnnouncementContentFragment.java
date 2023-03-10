package com.example.interstellarenemies.announcements;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.interstellarenemies.R;
import com.google.firebase.database.*;

public class AnnouncementContentFragment extends Fragment {
    private DatabaseReference dbRef;

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        contentView.setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container, new AnnouncementFragment()).commit();
        });

        headerView.setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container, new AnnouncementFragment()).commit();
        });

        return ret_view;
    }
}