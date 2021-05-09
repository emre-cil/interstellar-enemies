package com.example.interstellarenemies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class AnnouncementFragment extends Fragment {

    private final LinkedList<AnnouncementObject> annList = new LinkedList<>();
    private final ArrayList<AnnouncementObject> listItems = new ArrayList<>();
    private AnnouncementAdapter adapter;
    private DatabaseReference dbRef;
    private ListView mListView;

    public AnnouncementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret_view = inflater.inflate(R.layout.fragment_announcement, container, false);
        mListView = (ListView) ret_view.findViewById(R.id.Announcements_ListView);
        dbRef = FirebaseDatabase.getInstance().getReference("announcements/");
        adapter = new AnnouncementAdapter(getActivity(), android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                while (!annList.isEmpty()) {
                    annList.remove();
                }
                adapter.clear();
                for (DataSnapshot announcementTable : snapshot.getChildren()) {
                    String date = "", id, content = "", header = "";
                    for (DataSnapshot elem : announcementTable.getChildren()) {
                        switch (elem.getKey()) {
                            case "content":
                                content = elem.getValue().toString();
                                break;
                            case "header":
                                header = elem.getValue().toString();
                                break;
                            case "date":
                                date = elem.getValue().toString();
                                break;
                        }
                    }
                    id = announcementTable.getKey();
                    annList.add(new AnnouncementObject(id, header, content, date));
                }
                adapter.addAll(annList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for (AnnouncementObject ao : annList) {
            System.out.printf("id: %s\ndate: %s\ncontent: %s\nheader: %s\n", ao.id, ao.date, ao.content, ao.header);
        }

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            getActivity().getIntent().putExtra("fragment::announcement::content::id", annList.get(position).id);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnnouncementContentFragment()).commit();
        });

        return ret_view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}